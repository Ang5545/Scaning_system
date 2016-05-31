package ru.ang5545.image_processing;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSize;
import org.bytedeco.javacpp.opencv_core.IplImage;



public class ImageHandler {
	
	private IplImage backImage;
	private IplImage objImage;

	private boolean backIsShoting;
	
	public ImageHandler(CvSize size) {
		this.backIsShoting = false;
		this.backImage 	= ImageHelper.createImage(size, 3);
		this.objImage	= ImageHelper.createImage(size, 3);
	}

	public void processImage(IplImage img) {
		if (!backIsShoting) {
			this.backImage	= cvCloneImage(img);
			cvSet(objImage, CvScalar.WHITE);
		} else {
			this.objImage	= cvCloneImage(img);
		}	
	}

	public BufferedImage getBackImage() {
		return ImageHelper.getBufferedImage(backImage);
	}
	
	
	public BufferedImage getObjImage() {
		return ImageHelper.getBufferedImage(objImage);
	}

	public void release() {
		if (!backIsShoting) {
			cvReleaseImage(backImage);
		}
		cvReleaseImage(objImage);
	}
	
	public void saveBackImage() {
		this.backIsShoting = true;
	}
	
}
