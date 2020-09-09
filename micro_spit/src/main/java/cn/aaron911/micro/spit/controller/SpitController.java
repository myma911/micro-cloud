package cn.aaron911.micro.spit.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.PageResult;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.spit.pojo.Spit;
import cn.aaron911.micro.spit.service.SpitService;

import java.util.List;

/**
 * 吐槽 HTTP 服务
 */
@Api(tags = "吐槽 HTTP 服务")
@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        final List<Spit> all = spitService.findAll();

        return Result.ok("查询成功", all);
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return Result.ok("查询成功", spitService.findById(spitId));
    }

    /**
     * 发布吐槽
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.add(spit);
        return Result.ok("添加成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return Result.ok("修改成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return Result.ok("删除成功");
    }

    /**
     * 根据上级id查询吐槽分页数据a
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{patentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page, @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentid(parentid, page, size);
        return  Result.ok("查询成功", new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @RequestMapping(value = "/thumbup/{id}", method = RequestMethod.PUT)
    public Result updatethumbup(@PathVariable String id){
        //判断用户是否已经点过赞,这里到时可以重构调用分布式锁
        String userId = "1111";//这里可以从token从用户中心服务拿用户id,暂时写死
        if (redisTemplate.opsForValue().get("thumbup_" + userId + "_" + id) != null) {
            return Result.failed("你已经点过赞了");
        }
        spitService.updatethumbup(id);
        redisTemplate.opsForValue().set("thumbup_" + userId + "_" + id, "1");
        return Result.ok("点赞成功");
    }



}
