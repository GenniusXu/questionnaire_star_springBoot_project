package mygroup.questionnaire_star.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
* myBatis配置，用于显式注册自定义类型处理器StringListTypeHandler
* */
@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            configuration.getTypeHandlerRegistry().register(mygroup.questionnaire_star.config.StringListTypeHandler.class);
            configuration.getTypeHandlerRegistry().register(mygroup.questionnaire_star.config.QuestionListTypeHandler.class);
        };
    }
}