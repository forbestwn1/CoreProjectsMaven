package com.nosliw.core.application.division.manual.core.definition;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;

import com.google.common.io.Files;
import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.location.HAPPathLocationBase;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPUtilityBrickId;

public class HAPManualDefinitionUtilityBrickLocation {

	private static Map<String, HAPSerializationFormat> m_extensionToFormat = new LinkedHashMap<String, HAPSerializationFormat>(); 
	
	static {
		m_extensionToFormat.put("json", HAPSerializationFormat.JSON);
		m_extensionToFormat.put("html", HAPSerializationFormat.HTML);
		m_extensionToFormat.put("js", HAPSerializationFormat.JAVASCRIPT);
	}

	public static HAPManualDefinitionInfoBrickLocation buildBrickLocationInfoFromMainFolder(HAPIdBrickType brickTypeId, Path manualFolder) {
		Pair<Path, HAPSerializationFormat> brickFileInfo = HAPManualDefinitionUtilityBrickLocation.findBrickFile(manualFolder, "main");
		return new HAPManualDefinitionInfoBrickLocation(brickTypeId, brickFileInfo.getLeft(), brickFileInfo.getRight(), new HAPPathLocationBase(manualFolder), false);
	}
	
	public static Map<String, HAPManualDefinitionInfoBrickLocation> getBranchBrickLocationInfos(Path basePath) {
		Map<String, HAPManualDefinitionInfoBrickLocation> out = new LinkedHashMap<String, HAPManualDefinitionInfoBrickLocation>();
		
		List<Path> branchPaths = HAPUtilityFileNio.getChildrenPath(HAPUtilityFileNio.buildPath(basePath, "branch"));
		if(branchPaths!=null) {
			for(Path branchPath : branchPaths) {
				if(HAPUtilityFileNio.isDictory(branchPath)) {
					String branchName = HAPUtilityFileNio.getLastNameOfPath(branchPath);
					JSONObject brickInfoObj = new JSONObject(HAPUtilityFileNio.readFile(branchPath, "brickinfo.json"));
					if(HAPUtilityEntityInfo.isEnabled(brickInfoObj)) {
						HAPIdBrickType brickTypeId = HAPUtilityBrickId.parseBrickTypeId(brickInfoObj.get("brickTypeId"));
						out.put(branchName, HAPManualDefinitionUtilityBrickLocation.buildBrickLocationInfoFromMainFolder(brickTypeId, branchPath));
					}				
				}
			}
		}
		return out;
	}

	public static HAPManualDefinitionInfoBrickLocation getLocalBrickLocationInfo(Path basePath, HAPIdBrick brickId) {
		return getBrickLocationInfo(HAPUtilityFileNio.buildPath(basePath, "local"), brickId);
	}

	public static HAPManualDefinitionInfoBrickLocation getBrickLocationInfo(Path basePath, HAPIdBrick brickId) {
		HAPIdBrickType brickTypeId = brickId.getBrickTypeId(); 
		basePath = HAPUtilityFileNio.buildPath(basePath, brickTypeId.getBrickType());
		if(brickTypeId.getVersion()!=null) {
			basePath = HAPUtilityFileNio.buildPath(basePath, brickTypeId.getVersion());
		}
		
		Path newBasePath = HAPUtilityFileNio.buildPath(basePath, brickId.getId());
		if(HAPUtilityFileNio.isDictory(newBasePath)&&HAPUtilityFileNio.isPathExists(newBasePath)) {
			//from folder
			return HAPManualDefinitionUtilityBrickLocation.buildBrickLocationInfoFromMainFolder(brickTypeId, newBasePath);
		}
		else {
			//from file
			newBasePath = basePath;
			Pair<Path, HAPSerializationFormat> result = findBrickFile(newBasePath, brickId.getId());
			return new HAPManualDefinitionInfoBrickLocation(brickTypeId, result.getLeft(), result.getRight(), new HAPPathLocationBase(newBasePath), true);
		}
	}
	
	public static Pair<Path, HAPSerializationFormat> findBrickFile(Path dir, String fileName){
		Path match = null;
		List<Path> childrenPath = HAPUtilityFileNio.getChildrenPath(dir);
		for(Path childPath : childrenPath) {
			if(fileName.equals(HAPUtilityFileNio.getFileNameWithoutExtension(childPath))){
				match = childPath;
				break;
			}
		}
		
		if(match!=null) {
			HAPSerializationFormat format = m_extensionToFormat.get(Files.getFileExtension(match.getFileName().toString()).toLowerCase());
			if(format==null) {
				format = HAPSerializationFormat.JSON;
			}
			return Pair.of(match, format); 
		}
		return null;
	}

}
