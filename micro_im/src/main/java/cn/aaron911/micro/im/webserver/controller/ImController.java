package cn.aaron911.micro.im.webserver.controller;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.db.dto.ImFriendUserData;
import cn.aaron911.micro.im.db.dto.ImFriendUserInfoData;
import cn.aaron911.micro.im.db.dto.ImGroupUserData;
import cn.aaron911.micro.im.db.dto.ImUserData;
import cn.aaron911.micro.im.db.entity.UserAccount;
import cn.aaron911.micro.im.db.entity.UserInfo;
import cn.aaron911.micro.im.db.entity.UserMessage;
import cn.aaron911.micro.im.db.service.IUserAccountService;
import cn.aaron911.micro.im.db.service.IUserDepartmentService;
import cn.aaron911.micro.im.db.service.IUserMessageService;
import cn.aaron911.micro.im.server.proxy.IMessageProxy;
import cn.aaron911.micro.im.server.session.ISessionManager;
import cn.aaron911.micro.im.webserver.util.FilesInfo;
import cn.aaron911.micro.im.webserver.util.Query;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class ImController {

	@Autowired
	private ISessionManager sessionManager;

	@Autowired
	private IUserAccountService userAccountServiceImpl;

	@Autowired
	private IUserDepartmentService userDepartmentServiceImpl;


	@Autowired
	private IUserMessageService userMessageServiceImpl;

	@Autowired
	private IMessageProxy proxy;
	
	/**
	 * 单聊
	 */
	@RequestMapping("/chat")
	public String chat(@RequestParam Map<String, Object> params,HttpServletRequest request){
		request.setAttribute("allsession", sessionManager.getSessions());
		return "chat";
	}
	/**
	 * 群聊
	 */
	@RequestMapping("/groupchat")
	public String group(@RequestParam Map<String, Object> params,HttpServletRequest request){
		request.setAttribute("allsession", sessionManager.getSessions());
		return "groupchat";
	}
	/**
	 * 机器人
	 */
	@RequestMapping("/bot")
	public String list(@RequestParam Map<String, Object> params,HttpServletRequest request){
		request.setAttribute("allsession", sessionManager.getSessions());
		return "bot";
	}
	
	/**
	 * 登录IM
	 */
	@RequestMapping("/login")
	public String login(@RequestParam Map<String, Object> params,HttpServletRequest request){
		Query query = new Query(params);
		UserAccount userAccount = userAccountServiceImpl.getById(1);
		if(userAccount!=null){
			setLoginUser(userAccount);
			String template = check(request);
			if(template.equals(Constants.ViewTemplateConfig.mobiletemplate)){
				return "layimmobile";
			}
			return "layim";
		}
		return "redirect:login.jsp";
	}
	
	
	/** 
	 *  取得所有聊天用户
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getusers", produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getAllUser(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		    // 数据格式请参考文档  http://www.layui.com/doc/modules/layim.html
		final UserAccount loginUser = getLoginUser();
		if(loginUser !=null){

				//TO-DO 此处留坑
				UserInfo user = null;

				ImFriendUserInfoData my = new ImFriendUserInfoData();
				my.setId(user.getUid());
				my.setAvatar(user.getProfilephoto());
				my.setSign(user.getSignature());
				my.setUsername(user.getName());
				my.setStatus("online");
				
				//模拟群信息
				ImGroupUserData group = new ImGroupUserData();
				group.setAvatar("http://tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg");
				group.setId(1L);
				group.setGroupname("公司群");
				List<ImGroupUserData>  groups = new ArrayList<ImGroupUserData>();
				groups.add(group);
				
				Map map = new HashMap();
				ImUserData us = new ImUserData();
				us.setCode("0");
				us.setMsg("");
				map.put("mine", my);
				map.put("group",groups);
				//获取用户分组 及用户
				List<ImFriendUserData> friends = userDepartmentServiceImpl.queryGroupAndUser();
				map.put("friend",friends);
				us.setData(map);
				return JSONArray.toJSON(us);
			}else{
				return JSONArray.toJSON("");
			}
	}
	
	
	/** 
	 * 图片上传
	 * @param file
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgupload", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadImgFile(@RequestParam MultipartFile  file,
			HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception {
		
		    UserAccount u = getLoginUser();
			Long uid = u.getId();
		 	String path=request.getSession().getServletContext().getRealPath("upload/img/temp/");
		 	String files = FilesInfo.savePicture(file,uid.toString()+UUID.randomUUID().toString(), path);
		 	Map<String,Object> map = new HashMap<String,Object>();
		 	 Map<String,String> submap = new HashMap<String,String>();
			 if(files.length()>0){
					map.put("code","0");
					map.put("msg", "上传过成功");  
				    submap.put("src", request.getContextPath()+"/upload/img/temp/"+files+"?" + System.currentTimeMillis());
			  }else{
				  submap.put("src", "");
				  map.put("code","1");
				  map.put("msg", "上传过程中出现错误，请重新上传");  
			  }
			  map.put("data",submap);
			  return  JSONArray.toJSON(map);
	}
	
	
	/** 
	 * 文件上传
	 * @param file
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileupload", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadAllFile(@RequestParam MultipartFile  file,
			HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception {
		
		    UserAccount  u = getLoginUser();
			Long uid = u.getId();
		 	String path=request.getSession().getServletContext().getRealPath("upload/file/temp/");
		 	String files = FilesInfo.saveFiles(file, uid.toString()+UUID.randomUUID().toString(), path);
		 	Map<String,Object> map = new HashMap<String,Object>();
		 	Map<String,String> submap = new HashMap<String,String>();
			 if(files.length()>0){
					map.put("code","0");
					map.put("msg", "上传过成功");  
				    submap.put("src", request.getContextPath()+"/upload/file/temp/"+files+"?" + System.currentTimeMillis());
				    submap.put("name", file.getOriginalFilename());
			  }else{
				  submap.put("src", "");
				  map.put("code","1");
				  map.put("msg", "上传过程中出现错误，请重新上传");  
			  }
			  map.put("data",submap);
			  return  JSONArray.toJSON(map);
	}
	
	/**
	 * 模拟最新系统消息
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String userMessage(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		
		List<UserMessage>  list = new ArrayList<UserMessage>();
		UserMessage  msg = new UserMessage();
		msg.setContent("模拟系统消息");
		msg.setCreatedate(new Date());
		list.add(msg);
		UserMessage  msgTwo = new UserMessage();
		msgTwo.setContent("模拟系统消息1");
		msgTwo.setCreatedate(new Date());
		list.add(msgTwo);
		request.setAttribute("msgList", list);
		return "message";
	}
	/**
	 * 取得离线消息
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getofflinemsg", produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public Object userMessageCount(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
//		if(getLoginUser()!=null){
//			map.put("receiveuser", getLoginUser().getId().toString());
//		}else{
//			map.put("receiveuser", request.getSession().getId());
//		}
		List<UserMessage> list = userMessageServiceImpl.getOfflineMessageList(map);
		return JSONArray.toJSON(list);
	} 
	
	/**
	 * 聊天记录
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/historymessageajax", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object userHistoryMessages(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
//		map.put("page", getSkipToPage());
//		map.put("limit", getPageSize());
//		map.put("senduser", getLoginUser().getId());
		map.put("receiveuser", Long.parseLong(request.getParameter("id")));
		List<UserMessage> list = userMessageServiceImpl.getHistoryMessageList(new Query(map));
		Map<String,List<UserMessage>>  resultMap = new HashMap();
		resultMap.put("data", list);
		return JSONArray.toJSON(resultMap);
	}
	
	/**
	 * 聊天记录页面
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/historymessage", method = RequestMethod.GET)
	public String userHistoryMessagesPage(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
//		map.put("senduser", getLoginUser().getId());
//		map.put("receiveuser", Long.parseLong(request.getParameter("id")));
//		int totalsize = userMessageServiceImpl.getHistoryMessageCount(map);
//		Pager pager = new Pager(getSkipToPage(),getPageSize(),totalsize);
//		request.setAttribute("pager", pager);
		return "/historymessage";
	} 
	
	
	@RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
	@ResponseBody
	public Object sendMsg(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		String sessionid = request.getSession().getId();
		if(getLoginUser()!=null){
			sessionid = getLoginUser().getId().toString();
		}
		MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
        builder.setCmd(Constants.CmdType.MESSAGE);
        builder.setSender(sessionid);
        builder.setReceiver((String)request.getParameter("receiver"));
        builder.setMsgtype(Constants.ProtobufType.REPLY);
        MessageBodyProto.MessageBody.Builder  msg =  MessageBodyProto.MessageBody.newBuilder();
        msg.setContent((String)request.getParameter("content"));
        builder.setContent(msg.build().toByteString());
        MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionid, builder.build());
		dwrConnertorImpl.pushMessage(sessionid, wrapper);
		return JSONArray.toJSON("");
	}
	
	 
	
}
