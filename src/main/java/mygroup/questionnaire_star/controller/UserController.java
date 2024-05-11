package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.mapper.UserMapper;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.pojo.User;
import mygroup.questionnaire_star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //更新用户
    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        //System.out.println(user);
        userService.updateUser(user);
        return Result.success();
    }

    //创建用户
    @PostMapping("/add")
    public Result<?> add(@RequestBody Map<String, String> userData){
        System.out.println(1);
        // 从userData中提取openid和username
        String openid = userData.get("openid");
        String name = userData.get("name");
        if (openid == null || openid.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            return Result.error("OpenID and Username must not be null or empty");
        }

        User user=userService.add(openid,name);
        return Result.success();
    }

    //根据openid查询用户
    @GetMapping("/searchByOpenId")
    public User searchByOpenId(@RequestParam String openid) {
        return userService.findUserByOpenid(openid);
    }

    //删除用户
    @DeleteMapping("/deleteUser")
    public Result deleteUser(String openid) {
        userService.deleUserByOpenID(openid);
        return Result.success();
    }

}
