package cn.aaron911.micro.spit.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.aaron911.micro.spit.pojo.Spit;

/**
 * 吐槽数据访问层
 */
public interface SpitDao extends MongoRepository<Spit, String> {

    /**
     *  根据上级id查询吐槽列表
     * @param parentid
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
