
package cn.aaron911.micro.im.webserver.controller;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.server.connector.ImConnertor;
import cn.aaron911.micro.im.server.group.ImChannelGroup;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageBodyProto;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.aaron911.micro.im.server.session.ISessionManager;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@RestController("/user/imuser")
public class SessionController {

    @Autowired
    private ISessionManager sessionManager;

    @Autowired
    private ImConnertor connertor;


    /**
     * 列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        request.setAttribute("allsession", sessionManager.getSessions());
        return "user/userlist";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/msgbroadcast", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String broadcast(@RequestParam String msgContent, String session, HttpServletRequest request) {

        MessageProto messageProto = new MessageProto();
        messageProto.setCmd(Constants.CmdType.MESSAGE);
        messageProto.setSender("-1");
        messageProto.setMsgtype(Constants.ProtobufType.NOTIFY);
        MessageBodyProto messageBodyProto = new MessageBodyProto();
        messageBodyProto.setContent(msgContent);
        messageProto.setMessageBody(messageBodyProto);

        if (StringUtils.isNotEmpty(session)) {
            //推送到个人
            MessageWrapper msgWrapper = new MessageWrapper(MessageWrapper.MessageProtocol.NOTIFY, session, null, messageProto);
            connertor.pushMessage(msgWrapper);
        } else {
            //推送到所有用户
            ImChannelGroup.broadcast(messageProto);
        }
        return JSONArray.toJSONString(1);
    }


    /**
     * 列表
     */
    @RequestMapping(value = "/offline", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String offlineUser(@RequestParam String msgContent, String session, HttpServletRequest request) {
        boolean result = false;
        MessageProto messageProto = new MessageProto();
        messageProto.setCmd(Constants.CmdType.MESSAGE);
        messageProto.setSender("-1");
        messageProto.setMsgtype(Constants.ProtobufType.NOTIFY);
        MessageBodyProto messageBodyProto= new MessageBodyProto();
        if (StringUtils.isNotEmpty(msgContent)) {
            messageBodyProto.setContent(msgContent);
        } else {
            messageBodyProto.setContent("已被系统强制下线");
        }
        messageProto.setMessageBody(messageBodyProto);
        if (StringUtils.isNotEmpty(session)) {
            //推送到个人
            MessageWrapper msgWrapper = new MessageWrapper(MessageWrapper.MessageProtocol.NOTIFY, session, null, messageProto);
            connertor.pushMessage(msgWrapper);
            connertor.close(session);
            result = true;
        } else {
            //广播下线消息，所有用户下线
            ImChannelGroup.broadcast(messageProto);
            ImChannelGroup.disconnect();
        }
        return JSONArray.toJSONString(result);
    }


}


 