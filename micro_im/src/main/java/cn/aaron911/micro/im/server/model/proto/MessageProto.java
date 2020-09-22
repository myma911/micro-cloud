package cn.aaron911.micro.im.server.model.proto;

import lombok.Data;

@Data
public final class MessageProto {

  //接口版本号
  private String version;

  //设备uuid
  private String deviceId;

  //请求接口命令字  1绑定  2心跳   3上线   4下线
  private int cmd;

  //发送人
  private String sender;

  //接收人
  private String receiver;

  //用户组编号
  private String groupId;

  //请求1，应答2，通知3，响应4  format
  private int msgtype;

  //1 rsa加密 2aes加密
  private int flag;

  //mobile-ios mobile-android pc-windows pc-mac
  private String platform;

  //客户端版本号
  private String platformVersion;

  //客户端凭证
  private String token;

  //客户端key
  private String appKey;

  //时间戳
  private String timeStamp;

  //签名
  private String sign;

  //请求数据
  private MessageBodyProto messageBody;
}
