package cn.aaron911.micro.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.aaron911.micro.common.util.IdWorker;
import cn.aaron911.micro.user.dao.AdminDao;
import cn.aaron911.micro.user.pojo.Admin;

/**
 * 管理员service
 *
 */
@Service
public class AdminService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private AdminDao adminDao;

    @Transactional(rollbackFor = Exception.class)
    public void add(Admin admin) {
        admin.setId( idWorker.nextId()+"" );
        //密码加密
        String encodePass = encoder.encode(admin.getPassword());
        admin.setPassword(encodePass);
        adminDao.save(admin);
    }

    /**
     * 根据登陆名和密码查询
     * @param loginname
     * @param password
     * @return
     */
    public Admin findByLoginnameAndPassword(String loginname, String password){
        Admin admin = adminDao.findByLoginname(loginname);
        if( admin!=null && encoder.matches(password,admin.getPassword())) {
            return admin;
        } else {
            return null;
        }
    }
}
