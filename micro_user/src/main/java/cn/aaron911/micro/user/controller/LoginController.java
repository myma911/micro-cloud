package cn.aaron911.micro.user.controller;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.user.pojo.User;
import cn.aaron911.micro.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * sso server
 *
 */
@Api(tags="登录服务")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    /**
     * Login
     *
     * @param username
     * @param password
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        String token = loginService.login(username, password);
        return Result.ok("登录成功", token);
    }


    /**
     * Logout
     *
     * @param token
     * @return
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<String> logout(String token) {
        // logout, remove storeKey
        loginService.logout(token);
        return Result.ok();
    }

    /**
     * logincheck
     *
     * @param token
     * @return
     */
    @ApiOperation("是否登录")
    @GetMapping("/logincheck/{token}")
    public Result<User> logincheck(@PathVariable String token) {
        // logout
        User user = loginService.loginCheck(token);
        if (user == null) {
        	return Result.failed("未登录");
        }
        return Result.ok("已登录", user);
    }

}