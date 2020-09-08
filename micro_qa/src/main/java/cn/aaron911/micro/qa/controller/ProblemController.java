package cn.aaron911.micro.qa.controller;

import cn.aaron911.micro.common.annotation.LoginUser;
import cn.aaron911.micro.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.annotation.Login;
import cn.aaron911.micro.common.result.PageResult;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.qa.client.LabelClient;
import cn.aaron911.micro.qa.pojo.Problem;
import cn.aaron911.micro.qa.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 问答
 */
@Api(tags="问答 HTTP 服务")
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private LabelClient labelClient;

    @ApiOperation("根据labelid查询newlist")
    
    
    
    @GetMapping(value="/newlist/{labelid}")
    public Result<PageResult<Problem>> findNewListByLabelId(
    	@PathVariable String labelid,
    	@RequestParam int page,
    	@RequestParam int size 
	){
        Page<Problem> pageList = problemService.findNewListByLabelId(labelid, page, size);
        PageResult<Problem> pageResult = new PageResult<> (pageList.getTotalElements(), pageList.getContent());
        return Result.ok(pageResult);
    }

    /**
     * 根据标签ID查询热门问题列表
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("根据labelid查询hotlist")
    @GetMapping(value="/hotlist/{labelid}")
    public Result<PageResult<Problem>> findHotListByLabelId(
		@PathVariable String labelid,
		@RequestParam int page,
		@RequestParam int size 
	){
        Page<Problem> pageList = problemService.findHotListByLabelId(labelid, page, size);
        PageResult<Problem> pageResult = new PageResult<> (pageList.getTotalElements(), pageList.getContent());
        return Result.ok(pageResult);
    }

    /**
     * 根据标签ID查询等待回答列表
     * @param labelid
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("根据labelid查询waitlist")
    @GetMapping(value="/waitlist/{labelid}")
    public Result<PageResult<Problem>> findWaitListByLabelId(
        @ApiIgnore @LoginUser User user,
		@PathVariable String labelid,
		@RequestParam int page,
		@RequestParam int size 
	){
        Page<Problem> pageList = problemService.findWaitListByLabelId(labelid, page, size);
        PageResult<Problem> pageResult = new PageResult<> (pageList.getTotalElements(), pageList.getContent());
        return Result.ok(pageResult);
    }

    /**
     * 增加
     * @param problem
     */
    @ApiOperation("增加")
    @PostMapping
    public Result<String> add(@RequestBody Problem problem ){
        problemService.add(problem);
        return Result.ok();
    }

    
    @Login
    @ApiOperation("查询")
    @GetMapping(value = "/label/{labelid}")
    public Result<?> findLabelById(@PathVariable String labelid){
        Result<?> result = labelClient.findById(labelid);
        return result;
    }

}
