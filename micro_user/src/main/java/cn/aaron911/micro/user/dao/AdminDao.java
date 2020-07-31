package cn.aaron911.micro.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.aaron911.micro.user.pojo.Admin;

/**
 * 管理员数据访问层
 * 
 */
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin> {
	
    public Admin findByLoginname(String loginname);
}
