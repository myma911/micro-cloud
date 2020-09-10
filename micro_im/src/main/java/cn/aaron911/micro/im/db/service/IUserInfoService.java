package cn.aaron911.micro.im.db.service;


import cn.aaron911.micro.im.db.entity.UserInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 * 
 */
public interface IUserInfoService {
	
	UserInfoEntity queryObject(Long id);
	
	List<UserInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserInfoEntity userInfo);
	
	int update(UserInfoEntity userInfo);
	
	int delete(Long id);
	
	int deleteBatch(Long[] ids);
}
