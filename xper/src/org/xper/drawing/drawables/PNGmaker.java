package org.xper.drawing.drawables;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.jzy3d.plot3d.rendering.image.GLImage;
import org.lwjgl.opengl.GL11;
import org.xper.utils.DbUtil;

public class PNGmaker {
	int height = 512;
	int width = 512;
	String imageFolderName = "";
	boolean saveToDb = false;
	boolean saveToFile = false;
	DbUtil dbUtilObj;
	
	public PNGmaker() {}

	public <T extends Drawable> void createAndSavePNGsfromObjs(List<T> objs,List<Double> stimObjIds, String prefix) {	
		DrawingManager testWindow = new DrawingManager(height,width);
		testWindow.setBackgroundColor(0.3f,0.3f,0.3f);
		testWindow.setPngMaker(this);

		testWindow.setStimObjs(objs);
		testWindow.setStimObjIds(stimObjIds);
		
		testWindow.drawStimuli(saveToFile,saveToDb,prefix);				// draw object
		testWindow.close();
	}
	
	public <T extends Drawable> void createAndSavePNGsfromObjs(List<T> objs,List<Long> stimObjIds) {	
		DrawingManager testWindow = new DrawingManager(height,width);
		testWindow.setBackgroundColor(0.3f,0.3f,0.3f);
		testWindow.setPngMaker(this);

		testWindow.setStimObjs(objs);
		List<Double> ids = new ArrayList<Double>();
		for(Long id : stimObjIds)
			ids.add((double)id);
		
		testWindow.setStimObjIds(ids);
		
		testWindow.drawStimuli(saveToFile,saveToDb,"");				// draw object
		testWindow.close();
	}
	
	public void saveImageToFile(double stimObjId, String prefix) {
		byte[] data = screenShotBinary();  

		try {
			FileOutputStream fos = new FileOutputStream(imageFolderName + "/" + prefix + (int)stimObjId + ".png");
		    fos.write(data);
		    fos.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveImageToDb(double stimObjId) {
		byte[] data = screenShotBinary();  
		dbUtilObj.writeThumbnail(stimObjId,data);
	}
	
	private byte[] screenShotBinary() 
	{
		ByteBuffer framebytes = allocBytes(width * height * 3);

		int[] pixels = new int[width * height];
		int bindex;
		// grab a copy of the current frame contents as RGB (has to be UNSIGNED_BYTE or colors come out too dark)
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, framebytes);
		// copy RGB data from ByteBuffer to integer array
		for (int i = 0; i < pixels.length; i++) {
			bindex = i * 3;
			pixels[i] =
					0xFF000000                                          // A
					| ((framebytes.get(bindex)   & 0x000000FF) << 16)   // R
					| ((framebytes.get(bindex+1) & 0x000000FF) <<  8)   // G
					| ((framebytes.get(bindex+2) & 0x000000FF) <<  0);  // B
		}
		// free up this memory
		framebytes = null;
		// flip the pixels vertically (opengl has 0,0 at lower left, java is upper left)
		pixels = GLImage.flipPixels(pixels, width, height);

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, width, height, pixels, 0, width);

			javax.imageio.ImageIO.write(image, "png", out);
			
			byte[] data = out.toByteArray();
			return data;
		}
		catch (Exception e) {
			System.out.println("screenShot(): exception " + e);
			return null;
		}
	}

	private static ByteBuffer allocBytes(int howmany) {
		final int SIZE_BYTE = 4;
		return ByteBuffer.allocateDirect(howmany * SIZE_BYTE).order(ByteOrder.nativeOrder());
	}
	
	public void setImageFolderName(String ifn) {
		this.imageFolderName = ifn;
	}
	public void setDbUtilObj(DbUtil dbUtilObj) {
		this.dbUtilObj = dbUtilObj;
	}
	public void setSaveToFile(boolean saveToFile) {
		this.saveToFile = saveToFile;
	}
	public void setSaveToDb(boolean saveToDb) {
		this.saveToDb = saveToDb;
	}
}
