package org.xper.utils;

public class Lighting {
	RGBColor color = new RGBColor(1f,0f,0f);
	Material material = Material.SPECULAR;
	
	public enum Material {
		SPECULAR,SHADE,TWOD
	}
	
	float shine;
	float [] amb;
	float [] diff;
	float [] spec;
	
	public void setLightColor(RGBColor color) {
		this.color = color;
		computeProperties();
	}
	
	public void setMaterial(Material material) {
		this.material = material;
		computeProperties();
	}
	
	void computeProperties() {
		if (material.equals(Material.SHADE)) {
			spec = new float[]{0f,0f,0f,1f};
			shine = 0f;
			
			amb = new float[]{(float)(color.getRed() * 0.4),(float)(color.getGreen() * 0.4), (float)(color.getBlue() * 0.4),1f};
			diff = new float[]{(float)(color.getRed() * 0.6),(float)(color.getGreen() * 0.6), (float)(color.getBlue() * 0.6),1f};
			
		} else {
			spec = new float[]{0.7f,0.7f,0.7f,1f};
			shine = 0.5f*128f;
			
			amb = new float[]{(float)(color.getRed() * 0.8),(float)(color.getGreen() * 0.8), (float)(color.getBlue() * 0.8),1f};
			diff = new float[]{(float)(color.getRed() * 0.4),(float)(color.getGreen() * 0.4), (float)(color.getBlue() * 0.4),1f};
		}
	}
	
	public float[] getAmbient() {
		return amb;
	}
	public float[] getDiffuse() {
		return diff;
	}
	public float[] getSpecular() {
		return spec;
	}
	public float getShine() {
		return shine;
	}
}
