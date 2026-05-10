package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPStoryBuilderResponseBuild extends HAPSerializableImp{

	@HAPAttribute
	public static final String STEPINFO = "stepInfo";
	
	private List<HAPStoryDesignMetadataStep> m_stepsInfo;
	
	public HAPStoryBuilderResponseBuild() {
		this.m_stepsInfo = new ArrayList<HAPStoryDesignMetadataStep>();
	}
	
	public void addStepInfo(HAPStoryDesignMetadataStep stepInfo) {
		this.m_stepsInfo.add(stepInfo);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STEPINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_stepsInfo, HAPSerializationFormat.JSON));
	}
	
}
