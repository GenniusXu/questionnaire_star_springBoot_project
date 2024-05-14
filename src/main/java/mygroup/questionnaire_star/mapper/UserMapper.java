package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Update("UPDATE user SET name=#{name},updateTime=#{updateTime} WHERE openid = #{openid}")
    void updateUser(User user);

    @Select("SELECT * FROM user WHERE openid=#{openid}")
    User findUserByOpenid(String openid);

    @Select("SELECT * FROM user WHERE name=#{name}")
    User findUserByName(String name);

    @Insert("INSERT INTO user (openid,name,createTime,updateTime,password)" +
            "VALUES (#{openid},#{name},NOW(), NOW(),#{password})")
    void add(User user);

    // 根据OpenID删除用户
    @Delete("DELETE FROM user where openid=#{openid}")
    void deleUserByOpenID(String openid);

    @Update("UPDATE user SET password=#{password} WHERE openid = #{openid}")
    void setPassword(String openid, String password);

    @Update("UPDATE user SET phoneNumber=#{phoneNumber} WHERE openid=#{openid}")
    void setPhoneNumber(String openid, String phoneNumber);
}
