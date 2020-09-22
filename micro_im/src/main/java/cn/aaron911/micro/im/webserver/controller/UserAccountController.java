package cn.aaron911.micro.im.webserver.controller;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.db.entity.UserAccount;
import cn.aaron911.micro.im.db.service.IUserAccountService;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户帐号

 */
@Controller
@RequestMapping("useraccount")
public class UserAccountController {

	@Autowired
	private IUserAccountService userAccountService;
	
	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params){
		return "useraccount";
	}
	/**
	 * 列表
	 */
	@RequestMapping(value="/list", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params){
//	    Query query = new Query(params);
//		List<UserAccount> userAccountList = userAccountService.queryList(query);
//		int total = userAccountService.queryTotal(query);
//		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",total,userAccountList);


		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,null);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping(value="/info/{id}", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object info(@PathVariable("id") Long id){
//		UserAccount userAccount = userAccountService.queryObject(id);
//		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userAccount);

		final UserAccount userAccount = userAccountService.getById(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userAccount);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="/save", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@ModelAttribute UserAccount userAccount){
		userAccount.setDisablestate(0);
		userAccount.setIsdel(0);
		userAccountService.save(userAccount);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userAccount);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/update", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute UserAccount userAccount){
//		int result = userAccountService.update(userAccount);
//		return putMsgToJsonString(result,"",0,"");


		userAccountService.updateById(userAccount);
		return putMsgToJsonString(1,"",0,"");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestParam Long[] ids){
//		int result = userAccountService.deleteBatch(ids);
//		return putMsgToJsonString(result,"",0,"");

		userAccountService.removeByIds(CollUtil.toList(ids));
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
