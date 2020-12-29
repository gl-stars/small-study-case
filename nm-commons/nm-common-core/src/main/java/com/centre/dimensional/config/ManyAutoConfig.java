package com.centre.dimensional.config;

import com.centre.dimensional.utils.QrCodeGenerate;
import com.centre.dimensional.utils.VerificationCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 指定加载配置类
 * @author: GL
 * @create: 2020年 05月 30日 15:35
 **/
@ComponentScan({"com.centre.common.exception"})
public class ManyAutoConfig {

    /**
     * 注入验证码生成类到spring容器中
     * @return
     */
    @Bean
    public VerificationCode verificationCode(){
        return new VerificationCode();
    }

    /**
     * 注入二维码生成类到spring容器中
     * @return
     */
    @Bean
    public QrCodeGenerate qrCodeGenerate(){
        return new QrCodeGenerate();
    }
}
