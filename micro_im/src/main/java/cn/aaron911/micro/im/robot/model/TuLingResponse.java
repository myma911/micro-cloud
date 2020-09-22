package cn.aaron911.micro.im.robot.model;

import lombok.Data;

@Data
public class TuLingResponse {

    /**
     * 请求意图
     */
    private TuLingResponseIntent intent;

    /**
     * 输出结果集
     */
    private TuLingResponseResult[] results;
}
