package cn.aaron911.micro.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aaron911.micro.recruit.dao.EnterpriseDao;
import cn.aaron911.micro.recruit.pojo.Enterprise;

/**
 * 招聘企业service层
 */
@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    /**
     * 热门企业列表
     * ishot = 1 代表热门
     * @return
     */
    public List<Enterprise> hotlist(){
        return enterpriseDao.findByIshot("1");
    }


}
