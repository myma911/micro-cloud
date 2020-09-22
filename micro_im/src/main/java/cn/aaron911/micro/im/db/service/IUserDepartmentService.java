package cn.aaron911.micro.im.db.service;

import cn.aaron911.micro.im.db.dto.ImFriendUserData;
import cn.aaron911.micro.im.db.entity.UserDepartment;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
public interface IUserDepartmentService extends IService<UserDepartment> {

    UserDepartment queryObject(Long id);

    List<UserDepartment> queryList(Map<String, Object> map);

    List<ImFriendUserData> queryGroupAndUser();

    int queryTotal(Map<String, Object> map);

    int update(UserDepartment userDepartment);

    int delete(Long id);

    int deleteBatch(Long[] ids);
}
