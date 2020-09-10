package cn.aaron911.micro.im.db.dao;
import java.util.Map;
import cn.aaron911.micro.im.db.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户帐号
 *
 */
public interface IUserAccountDao extends JpaRepository<UserAccountEntity,String>, JpaSpecificationExecutor<UserAccountEntity> {


	UserAccountEntity queryObjectByAccount(Map<String, Object> map);

}
