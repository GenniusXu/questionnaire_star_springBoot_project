package mygroup.questionnaire_star.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import mygroup.questionnaire_star.mapper.UserMapper;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.pojo.User;
import mygroup.questionnaire_star.service.UserService;
import mygroup.questionnaire_star.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 用户登录
    @PostMapping("/Login")
    public Result login(@RequestParam("name") @NotEmpty(message = "用户名不能为空！") @NotNull(message = "用户名不能为空！") String name,
                        @RequestParam("password") @NotEmpty(message = "密码不能为空！") @NotNull(message = "密码不能为空！") String password) {
        // 检查用户名和密码
        User user = userService.findUserByName(name);
        //System.out.println(user);
        if (user != null && user.getPassword().equals(password)) {// 验证成功，创建会话或令牌

            // 准备数据返回给客户端
            Map<String, Object> data = new HashMap<>();
            data.put("openid", user.getOpenid());
            data.put("name", user.getName());
            data.put("password", user.getPassword());

            //创建令牌
            //String token = JwtUtil.genToken(data);

            // 返回成功响应
            return Result.success(user.getOpenid());
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
    public Result updateUser(@RequestBody @Validated(User.onUpdate.class) User user) {
        //System.out.println(user);
        userService.updateUser(user);
        return Result.success();
    }

    //创建用户
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(User.onAdd.class) User user) {
        User temUser = userService.findUserByName(user.getName());
        if (temUser != null) return Result.error("The username has been registered");
        userService.add(user);
        String openid = userService.setOpenid(user);
        return Result.success(openid);
    }

    //根据openid查询用户
    @GetMapping("/searchByOpenId")
    public User searchByOpenId(@RequestParam String openid) {
        return userService.findUserByOpenid(openid);
    }

    //根据名字查询用户
    @GetMapping("/searchByName")
    public User searchByName(@RequestParam String name) {
        return userService.findUserByName(name);
    }

    //删除用户
    @DeleteMapping("/deleteUser")
    public Result deleteUser(String openid) {
        userService.deleUserByOpenID(openid);
        return Result.success();
    }

    //设置密码
    @PostMapping("/setPasswordByPhone")
    public Result setPasswordByPhone(@RequestParam @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "手机号格式不正确！") @NotEmpty(message = "手机号不能为空！") String phone,
                                     @RequestParam @NotEmpty(message = "密码不能为空！") @NotNull(message = "密码不能为空！") String password,
                                     @RequestParam String code) {
        User user = userService.findUserByPhone(phone);
        if (user == null) {
            return Result.error("该手机号未创建用户！");
        }
        // 从Redis中获取存储的验证码
        String savedCode = redisTemplate.opsForValue().get(phone);
        // 检查Redis中是否有保存的验证码
        if (savedCode == null) {
            // 验证码不存在，可能是用户未请求验证码或验证码已过期
            return Result.error("验证码不存在或已过期，请重新获取验证码后再试！");
        }
        // 验证码存在，进行比对
        if (!savedCode.equals(code)) {
            // 提交的验证码与存储的验证码不匹配
            return Result.error("验证码错误，请重新输入！");
        }
        String openid = user.getOpenid();
        userService.setPassword(openid, password);

        // 验证成功后，从Redis中删除验证码
        redisTemplate.delete(phone);

        return Result.success("密码设置成功为" + password);
    }

    @PostMapping("/setPasswordByEmail")
    public Result setPasswordByEmail(@RequestParam @Email(message = "邮箱格式不正确！") @NotEmpty(message = "邮箱不能为空！") String Email,
                                     @RequestParam @NotEmpty(message = "密码不能为空！") @NotNull(message = "密码不能为空！") String password,
                                     @RequestParam String code) {
        User user = userService.findUserByEmail(Email);
        if (user == null) {
            return Result.error("该邮箱未创建用户！");
        }
        // 从Redis中获取存储的验证码
        String savedCode = redisTemplate.opsForValue().get(Email);

        // 检查Redis中是否有保存的验证码
        if (savedCode == null) {
            // 验证码不存在，可能是用户未请求验证码或验证码已过期
            return Result.error("验证码不存在或已过期，请重新获取验证码后再试！");
        }

        // 验证码存在，进行比对
        if (!savedCode.equals(code)) {
            // 提交的验证码与存储的验证码不匹配
            return Result.error("验证码错误，请重新输入！");
        }

        String openid = user.getOpenid();
        userService.setPassword(openid, password);

        // 验证成功后，从Redis中删除验证码
        redisTemplate.delete(Email);

        return Result.success("密码设置成功为" + password);
    }

    //设置手机
    @PostMapping("/setPhoneNumber")
    public Result setPhoneNumber(String openid,
                                 @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "手机号格式不正确！") @NotEmpty(message = "手机号不能为空！") String phoneNumber) {
        userService.setPhoneNumber(openid, phoneNumber);
        return Result.success();
    }

    //设置邮箱
    @PostMapping("/setEmail")
    public Result setEmail(String openid, @Email(message = "邮箱格式不正确！") @NotEmpty(message = "邮箱不能为空！") String Email) {
        userService.setEmail(openid, Email);
        return Result.success();
    }


}
