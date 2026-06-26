package com.nosliw.core.application.division.story.converter.manual;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.design.HAPStoryDesignUtilityExport;

public class HAPStoryUtilityConverter {

	public static String convertToBrickWrapper(HAPStoryIdElement elementId, HAPEntityInfo entity, HAPIdBrick brickId) {
		InputStream brickWrapperTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "wrapperbrick_reference.temp");
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("name", elementId.getId());
		templateParms.put("localId", brickId.toStringValue(HAPSerializationFormat.LITERATE));
		return HAPStringTemplateUtil.getStringValue(brickWrapperTemplateStream, templateParms);
	}

	public static String getDesignConverToManualFolder(HAPIdBrick brickId) {
		return HAPStoryDesignUtilityExport.getDesignFolder(brickId).getAbsolutePath()+"/manual";
	}
	
	
	
}
