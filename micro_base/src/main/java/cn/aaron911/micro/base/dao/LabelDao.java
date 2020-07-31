package cn.aaron911.micro.base.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.aaron911.micro.base.pojo.Label;

/**
 * 标签数据访问接口
 * 
 * JpaRepository提供了基本的增删改查
 * JpaSpecificationExecutor用于做复杂的条件查询
 */
public interface LabelDao extends JpaRepository<Label, String>,JpaSpecificationExecutor<Label> {


}
