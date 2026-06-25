package com.nosliw.core.application.division.story.design;

import java.io.File;
import java.util.List;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.system.HAPSystemFolderUtility;

public class HAPStoryDesignUtilityExport {

	public static HAPStoryDesign loadDesign(String designId, HAPServiceParseEntity entityParseService, HAPStoryManagerChange changeMan) {
		HAPStoryDesign out = null;
		File dir = HAPStoryDesignUtilityExport.getDesignFolder(designId);
		if(dir.exists()) {
			List<File> children = HAPUtilityFile.getChildrenSortedByName(dir);
			out = HAPStoryDesignUtilityParse.parseStoryDesign(children.get(children.size()-1), entityParseService, changeMan);
		}
		else {
			throw new RuntimeException();
		}
		return out;
	}
	
	public static File getDesignFolder(String designId) {
		return new File(HAPSystemFolderUtility.getStoryDesignFolder() + "/" + designId);
	}
	
	public static void saveStoryDesign(HAPStoryDesign storyDesign) {  
		String seperator = "__";
		
		String designId = storyDesign.getId();

		File dir = HAPUtilityFile.getOrCreateFolder(HAPStoryDesignUtilityExport.getDesignFolder(designId));
		List<File> children = HAPUtilityFile.getChildrenSortedByName(dir);
		int indx = 100;
		if(children.size()>0) {
			String name = children.get(children.size()-1).getName();
			int i1 = name.indexOf(seperator);
			int i2 = name.indexOf(".");
			indx = Integer.valueOf(name.substring(i1+seperator.length(), i2));
			indx++;
		}
		
		HAPUtilityFile.writeJsonFile(dir.getAbsolutePath(), "version"+seperator+indx+".json", storyDesign.toStringValue(HAPSerializationFormat.JSON));
	}
}
