package com.oribo.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.oribo.log.Log;
import com.sun.jna.platform.FileUtils;

public class compareimage {
	public static void cutJPG(InputStream input, OutputStream out, int x,
			int y, int width, int height) throws IOException {
		ImageInputStream imageStream = null;
		try {
			Iterator<ImageReader> readers = ImageIO
					.getImageReadersByFormatName("jpg");
			ImageReader reader = readers.next();
			imageStream = ImageIO.createImageInputStream(input);
			reader.setInput(imageStream, true);
			ImageReadParam param = reader.getDefaultReadParam();

			// System.out.println(reader.getWidth(0));
			// System.out.println(reader.getHeight(0));
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "jpg", out);
		} finally {
			imageStream.close();
		}
	}

	public static void cutPNG(InputStream input, OutputStream out, int x,
			int y, int width, int height) throws IOException {
		ImageInputStream imageStream = null;
		try {
			
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = readers.next();
			imageStream = ImageIO.createImageInputStream(input);
			reader.setInput(imageStream, true);
			ImageReadParam param = reader.getDefaultReadParam();

			// System.out.println(reader.getWidth(0));
			// System.out.println(reader.getHeight(0));

			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "png", out);
		} finally {
			imageStream.close();
		}
	}

	public static void cutImage(InputStream input, OutputStream out,String type, int x, int y, int width, int height)throws IOException {
		ImageInputStream imageStream = null;
		try {
			String imageType = (null == type || "".equals(type)) ? "jpg" : type;
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);
			ImageReader reader = readers.next();
			imageStream = ImageIO.createImageInputStream(input);
			reader.setInput(imageStream, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, imageType, out);
		} finally {
			imageStream.close();
		}
	}

	/**
	 * 对比图片是否一致
	 * @param targetImagePath
	 * @param myImagePath
	 * @param percent
	 * @return
	 */
	public static boolean sameAs(String targetImagePath, String myImagePath,double percent){
		BufferedImage otherImage = null;
		BufferedImage myImage = null;
		try {
			otherImage = ImageIO.read(new FileInputStream(targetImagePath));
			myImage = ImageIO.read(new FileInputStream(myImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.logInfo("读取文件异常");
		}
        
		if (otherImage.getWidth() != myImage.getWidth()) {
			return false;
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
			return false;
		}

		int width = myImage.getWidth();
		int height = myImage.getHeight();

		int numDiffPixels = 0;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
					numDiffPixels++;
				}
			}
		}
		Log.logInfo("对比图片:"+targetImagePath+"与图片:"+myImage+"的相似度");
		double numberPixels = (height * width);
		double diffPercent = numDiffPixels / numberPixels;
		return percent <= 1.0 - diffPercent;
	}



	
	public static void main(String[] args)
	{   
		System.out.println(sameAs("D:\\5.png", "D:\\u.png", 0.97));

	}
 


}
