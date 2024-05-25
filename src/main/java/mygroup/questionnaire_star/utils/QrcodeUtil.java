package mygroup.questionnaire_star.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 二维码工具类
 */
@Component
public class QrcodeUtil {
    @Autowired
    private QrConfig qrConfig;

    /**
     * 二维码生成base64字符串
     * @param content 跳转地址(即扫码后跳转的地址,可以是后端项目地址,用户扫码后填写数据提交到后台)
     * @param imageType 图片类型
     */
    public String generateBase64(@Nonnull String content, String imageType) {
        return QrCodeUtil.generateAsBase64(content, qrConfig, imageType);
    }

    /*public byte[] getWechatQrcodeByUnirest(String url, Map<String, Object> body) {
        return Unirest.post(url).body(JSONObject.toJSONString(body)).asBytes().getBody();
    }*/

}