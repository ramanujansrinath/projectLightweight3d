package org.xper.generate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class Parameterize_v4step2 {
	public static void main(String[] args) {
		String folderPath = "/Users/ecpc32/Desktop/v4_step2";
		
		Parameterize_v4step2 p = new Parameterize_v4step2();
		
		for (int kk=2; kk<=4; kk++) {
			List<double[]> params = p.getParams(kk);
			int nShape = params.size();
			for (int ii=2; ii < nShape; ii++) {
				double[] fr = params.get(ii);
				
				List<Double> ids = new ArrayList<Double>();
				List<MatchStick> objs = new ArrayList<MatchStick>();
				
				File theDir = new File(folderPath + "/" + kk + "/" + ii + "/");
				if (!theDir.exists()); theDir.mkdir();
				
				ids.clear(); objs.clear();
				for (int jj=0; jj<200; jj+=2) {
					ids.add((double)jj);
					objs.add(new MatchStick());
					objs.get(jj).genMatchStickFromFile_paramChange(folderPath + "/" + kk + "_spec.xml",fr);
					objs.get(jj).mutate(0);
					if (Math.random() > 0.5)
						objs.get(jj).setTextureType("SHADE");
					else
						objs.get(jj).setTextureType("SPECULAR");
					objs.get(jj).setStimColor(new RGBColor(1,1,1)); //0.6f,0.6f,0.6f
					
					MStickSpec spec = new MStickSpec();
					spec.setMStickInfo(objs.get(jj));
					spec.writeInfo2File(folderPath + "/" + kk + "/" + ii + "/" + jj);
					
					ids.add((double)jj+1);
					objs.add(new MatchStick());
					objs.get(jj+1).genMatchStickFromShapeSpec(spec);
					objs.get(jj+1).setTextureType("TWOD");
					objs.get(jj+1).setStimColor(new RGBColor(0.6f,0.6f,0.6f));
					
					System.out.println(ii + "_" + jj);
				}
				
				PNGmaker pngMaker = new PNGmaker();
				pngMaker.setSaveToFile(true);
				pngMaker.setImageFolderName(folderPath + "/" + kk + "/" + ii);
				pngMaker.createAndSavePNGsfromObjs(objs, ids, "");
			}
		}
		
	}
	
	public List<double[]> getParams(int specId) {
		 List<double[]> params = new ArrayList<double[]>();
		 List<int[]> oriCase = new ArrayList<int[]>();
		 List<int[]> stimId = new ArrayList<int[]>();

		 switch(specId) {
		 case 1:
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
			 params.add(new double[]{315,180,0});
			 params.add(new double[]{0,180,0});
			 params.add(new double[]{45,180,0});
			 break;
		 case 2:
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
			 params.add(new double[]{0,180,0});
			 params.add(new double[]{45,180,0});
			 break;
		 case 3:
			 params.add(new double[]{90,180,0});
			 params.add(new double[]{90,180,45});
			 params.add(new double[]{90,180,90});
			 params.add(new double[]{90,180,135});
			 
			 params.add(new double[]{90,225,0});
			 params.add(new double[]{90,270,0});
			 params.add(new double[]{90,45,0});
			 params.add(new double[]{90,90,0});			 
			 break;
		 case 4:			 
			 params.add(new double[]{90,225,0});
			 params.add(new double[]{90,270,0});
			 params.add(new double[]{90,45,0});
			 params.add(new double[]{90,90,0});
			 break;
		 }

		 return params;
	}
}
