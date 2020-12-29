package com.centre.dimensional.constant;

/**
 * @version : 1.0.0
 * @description: 常量值
 * @author: GL
 * @create: 2020年 05月 18日 22:39
 **/
public interface CommonConstant {

    /**
     * 二维码图片宽度
     */
    int QRCODE_WIDTH = 430 ;

    /**
     * 二维码高度
     */
    int GRCODE_HEIGHT = 430 ;

    /***
     * 二维码图片格式
     */
    String GRCODE_FORMAT = "gif" ;

    /**
     * 二维码边框距离
     */
    int GRCODE_BORDER_DISTANCE = 1 ;

    /**
     * 二维码输出图片格式
     */
    String GRCODE_OUTPUT_IMAGES = "png";

    /**
     * 验证码默认宽度
     */
    int VERIFICATION_CODE_WIDTH = 130 ;

    /**
     * 验证码默认高度
     */
    int VERIFICATION_CODE_HEIGHT = 48 ;

    /***
     * 验证码默认长度
     */
    int VERIFICATION_CODE_LEN = 4 ;

    /**
     * 算术长度，默认为 2 位
     */
    int ARITHMETIC_LEN = 2 ;
}
