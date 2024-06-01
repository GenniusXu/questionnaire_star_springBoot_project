package mygroup.questionnaire_star.controller;

import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import mygroup.questionnaire_star.service.MsmService;
import mygroup.questionnaire_star.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import mygroup.questionnaire_star.pojo.Result;

import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Message")
public class MessageController {


    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send")
    public Boolean send(@RequestParam String phone) {
        //1、从redis中获取验证码，如果获取到就直接返回
        String code = redisTemplate.opsForValue().get(phone);
//        if(code!=null) return false;

        //2、如果获取不到，就进行阿里云发送
        code = RandomUtil.buildCheckCode(6);//生成验证码的随机值
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        System.out.println(param);
        //调用方法
        boolean isSend = msmService.send(param,phone);

        if(isSend) {
            //往redis中设置数据：key、value、过期值、过期时间单位  MINUTES代表分钟
            redisTemplate.opsForValue().set(phone, code,30, TimeUnit.MINUTES);
            return true;
        } else {
            return false;
        }
    }


}
