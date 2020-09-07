package cn.aaron911.micro.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.user.pojo.User;
import cn.aaron911.micro.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 */
@Api("用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;


    /**
     * 发送短信验证码
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return Result.ok("发送成功");
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @RequestMapping(value="/register/{code}",method=RequestMethod.POST)
    public Result register( @RequestBody User user ,@PathVariable String code) {
        userService.add(user,code);
        return Result.ok("注册成功");
    }
    

    /**
     * 删除 必须有admin角色才能删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        String token = (String) request.getAttribute("claims_admin");
        if (token==null || "".equals(token)){
            throw new RuntimeException("权限不足！");
        }
        userService.deleteById(id);
        return Result.ok("删除成功");
    }

    /**
     * 更新好友粉丝数和用户关注数
     * @return
     */
    @RequestMapping(value = "/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    public void updatefanscountandfollowcount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x){
        userService.updatefanscountandfollowcount(x, userid, friendid);
    }
    
    @ApiOperation("根据用户名、密码查询用户")
    @GetMapping(value = "/findUser")
    public Result<User> findUser(String username, String password){
    	User user = userService.findUser(username, password);
    	if (null == user) {
    		return Result.failed("查找失败");
    	}
    	return Result.ok(user);
    }
}
