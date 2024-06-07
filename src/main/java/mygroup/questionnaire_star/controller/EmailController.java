package mygroup.questionnaire_star.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import mygroup.questionnaire_star.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Email")
@Validated
public class EmailController {
    @Autowired(required = false)
    private JavaMailSender sender; // 引入Spring Mail依赖后，会自动装配到IOC容器。用来发送邮件
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send")
    public String send(@Email(message = "邮箱格式不正确！") @NotEmpty(message = "邮箱不能为空！") String target_email) {
        String code = RandomUtil.buildCheckCode(6);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【测试验证码】验证消息"); // 发送邮件的标题
        message.setText("您正在执行问卷Cloud修改密码操作，验证码：" + code
                + "，切勿将验证码泄露给他人，本条验证码有效期5分钟。\n" +
                "若不是本人操作，请无视"); // 发送邮件的内容
        message.setTo(target_email); // 指定要接收邮件的用户邮箱账号
        message.setFrom("2737804063@qq.com"); // 发送邮件的邮箱账号，注意一定要和配置文件中的一致！
        System.out.println("到sender.send(message)了！");
        sender.send(message); // 调用send方法发送邮件即可
        System.out.println("到redisTemplate.opsForValue().set(target_email, code, 30, TimeUnit.MINUTES)了！");
        //往redis中设置数据：key、value、过期值、过期时间单位  MINUTES代表分钟
        //redisTemplate.opsForValue().set(target_email, code, 30, TimeUnit.MINUTES);
        return "放送成功";
    }
}
