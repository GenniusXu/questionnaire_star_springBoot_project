package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void updateUser(User user);

    User findUserByOpenid(String openid);

    User add(String openid, String name);

    //删除用户
    void deleUserByOpenID(String openid);
}
