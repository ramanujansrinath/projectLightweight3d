package org.xper.drawing.drawables;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.xper.drawing.drawables.Drawable;
import org.xper.drawing.renderer.AbstractRenderer;
import org.xper.drawing.renderer.OrthographicRenderer;
import org.xper.drawing.renderer.PerspectiveRenderer;
import org.xper.drawing.stick.MakeFromVertFace;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class DrawingManager implements Drawable {
	Drawable stimObj;
	
	List<? extends Drawable> stimObjs;
	List<Double> stimObjIds = new ArrayList<Double>();
	
	int nStim = 0;
	int stimCounter = 0;
	RGBColor background;
	
	int height;
	int width;
	
	PNGmaker pngMaker;
	
	BaseWindow window;
	AbstractRenderer renderer;
	
	public DrawingManager(int height, int width) {
		super();
		this.height = height;
		this.width = width;
	}
	
	public void drawStimuli(boolean saveToFile, boolean saveToDb, String prefix) {
		window = new BaseWindow(height,width);
		
		PixelFormat pixelFormat = new PixelFormat(0, 8, 1, 4);
		window.setPixelFormat(pixelFormat);
		window.create();
		
//		renderer = new PerspectiveRenderer();
		renderer = new OrthographicRenderer();
		renderer.setDepth(6000);
		renderer.setDistance(525);
		renderer.setPupilDistance(34.5);
		renderer.setHeight(height);
		renderer.setWidth(width);
		renderer.init(window.getWidth(), window.getHeight());
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(background.getRed(),background.getGreen(),background.getBlue(),0);
				
		while(stimCounter < nStim) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			GL11.glClearColor(background.getRed(),background.getGreen(),background.getBlue(),0);			
			renderer.draw(this);
			if (saveToFile)
				pngMaker.saveImageToFile(stimObjIds.get(stimCounter), prefix);
			if (saveToDb)
				pngMaker.saveImageToDb(stimObjIds.get(stimCounter));
			window.swapBuffers();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stimCounter++;
		}
		window.destroy();
	}

	public void draw() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearColor(background.getRed(),background.getGreen(),background.getBlue(),0);
				
		if (nStim > 0) {
			stimObjs.get(stimCounter).draw();
		}
	}
	
	public void setBackgroundColor(float red,float green,float blue) {
		this.background = new RGBColor(red, green, blue);
	}
	
	public void close() {
		window.destroy();
	}

	public AbstractRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(AbstractRenderer renderer) {
		this.renderer = renderer;
	}

	public void setPngMaker(PNGmaker pngMaker) {
		this.pngMaker = pngMaker;
	}
	
	public void setStimObjIds(List<Double> stimObjIds) {
		this.stimObjIds = stimObjIds;
	}
	
	
	public <T extends Drawable> void setStimObjs(List<T> stimObjs) {
		this.stimObjs = stimObjs;
		nStim = stimObjs.size();
	}
}
