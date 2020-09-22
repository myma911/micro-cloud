package cn.aaron911.micro.im.db.mapper;

import cn.aaron911.micro.im.db.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
