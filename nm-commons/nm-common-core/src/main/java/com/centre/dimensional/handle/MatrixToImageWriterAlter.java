package com.centre.dimensional.handle;

import com.centre.dimensional.config.LogoConfig;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 生产条形码的基类
 * @version : 1.0.0
 * @description: 二维码的生成需要借助MatrixToImageWriter类
 * @author: GL
 * @program: mind-center-platform
 * @create: 2020年 05月 18日 22:52
 **/
public class MatrixToImageWriterAlter {

    /**
     * 用于设置图案的颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 用于背景色
     */
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriterAlter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
//                设置二维码背景色
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
//				image.setRGB(x, y,  (matrix.get(x, y) ? Color.black.getRGB() : Color.WHITE.getRGB()));
            }
        }
        return image;
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, String logoImages) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        LogoConfig logoConfig = new LogoConfig();
        image = logoConfig.LogoMatrix(image,logoImages);

        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}
