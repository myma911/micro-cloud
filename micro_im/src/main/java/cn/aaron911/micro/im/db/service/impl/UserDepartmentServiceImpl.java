package cn.aaron911.micro.im.db.service.impl;

import cn.aaron911.micro.im.db.dto.ImFriendUserData;
import cn.aaron911.micro.im.db.entity.UserDepartment;
import cn.aaron911.micro.im.db.mapper.UserDepartmentMapper;
import cn.aaron911.micro.im.db.service.IUserDepartmentService;
import cn.aaron911.micro.im.server.session.impl.SessionManagerImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements IUserDepartmentService {

    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Autowired
    private SessionManagerImpl sessionManager;


    @Override
    public UserDepartment queryObject(Long id){

        return userDepartmentMapper.selectById(id);
    }

    @Override
    public List<UserDepartment> queryList(Map<String, Object> map){
        //return userDepartmentMapper.(map);
        return null;
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        //return userDepartmentDao.queryTotal(map);
        return 0;
    }

    @Override
    public int update(UserDepartment userDepartment)
    {
        //return userDepartmentDao.update(userDepartment);
        return 0;
    }

    @Override
    public int delete(Long id){

        //return userDepartmentDao.delete(id);
        return 0;
    }

    @Override
    public int deleteBatch(Long[] ids){
        //return userDepartmentDao.deleteBatch(ids);
        return 0;
    }

    @Override
    public List<ImFriendUserData> queryGroupAndUser() {
//        List<ImFriendUserData>  friendgroup = userDepartmentDao.queryGroupAndUser();
//        for(ImFriendUserData fg:friendgroup){
//            List<ImFriendUserInfoData> friends = fg.getList();
//            if(friends!=null&&friends.size()>0){
//                for(ImFriendUserInfoData  fr:friends){
//                    boolean  exist = sessionManager.exist(fr.getId().toString());
//                    if(exist)
//                        fr.setStatus("online");
//                }
//            }
//        }
//
//        return friendgroup;


        return null;
    }
}
