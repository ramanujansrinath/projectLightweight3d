package org.xper.drawing.stick;

import javax.vecmath.Point3d;
import org.xper.utils.RGBColor;

public class Occluder {
	Point3d leftBottom;
	Point3d rightTop; 
	RGBColor color;
	
	public Occluder(double[] leftBottom, double[] rightTop, RGBColor color) {
		this.leftBottom.x = leftBottom[0];
		this.leftBottom.y = leftBottom[1];
		this.leftBottom.z = leftBottom[2];
		this.rightTop.x = rightTop[0];
		this.rightTop.y = rightTop[1];
		this.rightTop.z = rightTop[2];
		this.color = color;
	}
	
	public Occluder() {
		this.leftBottom = new Point3d(-10,-10,10);
		this.rightTop = new Point3d(10,10,10);
		this.color = new RGBColor();
	}
	
	public void setLeftBottom(Point3d lb) {
		this.leftBottom = lb;
	}
	public void setRightTop(Point3d rt) {
		this.rightTop = rt;
	}
	public void setColor(RGBColor color) {
		this.color = color;
	}
	
	public Point3d getLeftBottom() {
		return leftBottom;
	}
	public Point3d getRightTop() {
		return rightTop;
	}
	public RGBColor getColor() {
		return color;
	}
}
