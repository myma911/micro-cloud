package cn.aaron911.micro.sso.server.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.exception.LoginErrorException;
import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.sso.server.client.UserClient;
import cn.aaron911.micro.sso.server.pojo.User;
import cn.aaron911.sso.core.login.SsoTokenLoginHelper;
import cn.aaron911.sso.core.store.SsoLoginStore;
import cn.aaron911.sso.core.store.SsoSessionIdHelper;
import cn.aaron911.sso.core.user.SsoUser;
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
    private UserClient userClient;
    
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

    	Result result = userClient.findUser(username, password);
    	Object data = result.getData();
		if (result.getCode() != StateCodeEnum.OK.getCode() || null == data) {
    		throw new LoginErrorException();
    	}
    	
   
        // valid login
//        ReturnT<UserInfo> result = userService.findUser(username, password);
//        
//        if (result.getCode() != ReturnT.SUCCESS_CODE) {
//            return Result.failed(result.getMsg());
//        }
    	
    	User user = (User) data;

        // 1、make sso user
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(String.valueOf(user.getId()));
        ssoUser.setUsername(user.getNickname());
        ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        ssoUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(ssoUser);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(sessionId, ssoUser);

        // 4、return sessionId
        return Result.ok(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<String> logout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return Result.ok();
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @ApiOperation("是否登录")
    @GetMapping("/logincheck/{sessionId}")
    public Result<SsoUser> logincheck(@PathVariable String sessionId) {
        // logout
    	SsoUser ssoUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (ssoUser == null) {
        	return Result.failed("未登录");
        }
        return Result.ok("未登录", ssoUser);
    }

}