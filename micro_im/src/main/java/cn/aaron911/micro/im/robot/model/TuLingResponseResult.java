package cn.aaron911.micro.im.robot.model;

import cn.hutool.json.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求意图
 */
@Data
public class TuLingResponseResult {

    /**
     * 输出类型
     * 	文本(text);连接(url);音频(voice);视频(video);图片(image);图文(news)
     */
    private String resultType;

    /**
     * 	输出值
     */
    private HashMap<String, String> values;


    /**
     * ‘组’编号:0为独立输出，大于0时可能包含同组相关内容 (如：音频与文本为一组时说明内容一致)
     */
    private int groupType;

}
