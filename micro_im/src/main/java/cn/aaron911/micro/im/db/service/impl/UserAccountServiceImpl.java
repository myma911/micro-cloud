package cn.aaron911.micro.im.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.aaron911.micro.im.db.dao.IUserAccountDao;
import cn.aaron911.micro.im.db.entity.UserAccountEntity;
import cn.aaron911.micro.im.db.entity.UserInfoEntity;
import cn.aaron911.micro.im.db.service.IUserAccountService;
import cn.aaron911.micro.im.db.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class UserAccountServiceImpl implements IUserAccountService {

	@Autowired
	private IUserAccountDao userAccountDao;

	@Autowired
	private IUserInfoService userInfoServiceImpl;
	
	@Override
	public UserAccountEntity queryObject(Long id){
		return userAccountDao.queryObject(id);
	}
	
	@Override
	public List<UserAccountEntity> queryList(Map<String, Object> map){
		return userAccountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userAccountDao.queryTotal(map);
	}
	
	@Override
	public void save(UserAccountEntity userAccount){
		//判断用户是否已注册
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("account", userAccount.getAccount());
		UserAccountEntity  user = queryObjectByAccount(map);
		if(user==null||user.getId()<1){
			userAccountDao.save(userAccount);
			if(userAccount!=null&&userAccount.getId()!=null){
				//保存基本信息
				UserInfoEntity userInfo = userAccount.getUserInfo();
				userInfo.setUid(userAccount.getId());
				userInfoServiceImpl.save(userInfo);
			}
		} 
	}
	
	@Override
	public int update(UserAccountEntity userAccount){
		return userAccountDao.update(userAccount);
	}
	
	@Override
	public int delete(Long id){
		return userAccountDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Long[] ids){
		return userAccountDao.deleteBatch(ids);
	}

	@Override
	public UserAccountEntity queryObjectByAccount(Map<String, Object> map) {
		return userAccountDao.queryObjectByAccount(map);
	}

	@Override
	public UserAccountEntity validateUser(Map<String, Object> map) {
		UserAccountEntity  user = queryObjectByAccount(map);
		if(user!=null){
			String password = (String)map.get("password");
			if(password.equals(user.getPassword())){
				return user;
			}
		}
		return null;
	}
	
}
