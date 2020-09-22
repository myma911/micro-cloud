package cn.aaron911.micro.im.robot.model;

import lombok.Data;

import java.util.Map;

/**
 * 请求意图
 */
@Data
public class TuLingResponseIntent {

    /**
     * 输出功能code
     */
    private Integer code;

    /**
     * 	意图名称
     */
    private String intentName;

    /**
     * 意图动作名称
     */
    private String actionName;

    /**
     * 功能相关参数
     */
    private Map parameters;

}
