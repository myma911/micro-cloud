package cn.aaron911.micro.im.db.service;

import java.util.List;
import java.util.Map;

import cn.aaron911.micro.im.db.dto.ImFriendUserData;
import cn.aaron911.micro.im.db.entity.UserDepartmentEntity;

/**
 * 部门
 * 
 */
public interface IUserDepartmentService {
	
	UserDepartmentEntity queryObject(Long id);
	
	List<UserDepartmentEntity> queryList(Map<String, Object> map);
	
    List<ImFriendUserData> queryGroupAndUser();
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserDepartmentEntity userDepartment);
	
	int update(UserDepartmentEntity userDepartment);
	
	int delete(Long id);
	
	int deleteBatch(Long[] ids);
}
