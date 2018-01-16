package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class Parameterize {
	public static void main(String[] args) {
		String folderPath = args[0];
		
		Parameterize p = new Parameterize();
		List<double[]> params = p.getParams();
		
		List<Double> ids = new ArrayList<Double>();
		List<MatchStick> objs = new ArrayList<MatchStick>();
		int nShape = params.size();
		for (int ii=0; ii < nShape; ii++) {
			ids.add((double)ii);
			
			double[] fr = params.get(ii);
			
			objs.add(new MatchStick());
			objs.get(ii).genMatchStickFromFile_paramChange(folderPath + "/" + args[1] + "_spec.xml",fr);
			objs.get(ii).setTextureType(args[2]);
			objs.get(ii).setDoClouds(Boolean.parseBoolean(args[3]));
			objs.get(ii).setStimColor(new RGBColor(1,1,1)); //0.6f,0.6f,0.6f
			
			System.out.println(ii);
		}
		PNGmaker pngMaker = new PNGmaker();
		pngMaker.setSaveToFile(true);
		pngMaker.setImageFolderName(folderPath + "/" + args[1]);
		pngMaker.createAndSavePNGsfromObjs(objs, ids,"");
	}
	
	public List<double[]> getParams() {
		 List<double[]> params = new ArrayList<double[]>();
		 
		 /* T
		 params.add(new double[]{90,0,90});
		 params.add(new double[]{90,0,135});
		 params.add(new double[]{90,0,180});
		 params.add(new double[]{90,0,225});
		 params.add(new double[]{90,0,270});
		 params.add(new double[]{90,0,315});
		 params.add(new double[]{90,0,0});
		 params.add(new double[]{90,0,45});
		 
		 params.add(new double[]{90,45,90});
		 params.add(new double[]{90,90,90});
		 params.add(new double[]{90,135,90});
		 params.add(new double[]{90,180,90});
		 params.add(new double[]{90,225,90});
		 params.add(new double[]{90,270,90});
		 params.add(new double[]{90,315,90});
		 
		 params.add(new double[]{135,0,90});
		 params.add(new double[]{180,0,90});
		 params.add(new double[]{225,0,90});
		 */
		 
		 // S
//		 params.add(new double[]{-90,90,0});
		 
		 params.add(new double[]{90,180,0});
		 params.add(new double[]{90,180,45});
		 params.add(new double[]{90,180,90});
		 params.add(new double[]{90,180,135});
		 params.add(new double[]{90,0,0});
		 params.add(new double[]{90,0,45});
		 params.add(new double[]{90,0,90});
		 params.add(new double[]{90,0,135});
		 
		 params.add(new double[]{90,225,0});
		 params.add(new double[]{90,270,0});
		 params.add(new double[]{90,315,0});
		 params.add(new double[]{90,45,0});
		 params.add(new double[]{90,90,0});
		 params.add(new double[]{90,135,0});
		 
		 params.add(new double[]{135,180,0});
		 params.add(new double[]{180,180,0});
		 params.add(new double[]{225,180,0});
		 // params.add(new double[]{315,180,0});
		 params.add(new double[]{0,180,0});
		 params.add(new double[]{45,180,0});
		 
		 
		 /* U
		 params.add(new double[]{90,0,180});
		 params.add(new double[]{90,0,225});
		 params.add(new double[]{90,0,270});
		 params.add(new double[]{90,0,315});
		 params.add(new double[]{90,0,0});
		 params.add(new double[]{90,0,45});
		 params.add(new double[]{90,0,90});
		 params.add(new double[]{90,0,135});
		 
		 params.add(new double[]{90,45,180});
		 params.add(new double[]{90,90,180});
		 params.add(new double[]{90,135,180});
		 
		 params.add(new double[]{135,0,180});
		 params.add(new double[]{180,0,180});
		 params.add(new double[]{225,0,180});
		 params.add(new double[]{270,0,180});
		 params.add(new double[]{315,0,180});
		 params.add(new double[]{0,0,180});
		 params.add(new double[]{45,0,180});
		 */
		 
//		 for (int x=-45; x<46; x+=45)
//			 for (int y=-45; y<46; y+=45)
//				 for (int z=0; z<359; z+=45)
//					 params.add(new double[]{x,y,z});
		 return params;
	}
}
