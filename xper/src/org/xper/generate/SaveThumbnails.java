package org.xper.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.xper.proxy.StimSet;
import org.xper.utils.DbUtil;

public class SaveThumbnails {
	DbUtil dbUtilObj;
	public static void main_saveall(String[] args) {
		SaveThumbnails st = new SaveThumbnails();
		st.dbUtilObj = new DbUtil();
		String prefix = st.dbUtilObj.getDescGenId();
		int nStim = st.dbUtilObj.getStimPerLin();
		
		StimSet stimObj = new StimSet();
		List<Long> ids = new ArrayList<Long>();
		
		Scanner inputReader = new Scanner(System.in);
		
		boolean doSaveThumbnails = false, doSaveVert = false;
		System.out.print("Save thumbnails: ");
		char c = inputReader.next().charAt(0);
		if (c == '1') doSaveThumbnails = true;
		System.out.print("Save vert: ");
		c = inputReader.next().charAt(0);
		if (c == '1') doSaveVert = true;
		
		for (int l=1; l<=2; l++) {
			System.out.println("Retrieving stim ids for lineage " + l);
			for (int s=1; s<=nStim; s++) {
				String did = prefix + "_l-" + l + "_s-" + s;
				ids.add(st.dbUtilObj.getIdForDescId(did));
			}
		}
		
		stimObj.setIds(ids);
		
		stimObj.setDbUtil(st.dbUtilObj);
		
		System.out.println("Retrieving stim specs");
		stimObj.retrieveSpec();
		
		stimObj.saveToDb(doSaveThumbnails,doSaveVert);
	}
	
	public static void main(String[] args) {
		SaveThumbnails st = new SaveThumbnails();
		st.dbUtilObj = new DbUtil();
		
		StimSet stimObj = new StimSet();
		List<Long> ids = new ArrayList<Long>();
		
		boolean doSaveThumbnails = false, doSaveVert = true;
		
		String did = args[0];
		ids.add(st.dbUtilObj.getIdForDescId(did));
		
		stimObj.setIds(ids);
		
		stimObj.setDbUtil(st.dbUtilObj);
		
		System.out.println("Retrieving stim specs");
		stimObj.retrieveSpec();
		
		stimObj.saveToDb(doSaveThumbnails,doSaveVert);
	}
}
