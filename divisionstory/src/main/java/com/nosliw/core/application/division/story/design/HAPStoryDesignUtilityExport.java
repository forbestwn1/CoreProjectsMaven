package com.nosliw.core.application.division.story.design;

import java.io.File;
import java.util.List;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.system.HAPSystemFolderUtility;

public class HAPStoryDesignUtilityExport {

	public static HAPStoryDesign loadDesign(HAPIdBrick brickId, HAPServiceParseEntity entityParseService, HAPStoryManagerChange changeMan) {
		HAPStoryDesign out = null;
		File dir = HAPStoryDesignUtilityExport.getDesignFolder(brickId);
		if(dir.exists()) {
			List<File> children = HAPUtilityFile.getChildrenSortedByName(dir);
			out = HAPStoryDesignUtilityParse.parseStoryDesign(children.get(children.size()-1), entityParseService, changeMan);
		}
		else {
			throw new RuntimeException();
		}
		return out;
	}
	
	public static void saveStoryDesign(HAPStoryDesign storyDesign) {  
		String seperator = "__";
		
		File dir = HAPUtilityFile.getOrCreateFolder(HAPStoryDesignUtilityExport.getDesignFolder(storyDesign.getBrickId()));
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

	public static File getDesignFolder(HAPIdBrick brickId) {
		return new File(HAPSystemFolderUtility.getStoryDesignFolder() + "/" + brickId.getBrickTypeId().getKey() + "/" + brickId.getId());
	}
	
}
