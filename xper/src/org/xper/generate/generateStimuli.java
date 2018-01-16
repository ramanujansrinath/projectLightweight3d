package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class GenerateStimuli {
	public static void main(String[] args) {
		String folderPath = args[0];
		List<Double> ids = new ArrayList<Double>();
		List<MatchStick> objs = new ArrayList<MatchStick>();
		
	  String prefix;
	  int nBarWidth = 10, nLengths = 9;
	  double[] lengths = new double[]{1.001,1.25,1.5,1.75,2,2.5,3,4,5}; 
	  
	  double screenDist = 100;
	  for (int jj=0; jj < nLengths; jj++) {
		  prefix = "cfront_l" + lengths[jj] + "_m1_d" + screenDist + "_s";
		  objs.clear(); ids.clear();
		  for (int ii=0; ii < nBarWidth; ii++) {
			  double degWidth = ((double)ii+1)/10;
			  if (ii==0)
				  degWidth = 0.15;
		      ids.add(degWidth);
		      objs.add(new MatchStick());
		      objs.get(ii).setDegWidth(degWidth);
		      objs.get(ii).setScreenDist(screenDist);
		      objs.get(ii).setLengthFactor(lengths[jj]);
		      objs.get(ii).genMatchStickRand();
		      objs.get(ii).setTextureType("SHADE");
		      objs.get(ii).setDoClouds(false);
		      objs.get(ii).setStimColor(new RGBColor(1,1,1));
		  }
		  PNGmaker pngMaker = new PNGmaker();
		  pngMaker.setSaveToFile(true);
		  pngMaker.setImageFolderName(folderPath);
		  pngMaker.createAndSavePNGsfromObjs(objs, ids, prefix);
	  }
	  
//	  for (int jj=0; jj < nLengths; jj++) {
//		  prefix = "cback_l" + (int)(lengths[jj]) + "_m2_d100_s";
//	  objs.clear(); ids.clear();
//	  for (int ii=0; ii < nBarWidth; ii++) {
//		  double degWidth = ((double)ii+1)/10;
//		  if (ii==0)
//			  degWidth = 0.15;
//	      ids.add(degWidth);
//	      objs.add(new MatchStick());
//	      objs.get(ii).setDegWidth(degWidth);
//	      objs.get(ii).genMatchStickRand();
//	      objs.get(ii).setTextureType("SPECULAR");
//		      objs.get(ii).setDoClouds(false);
//		      objs.get(ii).setStimColor(new RGBColor(1,1,1));
//		  }
//		  PNGmaker pngMaker = new PNGmaker();
//		  pngMaker.setSaveToFile(true);
//		  pngMaker.setImageFolderName(folderPath);
//		  pngMaker.createAndSavePNGsfromObjs(objs, ids, prefix);
//	  }
		
	  /* V1 rod stimuli
	  String prefix = "rod3d_l0_m1_d100_s";
	  int nBarWidth = 10, nLightAngles = 7;
	  double[] lightAngles = new double[]{0,30,60,90,120,150,180}; 
	  
	  for (int jj=0; jj < nLightAngles; jj++) {
		  prefix = "rod3d_l" + (int)(lightAngles[jj]) + "_m1_d100_s";
		  objs.clear(); ids.clear();
		  for (int ii=0; ii < nBarWidth; ii++) {
			  double degWidth = ((double)ii+1)/10;
			  if (ii==0)
				  degWidth = 0.15;
		      ids.add(degWidth);
		      objs.add(new MatchStick());
		      objs.get(ii).setDegWidth(degWidth);
		      objs.get(ii).genMatchStickRand();
		      objs.get(ii).setLightAngle(lightAngles[jj]);;
		      objs.get(ii).setTextureType("SHADE");
		      objs.get(ii).setDoClouds(false);
		      objs.get(ii).setStimColor(new RGBColor(1,1,1));
		  }
		  PNGmaker pngMaker = new PNGmaker();
		  pngMaker.setSaveToFile(true);
		  pngMaker.setImageFolderName(folderPath);
		  pngMaker.createAndSavePNGsfromObjs(objs, ids, prefix);
	  }
	  
	  for (int jj=0; jj < nLightAngles; jj++) {
		  prefix = "rod3d_l" + (int)(lightAngles[jj]) + "_m2_d100_s";
		  objs.clear(); ids.clear();
		  for (int ii=0; ii < nBarWidth; ii++) {
			  double degWidth = ((double)ii+1)/10;
			  if (ii==0)
				  degWidth = 0.15;
		      ids.add(degWidth);
		      objs.add(new MatchStick());
		      objs.get(ii).setDegWidth(degWidth);
		      objs.get(ii).genMatchStickRand();
		      objs.get(ii).setLightAngle(lightAngles[jj]);;
		      objs.get(ii).setTextureType("SPECULAR");
		      objs.get(ii).setDoClouds(false);
		      objs.get(ii).setStimColor(new RGBColor(1,1,1));
		  }
		  PNGmaker pngMaker = new PNGmaker();
		  pngMaker.setSaveToFile(true);
		  pngMaker.setImageFolderName(folderPath);
		  pngMaker.createAndSavePNGsfromObjs(objs, ids, prefix);
	  }
	  */
	  
		
////		Long id = Long.parseLong(args[1]);
//		ids.add(degWidth);
//		objs.add(new MatchStick());
//		objs.get(0).setDegWidth(degWidth);
//		objs.get(0).genMatchStickRand();
//		objs.get(0).setTextureType(args[2]);
//		objs.get(0).setDoClouds(Boolean.parseBoolean(args[3]));
//		objs.get(0).setStimColor(new RGBColor(1,1,1));
//		
////		System.out.println(degWidth + ": Saving stick/face/vert spec");
////		MStickSpec spec = ne/w MStickSpec();
////		spec.setMStickInfo(objs.get(0));
////		spec.writeInfo2File(folderPath + "/" + args[1]);
//		
//		System.out.println(degWidth + ": Saving PNG");
//		PNGmaker pngMaker = new PNGmaker();
//		pngMaker.setSaveToFile(true);
//		pngMaker.setImageFolderName(folderPath);
//		pngMaker.createAndSavePNGsfromObjs(objs, ids);
		
//        String folderPath = "/Users/ecpc32/Desktop/temp";
//        List<MStickSpec> specs = new ArrayList<MStickSpec>();
//        List<Long> ids = new ArrayList<Long>();
//        List<MatchStick> objs = new ArrayList<MatchStick>();
//        int nShape = 200;
//        for (int ii=0; ii < nShape; ii++) {
//            ids.add((long)ii);
//            objs.add(new MatchStick());
//            objs.get(ii).genMatchStickRand();
//            objs.get(ii).setTextureType("SHADE");
//            objs.get(ii).setDoClouds(false);
//            
//            MStickSpec spec = new MStickSpec();
//            spec.setMStickInfo(objs.get(ii));
//            spec.writeInfo2File(folderPath + "/specs/" + ii);
//            
//            specs.add(spec);
//        }
//        PNGmaker pngMaker = new PNGmaker();
//		pngMaker.createAndSavePNGsfromObjs(objs, ids, folderPath + "/SHADE/");
//		  
//		objs.clear();
//		objs = new ArrayList<MatchStick>();
//		for (int ii=0; ii < nShape; ii++) {
//			objs.add(new MatchStick());
//			objs.get(ii).genMatchStickFromShapeSpec(specs.get(ii));
//		    objs.get(ii).setTextureType("SPEC");
//		    objs.get(ii).setDoClouds(false);
//		}
//		pngMaker.createAndSavePNGsfromObjs(objs, ids, folderPath + "/SPEC/");
//		  
//		  
//		objs.clear();
//		objs = new ArrayList<MatchStick>();
//		for (int ii=0; ii < nShape; ii++) {
//			objs.add(new MatchStick());
//		    objs.get(ii).genMatchStickFromShapeSpec(specs.get(ii));
//		    objs.get(ii).setTextureType("2D");
//		    objs.get(ii).setDoClouds(false);
//		}
//		pngMaker.createAndSavePNGsfromObjs(objs, ids, folderPath + "/2D/");
//        
//        objs.clear();
//        objs = new ArrayList<MatchStick>();
//        for (int ii=0; ii < nShape; ii++) {            
//            objs.add(new MatchStick());
//            objs.get(ii).genMatchStickFromShapeSpec(specs.get(ii));
//            objs.get(ii).setTextureType("SHADE");
//            objs.get(ii).setDoClouds(true);
//        }
//        pngMaker.createAndSavePNGsfromObjs(objs, ids, folderPath + "/SCRAMBLE/");
	}
}