package cn.aaron911.micro.im.db.service.impl;

import cn.aaron911.micro.im.db.entity.UserAccount;
import cn.aaron911.micro.im.db.mapper.UserAccountMapper;
import cn.aaron911.micro.im.db.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户帐号 服务实现类
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
