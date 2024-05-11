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
    public User add(String openid, String name) {
        if (openid == null || openid.trim().isEmpty()) {
            throw new IllegalArgumentException("OpenID must not be null or empty");
        }
        User existingUser = userMapper.findUserByOpenid(openid);
        if (existingUser != null) {
            // 如果用户已存在，则不创建新用户并抛出异常
            throw new IllegalStateException("User with OpenID " + openid + " already exists");
        }

        User user = new User();
        user.setOpenid(openid);
        user.setName(name);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.add(user);
        return user;
    }

    @Override
    public void deleUserByOpenID(String openid) {
        userMapper.deleUserByOpenID(openid);
    }
}
