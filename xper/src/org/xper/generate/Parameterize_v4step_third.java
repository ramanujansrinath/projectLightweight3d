package org.xper.generate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class Parameterize_v4step_third {
	public static void main(String[] args) {
		String folderPath = "/Users/ecpc32/Desktop/v4_step2";
		
		Parameterize_v4step_third p = new Parameterize_v4step_third();
		
		for (int kk=1; kk<=51; kk++) {
			double[] fr = p.getFinalRot(kk);
			int oriIdxToPreserve = p.getOriChangeIdx(kk);
			int specIdToLoad = p.getSpecId(kk);
			MatchStick origObj = new MatchStick(); 
			origObj.genMatchStickFromFile_paramChange(folderPath + "/" + specIdToLoad + "_spec.xml",fr);
				
			List<Double> ids = new ArrayList<Double>();
			List<MatchStick> objs = new ArrayList<MatchStick>();
			
			List<Double> tdids = new ArrayList<Double>();
			List<MatchStick> tdobjs = new ArrayList<MatchStick>();
			
			File theDir = new File(folderPath + "/images/" + kk);
			if (!theDir.exists()); theDir.mkdir();
			theDir = new File(folderPath + "/specs/" + kk);
			if (!theDir.exists()); theDir.mkdir();
			
			ids.clear(); objs.clear();
			tdids.clear(); tdobjs.clear();
			for (int jj=0; jj<200; jj++) {
				ids.add((double)jj+1);
				objs.add(new MatchStick());
				double[] fr_new = p.getFinalRotNew_preserve1(fr,oriIdxToPreserve,false);
				objs.get(jj).genMatchStickFromFile_paramChange(folderPath + "/" + specIdToLoad + "_spec.xml",fr_new);
				// objs.get(jj).copyFrom(origObj);
				objs.get(jj).mutate(0);
				if (Math.random() > 0.5)
					objs.get(jj).setTextureType("SHADE");
				else
					objs.get(jj).setTextureType("SPECULAR");
				objs.get(jj).setStimColor(new RGBColor(1,1,1)); //0.6f,0.6f,0.6f
				
				MStickSpec spec = new MStickSpec();
				spec.setMStickInfo(objs.get(jj));
				spec.writeInfo2File(folderPath + "/specs/" + kk + "/" + (jj+1));
				
				tdids.add((double)jj+401);
				tdobjs.add(new MatchStick());
				tdobjs.get(jj).copyFrom(objs.get(jj));
				tdobjs.get(jj).setTextureType("TWOD");
				tdobjs.get(jj).setStimColor(new RGBColor(0.6f,0.6f,0.6f));
				
				System.out.println(kk + "_" + jj);
			}
			
			PNGmaker pngMaker = new PNGmaker();
			pngMaker.setSaveToFile(true);
			pngMaker.setImageFolderName(folderPath + "/images/" + kk);
			pngMaker.createAndSavePNGsfromObjs(objs, ids, "");
			
			PNGmaker pngMaker2d = new PNGmaker();
			pngMaker2d.setSaveToFile(true);
			pngMaker2d.setImageFolderName(folderPath + "/images/" + kk);
			pngMaker2d.createAndSavePNGsfromObjs(tdobjs, tdids, "");
			
			ids.clear(); objs.clear();
			tdids.clear(); tdobjs.clear();
			for (int jj=0; jj<200; jj++) {
				ids.add((double)jj+201);
				objs.add(new MatchStick());
				double[] fr_new = p.getFinalRotNew_preserve1(fr,oriIdxToPreserve,true);
				objs.get(jj).genMatchStickFromFile_paramChange(folderPath + "/" + specIdToLoad + "_spec.xml",fr_new);
				boolean res = objs.get(jj).mutate(5);
				while (!res)
					res = objs.get(jj).mutate(5);
				
				if (Math.random() > 0.5)
					objs.get(jj).setTextureType("SHADE");
				else
					objs.get(jj).setTextureType("SPECULAR");
				objs.get(jj).setStimColor(new RGBColor(1,1,1)); //0.6f,0.6f,0.6f
				
				MStickSpec spec = new MStickSpec();
				spec.setMStickInfo(objs.get(jj));
				spec.writeInfo2File(folderPath + "/specs/" + kk + "/" + (jj+201));
				
				tdids.add((double)jj+601);
				tdobjs.add(new MatchStick());
				tdobjs.get(jj).copyFrom(objs.get(jj));
				tdobjs.get(jj).setTextureType("TWOD");
				tdobjs.get(jj).setStimColor(new RGBColor(0.6f,0.6f,0.6f));
			}
			
			PNGmaker pngMaker_ang = new PNGmaker();
			pngMaker_ang.setSaveToFile(true);
			pngMaker_ang.setImageFolderName(folderPath + "/images/" + kk);
			pngMaker_ang.createAndSavePNGsfromObjs(objs, ids, "");
			
			PNGmaker pngMakerang2d = new PNGmaker();
			pngMakerang2d.setSaveToFile(true);
			pngMakerang2d.setImageFolderName(folderPath + "/images/" + kk);
			pngMakerang2d.createAndSavePNGsfromObjs(tdobjs, tdids, "");
			
		}
		
	}
	
    public double[] getFinalRot(int specId) {
        switch(specId) {
            case 1: return new double[]{90,180,0};  
            case 2: return new double[]{90,180,45}; 
            case 3: return new double[]{90,180,90}; 
            case 4: return new double[]{90,180,135}; 
            case 5: return new double[]{90,0,0}; 
            case 6: return new double[]{90,0,45}; 
            case 7: return new double[]{90,0,90}; 
            case 8: return new double[]{90,0,135}; 

            case 9: return new double[]{90,225,0}; 
            case 10: return new double[]{90,270,0}; 
            case 11: return new double[]{90,315,0}; 
            case 12: return new double[]{90,45,0}; 
            case 13: return new double[]{90,90,0}; 
            case 14: return new double[]{90,135,0}; 

            case 15: return new double[]{135,180,0}; 
            case 16: return new double[]{180,180,0}; 
            case 17: return new double[]{225,180,0}; 
            case 18: return new double[]{315,180,0}; 
            case 19: return new double[]{0,180,0}; 
            case 20: return new double[]{45,180,0}; 

            // =================

            case 21: return new double[]{90,180,0}; 
            case 22: return new double[]{90,180,45}; 
            case 23: return new double[]{90,180,90}; 
            case 24: return new double[]{90,180,135}; 
            case 25: return new double[]{90,0,0}; 
            case 26: return new double[]{90,0,45}; 
            case 27: return new double[]{90,0,90}; 
            case 28: return new double[]{90,0,135}; 

            case 29: return new double[]{90,225,0}; 
            case 30: return new double[]{90,270,0}; 
            case 31: return new double[]{90,315,0}; 
            case 32: return new double[]{90,45,0}; 
            case 33: return new double[]{90,90,0}; 
            case 34: return new double[]{90,135,0}; 

            case 35: return new double[]{135,180,0}; 
            case 36: return new double[]{180,180,0}; 
            case 37: return new double[]{225,180,0}; 
            case 38: return new double[]{0,180,0}; 
            case 39: return new double[]{45,180,0}; 

            // =================

            case 40: return new double[]{90,180,0}; 
            case 41: return new double[]{90,180,45}; 
            case 42: return new double[]{90,180,90}; 
            case 43: return new double[]{90,180,135}; 

            case 44: return new double[]{90,225,0}; 
            case 45: return new double[]{90,270,0}; 
            case 46: return new double[]{90,45,0}; 
            case 47: return new double[]{90,90,0};              

            // =================
            case 48: return new double[]{90,225,0}; 
            case 49: return new double[]{90,270,0}; 
            case 50: return new double[]{90,45,0}; 
            case 51: return new double[]{90,90,0}; 
        }
    return null;
    }
    
    public int getOriChangeIdx(int specId) {
        switch(specId) {
            case 1: return 2;
            case 2: return 2; 
            case 3: return 2; 
            case 4: return 2; 
            case 5: return 2; 
            case 6: return 2; 
            case 7: return 2; 
            case 8: return 2; 

            case 9: return 1; 
            case 10: return 1; 
            case 11: return 1; 
            case 12: return 1; 
            case 13: return 1; 
            case 14: return 1; 

            case 15: return 0; 
            case 16: return 0; 
            case 17: return 0; 
            case 18: return 0; 
            case 19: return 0;  
            case 20: return 0;  

            // =================

            case 21: return 2;  
            case 22: return 2;  
            case 23: return 2; 
            case 24: return 2; 
            case 25: return 2; 
            case 26: return 2; 
            case 27: return 2; 
            case 28: return 2; 

            case 29: return 1; 
            case 30: return 1;
            case 31: return 1; 
            case 32: return 1; 
            case 33: return 1; 
            case 34: return 1; 

            case 35: return 0; 
            case 36: return 0; 
            case 37: return 0; 
            case 38: return 0; 
            case 39: return 0; 

            // =================

            case 40: return 2; 
            case 41: return 2; 
            case 42: return 2; 
            case 43: return 2; 

            case 44: return 1;
            case 45: return 1;
            case 46: return 1;
            case 47: return 1;

            // =================
            case 48: return 1;
            case 49: return 1;
            case 50: return 1;
            case 51: return 1;
        }
    return 0;
    }
    
    public int getSpecId(int specId) {
        switch(specId) {
            case 1: return 1;
            case 2: return 1; 
            case 3: return 1; 
            case 4: return 1; 
            case 5: return 1; 
            case 6: return 1; 
            case 7: return 1; 
            case 8: return 1; 

            case 9: return 1; 
            case 10: return 1; 
            case 11: return 1; 
            case 12: return 1; 
            case 13: return 1; 
            case 14: return 1; 

            case 15: return 1; 
            case 16: return 1; 
            case 17: return 1; 
            case 18: return 1; 
            case 19: return 1;  
            case 20: return 1;  

            // =================

            case 21: return 2;  
            case 22: return 2;  
            case 23: return 2; 
            case 24: return 2; 
            case 25: return 2; 
            case 26: return 2; 
            case 27: return 2; 
            case 28: return 2; 

            case 29: return 2; 
            case 30: return 2;
            case 31: return 2; 
            case 32: return 2; 
            case 33: return 2; 
            case 34: return 2; 

            case 35: return 2; 
            case 36: return 2; 
            case 37: return 2; 
            case 38: return 2; 
            case 39: return 2; 

            // =================

            case 40: return 1; 
            case 41: return 1; 
            case 42: return 1; 
            case 43: return 1; 

            case 44: return 1;
            case 45: return 1;
            case 46: return 1;
            case 47: return 1;

            // =================
            case 48: return 2;
            case 49: return 2;
            case 50: return 2;
            case 51: return 2;
        }
    return 0;
    }

    public double[] getFinalRotNew_preserve1(double[] fr, int oriToPreserve, boolean highChange) {
    	double[] fr_new = new double[]{0,0,0};
    	
    	double randAngle1,randAngle2;
    	if (highChange) {
	    	randAngle1 = 10 + 35*Math.random();
	    	randAngle2 = 10 + 35*Math.random();
	    	if (Math.random()>0.5) randAngle1 = -randAngle1;
	    	if (Math.random()>0.5) randAngle2 = -randAngle2;
    	} else {
    		randAngle1 = -10 + 20*Math.random();
	    	randAngle2 = -10 + 20*Math.random();
    	}
    	
    	switch(oriToPreserve) {
	    	case 0: fr_new[0] = fr[0]; fr_new[1] = fr[1] + randAngle1; fr_new[2] = fr[2] + randAngle2; break;
	    	case 1: fr_new[1] = fr[1]; fr_new[2] = fr[2] + randAngle1; fr_new[0] = fr[0] + randAngle2; break;
	    	case 2: fr_new[2] = fr[2]; fr_new[0] = fr[0] + randAngle1; fr_new[1] = fr[1] + randAngle2; break;
    	}
    	return fr_new;
    }
}
