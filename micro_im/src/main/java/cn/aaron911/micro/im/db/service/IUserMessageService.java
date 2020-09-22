package cn.aaron911.micro.im.db.service;

import cn.aaron911.micro.im.db.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
public interface IUserMessageService extends IService<UserMessage> {

    /**
     * 获取历史记录
     * @param map
     * @return
     */
    List<UserMessage> getHistoryMessageList(Map<String, Object> map);
    /**
     * 获取离线消息
     * @param map
     * @return
     */
    List<UserMessage> getOfflineMessageList(Map<String, Object> map);
    /**
     * 获取历史记录总条数
     * @param map
     * @return
     */
    int getHistoryMessageCount(Map<String, Object> map);

}
