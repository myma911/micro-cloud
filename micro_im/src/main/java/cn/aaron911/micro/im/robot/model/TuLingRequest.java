package cn.aaron911.micro.im.robot.model;

import lombok.Data;

/**
 * https://www.kancloud.cn/turing/www-tuling123-com/718227
 * @description: 图灵v2.0 接口请求参数格式
 * @author:
 * @time: 2020/9/21 15:47
 */
@Data
public class TuLingRequest {

    /**
     * 输入类型:0-文本(默认)、1-图片、2-音频
     */
    private int reqType;

    /**
     * 输入信息
     */
    private TuLingRequestPerception perception;

    /**
     * 用户参数
     */
    private TuLingRequestUserInfo userInfo;
}
