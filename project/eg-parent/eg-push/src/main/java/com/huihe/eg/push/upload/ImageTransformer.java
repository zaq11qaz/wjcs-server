package com.huihe.eg.push.upload;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.huihe.eg.comm.ImageTransformException;
import org.apache.log4j.Logger;


/**
 * 含义：图片的缩放处理
 * @author zhufei 2014-7-9 上午9:44:57
 */
public class ImageTransformer {

    private boolean isInitFlag = false; // 对象是否己经初始化
    private int tinyImgWidth = 0; // 定义生成小图片的宽度和高度，给其一个就可以了
    private int tinyImgHeight = 0;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private double scale = 0; // 定义小图片的相比原图片的比例
    private int maxPixel = 0;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 构造函数
     * @param
     */
    public ImageTransformer(){
        this.isInitFlag = false;
    }

    /**
     * 私有函数，重置所有的参数
     * @param
     * @return 没有返回参数
     */
    private void resetImageTransformerParams() {
        this.scale = 0;
        this.tinyImgWidth = 0;
        this.tinyImgHeight = 0;
        this.isInitFlag = false;
    }

    /**
     * @param scale  设置缩影图像相对于源图像的大小比例如 0.5
     * @throws
     */
    public void setScale(double scale) throws ImageTransformException {
        if (scale <= 0) {
            logger.warn("The image scaling ratio can't less than 0, scale="+scale);
            throw new ImageTransformException("缩放比例不能为 0 和负数！ ");
        }
        resetImageTransformerParams();
        this.scale = scale;
        isInitFlag = true;
    }

    /**
     * @param smallpicwidth  设置缩影图像的宽度
     * @throws ImageTransformException
     */
    public void SetSmallWidth(int smallpicwidth) throws ImageTransformException {
        if (smallpicwidth <= 0) {
            logger.warn("The image width can't less than 0, width="+smallpicwidth);
            throw new ImageTransformException("缩影图片的宽度不能为 0 和负数！");
        }
        resetImageTransformerParams();
        tinyImgWidth = smallpicwidth;
        isInitFlag = true;
    }

    /**
     * @param smallpicheight
     *            设置缩影图像的高度
     * @throws ImageTransformException
     */

    public void SetSmallHeight(int smallpicheight) throws ImageTransformException {
        if (smallpicheight <= 0) {
            logger.warn("The image height can't less than 0, height="+smallpicheight);
            throw new ImageTransformException("缩影图片的高度不能为 0 和负数！");
        }
        resetImageTransformerParams();
        tinyImgHeight = smallpicheight;
        isInitFlag = true;
    }


    /**
     * 方法描述：进行图片的缩放处理，根据设定的最大像素数，或缩放比例，或者指定高度，或者指定宽度，来进行图片缩放
     * @param srcImage 源文件
     * @param targetImage 目的文件
     * @throws ImageTransformException
     * @author zhufei 2014-7-9 上午11:09:02
     */
    public void doImageTransform(File srcImage,
                                 File targetImage) throws ImageTransformException {
        if (!isInitFlag) {
            logger.warn("The imageTransform object havent initialized");
            throw new ImageTransformException("对象参数没有初始化！");
        }
        if(!srcImage.exists()){
            logger.warn("The source file is not exist");
            throw new ImageTransformException("源文件不存在！");
        }

        //生成图像变换对象
        AffineTransform transform = new AffineTransform();
        //通过缓冲读入源图片文件
        BufferedImage srcImg = null;
        try {
            srcImg = ImageIO.read(srcImage);
        } catch (Exception ex) {
            logger.error("Read source file error");
            throw new ImageTransformException("读取源图像文件出错！");
        }
        imageWidth = srcImg.getWidth();// 原图像的长度
        imageHeight = srcImg.getHeight();// 原图像的宽度
        if(maxPixel != 0){
            if(imageWidth > imageHeight){
                tinyImgWidth = maxPixel;
                scale = (double)tinyImgWidth / (double)imageWidth;
                tinyImgHeight = (int)(imageHeight * scale);
            }else{
                tinyImgHeight = maxPixel;
                scale = (double)tinyImgHeight / (double)imageHeight;
                tinyImgWidth = (int)(imageWidth * scale);
            }
        }else if(scale != 0){
            tinyImgHeight = (int)(scale * imageHeight);
            tinyImgWidth = (int)(scale * imageWidth);
        }else if(tinyImgHeight != 0){
            scale = (double)imageHeight / (double)tinyImgHeight;
            tinyImgWidth = (int)(scale * imageWidth);
        }else if(tinyImgWidth != 0){
            scale = (double)tinyImgWidth / (double)imageWidth;
            tinyImgHeight = (int)(scale * imageHeight);
        }

        transform.setToScale(scale,scale);// 设置图像转换的比例
        // 生成图像转换操作对象
        AffineTransformOp ato = new AffineTransformOp(transform, null);
        // 生成缩小图像的缓冲对象
        BufferedImage bsmall = new BufferedImage(this.tinyImgWidth, this.tinyImgHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        // 生成小图像
        ato.filter(srcImg, bsmall);
        // 输出小图像
        try {
            ImageIO.write(bsmall, "jpeg", targetImage);
        } catch (IOException ex1) {
            logger.error("Write tiny image file error,file="+targetImage.getName());
            throw new ImageTransformException("写入缩略图像文件出错！");
        }
    }

    /**
     * 方法描述：设置图片的最大像素长度，如果长度大于宽度，则最大长度即为设定值，反之，如果宽度大于长度，则最大宽度为设定值
     * @param maxPixel 最大像素数
     * @throws Exception
     * @author zhufei 2014-7-9 上午11:06:45
     */
    public void setMaxPixel(int maxPixel) throws Exception{
        if(maxPixel <= 0){
            logger.warn("Scaling the image to the special pixel can't less than 0, maxPixel="+maxPixel);
            throw new ImageTransformException("设置图片的最大像素大小不能为 0 和负数！");
        }
        resetImageTransformerParams();
        this.isInitFlag = true;
        this.maxPixel = maxPixel;
    }

    public int getTinyImgWidth() {
        return tinyImgWidth;
    }

    public int getTinyImgHeight() {
        return tinyImgHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public double getScale() {
        return scale;
    }


}
