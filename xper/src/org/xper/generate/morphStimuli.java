package org.xper.generate;

import java.util.ArrayList;
import java.util.List;

import org.xper.drawing.drawables.PNGmaker;
import org.xper.drawing.stick.MStickSpec;
import org.xper.drawing.stick.MatchStick;
import org.xper.utils.RGBColor;

public class MorphStimuli {
	public static void main(String[] args) {
		String folderPath = args[0];
		List<Long> ids = new ArrayList<Long>();
		List<MatchStick> objs = new ArrayList<MatchStick>();
		
		Long id = Long.parseLong(args[1]);
		
		objs.add(new MatchStick());
		System.out.println("Fetching stim spec for " + id);
		objs.get(0).genMatchStickFromFile(folderPath + "/" + args[1] + "_spec.xml");
		objs.get(0).mutate(0);
		objs.get(0).setTextureType(args[3]);
		objs.get(0).setDoClouds(Boolean.parseBoolean(args[4]));
		objs.get(0).setStimColor(new RGBColor(1,0,0));

		Long id_new = Long.parseLong(args[2]);
		ids.add(id_new);
		
		System.out.println(id_new + ": Saving stick/face/vert spec");
		MStickSpec spec = new MStickSpec();
		spec.setMStickInfo(objs.get(0));
		spec.writeInfo2File(folderPath + "/" + id_new);
		
		System.out.println(id_new + ": Saving PNG");
		PNGmaker pngMaker = new PNGmaker();
		pngMaker.setSaveToFile(true);
		pngMaker.setImageFolderName(folderPath);
		pngMaker.createAndSavePNGsfromObjs(objs, ids);
	}
}
