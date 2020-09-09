package cn.aaron911.micro.recruit.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.recruit.service.EnterpriseService;

/**
 * 招聘企业
 */
@Api(tags = "招聘企业")
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 查询热门企业
     * @return
     */
    @RequestMapping(value="/search/hotlist",method=RequestMethod.GET)
    public Result hotlist(){
        return Result.ok("查询成功", enterpriseService.hotlist());
        
    }

}
