package cn.aaron911.micro.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.common.result.StatusCode;
import cn.aaron911.micro.common.util.JwtUtil;
import cn.aaron911.micro.user.pojo.User;
import cn.aaron911.micro.user.service.UserService;

/**
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 发送短信验证码
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
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
        return new Result(true,StatusCode.OK,"注册成功");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(String mobile, String password){
        User user = userService.findByMobileAndPassword(mobile,password);
        if(user==null){
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("roles", "user");
        return new Result(true, StatusCode.OK, "登录成功", map);
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
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 更新好友粉丝数和用户关注数
     * @return
     */
    @RequestMapping(value = "/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    public void updatefanscountandfollowcount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x){
        userService.updatefanscountandfollowcount(x, userid, friendid);
    }
}
