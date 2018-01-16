package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class GenerateStimuli2 {
	public static void main(String[] args) {
		String folderPath = args[0];
		List<Double> ids = new ArrayList<Double>();
		List<MatchStick> objs = new ArrayList<MatchStick>();
	
	  
		
		double id = Double.parseDouble(args[1]);
		ids.add(id);
		objs.add(new MatchStick());
		objs.get(0).setDegWidth(1);
		objs.get(0).genMatchStickRand();
		objs.get(0).setTextureType(args[2]);
		objs.get(0).setDoClouds(Boolean.parseBoolean(args[3]));
		objs.get(0).setStimColor(new RGBColor(1,1,1));
		
//		System.out.println(degWidth + ": Saving stick/face/vert spec");
		MStickSpec spec = new MStickSpec();
		spec.setMStickInfo(objs.get(0));
		spec.writeInfo2File(folderPath + "/" + args[1]);
		
		PNGmaker pngMaker = new PNGmaker();
		pngMaker.setSaveToFile(true);
		pngMaker.setImageFolderName(folderPath);
		pngMaker.createAndSavePNGsfromObjs(objs, ids, "");
		
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