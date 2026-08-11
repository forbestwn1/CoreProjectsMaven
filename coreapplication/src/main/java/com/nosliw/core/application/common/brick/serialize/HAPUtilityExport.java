package com.nosliw.core.application.common.brick.serialize;

import java.io.File;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPWithDomain;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrickType;

public class HAPUtilityExport{

	public static void exportBundle(HAPBundleForBrick bundle, String exportFolder, HAPSerializationFormat format) {
		String exportForderFormat = exportFolder + "/" + format.toString();
		HAPUtilityFile.deleteFolder(exportForderFormat);
		HAPUtilityFile.writeJsonFile(exportForderFormat, "bundle.json", bundle.toStringValue(format));
	} 
		
	public static HAPBundleForBrick importBundle(String importFolder, HAPSerializationFormat format, HAPServiceParseEntity parseService) {
		HAPBundleForBrick out = null;
		try {
			File importFile = new File(importFolder + "/" + format.toString() + "/bundle.json");
			if(importFile.exists()) {
				String content = HAPUtilityFile.readFile(importFile);
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
