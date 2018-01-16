package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class Parameterize_MTAB6_main {
	public static void main(String[] args) {
		String folderPath = "/Users/ecpc32/Desktop/mtab6/main_old";
		
		Parameterize_MTAB6_main p = new Parameterize_MTAB6_main();
		List<double[]> oris = p.getParams_step2();
				
		List<String> shapeTypes = new ArrayList<String>();
		shapeTypes.add("c");
		shapeTypes.add("l");
		
		List<String> shadeIds = new ArrayList<String>();
		shadeIds.add("SHADE");
//		shadeIds.add("TWOD");
		
		for (String shapeType : shapeTypes) {
			int rotId = 1;
			for (double[] ori : oris) {
				for (String shadeId : shadeIds) {
					System.out.println("Generating stimulus: " + shapeType + " | Rotation: " + rotId + " | Shade: " + shadeId);
					List<Double> ids = new ArrayList<Double>();
					List<MatchStick> objs = new ArrayList<MatchStick>();
					
					ids.add((double)rotId);
					
					objs.add(new MatchStick());
					objs.get(0).setDoCenter(true);
					objs.get(0).genMatchStickFromFile_paramChange(folderPath + "/" + shapeType + "/" + shapeType + ".xml",ori);
					objs.get(0).setTextureType(shadeId);
					if (shadeId.equalsIgnoreCase("SHADE")) {
						objs.get(0).setStimColor(new RGBColor(1,1,1));
						MStickSpec spec = new MStickSpec();
						spec.setMStickInfo(objs.get(0));
						spec.writeInfo2File(folderPath + "/" + shapeType + "/spec/" + shapeType + "_" + rotId);
					}
					
					PNGmaker pngMaker = new PNGmaker();
					pngMaker.setSaveToFile(true);
					pngMaker.setImageFolderName(folderPath + "/" + shapeType + "/img");
					pngMaker.createAndSavePNGsfromObjs(objs, ids, shapeType + "_" + shadeId + "_");
				}
				rotId++;
			}
		}
	}
	
	public List<double[]> getParams_step2() {
		 List<double[]> params = new ArrayList<double[]>();
		 // int[] nonRedIds = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,121,122,123,124,125,126,127,128,129,130,131,132,137,138,139,140,141,142,143,144,153,154,155,156,157,158,159,160,161,162,163,164};
		 int[] nonRedIds = {1};
		 int count = 1;
		 
		 for (int i=0; i<359; i+=45)
			 for (int j=0; j<359; j+=45)
				 for (int k=0; k<359; k+=45) { 
					 if (searchArray(nonRedIds, count)) 
						 params.add(new double[]{i,j,k});
					 
					 count++;
				 }

		 return params;
	}
	
	public static boolean searchArray(int[] arr, int targetValue) {
		for (int s : arr) {
			if (s == targetValue)
				return true;
		}
		return false;
	}
}
