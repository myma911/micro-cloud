package cn.aaron911.micro.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.common.result.StatusCode;
import cn.aaron911.micro.common.util.JwtUtil;
import cn.aaron911.micro.user.pojo.Admin;
import cn.aaron911.micro.user.service.AdminService;

/**
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 增加
     * @param admin
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 用户登录
     * @param loginMap
     * @return
     */
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap){
        Admin admin = adminService.findByLoginnameAndPassword(loginMap.get("loginname"), loginMap.get("password"));
        if(admin!=null){
            //生成令牌
            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("role", "admin");
            return new Result(true,StatusCode.OK,"登陆成功", map);
        } else {
            return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
        }
    }

}
