package cn.aaron911.micro.qa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.aaron911.micro.common.util.IdWorker;
import cn.aaron911.micro.qa.dao.ProblemDao;
import cn.aaron911.micro.qa.pojo.Problem;

/**
 * 问题服务service层
 *
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据标签ID查询问题列表
     * @param lableId 标签id
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findNewListByLabelId(String lableId,int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return problemDao.findNewListByLabelId(lableId,pageRequest);
    }

    /**
     * 根据标签ID查询热门问题列表
     * @param lableId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findHotListByLabelId(String lableId,int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return problemDao.findHotListByLabelId(lableId,pageRequest);
    }

    /**
     * 根据标签ID查询等待回答列表
     * @param lableId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findWaitListByLabelId(String lableId,int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return problemDao.findWaitListByLabelId(lableId,pageRequest);
    }

    /**
     * 增加
     * @param problem
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId()+"" );
        problemDao.save(problem);
    }
}
