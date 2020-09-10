package cn.aaron911.micro.im.db.dao;

import cn.aaron911.micro.im.db.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户信息表
 *
 */
public interface IUserInfoDao extends JpaRepository<UserInfoEntity,String>, JpaSpecificationExecutor<UserInfoEntity> {
	
}
