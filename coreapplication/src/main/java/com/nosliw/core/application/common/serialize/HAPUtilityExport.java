package com.nosliw.core.application.common.serialize;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPUtilityExport{

	public static void exportBundle(HAPBundleForBrick bundle, String exportFolder, HAPSerializationFormat format) {
//		String exportForderFormat = exportFolder + "/" + format.toString();
//		HAPUtilityFile.deleteFolder(exportForderFormat);
//		HAPUtilityFile.writeJsonFile(exportForderFormat, "bundle.json", bundle.toStringValue(format));
	} 
		
	public static HAPBundleForBrick importBundle(String importFolder, HAPSerializationFormat format, HAPServiceParseEntity parseService) {
		HAPBundleForBrick out = null;
//		File importFile = new File(importFolder + "/" + format.toString() + "/bundle.json");
//		if(importFile.exists()) {
//			String content = HAPUtilityFile.readFile(importFile);
//			out = (HAPBundleForBrick)parseService.parseEntityJSONExplicit(new JSONObject(content), HAPBundleForBrick.class.getName());
//		}
		
		return out;
	}
}
