package com.centre.dimensional.config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * 二维码 添加 logo图标 处理的方法,
 * <p>模仿微信自动生成二维码效果，有圆角边框，logo和二维码间有空白区，logo带有灰色边框</p>
 * @author: gl_stars
 * @data: 2020年 09月 18日 16:11
 **/
public class LogoConfig {

    /**
     * 设置 logo
     * @param matrixImage 源二维码图片
     * @param logoImage  logo图片
     * @return 返回带有logo的二维码图片
     * @throws IOException
     * @author Administrator sangwenhao
     */
    public BufferedImage LogoMatrix(BufferedImage matrixImage, String logoImage) throws IOException {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /**
         * logo图片在本地获取
         */
//         BufferedImage logo = ImageIO.read(new File(logoImage));
        // logo图片在远程获取
        URL url = new URL(logoImage);
        BufferedImage logo = ImageIO.read(url);
        //开始绘制图片
        g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);
        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke);
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
        g2.setColor(Color.white);
        // 绘制圆弧矩形
        g2.draw(round);

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke2);
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
        g2.setColor(new Color(128,128,128));
        // 绘制圆弧矩形
        g2.draw(round2);

        g2.dispose();
        matrixImage.flush() ;
        return matrixImage ;
    }
}
