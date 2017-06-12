package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;
import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.dbUtil;

public class saveThumbAndVert {
	public static void main(String[] args) {
		Long id = 1496344499051525l;
		dbUtil dbUtilObj = new dbUtil();
		String stickSpec = dbUtilObj.getMstickSpec(id);
		
		List<Long> ids = new ArrayList<Long>();
		List<MatchStick> objs = new ArrayList<MatchStick>();
		
		ids.add(id);
		objs.add(new MatchStick());
		objs.get(0).genMatchStickFromShapeSpec(MStickSpec.fromXml(stickSpec));
		
		objs.get(0).setTextureType(dbUtilObj.getTextureType(id));
		objs.get(0).setDoClouds(dbUtilObj.getDoClouds(id));
		objs.get(0).setStimColor(dbUtilObj.getStimColor(id));
		objs.get(0).setLightPosition(dbUtilObj.getLightPosition(id));

		PNGmaker pngMaker = new PNGmaker();
		pngMaker.setDbUtilObj(dbUtilObj);
		pngMaker.setSaveToDb(true);
		System.out.println(id + ": Saving PNG");
		pngMaker.createAndSavePNGsfromObjs(objs, ids);
		
		MStickSpec spec = new MStickSpec();
		spec.setMStickInfo(objs.get(0));
		System.out.println(id + ": Saving FaceSpec");
		dbUtilObj.writeFaceSpec(id,spec.getFaceSpec());
		System.out.println(id + ": Saving VertSpec");
		dbUtilObj.writeVertSpec(id,spec.getVertSpec());
	}
}
