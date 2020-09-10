package cn.aaron911.micro.im.robot.model;

import lombok.Data;

@Data
public class RobotMessage {

    /**
     * 100000 文本  200000 链接   302000 新闻   308000 菜谱
     */
    private Integer code;

    private String text;

    private String url;

    private String list;

    private RobotMessageArticle subList;
}
