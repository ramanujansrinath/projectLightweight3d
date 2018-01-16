package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class Parameterize_MTAB6 {
	public static void main(String[] args) {
		String folderPath = "/Users/ecpc32/Desktop/mtab6/main";
		
		Parameterize_MTAB6 p = new Parameterize_MTAB6();
		List<double[]> params = p.getParams_step2();
		
		int nRot = params.size();
		
		List<String> shapeTypes = new ArrayList<String>();
		shapeTypes.add("c");
		shapeTypes.add("l");
				
		for (int jj=0; jj < shapeTypes.size(); jj++) {
			String sType = shapeTypes.get(jj);
			for (int ii=0; ii < nRot; ii++) {
				List<Double> ids = new ArrayList<Double>();
				List<MatchStick> objs = new ArrayList<MatchStick>();
				
				ids.add((double)ii+1);
				
				double[] fr = params.get(ii);
				
				objs.add(new MatchStick());
				objs.get(0).setDoCenter(true);
				objs.get(0).genMatchStickFromFile_paramChange(folderPath + "/" + sType + "/" + sType + ".xml",fr);
				objs.get(0).setTextureType("SHADE");
				objs.get(0).setStimColor(new RGBColor(1,1,1)); //0.6f,0.6f,0.6f
				
				MStickSpec spec = new MStickSpec();
				spec.setMStickInfo(objs.get(0));
				spec.writeInfo2File(folderPath + "/" + sType + "/spec/" + sType + "_" + (ii+1));
				
				System.out.println(ii);
				PNGmaker pngMaker = new PNGmaker();
				pngMaker.setSaveToFile(true);
				pngMaker.setImageFolderName(folderPath + "/" + sType + "/img");
				pngMaker.createAndSavePNGsfromObjs(objs, ids, sType + "_");
			}
			
		}
		
	}
	
	public List<double[]> getParams_step1() {
		 List<double[]> params = new ArrayList<double[]>();
	 
		 // 0
		 params.add(new double[]{0,0,0});
		 params.add(new double[]{0,0,90});
		 params.add(new double[]{0,0,180});
		 params.add(new double[]{0,0,270});
//		 
		 params.add(new double[]{0,90,0});
		 params.add(new double[]{0,90,90});
		 params.add(new double[]{0,90,180});
		 params.add(new double[]{0,90,270});
		 
//		 params.add(new double[]{0,180,0});
//		 params.add(new double[]{0,180,90});
//		 params.add(new double[]{0,180,180});
//		 params.add(new double[]{0,180,270});
//		 
//		 params.add(new double[]{0,270,0});
//		 params.add(new double[]{0,270,90});
//		 params.add(new double[]{0,270,180});
//		 params.add(new double[]{0,270,270});
		 
		 // 90
		 params.add(new double[]{90,0,0});
		 params.add(new double[]{90,0,90});
//		 params.add(new double[]{90,0,180});
//		 params.add(new double[]{90,0,270});
		 
//		 params.add(new double[]{90,90,0});
//		 params.add(new double[]{90,90,90});
//		 params.add(new double[]{90,90,180});
//		 params.add(new double[]{90,0,270});
		 
		 params.add(new double[]{90,180,0});
		 params.add(new double[]{90,180,90});
//		 params.add(new double[]{90,180,180});
//		 params.add(new double[]{90,0,270});
		 
//		 params.add(new double[]{90,270,0});
//		 params.add(new double[]{90,270,90});
//		 params.add(new double[]{90,270,180});
//		 params.add(new double[]{90,270,270});
		 
		 // 180
//		 params.add(new double[]{180,0,0});
//		 params.add(new double[]{180,0,90});
//		 params.add(new double[]{180,0,180});
//		 params.add(new double[]{180,0,270});
//		 
//		 params.add(new double[]{180,90,0});
//		 params.add(new double[]{180,90,90});
//		 params.add(new double[]{180,90,180});
//		 params.add(new double[]{180,0,270});
//		 
//		 params.add(new double[]{180,180,0});
//		 params.add(new double[]{180,180,90});
//		 params.add(new double[]{180,180,180});
//		 params.add(new double[]{180,0,270});
//		 
//		 params.add(new double[]{180,270,0});
//		 params.add(new double[]{180,270,90});
//		 params.add(new double[]{180,270,180});
//		 params.add(new double[]{180,270,270});
		 
		 
		 return params;
	}
	
	public List<double[]> getParams_step2() {
		 List<double[]> params = new ArrayList<double[]>();
	 
		 for (int i=0; i<359; i+=45)
			 for (int j=0; j<359; j+=45)
				 for (int k=0; k<359; k+=45)
					 params.add(new double[]{i,j,k});
		 return params;
	}
}
