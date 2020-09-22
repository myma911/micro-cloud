package cn.aaron911.micro.im.server.model.proto;

import lombok.Data;

@Data
public final class MessageBodyProto {

  /**
   * 标题
   */
  private String title;

  /**
   * 内容
   */
  private String content;

  /**
   * 发送时间
   */
  private String time;

  /**
   * 0 文字   1 文件
   */
  private int type;

  /**
   * 扩展字段
   */
  private String extend;
}
