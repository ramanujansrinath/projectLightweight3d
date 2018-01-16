package org.xper.drawing.stick;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.xper.drawing.drawables.Drawable;
import org.xper.utils.Coordinates2D;
import org.xper.utils.Lighting;
import org.xper.utils.RGBColor;
import org.xper.utils.Lighting.Material;

public class MakeFromVertFace implements Drawable {
    
    public Point3d[] vect_info = new Point3d[25000]; // not sure if 15000 will work..let's see
    public int[][] facInfo = new int[45000][3];
    public Vector3d[] normMat_info = new Vector3d[25000];
    public int nVect;
    public int nFac;
    
    boolean doLighting = true;
    RGBColor stimColor = new RGBColor(0.6f,0.6f,0.6f);
    private String textureType = "SHADE";
    
    double light_angle = Math.toRadians(90);
	double light_distance = 200;
//	float[] light_position = new float[]{(float) (light_distance*Math.cos(light_angle)),0, (float) (light_distance*Math.sin(light_angle)),1};
	float[] light_position = new float[]{0f,0f,200f,1f};
//	float[] light_spotDir = new float[]{-1f,-1f,0f,0};
//	float light_spotAngle = 20;

	Occluder occluder;
	List<Aperture> apertures;
	float occluderAlpha = 0.95f;
	int shaderProgram = 0;
	
    public void setInfo(int inVect, Point3d[] ivect_info, Vector3d[] inormMat_info, int inFac, int[][] ifacInfo) {
        this.nVect = inVect;
        this.nFac = inFac;
        
        for (int i=1; i<=nVect; i++) {
            this.vect_info[i] = new Point3d( ivect_info[i]);
            this.normMat_info[i] = new Vector3d(inormMat_info[i]);
        }

        for (int i=0; i<nFac; i++)
            for (int j=0; j<3; j++)
            		this.facInfo[i][j] = ifacInfo[i][j];
    }
    
    public void setInfo(int inVect, Point3d[] ivect_info, Vector3d[] inormMat_info, int inFac, int[][] ifacInfo, Aperture aper) {
        this.nVect = inVect;
        this.nFac = inFac;
        
        for (int i=1; i<=nVect; i++) {
            this.vect_info[i] = new Point3d( ivect_info[i]);
            this.normMat_info[i] = new Vector3d(inormMat_info[i]);
        }

        for (int i=0; i<nFac; i++)
            for (int j=0; j<3; j++)
            		this.facInfo[i][j] = ifacInfo[i][j];
        
        initOccluder(aper);
    }

	@Override
	public void draw() {
		init();
		if (Material.valueOf(textureType) == Material.TWOD) {
	    		doLighting = false;
	    		GL11.glDisable(GL11.GL_LIGHTING);
		} else {
			doLighting = true;
    			GL11.glEnable(GL11.GL_LIGHTING);
		}
	    drawVect();
		
	    if (apertures != null)
	    		drawOccluder();
	}

