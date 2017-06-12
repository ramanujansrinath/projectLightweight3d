package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.dbUtil;

public class saveThumbAndVert {
	public static void main(String[] args) {
		Long id = 1496344499051525l;
		dbUtil dbUtilObj = new dbUtil();
		String stickSpec = dbUtilObj.getMstickSpec(id);
		
		String folderPath = "/Users/ecpc32/Desktop/temp";
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
		pngMaker.createAndSavePNGsfromObjs(objs, ids, folderPath);
		
		MStickSpec spec = new MStickSpec();
		spec.setMStickInfo(objs.get(0));
		spec.writeInfo2File(folderPath + "/" + id);
	}
}
