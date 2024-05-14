package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.UserMapper;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.pojo.User;
import mygroup.questionnaire_star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public User findUserByOpenid(String openid) {
        return userMapper.findUserByOpenid(openid);
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    public User add(User user) {
        userMapper.add(user);
        return user;
    }

    @Override
    public void deleUserByOpenID(String openid) {
        userMapper.deleUserByOpenID(openid);
    }

    @Override
    public void setPassword(String openid,String password) {
        userMapper.setPassword(openid,password);
    }

    @Override
    public void setPhoneNumber(String openid, String phoneNumber) {
        userMapper.setPhoneNumber(openid,phoneNumber);
    }
}
