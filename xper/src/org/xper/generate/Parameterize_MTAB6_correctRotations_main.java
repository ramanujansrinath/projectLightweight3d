package org.xper.generate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MakeFromVertFace;
import org.xper.utils.RGBColor;

public class Parameterize_MTAB6_correctRotations_main {
	public static void main(String[] args) {
		String folderPath = "/Users/ecpc32/Desktop/mtab6/main";
				
		List<String> shapeTypes = new ArrayList<String>();
		shapeTypes.add("c");
		shapeTypes.add("l");
		
		List<String> shadeIds = new ArrayList<String>();
		shadeIds.add("SHADE");
		shadeIds.add("TWOD");
		
		for (String shapeType : shapeTypes) {
			for (int rotId=1; rotId<=104; rotId++) {
				MakeFromVertFace obj = Parameterize_MTAB6_correctRotations_main.setVertFaceInfo(folderPath,shapeType,rotId);
				List<Double> ids = new ArrayList<Double>();
				List<MakeFromVertFace> objs = new ArrayList<MakeFromVertFace>();
				ids.add((double)rotId);
				objs.add(obj);
				
				for (String shadeId : shadeIds) {
					System.out.println("Generating stimulus: " + shapeType + " | Rotation: " + rotId + " | Shade: " + shadeId);	
					
					objs.get(0).setTextureType(shadeId);
					
					if (shadeId.equalsIgnoreCase("SHADE")) 
						objs.get(0).setStimColor(new RGBColor(1,1,1));
					else
						objs.get(0).setStimColor(new RGBColor(0.6f,0.6f,0.6f));
					
					PNGmaker pngMaker = new PNGmaker();
					pngMaker.setSaveToFile(true);
					pngMaker.setImageFolderName(folderPath + "/" + shapeType + "/img");
					pngMaker.createAndSavePNGsfromObjs(objs, ids, shapeType + "_" + shadeId + "_");
				}
			}
		}
	}
	
	public static MakeFromVertFace setVertFaceInfo(String folderPath, String shapeType, int rotId) {
		MakeFromVertFace mfvf = new MakeFromVertFace();
		 
		Point3d[] vect_info = new Point3d[25000]; // not sure if 15000 will work..let's see
	    int[][] facInfo = new int[45000][3];
	    Vector3d[] normMat_info = new Vector3d[25000];
	    int nVect = 0;
	    int nNorm = 0;
	    int nFac = 0;
		
		// vert file
		String fileName = folderPath + "/" + shapeType + "/spec/" + shapeType + "_" + rotId + "_vert.txt";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] numbers = line.split(",");
		        nVect++;
		        vect_info[nVect] = new Point3d();
		        vect_info[nVect].x = Double.parseDouble(numbers[0]);
		        vect_info[nVect].y = Double.parseDouble(numbers[1]);
		        vect_info[nVect].z = Double.parseDouble(numbers[2]);
		    }
		    
		    br.close();
	    } catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    	} catch (IOException e) {
			e.printStackTrace();
		}
		
		// face file
		fileName = folderPath + "/" + shapeType + "/spec/" + shapeType + "_" + rotId + "_face.txt";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] numbers = line.split(",");
		        facInfo[nFac][0] = Integer.parseInt(numbers[0]);
		        facInfo[nFac][1] = Integer.parseInt(numbers[1]);
		        facInfo[nFac][2] = Integer.parseInt(numbers[2]);
		        
		        nFac++;
		    }
		    
		    br.close();
	    } catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    	} catch (IOException e) {
			e.printStackTrace();
		}
		
		// norm file
		fileName = folderPath + "/" + shapeType + "/spec/" + shapeType + "_" + rotId + "_norm.txt";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] numbers = line.split(",");
		        nNorm++;
		        normMat_info[nNorm] = new Vector3d();
		        normMat_info[nNorm].x = Double.parseDouble(numbers[0]);
		        normMat_info[nNorm].y = Double.parseDouble(numbers[1]);
		        normMat_info[nNorm].z = Double.parseDouble(numbers[2]);
		    }
		    
		    br.close();
	    } catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    	} catch (IOException e) {
			e.printStackTrace();
		}

		mfvf.setInfo(nVect, vect_info,normMat_info, nFac, facInfo);
		
		return mfvf;
	}
}
