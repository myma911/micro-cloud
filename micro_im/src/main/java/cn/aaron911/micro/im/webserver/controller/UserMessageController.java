package cn.aaron911.micro.im.webserver.controller;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.db.entity.UserMessage;
import cn.aaron911.micro.im.db.service.IUserMessageService;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "用户信息")
@Controller
@RequestMapping("usermessage")
public class UserMessageController  {

	@Autowired
	private IUserMessageService userMessageService;
	


	@ApiOperation("用户信息列表")
	@GetMapping(value="/list")
	public Object list(){
		final List<UserMessage> list = userMessageService.list();
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",list.size(),list);
	}


	@ApiOperation("根据信息id查询信息")
	@GetMapping(value="/info/{id}")
	public Object info(@PathVariable("id") Long id){
		final UserMessage userMessage = userMessageService.getById(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",1,userMessage);
	}

	@ApiOperation("保存")
	@PostMapping(value="/save")
	public Object save(@RequestBody UserMessage userMessage){
		userMessageService.save(userMessage);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",1,userMessage);
	}

	@ApiOperation("修改")
	@PutMapping(value="/update")
	public Object update(@RequestBody UserMessage userMessage){
		userMessageService.updateById(userMessage);
		return putMsgToJsonString(1,"",0,"");
	}

	@ApiOperation("删除")
	@RequestMapping(value="/delete")
	public Object delete(@RequestBody Long[] ids){
		final boolean b = userMessageService.removeByIds(CollUtil.toList(ids));
		return putMsgToJsonString(0,"",0,b);
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
