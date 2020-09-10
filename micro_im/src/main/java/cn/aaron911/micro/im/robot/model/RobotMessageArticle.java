package cn.aaron911.micro.im.robot.model;

import lombok.Data;

@Data
public class RobotMessageArticle {

    /**
     * 标题
     */
    private String article;

    /**
     * 来源
     */
    private String source;

    /**
     * 新闻图片
     */
    private String icon;

    /**
     * 新闻详情链接
     */
    private String detailurl;

    /**
     * 针对菜谱
     */
    private String name;

    /**
     * 菜谱详情
     */
    private String info;
}
