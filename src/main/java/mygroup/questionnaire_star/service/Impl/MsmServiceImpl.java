package mygroup.questionnaire_star.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import mygroup.questionnaire_star.service.MsmService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送验证码
     * @param param     验证码
     * @param phone     手机号
     * @return
     */

    private static final Logger logger = LoggerFactory.getLogger(MsmService.class);

    private String templateCode = "SMS_467595365";
    private String signName = "问卷系统";
    private String regionId = "cn-shenzhen";
    private String endpoint = "dysmsapi.aliyuncs.com";
    private String accessKeyId = "LTAI5tDK74j6uEX1cVk1ArLH"; // 请确保不要在代码中硬编码你的密钥
    private String accessKeySecret = "XAALjBCGzeOk9kDp0qMdQsiUYKn8aR"; // 请确保不要在代码中硬编码你的密钥
    //1旧的
//    private static final String ACCESS_KEY_ID="LTAI5tDK74j6uEX1cVk1ArLH";
//    private static final String ACCESS_KEY_SECRET="XAALjBCGzeOk9kDp0qMdQsiUYKn8aR";

    //2新的
//    private String accessKeyId = "LTAI5t9RKoPuj1crBdFZCjsC"; // 请确保不要在代码中硬编码你的密钥
//    private String accessKeySecret = "9QKaTQDU0VidXJIVKmAvUE88Z0MSQ8"; // 请确保不要在代码中硬编码你的密钥


    @Override
    public Boolean send(Map<String, Object> param, String phone) {

        SendSmsResponse resp = null ;
        if(phone.isEmpty()) return false;

        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                .build());

        AsyncClient client = AsyncClient.builder()
                .region(regionId) // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
                                .setEndpointOverride(endpoint)
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        String jsonString = "{\"code\":\""+param.get("code")+"\"}";
        SendSmsRequest request = SendSmsRequest.builder()
                .signName(signName)
                .templateCode(templateCode)
                .phoneNumbers(phone)
                .templateParam(jsonString)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        try {

            CompletableFuture<SendSmsResponse> response = client.sendSms(request);
            resp = response.get();
            System.out.println(new Gson().toJson(resp));
            client.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }finally {
            if (client != null) {
                client.close();
            }
        }
        return false;
    }

}

