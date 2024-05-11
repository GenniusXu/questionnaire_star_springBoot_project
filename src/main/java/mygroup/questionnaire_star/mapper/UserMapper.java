package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Update("UPDATE user SET name=#{name},updateTime=#{updateTime} WHERE openid = #{openid}")
    void updateUser(User user);

    @Select("SELECT * FROM user WHERE openid=#{openid}")
    User findUserByOpenid(String openid);

    @Insert("INSERT INTO user (openid,name,createTime,updateTime)" +
            "VALUES (#{openid},#{name},#{createTime},#{updateTime})")
    void add(User user);

    // 根据OpenID删除用户
    @Delete("DELETE FROM user where openid=#{openid}")
    void deleUserByOpenID(String openid);
}
