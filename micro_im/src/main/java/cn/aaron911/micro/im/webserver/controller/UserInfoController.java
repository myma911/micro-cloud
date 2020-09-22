package cn.aaron911.micro.im.webserver.controller;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.db.entity.UserInfo;
import cn.aaron911.micro.im.db.service.IUserInfoService;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户信息表
 *
 */
@Controller
@RequestMapping("userinfo")
public class UserInfoController  {
	@Autowired
	private IUserInfoService userInfoService;
	
	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params){
		return "userinfo";
	}
	/**
	 * 列表
	 */
	@RequestMapping(value="/list", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params){
//	    Query query = new Query(params);
//		List<UserInfo> userInfoList = userInfoService.queryList(query);
//		int total = userInfoService.queryTotal(query);
//		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",total,userInfoList);

		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",1,null);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping(value="/info/{id}", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object info(@PathVariable("id") Long id){
//		UserInfo userInfo = userInfoService.queryObject(id);
//		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userInfo);

		final UserInfo userInfo = userInfoService.getById(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="/save", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@ModelAttribute UserInfo userInfo){
		userInfoService.save(userInfo);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userInfo);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/update", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute UserInfo userInfo){
//		int result = userInfoService.update(userInfo);
//		return putMsgToJsonString(result,"",0,"");

		userInfoService.updateById(userInfo);
		return putMsgToJsonString(1,"",0,"");

	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestParam Long[] ids){
//		int result = userInfoService.deleteBatch(ids);
//		return putMsgToJsonString(result,"",0,"");

		userInfoService.removeByIds(CollUtil.toList(ids));
		return putMsgToJsonString(1,"",0,"");
	}


	public Object putMsgToJsonString(int code,String msg,int count ,Object data){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("count", count);
		map.put("data", data);
		return JSONArray.toJSON(map);
	}
	
}
