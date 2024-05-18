package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.mapper.UserMapper;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.pojo.User;
import mygroup.questionnaire_star.service.UserService;
import mygroup.questionnaire_star.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/Login")
    public Result login(@RequestParam("name") String name, @RequestParam("password") String password) {
        // 检查用户名和密码
        User user = userService.findUserByName(name);
        //System.out.println(user);
        if (user != null && user.getPassword().equals(password)) {// 验证成功，创建会话或令牌

            // 准备数据返回给客户端
            Map<String, Object> data = new HashMap<>();
            data.put("openid", user.getOpenid());
            data.put("name",user.getName());

            //创建令牌
            String token = JwtUtil.genToken(data);

            // 返回成功响应
            return Result.success(token);
        } else {
            // 验证失败，返回错误信息
            return Result.error("用户名或密码错误");
        }
    }

    // 假设的令牌创建方法，需要根据实际情况实现
    private String createToken(User user) {
        // 创建令牌的逻辑
        // 这里只是一个示例，实际应用中需要考虑安全性
        return "token_" + user.getOpenid();
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
