package cn.aaron911.micro.im.db.mapper;

import cn.aaron911.micro.im.db.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    @Select("<script>" +
            "SELECT" +
            "  a.id," +
            "  a.senduser," +
            "  b.name sendusername , " +
            "  ifnull(b.profilephoto,'layui/images/0.jpg') avatar," +
            "  a.content," +
            "  a.createuser," +
            "  a.createdate," +
            "  a.updatedate" +
            "FROM  user_message  a " +
            "LEFT JOIN user_info b ON b.uid=a.senduser" +
            "where (a.senduser =#{senduser} AND a.receiveuser =#{receiveuser} )  OR  (a.senduser =#{receiveuser} AND a.receiveuser =#{senduser}  )  ORDER BY id DESC   " +
            "<if test=\"offset != null and limit != null\">" +
            "   limit #{offset}, #{limit}" +
            "</if></script>")
    List<UserMessage> getHistoryMessageList(Map<String, Object> map);

    @Select("<script>select  count(1)" +
            "from  user_message  a " +
            " where (a.senduser =#{senduser} AND a.receiveuser =#{receiveuser} )  OR  (a.senduser =#{receiveuser} AND a.receiveuser =#{senduser}  )</script>")
    int getHistoryMessageCount(Map<String, Object> map);


    @Select("<script>SELECT\n" +
            "\t\t\t  a.id,\n" +
            "\t\t\t  a.senduser,\n" +
            "\t\t\t  b.name sendusername , \n" +
            "\t\t\t  IFNULL(b.profilephoto,'layui/images/0.jpg') avatar,\n" +
            "\t\t\t  a.content,\n" +
            "\t\t\t  a.createuser,\n" +
            "\t\t\t  a.createdate,\n" +
            "\t\t\t  a.updatedate\n" +
            "\t\t\tFROM  user_message  a \n" +
            "\t\t\tLEFT JOIN user_info b ON b.uid=a.senduser\n" +
            "\t\t\tWHERE a.receiveuser =#{receiveuser}  and  isread=0     ORDER BY a.createdate ASC  </script>")
    List<UserMessage> getOfflineMessageList(Map<String, Object> map);

    @Select("<script>update user_message \n" +
            "\t\t<set>\n" +
            "\t\t\t isread = 1,  \n" +
            "\t\t\t updatedate= NOW()\n" +
            "\t\t</set>\n" +
            "\t\twhere receiveuser = #{receiveuser}</script>")
    void updatemsgstate(Map<String, Object> map);
}
