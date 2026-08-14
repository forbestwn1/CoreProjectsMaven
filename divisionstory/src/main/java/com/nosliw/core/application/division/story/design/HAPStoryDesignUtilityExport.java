package com.nosliw.core.application.division.story.design;

import java.nio.file.Path;
import java.util.List;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;

public class HAPStoryDesignUtilityExport {

	public static HAPStoryDesign loadDesign(Path rootPath, HAPIdBrick brickId, HAPServiceParseEntity entityParseService, HAPStoryManagerChange changeMan) {
		HAPStoryDesign out = null;
		Path dir = HAPStoryDesignUtilityExport.getDesignFolder(brickId, rootPath);
		if(HAPUtilityFileNio.isPathExists(dir)) {
			List<Path> children = HAPUtilityFileNio.getChildrenSortedByName(dir);
			out = HAPStoryDesignUtilityParse.parseStoryDesign(HAPUtilityFileNio.readFile(children.get(children.size()-1)), entityParseService, changeMan);
		}
		else {
			throw new RuntimeException();
		}
		return out;
	}
	
	public static void saveStoryDesign(HAPStoryDesign storyDesign, Path rootPath) {  
		String seperator = "__";
		
		Path dir = HAPUtilityFileNio.getOrCreateFolder(HAPStoryDesignUtilityExport.getDesignFolder(storyDesign.getBrickId(), rootPath));
		List<Path> children = HAPUtilityFileNio.getChildrenSortedByName(dir);
		int indx = 100;
		if(children.size()>0) {
			String name = children.get(children.size()-1).getFileName().toString();
			int i1 = name.indexOf(seperator);
			int i2 = name.indexOf(".");
			indx = Integer.valueOf(name.substring(i1+seperator.length(), i2));
			indx++;
		}
		
		HAPUtilityFileNio.writeJsonFile(dir, "version"+seperator+indx+".json", storyDesign.toStringValue(HAPSerializationFormat.JSON));
	}

	public static Path getDesignFolder(HAPIdBrick brickId, Path rootPath) {
		return HAPUtilityFileNio.buildPath(rootPath, brickId.getBrickTypeId().getKey(), brickId.getId());
	}
	
}
