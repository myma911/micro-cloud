package cn.aaron911.micro.im.db.dao;

import java.util.List;
import java.util.Map;
import cn.aaron911.micro.im.db.entity.UserMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 */
public interface IUserMessageDao  extends JpaRepository<UserMessageEntity,String>, JpaSpecificationExecutor<UserMessageEntity> {
	/**
	 * 获取历史记录
	 * @param map
	 * @return
	 */
	List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map);
	/**
	 * 获取历史记录总条数
	 * @param map
	 * @return
	 */
	int getHistoryMessageCount(Map<String, Object> map);
	/**
	 * 获取离线消息
	 * @param map
	 * @return
	 */
	List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map);
	
	/**
	 * 修改消息为已读状态
	 * @param map
	 * @return
	 */
	int updatemsgstate(Map<String, Object> map);
	
}