	protected void init() {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);    // Enables hidden-surface removal allowing for use of depth buffering
		GL11.glEnable(GL11.GL_AUTO_NORMAL);   // Automatic normal generation when doing NURBS, if not enabled we have to provide the normals ourselves if we want to have a lighted image (which we do).
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);

        this.initLight();
    }
    protected void initLight() {    	
	    	Lighting light = new Lighting();
	    	light.setLightColor(stimColor);
	    	light.setMaterial(Material.valueOf(textureType));
	
	    float[] mat_ambient = light.getAmbient();
	    float[] mat_diffuse = light.getDiffuse();
	    float[] mat_specular = light.getSpecular();
	    float mat_shininess = light.getShine();
	
	    // x: horizontal: positive right
	    // y: vertical: positive up
	    // z: in-out: positive out
	    // w: directional or not: 1=non-directional
	
	    FloatBuffer mat_specularBuffer = BufferUtils.createFloatBuffer(mat_specular.length);
	    mat_specularBuffer.put(mat_specular).flip();
	
	    FloatBuffer mat_ambientBuffer = BufferUtils.createFloatBuffer(mat_ambient.length);
	    mat_ambientBuffer.put(mat_ambient).flip();
	
	    FloatBuffer mat_diffuseBuffer = BufferUtils.createFloatBuffer(mat_diffuse.length);
	    mat_diffuseBuffer.put(mat_diffuse).flip();
	
	    FloatBuffer light_positionBuffer = BufferUtils.createFloatBuffer(light_position.length);
	    light_positionBuffer.put(light_position).flip();
	    
	//    FloatBuffer light_spotDirBuffer = BufferUtils.createFloatBuffer(light_spotDir.length);
	//    light_spotDirBuffer.put(light_spotDir).flip();
	    
	
	    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, mat_specularBuffer);
	    GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, mat_shininess);
	    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, mat_ambientBuffer);
	    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, mat_diffuseBuffer);
	
	    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, light_positionBuffer);
	    
	//    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION, light_spotDirBuffer);
	//    GL11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPOT_CUTOFF, light_spotAngle);
	
	    // make sure white light
	    float[] white_light = { 1.0f, 1.0f, 1.0f, 1.0f};
	    FloatBuffer wlightBuffer = BufferUtils.createFloatBuffer( white_light.length);
	    wlightBuffer.put(white_light).flip();
	    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, wlightBuffer);
	    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, wlightBuffer);
	
	    GL11.glEnable(GL11.GL_LIGHT0);
    }

    protected void initOccluder(Aperture aper) {
    		occluder = new Occluder();
    		occluder.color = new RGBColor(0.12f,0.12f,0.12f);
    		occluder.leftBottom = new Point3d(-2.7, -2.7, 1);
    		occluder.rightTop = new Point3d(2.7,2.7, 1);
    		
    		apertures = new ArrayList<Aperture>();
    		apertures.add(aper);
    		apertures.add(new Aperture(0,0,0,0,false));
    }
   
    public void drawVect() {
		GL11.glColor3f(stimColor.getRed(),stimColor.getGreen(),stimColor.getBlue());
		
		for (int i=0; i< nFac; i++) {
			GL11.glBegin(GL11.GL_TRIANGLES);
			
			Point3d p1 = vect_info[ facInfo[i][0]];
			Point3d p2 = vect_info[ facInfo[i][1]];
			Point3d p3 = vect_info[ facInfo[i][2]];
			Vector3d v1 = normMat_info[ facInfo[i][0]];
			Vector3d v2 = normMat_info[ facInfo[i][1]];
			Vector3d v3 = normMat_info[ facInfo[i][2]];
			
			if ( v1.length() >= 1.01 || v1.length() <= 0.99) {
				System.out.println("error in v1 length as:");
				System.out.println(v1.x +" "+ v1.y + " " +v1.z);
				System.out.println(v1.length());
			}
			if ( v2.length() >= 1.01 || v2.length() <= 0.99) {
				System.out.println("error in v2 length as:");
				System.out.println(v2.x +" "+ v2.y + " " +v2.z);
				System.out.println(v2.length());
			}
			if ( v3.length() >= 1.01 || v3.length() <= 0.99) {
				System.out.println("error in v3 length as:");
				System.out.println(v3.x +" "+ v3.y + " " +v3.z);
				System.out.println(v3.length());
			}
			
			GL11.glNormal3d( v1.x, v1.y, v1.z);
			GL11.glVertex3d( p1.x, p1.y, p1.z);
			GL11.glNormal3d( v2.x, v2.y, v2.z);
			GL11.glVertex3d( p2.x, p2.y, p2.z);
			GL11.glNormal3d( v3.x, v3.y, v3.z);
			GL11.glVertex3d( p3.x, p3.y, p3.z);
			
			GL11.glEnd();
		}
		GL11.glDisable(GL11.GL_LIGHTING);
    }
    
    public void scaleObj(double scaleFactor) {
        for (int i=1; i<=nVect; i++) {
            vect_info[i].x *= scaleFactor;
            vect_info[i].y *= scaleFactor;
            vect_info[i].z *= scaleFactor;
        }
    }
    
    void drawOccluder() {
    		GL11.glDisable(GL11.GL_DEPTH_TEST);
    		Point3d lb = occluder.getLeftBottom();
	    	Point3d rt = occluder.getRightTop();

        float width = (float)((Math.abs(lb.x - rt.x)))/2;
        float height = (float)((Math.abs(lb.y - rt.y)))/2;

        Coordinates2D center = new Coordinates2D((lb.x + rt.x)/2, (lb.y + rt.y)/2);

        float marginWidth = 1.3f;

        float s1 = (float)(apertures.get(0).getS());
        float s2 = (float)(apertures.get(1).getS());

        if (!apertures.get(0).getIsActive())
        	s1 = 0.0f;
        if (!apertures.get(1).getIsActive())
        	s2 = 0.0f;

        float x1 = (float)(apertures.get(0).x);
        float y1 = (float)(apertures.get(0).y);
        float x2 = (float)(apertures.get(1).x);
        float y2 = (float)(apertures.get(1).y);

        float[] apertureSpecs = {x1,y1,s1,x2,y2,s2};

        FloatBuffer apertureSpecBuffer = BufferUtils.createFloatBuffer(2 * 3); // numHoles * numSpecsPerHole
        apertureSpecBuffer.put(apertureSpecs);
        apertureSpecBuffer.rewind();

        createShaders();

    //  critical ...
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // It is recommended to have the GLSL shaderProgram in use before setting values
        GL20.glUseProgram(shaderProgram);
        int location = GL20.glGetUniformLocation(shaderProgram, "marginWidth");
        GL20.glUniform1f(location, marginWidth);

        location = GL20.glGetUniformLocation(shaderProgram, "top");
        GL20.glUniform1f(location, (float)center.getY() + (float) height);

        location = GL20.glGetUniformLocation(shaderProgram, "bottom");
        GL20.glUniform1f(location, (float)center.getY() - (float) height);

        location = GL20.glGetUniformLocation(shaderProgram, "left");
        GL20.glUniform1f(location, ((float)center.getX() - width));

        location = GL20.glGetUniformLocation(shaderProgram, "right");
        GL20.glUniform1f(location, ((float)center.getX() + width ));

        location = GL20.glGetUniformLocation(shaderProgram, "alphaGain");
        GL20.glUniform1f(location, occluderAlpha);

        location = GL20.glGetUniformLocation(shaderProgram, "numHoles");
        GL20.glUniform1i(location, apertures.size());

        location = GL20.glGetUniformLocation(shaderProgram, "red");
        GL20.glUniform1f(location, occluder.color.getRed());
        
        location = GL20.glGetUniformLocation(shaderProgram, "green");
        GL20.glUniform1f(location, occluder.color.getGreen());
        
        location = GL20.glGetUniformLocation(shaderProgram, "blue");
        GL20.glUniform1f(location, occluder.color.getBlue());
        
        
        int loc4 = GL20.glGetUniformLocation(shaderProgram, "specs");
        GL20.glUniform1(loc4, apertureSpecBuffer);

        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(center.getX() - width - marginWidth, center.getY() - height - marginWidth);
            GL11.glVertex2d(center.getX() - width - marginWidth, center.getY() + height + marginWidth);
            GL11.glVertex2d(center.getX() + width + marginWidth, center.getY() + height + marginWidth);
            GL11.glVertex2d(center.getX() + width + marginWidth, center.getY() - height - marginWidth);
        GL11.glEnd();

        // "deactivate" the shader
        GL20.glUseProgram(0);
    }
    void createShaders(){

        int vertShader = 0, fragShader = 0;

        try {
            vertShader = createShader("screen.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragShader = createShader("screen.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        }
        catch(Exception exc) {
            exc.printStackTrace();
            return;
        }
        finally {
            if(vertShader == 0 || fragShader == 0)
                return;
        }

        shaderProgram = ARBShaderObjects.glCreateProgramObjectARB();

        if(shaderProgram == 0)
            return;

        /*
        * if the vertex and fragment shaders setup sucessfully,
        * attach them to the shader program, link the shader program
        * (into the GL context I suppose), and validate
        */
        ARBShaderObjects.glAttachObjectARB(shaderProgram, vertShader);
        ARBShaderObjects.glAttachObjectARB(shaderProgram, fragShader);

        ARBShaderObjects.glLinkProgramARB(shaderProgram);
        if (ARBShaderObjects.glGetObjectParameteriARB(shaderProgram, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(shaderProgram));
            return;
        }

        ARBShaderObjects.glValidateProgramARB(shaderProgram);
        if (ARBShaderObjects.glGetObjectParameteriARB(shaderProgram, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(shaderProgram));
            return;
        }
    }
    private int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if(shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " +  getLogInfo(shader));

            return shader;
        }
        catch(Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }
    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        InputStream inptStrm = getClass().getResourceAsStream(filename);
        Exception exception = null;

        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(inptStrm,"UTF-8"));

            Exception innerExc= null;
            try {
                String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
                exception = exc;
            }
            finally {
                try {
                    reader.close();
                }
                catch(Exception exc) {
                    if(innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }

            if(innerExc != null)
                throw innerExc;
        }
        catch(Exception exc) {
            exception = exc;
        }
        finally {
            try {
            	inptStrm.close();
            }
            catch(Exception exc) {
                if(exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }

            if(exception != null)
                throw exception;
        }

        return source.toString();
    }

    
    public void setStimColor(RGBColor color) {
    		this.stimColor = color;
    }
    
    public void setDoLighting(boolean dl) {
    		this.doLighting = dl;
    }
    
    public void setTextureType(String tt) {
    		textureType = tt;
    }
}














