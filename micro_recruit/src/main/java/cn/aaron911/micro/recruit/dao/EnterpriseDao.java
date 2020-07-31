package cn.aaron911.micro.recruit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.aaron911.micro.recruit.pojo.Enterprise;

/**
 *
 * 招聘企业实体类
 * 
 */

public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

	/**
	 * 根据热门状态获取企业列表
	 * @param ishot
	 * @return      
	 */
	public List<Enterprise> findByIshot(String ishot);
}
