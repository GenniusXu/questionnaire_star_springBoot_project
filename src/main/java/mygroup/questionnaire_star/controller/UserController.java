package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.mapper.UserMapper;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.pojo.User;
import mygroup.questionnaire_star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //登录
    @PostMapping("/Login")
    public Result Login(){

        //用户名如果已经存在，则返回错误提示

        return Result.success();
    }
    //更新用户
    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        //System.out.println(user);
        userService.updateUser(user);
        return Result.success();
    }

    //创建用户
    @PostMapping("/add")
    public Result<?> add(@RequestBody User user){
        User temUser=userService.findUserByName(user.getName());
        if(temUser!=null)return Result.error("The username has been registered");
        userService.add(user);
        return Result.success();
    }

    //根据openid查询用户
    @GetMapping("/searchByOpenId")
    public User searchByOpenId(@RequestParam String openid) {
        return userService.findUserByOpenid(openid);
    }

    @GetMapping("/searchByName")
    public User searchByName(@RequestParam String name){return userService.findUserByName(name);}

    //删除用户
    @DeleteMapping("/deleteUser")
    public Result deleteUser(String openid) {
        userService.deleUserByOpenID(openid);
        return Result.success();
    }

    //设置密码
    @PostMapping("/setPassword")
    public Result setPassword(String openid,String password){
        userService.setPassword(openid,password);
        return Result.success(password);
    }

    //设置手机
    @PostMapping("/setPhoneNumber")
    public Result setPhoneNumber(String openid,String phoneNumber){
        userService.setPhoneNumber(openid,phoneNumber);
        return Result.success();
    }

}
