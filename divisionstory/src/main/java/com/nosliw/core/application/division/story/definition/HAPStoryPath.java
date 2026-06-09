package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

public class HAPStoryPath extends HAPSerializableImp{

	public static final String BASESTORYELEMENTID = "baseStoryElementId";
	
	public static final String PATH = "path";
	
	//base element
	private HAPStoryIdElement  m_baseStoryElementId;
	
	//path from base element to target (an element or a collection of element)
	private HAPPath m_path;

	public HAPStoryPath() {}
	
	public HAPStoryPath(HAPStoryIdElement baseStoryElementId, HAPPath path) {
		this.m_baseStoryElementId = baseStoryElementId;
		this.m_path = path;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BASESTORYELEMENTID, this.m_baseStoryElementId.toStringValue(HAPSerializationFormat.JSON));
		if(this.m_path!=null) {
			jsonMap.put(PATH, this.m_path.toString());
		}
	}

}
