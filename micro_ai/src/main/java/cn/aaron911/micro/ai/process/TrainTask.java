package cn.aaron911.micro.ai.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 训练任务类
 */
@Component
public class TrainTask {
    @Autowired
    private Word2VecService word2VecService;
    
    
    /**
     * 训练模型
     */
    @Scheduled(cron="0 30 16 * *  ")
    public void trainModel(){
        System.out.println("开始合并语料库......");
        word2VecService.mergeWord();
        System.out.println("合并语料库结束‐‐‐‐‐‐");
        
        System.out.println("开始构建词向量模型");
        word2VecService.build();
        System.out.println("构建词向量模型结束");
        
        System.out.println("开始构建神经网络卷积模型");
        //cnnService.build();
        System.out.println("构建神经网络卷积模型结束");
    }
}