package cn.aaron911.micro.im.db.service.impl;

import cn.aaron911.micro.im.db.entity.UserMessage;
import cn.aaron911.micro.im.db.mapper.UserMessageMapper;
import cn.aaron911.micro.im.db.service.IUserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements IUserMessageService {

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public List<UserMessage> getHistoryMessageList(Map<String, Object> map) {
        return userMessageMapper.getHistoryMessageList(map);
    }

    @Override
    public int getHistoryMessageCount(Map<String, Object> map) {
        return userMessageMapper.getHistoryMessageCount(map);
    }

    @Override
    public List<UserMessage> getOfflineMessageList(Map<String, Object> map) {
        List<UserMessage> result = userMessageMapper.getOfflineMessageList(map);
        //更新状态为已读状态
        userMessageMapper.updatemsgstate(map);
        return result;
    }



}
