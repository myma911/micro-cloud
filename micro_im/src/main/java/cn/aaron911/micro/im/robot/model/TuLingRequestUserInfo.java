package cn.aaron911.micro.im.robot.model;

import lombok.Data;

/**
 * @description:
 * @author:
 * @time: 2020/9/21 15:52
 */
@Data
public class TuLingRequestUserInfo {

    /**
     * 		Y	32位	机器人标识
     */
    private String apiKey;

    /**
     * Y	长度小于等于32位	用户唯一标识
     */
    private String userId;

    /**
     * 		N	长度小于等于64位	群聊唯一标识
     */
    private String groupId;

    /**
     * 		N	长度小于等于64位	群内用户昵称
     */
    private String userIdName;
}
