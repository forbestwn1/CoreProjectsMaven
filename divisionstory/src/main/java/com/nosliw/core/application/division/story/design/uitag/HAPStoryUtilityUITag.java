package com.nosliw.core.application.division.story.design.uitag;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplate;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.application.division.manual.core.standalone.HAPManualManangerStandalone;
import com.nosliw.core.application.division.manual.core.standalone.HAPStandaloneDefinition;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.application.entity.uitag.HAPUITagInfo;
import com.nosliw.core.application.entity.uitag.HAPUITageQueryData;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPStoryUtilityUITag {

	public static HAPBundleForBrick buildStandaloneBundleForUITag(HAPUITageQueryData dataUITagQuery, HAPManagerUITag uiTagMan, HAPManualManangerStandalone standaloneMan, HAPRuntimeInfo runtimeInfo) {
		HAPUITagInfo uiTagInfo = uiTagMan.getDefaultUITagData(dataUITagQuery);

		HAPDataDefinition dataDefinition = dataUITagQuery.getDataDefinition();

		String dataVariableName = "data";
		
		StringBuffer attContent = new StringBuffer();
		Map<String, String> attributes = new LinkedHashMap<>(uiTagInfo.getAttributes());
		attributes.put(uiTagInfo.getAttributeForData(), dataVariableName);
		for(String name : attributes.keySet()) {
			attContent.append(name + "=\"" + attributes.get(name) + "\" ");
		}

		String initDataStr = "";
		HAPData initData = HAPUtilityDataDefinition.getInitData(dataDefinition);
		if(initData!=null) {
			initDataStr = "\"" + dataVariableName + "\"" + ":" + initData.toStringValue(HAPSerializationFormat.JSON);
		}
		
		String content = new  HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryUtilityUITag.class, "uitag.html"))
		     .setParm("tagName", uiTagInfo.getName())
		     .setParm("dataVariableName", dataVariableName)
		     .setParm("attributes", attContent.toString())
		     .setParm("dataDefinition", dataDefinition.toStringValue(HAPSerializationFormat.JSON))
		     .setParm("initData", initDataStr)
		     .getContent();
		
		return standaloneMan.buildStandalone(new HAPStandaloneDefinition(content, HAPSerializationFormat.HTML, HAPEnumBrickType.UIPAGE_100), runtimeInfo);
	}
	
}
