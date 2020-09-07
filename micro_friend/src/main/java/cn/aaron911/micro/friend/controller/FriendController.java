package cn.aaron911.micro.friend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.exception.AccessErrorException;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.friend.client.UserClient;
import cn.aaron911.micro.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 朋友 HTTP 服务
 *
 */
@Api(tags = "朋友 HTTP 服务")
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友或者添加非好友
     * @return
     */
    @ApiOperation("添加好友或者添加非好友")
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result<String> addFriend(@PathVariable String friendid, @PathVariable String type){
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims==null){
            //说明当前用户没有user角色
        	throw new AccessErrorException();
        }
        //得到当前登录的用户id
        String userid = claims.getId();
        //判断是添加好友还是添加非好友
        if(type!=null){
            if(type.equals("1")){
                //添加好友
                int flag = friendService.addFriend(userid, friendid);
                if(flag==0){
                	return Result.failed("不能重复添加好友");
                }
                if(flag==1){
                    userClient.updatefanscountandfollowcount(userid, friendid, 1);
                    return Result.ok("添加成功");
                }
            }else if (type.equals("2")){
                //添加非好友
                int flag =  friendService.addNoFriend(userid, friendid);
                if(flag==0){
                	return Result.failed("不能重复添加好友");
                }
                if(flag==1){
                	return Result.ok("添加成功");
                }
            }
            return Result.failed("参数异常");
        }else {
        	 return Result.failed("参数异常");
        }
    }

    
    /**
     * 删除好友
     */
    @ApiOperation("删除好友")
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result<String> deleteFriend(@PathVariable String friendid){
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims==null){
            //说明当前用户没有user角色
        	throw new AccessErrorException();
        }
        //得到当前登录的用户id
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        userClient.updatefanscountandfollowcount(userid, friendid, -1);
        return Result.ok("删除成功");
    }

}
