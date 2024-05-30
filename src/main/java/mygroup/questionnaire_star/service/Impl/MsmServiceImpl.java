package mygroup.questionnaire_star.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import mygroup.questionnaire_star.service.MsmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送验证码
     * @param param     验证码
     * @param phone     手机号
     * @return
     */

    private String templateCode = "SMS_467510333";
    private String signName = "问卷cloud";
    private String templateParam = "code";
    private String regionId = "cn-hangzhou";
    private String endpoint = "dysmsapi.aliyuncs.com";
    private String accessKeyId = "LTAI5t9RKoPuj1crBdFZCjsC"; // 请确保不要在代码中硬编码你的密钥
    private String accessKeySecret = "9QKaTQDU0VidXJIVKmAvUE88Z0MSQ8"; // 请确保不要在代码中硬编码你的密钥


    @Override
    public boolean send(Map<String, Object> param, String phone) {

        if(phone.isEmpty()) return false;

        //default 地域节点，默认就好  后面是 阿里云的 id和秘钥（这里记得去阿里云复制自己的id和秘钥哦）
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //这里不能修改
        CommonRequest request = new CommonRequest();

        request.putQueryParameter("PhoneNumbers", phone);                    //手机号
        request.putQueryParameter("SignName", signName);    //申请阿里云 签名名称（暂时用阿里云测试的，自己还不能注册签名）
        request.putQueryParameter("TemplateCode", templateCode); //申请阿里云 模板code（用的也是阿里云测试的）
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

