package cn.aaron911.micro.im.robot.proxy.impl;

import java.util.Date;
import java.util.HashMap;

import cn.aaron911.micro.im.config.ImProperty;
import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.robot.model.*;
import cn.aaron911.micro.im.robot.proxy.IRobotProxy;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageBodyProto;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;


/**
 * 图灵机器人回复
 */
@Slf4j
@Component
@ConditionalOnExpression("'${im.robotType}'.equalsIgnoreCase('tuling')")
public class TLRebotProxy implements IRobotProxy {

    @Autowired
    private ImProperty imProperty;

    /**
     * https://www.kancloud.cn/turing/www-tuling123-com/718227
     *
     * @param userid  用于区分回复谁，机器人接口短暂记忆
     * @param content
     * @return
     */
    @Override
    public MessageWrapper botMessageReply(String userid, String content) {
        log.info("TLRebot reply user -->" + userid + "--mes:" + content);
        String message;
        try {
            TuLingRequest request = new TuLingRequest();
            request.setReqType(0);
            TuLingRequestPerception perception = new TuLingRequestPerception();
            TuLingRequestPerceptionInputText inputText = new TuLingRequestPerceptionInputText();
            inputText.setText(content);
            perception.setInputText(inputText);
            request.setPerception(perception);
            TuLingRequestUserInfo userInfo = new TuLingRequestUserInfo();
            userInfo.setApiKey(imProperty.getRobotKey());
            userInfo.setUserId(imProperty.getRobotSecret());
            request.setUserInfo(userInfo);

            message = HttpUtil.post(imProperty.getRobotApiUrl(), JSONUtil.toJsonStr(request), 62000);

            TuLingResponse response = JSON.parseObject(message, TuLingResponse.class);
            // 机器人接口调用错误
            if (null == response || null == response.getIntent() || null == response.getIntent().getCode()) {
                return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, userid, "机器人接口调用出错了");
            }


            message = handle(response);

            MessageProto messageProto= new MessageProto();
            messageProto.setCmd(Constants.CmdType.MESSAGE);
            messageProto.setMsgtype(Constants.ProtobufType.REPLY);
            //机器人ID
            messageProto.setSender(Constants.ImserverConfig.REBOT_SESSIONID);
            //回复人
            messageProto.setReceiver(userid);
            messageProto.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            MessageBodyProto messageBodyProto = new MessageBodyProto();
            messageBodyProto.setContent(message);
            messageProto.setMessageBody(messageBodyProto);
            return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, userid, messageProto);
        } catch (Exception e) {
            log.error("", e);
        }
        return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, userid, null);
    }

    /**
     * 处理图灵返回结果
     * 异常返回说明
     * 异常码	说明
     * 5000	无解析结果
     * 6000	暂不支持该功能
     * 4000	请求参数格式错误
     * 4001	加密方式错误
     * 4002	无功能权限
     * 4003	该apikey没有可用请求次数
     * 4005	无功能权限
     * 4007	apikey不合法
     * 4100	userid获取失败
     * 4200	上传格式错误
     * 4300	批量操作超过限制
     * 4400	没有上传合法userid
     * 4500	userid申请个数超过限制
     * 4600	输入内容为空
     * 4602	输入文本内容超长(上限150)
     * 7002	上传信息失败
     * 8008	服务器错误
     * 0	上传成功
     * @param response
     * @return
     */
    private String handle(TuLingResponse response) {
        String message = "";
        final Integer code = response.getIntent().getCode();
        switch (code) {
            case 5000:
                message = "无解析结果";
                break;
            case 6000:
                message = "暂不支持该功能";
                break;
            case 4000:
                message = "请求参数格式错误";
                break;
            case 4001:
                message = "加密方式错误";
                break;
            case 4002:
                message = "无功能权限";
                break;
            case 4003:
                message = "该apikey没有可用请求次数";
                break;
            case 4005:
                message = "无功能权限";
                break;
            case 4007:
                message = "apikey不合法";
                break;
            case 4100:
                message = "userid获取失败";
                break;
            case 4200:
                message = "上传格式错误";
                break;
            case 4300:
                message = "批量操作超过限制";
                break;
            case 4400:
                message = "没有上传合法userid";
                break;
            case 4500:
                message = "userid申请个数超过限制";
                break;
            case 4600:
                message = "输入内容为空";
                break;
            case 4602:
                message = "输入文本内容超长(上限150)";
                break;
            case 7002:
                message = "上传信息失败";
                break;
            case 8008:
                message = "服务器错误";
                break;
            case 0:
                message = "上传成功";
                break;
            default:
                // 这里先取第一个， 一会儿再优化
                final HashMap<String, String> map = response.getResults()[0].getValues();

                if (null != map){
                    for(String value : map.values()){
                        message = value;
                    }
                }
                break;
        }
        return message;
    }
}
