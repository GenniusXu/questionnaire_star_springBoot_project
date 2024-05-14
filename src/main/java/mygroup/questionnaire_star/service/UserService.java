package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public void updateUser(User user);

    User findUserByOpenid(String openid);
    User findUserByName(String name);

    User add(User user);

    //删除用户
    void deleUserByOpenID(String openid);

    //设置用户密码
    void setPassword(String openid,String password);
    //设置用户手机
    void setPhoneNumber(String openid,String phoneNumber);

}
