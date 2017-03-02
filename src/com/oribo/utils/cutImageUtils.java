package com.oribo.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class cutImageUtils {

 
    
    private static String DEFAULT_THUMB_PREVFIX = "thumb_";
    private static String DEFAULT_CUT_PREVFIX = "cut_";
    private static Boolean DEFAULT_FORCE = false;
    
    
    /**
     * <p>Title: cutImage</p>
     * <p>Description:  根据原图与裁切size截取局部图片</p>
     * @param srcImg    源图片
     * @param output    图片输出流
     * @param rect        需要截取部分的坐标和大小
     */
    public static void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect){
        if(srcImg.exists()){
            java.io.FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    return ;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis,true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fis != null) fis.close();
                    if(iis != null) iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
           
        }
    }
    
    public void cutImage(File srcImg, OutputStream output, int x, int y, int width, int height){
        cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
    }
    
    public static void cutImage(File srcImg, String destImgPath, java.awt.Rectangle rect){
        File destImg = new File(destImgPath);
        if(destImg.exists()){
            String p = destImg.getPath();
            try {
                if(!destImg.isDirectory()) p = destImg.getParent();
                if(!p.endsWith(File.separator)) p = p + File.separator;
                cutImage(srcImg, new java.io.FileOutputStream(p + DEFAULT_CUT_PREVFIX + "_" + new java.util.Date().getTime() + "_" + srcImg.getName()), rect);
            } catch (FileNotFoundException e) {
      
            }
        }else 
        	;
    }
    
    public static void cutImage(File srcImg, String destImg, int x, int y, int width, int height){
        cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
    }
    
    public void cutImage(String srcImg, String destImg, int x, int y, int width, int height){
        cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width, height));
    }
    
	public static void main(String[] args)
	{   
		/*System.out.println(FileOperate.getTestDatapFilePath()+File.separator+"percompare.png");
		compareimage.sameAs(FileOperate.getTestDatapFilePath()+File.separator+"percompare.png", FileOperate.getScreencapFilePath()+File.separator+"defaultphoto.png", 0.97);*/
		File f=new File(FileOperate.getTestDatapFilePath()+File.separator+"percompare.png");
		File f2=new File(FileOperate.getTestDatapFilePath()+File.separator+"percompare2.png");
		cutImage(f, FileOperate.getTestDatapFilePath()+File.separator+"percompare2.png", 693, 500, 270, 135);

	}
 
}