package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrick;

@HAPEntityWithAttribute
public class HAPStoryBuilderResponseNew extends HAPSerializableImp{

	@HAPAttribute
	public static final String STEPINFO = "stepInfo";

	@HAPAttribute
	public static final String BRICKID = "brickId";

	private HAPIdBrick m_brickId;
	
	private List<HAPStoryDesignMetadataStep> m_stepsInfo;
	
	public HAPStoryBuilderResponseNew(HAPIdBrick brickId) {
		this.m_stepsInfo = new ArrayList<HAPStoryDesignMetadataStep>();
		this.m_brickId = brickId;
	}
	
	public void addStepInfo(HAPStoryDesignMetadataStep stepInfo) {		this.m_stepsInfo.add(stepInfo); 	}
	public List<HAPStoryDesignMetadataStep> getStepInfos(){     return this.m_stepsInfo;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STEPINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_stepsInfo, HAPSerializationFormat.JSON));
		jsonMap.put(BRICKID, this.m_brickId.toStringValue(HAPSerializationFormat.JSON));
	}
	
}
