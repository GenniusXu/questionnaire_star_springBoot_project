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

    @Insert("INSERT INTO user (name,createTime,updateTime,password)" +
            "VALUES (#{name},NOW(), NOW(),#{password})")
    void add(User user);

    // 根据OpenID删除用户
    @Delete("DELETE FROM user where openid=#{openid}")
    void deleUserByOpenID(String openid);

    @Update("UPDATE user SET password=#{password} WHERE openid = #{openid}")
    void setPassword(String openid, String password);

    @Update("UPDATE user SET phoneNumber=#{phoneNumber} WHERE openid=#{openid}")
    void setPhoneNumber(String openid, String phoneNumber);

    @Update("UPDATE user SET openid=#{openid} WHERE name=#{name}")
    void setOpenid(String name, String openid);

    @Update("UPDATE user SET captcha=#{captcha} WHERE phoneNumber=#{phoneNumber}")
    void setCaptcha(String captcha,String phoneNumber);
}
