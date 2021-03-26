package com.huihe.eg.push.upload;

import java.io.File;

/**@author  作者yangjing:
 * @date 创建时间：2017-2-17 下午5:23:39
 */
public class LocalUploadThream extends Thread{

    private File srctImage;
    private  int[] sizes = { 50, 100, 200, 720, 0 };;// 图片存储的尺寸大小


    public void intitialize(File srctImage){
        this.srctImage=srctImage;
    }

    /**
     *
     * @Description: 图片产生压缩图片
     * @author yangjing
     * @date 2017-2-17 下午3:34:25
     */
    @Override
    public void run(){
        ImageTransformer it = new ImageTransformer();
        String targetName=srctImage.getName();
        String parentPath=srctImage.getParent();
        try {
            for (int size : this.sizes) {
                if (size == 200) {
                    continue;
                }
                String tgtName = null;
                if (size != 0) {
                    it.setMaxPixel(size);
                    tgtName = targetName.substring(0,
                            targetName.lastIndexOf("."))
                            + "_"
                            + size
                            + "_"
                            + size
                            + targetName.substring(targetName.lastIndexOf("."));
                    File targetImage = new File(parentPath + "/" + tgtName);
                    it.doImageTransform(srctImage, targetImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

