package cn.aaron911.micro.im.db.service;

import cn.aaron911.micro.im.db.entity.UserAccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户帐号
 * 
 *
 */
public interface IUserAccountService {
	
	UserAccountEntity queryObject(Long id);
	
	UserAccountEntity queryObjectByAccount(Map<String, Object> map);
	
	UserAccountEntity validateUser(Map<String, Object> map);
	
	List<UserAccountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserAccountEntity userAccount);
	
	int update(UserAccountEntity userAccount);
	
	int delete(Long id);
	
	int deleteBatch(Long[] ids);
}
