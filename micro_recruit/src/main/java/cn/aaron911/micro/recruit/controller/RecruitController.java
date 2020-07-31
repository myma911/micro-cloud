package cn.aaron911.micro.recruit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.common.result.StatusCode;
import cn.aaron911.micro.recruit.pojo.Recruit;
import cn.aaron911.micro.recruit.service.RecruitService;

/**
 * 职位controller
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;

    /**
     * 查询推荐职位
     * @return
     */
    @RequestMapping(value="/search/recommend",method= RequestMethod.GET)
    public Result recommend(){
        List<Recruit> list = recruitService.findTop4ByStateOrderByCreatetimeDesc("2");
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 查询最新职位列表
     * @return
     */
    @RequestMapping(value="/search/newlist",method= RequestMethod.GET)
    public Result newlist(){
        return new Result(true,StatusCode.OK,"查询成 功",recruitService.newlist());
    }
}
