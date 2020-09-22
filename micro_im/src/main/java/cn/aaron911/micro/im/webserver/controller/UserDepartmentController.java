package cn.aaron911.micro.im.webserver.controller;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.db.entity.UserDepartment;
import cn.aaron911.micro.im.db.service.IUserDepartmentService;
import cn.aaron911.micro.im.webserver.util.Query;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门
 *
 */
@Controller
@RequestMapping("userdepartment")
public class UserDepartmentController {

	@Autowired
	private IUserDepartmentService userDepartmentService;
	
	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params){
		return "userdepartment";
	}
	/**
	 * 列表
	 */
	@RequestMapping(value="/list", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params){
	    Query query = new Query(params);
		List<UserDepartment> userDepartmentList = userDepartmentService.queryList(query);
		int total = userDepartmentService.queryTotal(query);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",total,userDepartmentList);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping(value="/info/{id}", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object info(@PathVariable("id") Long id){
		UserDepartment userDepartment = userDepartmentService.queryObject(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userDepartment);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="/save", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@ModelAttribute UserDepartment userDepartment){
		userDepartmentService.save(userDepartment);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",0,userDepartment);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/update", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute UserDepartment userDepartment){
		int result = userDepartmentService.update(userDepartment);
		return putMsgToJsonString(result,"",0,"");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestParam Long[] ids){
		int result = userDepartmentService.deleteBatch(ids);
		return putMsgToJsonString(result,"",0,"");
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
