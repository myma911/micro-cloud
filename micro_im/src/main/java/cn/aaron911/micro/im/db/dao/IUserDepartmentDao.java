package cn.aaron911.micro.im.db.dao;

import cn.aaron911.micro.im.db.dto.ImFriendUserData;
import cn.aaron911.micro.im.db.entity.UserDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 部门
 *
 */
public interface IUserDepartmentDao extends JpaRepository<UserDepartmentEntity,String>, JpaSpecificationExecutor<UserDepartmentEntity> {
	
	List<ImFriendUserData> queryGroupAndUser();
}
