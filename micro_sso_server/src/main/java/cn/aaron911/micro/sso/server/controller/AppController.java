package cn.aaron911.micro.sso.server.controller;

import cn.aaron911.micro.common.pojo.User;
import cn.aaron911.micro.sso.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * sso server (for app)
 *
 */
@Api(tags="APP 单点登录服务")
@RestController
@RequestMapping("/app")
public class AppController {

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
        return Result.ok("登录", user);
    }

}