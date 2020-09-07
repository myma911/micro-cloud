package cn.aaron911.micro.gathering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.gathering.pojo.Gathering;
import cn.aaron911.micro.gathering.service.GatheringService;

/**
 *
 */
@RestController
@RequestMapping("/gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    /**
     * 修改
     * @param gathering
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Gathering gathering, @PathVariable String id ){
        gathering.setId(id);
        gatheringService.update(gathering);
        return Result.ok("修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        gatheringService.deleteById(id);
        return Result.ok("删除成功");
    }
}
