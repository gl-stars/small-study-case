package com.centre.dimensional.utils;

import com.centre.dimensional.constant.CommonConstant;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version : 1.0.0
 * @description: 验证码生成器
 * @author: GL
 * @program: mind-center-platform
 * @create: 2020年 05月 19日 16:48
 **/
@Slf4j
public class VerificationCode {

    /***
     * 生成png类型验证码
     * @param response
     * @return
     */
    public String pngCode(HttpServletResponse response){
        return this.pngCode(-1,response);
    }

    /**
     * 生成png类型验证码
     * @param len 验证码位数
     * @param response
     * @return
     */
    public String pngCode(int len, HttpServletResponse response){
        return this.pngCode(-1,-1,len,response);
    }

    /**
     * 生成png类型验证码
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param len 验证码位数
     * @param response
     * @return
     */
    public String pngCode(int width, int height, int len, HttpServletResponse response) {
        if (width < 0){
            width = CommonConstant.VERIFICATION_CODE_WIDTH;
        }
        if (height < 0){
            height = CommonConstant.VERIFICATION_CODE_HEIGHT;
        }
        if (len < 0){
            len = CommonConstant.VERIFICATION_CODE_LEN;
        }
        //png类型
        SpecCaptcha captcha = new SpecCaptcha(width, height, len);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.info("png类型的验证码生成错误");
        }
        return captcha.text();
    }

    /***
     * 生成gif类型验证码
     * @param response
     * @return
     */
    public String gifCode(HttpServletResponse response){
        return this.gifCode(-1,-1,-1,response);
    }

    /**
     * 生成gif类型验证码
     * @param len 验证码位数
     * @param response
     * @return
     */
    public String gifCode(int len, HttpServletResponse response){
        return this.gifCode(-1,-1,len,response);
    }

    /**
     * gif类型验证码
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param len 验证码长度
     * @param response
     * @return
     */
    public String gifCode(int width, int height, int len, HttpServletResponse response){
        if (width < 0){
            width = CommonConstant.VERIFICATION_CODE_WIDTH;
        }
        if (height < 0){
            height = CommonConstant.VERIFICATION_CODE_HEIGHT;
        }
        if (len < 0){
            len = CommonConstant.VERIFICATION_CODE_LEN;
        }
        //gif类型
        GifCaptcha captcha = new GifCaptcha (width, height, len);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.info("gif类型的验证码生成错误");
        }
        return captcha.text();
    }

    /***
     * 生成算术类型验证码
     * @param response
     * @return
     */
    public String arithmeticCode(HttpServletResponse response){
        return this.arithmeticCode(-1,-1,-1,response);
    }

    /**
     * 生成算术类型验证码
     * @param len 验证码位数
     * @param response
     * @return
     */
    public String arithmeticCode(int len, HttpServletResponse response){
        return this.arithmeticCode(-1,-1,len,response);
    }

    /**
     * 算术类型验证码
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param len 验证码长度
     * @param response
     * @return
     */
    public String arithmeticCode(int width, int height, int len, HttpServletResponse response){
        if (width < 0){
            width = CommonConstant.VERIFICATION_CODE_WIDTH;
        }
        if (height < 0){
            height = CommonConstant.VERIFICATION_CODE_HEIGHT;
        }
        if (len < 0){
            len = CommonConstant.ARITHMETIC_LEN;
        }
        //算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(width, height, len);
        // 获取运算的公式：3+2=?
        captcha.getArithmeticString();

        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.info("算术类型的验证码生成错误");
        }
        // 获取运算的结果：5
        return captcha.text();
    }

    /***
     * 生成中文类型验证码
     * @param response
     * @return
     */
    public String chineseCode(HttpServletResponse response){
        return this.chineseCode(-1,-1,-1,response);
    }

    /**
     * 生成中文类型验证码
     * @param len 验证码位数
     * @param response
     * @return
     */
    public String chineseCode(int len, HttpServletResponse response){
        return this.chineseCode(-1,-1,len,response);
    }

    /**
     * 中文类型验证码
     * @param width 验证码宽度
     * @param height 验证码高度
     * @param len 验证码长度
     * @param response
     * @return
     */
    public String chineseCode(int width, int height, int len, HttpServletResponse response){
        if (width < 0){
            width = CommonConstant.VERIFICATION_CODE_WIDTH;
        }
        if (height < 0){
            height = CommonConstant.VERIFICATION_CODE_HEIGHT;
        }
        if (len < 0){
            len = CommonConstant.ARITHMETIC_LEN;
        }
        //中文
        ChineseCaptcha captcha = new ChineseCaptcha(width, height, len);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.info("中文类型的验证码生成错误");
        }
        return captcha.text();
    }

}
