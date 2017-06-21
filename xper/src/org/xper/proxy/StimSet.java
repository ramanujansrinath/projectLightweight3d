package org.xper.proxy;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.DbUtil;

public class StimSet {
	List<Long> ids = new ArrayList<Long>();
	DbUtil dbUtilObj;
	List<MatchStick> objs = new ArrayList<MatchStick>();
	
	public void retrieveSpec() {
		for (Long id : ids) { 
			String stickSpecStr = dbUtilObj.getMstickSpec(id);
			MatchStick ms = new MatchStick();
			ms.genMatchStickFromShapeSpec(MStickSpec.fromXml(stickSpecStr));
			ms.setTextureType(dbUtilObj.getTextureType(id));
			ms.setDoClouds(dbUtilObj.getDoClouds(id));
			ms.setStimColor(dbUtilObj.getStimColor(id));
			ms.setLightPosition(dbUtilObj.getLightPosition(id));
			
			objs.add(ms);
		}
	}
	
	public void saveToDb(boolean saveThumbnails, boolean saveVert) {
		if (saveThumbnails) {
			PNGmaker pngMaker = new PNGmaker();
			pngMaker.setDbUtilObj(dbUtilObj);
			pngMaker.setSaveToDb(true);
			pngMaker.createAndSavePNGsfromObjs(objs, ids);
		}
		
		if (saveVert)
			for (int i=0; i<objs.size(); i++) {
				MStickSpec msSpec = new MStickSpec();
				msSpec.setMStickInfo(objs.get(i));
				dbUtilObj.writeFaceSpec(ids.get(i),msSpec.getFaceSpec());
				dbUtilObj.writeVertSpec(ids.get(i),msSpec.getVertSpec());
			}
	}
	
	public void saveToFile(boolean saveThumbnails, boolean saveVert) {
		if (saveThumbnails) {
			PNGmaker pngMaker = new PNGmaker();
			pngMaker.setDbUtilObj(dbUtilObj);
			pngMaker.setSaveToFile(true);
			pngMaker.setImageFolderName("/Users/ecpc32/Desktop/temp");
			pngMaker.createAndSavePNGsfromObjs(objs, ids);
		}
		
		if (saveVert)
			for (int i=0; i<objs.size(); i++) {
				MStickSpec msSpec = new MStickSpec();
				msSpec.setMStickInfo(objs.get(i));
				msSpec.writeInfo2File("/Users/ecpc32/Desktop/temp/" + ids.get(i));
			}
	}
	
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public void setDbUtil(DbUtil dbUtilObj) {
		this.dbUtilObj = dbUtilObj;
	}
}
