package com.nosliw.core.application.division.story.converter.manual;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPIdBrick;

public class HAPStoryUtilityConverter {

	public static String convertToBrickWrapper(HAPEntityInfo entity, HAPIdBrick brickId) {
		InputStream brickWrapperTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "wrapperbrick.temp");
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("name", entity.getName());
		templateParms.put("localId", brickId.toStringValue(HAPSerializationFormat.LITERATE));
		return HAPStringTemplateUtil.getStringValue(brickWrapperTemplateStream, templateParms);
	}

	
	
}
