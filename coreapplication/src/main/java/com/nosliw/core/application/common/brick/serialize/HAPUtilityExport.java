package com.nosliw.core.application.common.brick.serialize;

import java.nio.file.Path;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPWithDomain;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;

public class HAPUtilityExport{

	public static void exportBundle(HAPBundleForBrick bundle, Path exportFolder) {
		exportBundle(bundle.toStringValue(HAPSerializationFormat.JSON), exportFolder);
	}
	
	public static void exportBundle(String bundleJsonStr, Path exportFolder) {
		exportBundle(new JSONObject(bundleJsonStr), exportFolder);
	}
	
	public static void exportBundle(JSONObject bundle, Path exportFolder) {
		Path exportForderFormat = HAPUtilityFileNio.buildPath(exportFolder, HAPSerializationFormat.JSON.toString());
		HAPUtilityFileNio.deletePath(exportForderFormat);
		HAPUtilityFileNio.writeJsonFile(exportForderFormat, "bundle.json", bundle.toString());
	} 
		
	public static String importBundle(Path importFolder) {
		String out = null;
		try {
			Path importFile = HAPUtilityFileNio.buildPath(importFolder, HAPSerializationFormat.JSON.toString(), "bundle.json");
			if(HAPUtilityFileNio.isPathExists(importFile)) {
				String content = HAPUtilityFileNio.readFile(importFile);
				out = content;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return out;
	}

	public static JSONObject importBundleToJsonObj(Path importFolder) {
		String content = importBundle(importFolder);
		if(content!=null) {
			return new JSONObject(content);
		}
		return null;
	}

	public static HAPBrick parseBrickJson(JSONObject jsonObj, HAPServiceParseEntity parseService) {
	    return (HAPBrick)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPBrick.BRICKTYPE+"."+HAPIdBrickType.KEY, jsonObj.getString(HAPWithDomain.PARSEDOMAIN));
	}

}
