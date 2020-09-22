package cn.aaron911.micro.im.db.service.impl;

import cn.aaron911.micro.im.db.entity.UserInfo;
import cn.aaron911.micro.im.db.mapper.UserInfoMapper;
import cn.aaron911.micro.im.db.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
