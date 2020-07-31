package cn.aaron911.micro.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aaron911.micro.recruit.dao.RecruitDao;
import cn.aaron911.micro.recruit.pojo.Recruit;

/**
 *
 */
@Service
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    /**
     * 根据状态查询
     * @param state
     * @return
     */
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state){
        return recruitDao.findTop4ByStateOrderByCreatetimeDesc(state);
    }

    /**
     * 最新职位列表
     * @return
     */
    public List<Recruit> newlist() {
        return recruitDao.findTop12ByStateNotOrderByCreatetimeDesc("0");
    }
}
