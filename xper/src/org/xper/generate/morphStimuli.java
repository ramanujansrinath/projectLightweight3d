package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;

public class morphStimuli {
	public static void main(String[] args) {
		String folderPath = args[0];
		List<Long> ids = new ArrayList<Long>();
		List<MatchStick> objs = new ArrayList<MatchStick>();
		
		ids.add(Long.parseLong(args[1]));
		objs.add(new MatchStick());
		objs.get(0).genMatchStickFromFile(folderPath + "/" + args[1] + "_spec.xml");
		objs.get(0).mutate(0);
		objs.get(0).setTextureType(args[3]);
		objs.get(0).setDoClouds(Boolean.parseBoolean(args[4]));
	
		MStickSpec spec = new MStickSpec();
		spec.setMStickInfo(objs.get(0));
		spec.writeInfo2File(folderPath + "/" + args[2]);
		
		PNGmaker pngMaker = new PNGmaker();
		pngMaker.createAndSavePNGsfromObjs(objs, ids, folderPath + "/" + args[2] + ".png");
	}
}
