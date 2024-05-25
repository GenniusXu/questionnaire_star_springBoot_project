package mygroup.questionnaire_star.config;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * 二维码配置类
 */
@Configuration
public class QrcodeConfig {
    @Bean
    public QrConfig qrConfig(){
        QrConfig qrConfig=new QrConfig();
        qrConfig.setBackColor(Color.white.getRGB());
        qrConfig.setForeColor(Color.black.getRGB());
        return qrConfig;
    }
}