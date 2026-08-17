package com.nosliw.core.application.division.story.api;

import java.nio.file.Path;

import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.brick.HAPIdBrick;

public class HAPUtilityLocation {

	public static Path getDesignFolder(HAPIdBrick brickId, Path rootPath) {
		return HAPUtilityFileNio.buildPath(rootPath, brickId.getBrickTypeId().getKey(), brickId.getId());
	}

	public static Path getManualFolder(Path rootPath, HAPIdBrick brickId) {
		return HAPUtilityFileNio.buildPath(HAPUtilityLocation.getDesignFolder(brickId, rootPath), "manual");
	}
	
	public static Path getBundleFolder(Path rootPath, HAPIdBrick brickId) {
		return HAPUtilityFileNio.buildPath(HAPUtilityLocation.getDesignFolder(brickId, rootPath), "bundle");
	}

}
