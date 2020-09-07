package cn.aaron911.micro.base.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.base.pojo.Label;
import cn.aaron911.micro.base.service.LabelService;
import cn.aaron911.micro.common.result.PageResult;
import cn.aaron911.micro.common.result.Result;

/**
 * 标签控制层
 * 
 * 跨域是什么？浏览器从一个域名的网页去请求另一个域名的资源时，域名、端口、 协议任一不同，都是跨域 。我们是采用前后端分离开发的，也是前后端分离部署的，必
 * 然会存在跨域问题。 怎么解决跨域？很简单，只需要在controller类上添加注解 @CrossOrigin 即可！
 * 这个注解其实是CORS的实现。 ​ CORS(Cross-Origin Resource Sharing,跨源资源共享)是W3C出的一个标准，
 * 其思 想是使用自定义的HTTP头部让浏览器与服务器进行沟通，从而决定请求或响应是应该成功，还是应该失败。
 * 因此，要想实现CORS进行跨域，需要服务器进行一些设置，同时前端也需要做一些配置和分析。本文简单的对服务端的配置和前端的一些设置进行分析。
 * 
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelService labelService;

	/**
	 * 查询全部列表
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return Result.ok(labelService.findAll());
	}

	/***
	 * 根据ID查询标签
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result<Label> findById(@PathVariable String id) {
		return Result.ok(labelService.findById(id));
	}

	/**
	 * 增加标签
	 * 
	 * @param label
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Label label) {
		labelService.add(label);
		return Result.ok("增加成功");
	}

	/**
	 * 修改标签
	 * 
	 * @param label
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Label label, @PathVariable String id) {
		label.setId(id);
		labelService.update(label);
		return Result.ok("修改成功");
	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result deleteById(@PathVariable String id) {
		labelService.deleteById(id);
		return Result.ok("删除成功");
	}

	/**
	 * 根据条件查询
	 * 
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result<List> findSearch(@RequestBody Map searchMap) {
		return Result.ok(labelService.findSearch(searchMap));
	}

	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result<PageResult> findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<?> pageList = labelService.findSearch(searchMap, page, size);
		return Result.ok(new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
	}

}
