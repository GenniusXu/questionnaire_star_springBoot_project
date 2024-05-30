package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.utils.AliMessageUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import mygroup.questionnaire_star.pojo.Result;



@RestController
public class MessageController {
    @PostMapping("/sendSms")
    public Result sendSms(@RequestParam("phoneNumber") String phoneNumber,
                          @RequestParam("templateCode") String templateCode,
                          @RequestParam("signName") String signName,
                          @RequestParam("templateParam") String templateParam) {
        try {
            // 调用工具类发送短信
            AliMessageUtil.sendSms(phoneNumber, templateCode, signName, templateParam);
            return Result.success("短信发送成功");
        } catch (Exception e) {
            // 记录日志或处理异常
            e.printStackTrace();
            return Result.error("短信发送失败：" + e.getMessage());
        }
    }
}
