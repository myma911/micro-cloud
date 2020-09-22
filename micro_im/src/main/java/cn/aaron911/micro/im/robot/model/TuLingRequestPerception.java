package cn.aaron911.micro.im.robot.model;

import lombok.Data;

/**
 * @description:
 * @author:
 * @time: 2020/9/21 15:50
 */
@Data
public class TuLingRequestPerception {

    /**
     * 	-	N	-	文本信息
     */
    private TuLingRequestPerceptionInputText inputText;

    /**
     * 	-	N	-	图片信息
     */
    private TuLingRequestPerceptionInputImage inputImage;

    /**
     * 	-	N	-	音频信息
     */
    private TuLingRequestPerceptionInputMedia inputMedia;

    /**
     * 	-	N	-	客户端属性
     */
    private TuLingRequestPerceptionSelfInfo selfInfo;
}
