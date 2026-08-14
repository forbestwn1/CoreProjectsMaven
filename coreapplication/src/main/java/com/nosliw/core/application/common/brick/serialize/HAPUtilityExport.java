package com.nosliw.core.application.common.brick.serialize;

import java.nio.file.Path;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPWithDomain;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrickType;

public class HAPUtilityExport{

	public static void exportBundle(HAPBundleForBrick bundle, Path exportFolder, HAPSerializationFormat format) {
		Path exportForderFormat = HAPUtilityFileNio.buildPath(exportFolder, format.toString());
		HAPUtilityFileNio.deletePath(exportForderFormat);
		HAPUtilityFileNio.writeJsonFile(exportForderFormat, "bundle.json", bundle.toStringValue(format));
	} 
		
	public static HAPBundleForBrick importBundle(Path importFolder, HAPSerializationFormat format, HAPServiceParseEntity parseService) {
		HAPBundleForBrick out = null;
		try {
			Path importFile = HAPUtilityFileNio.buildPath(importFolder, format.toString(), "bundle.json");
			if(HAPUtilityFileNio.isPathExists(importFile)) {
				String content = HAPUtilityFileNio.readFile(importFile);
				out = (HAPBundleForBrick)parseService.parseEntityJSONExplicit(new JSONObject(content), HAPBundleForBrick.class.getName());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return out;
	}

	public static HAPBrick parseBrickJson(JSONObject jsonObj, HAPServiceParseEntity parseService) {
	    return (HAPBrick)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPBrick.BRICKTYPE+"."+HAPIdBrickType.KEY, jsonObj.getString(HAPWithDomain.PARSEDOMAIN));
	}

}
