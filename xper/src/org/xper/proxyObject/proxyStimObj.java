package org.xper.proxyObject;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.dbUtil;

public class proxyStimObj {
	List<Long> ids = new ArrayList<Long>();
	dbUtil dbUtilObj = new dbUtil();
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
	
	public void saveToDb() {
		PNGmaker pngMaker = new PNGmaker();
		pngMaker.setDbUtilObj(dbUtilObj);
		pngMaker.setSaveToDb(true);
		pngMaker.createAndSavePNGsfromObjs(objs, ids);
		
		for (int i=0; i<objs.size(); i++) {
			MStickSpec msSpec = new MStickSpec();
			msSpec.setMStickInfo(objs.get(i));
			dbUtilObj.writeFaceSpec(ids.get(i),msSpec.getFaceSpec());
			dbUtilObj.writeVertSpec(ids.get(i),msSpec.getVertSpec());
		}
	}
	
	public void saveToFile() {
		PNGmaker pngMaker = new PNGmaker();
		pngMaker.setDbUtilObj(dbUtilObj);
		pngMaker.setSaveToFile(true);
		pngMaker.setImageFolderName("/Users/ecpc32/Desktop/temp");
		pngMaker.createAndSavePNGsfromObjs(objs, ids);
		
		for (int i=0; i<objs.size(); i++) {
			MStickSpec msSpec = new MStickSpec();
			msSpec.setMStickInfo(objs.get(i));
			msSpec.writeInfo2File("/Users/ecpc32/Desktop/temp/" + ids.get(i));
		}
	}
	
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	
	public static void main(String[] args) {
		proxyStimObj stimObj = new proxyStimObj();
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(1497203447767928l);
		ids.add(1497203447728474l);
		ids.add(1497203447605302l);
		ids.add(1497203447566872l);
		ids.add(1497203447523167l);
		ids.add(1497203447482508l);
		ids.add(1497203447689049l);
		ids.add(1497203447649185l);
		
		stimObj.setIds(ids);
		
		stimObj.retrieveSpec();
		stimObj.saveToFile();
	}
}
