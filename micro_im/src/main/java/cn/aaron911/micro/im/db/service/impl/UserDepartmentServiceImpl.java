package cn.aaron911.micro.im.db.service.impl;

import java.util.List;
import java.util.Map;

import cn.aaron911.micro.im.db.dao.IUserDepartmentDao;
import cn.aaron911.micro.im.db.dto.ImFriendUserData;
import cn.aaron911.micro.im.db.dto.ImFriendUserInfoData;
import cn.aaron911.micro.im.db.entity.UserDepartmentEntity;
import cn.aaron911.micro.im.db.service.IUserDepartmentService;
import cn.aaron911.micro.im.server.session.ISessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDepartmentServiceImpl implements IUserDepartmentService {

	@Autowired
	private IUserDepartmentDao userDepartmentDao;

	@Autowired
	private ISessionManager sessionManager;
 

	@Override
	public UserDepartmentEntity queryObject(Long id){
		return userDepartmentDao.queryObject(id);
	}
	
	@Override
	public List<UserDepartmentEntity> queryList(Map<String, Object> map){
		return userDepartmentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userDepartmentDao.queryTotal(map);
	}
	
	@Override
	public void save(UserDepartmentEntity userDepartment){
		userDepartmentDao.save(userDepartment);
	}
	
	@Override
	public int update(UserDepartmentEntity userDepartment){
		return userDepartmentDao.update(userDepartment);
	}
	
	@Override
	public int delete(Long id){
		return userDepartmentDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Long[] ids){
		return userDepartmentDao.deleteBatch(ids);
	}

	@Override
	public List<ImFriendUserData> queryGroupAndUser() {
		List<ImFriendUserData>  friendgroup = userDepartmentDao.queryGroupAndUser();
		for(ImFriendUserData fg:friendgroup){
			List<ImFriendUserInfoData> friends = fg.getList();
			if(friends!=null&&friends.size()>0){
				for(ImFriendUserInfoData  fr:friends){
					boolean  exist = sessionManager.exist(fr.getId().toString());
					if(exist){
						fr.setStatus("online");
					}
				}
			} 
		}
		
		return friendgroup;
	}
	
}
