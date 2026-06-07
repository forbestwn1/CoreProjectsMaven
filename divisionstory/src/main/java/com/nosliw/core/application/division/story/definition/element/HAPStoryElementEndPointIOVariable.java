package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPParserDataDefinition;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIOParser;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementEndPointIOVariable extends HAPStoryElementEndPointIO{

	public final static String DATADEFINITION = "dataDefinition";
	
	//
	private HAPDataDefinition m_dataDefinition;
	
	public HAPStoryElementEndPointIOVariable() {
		this(null, null);
	}
	
	public void setDataDefinition(HAPDataDefinition dataDefinition) {      this.m_dataDefinition = dataDefinition;          }
	public HAPDataDefinition getDataDefinition() {    return this.m_dataDefinition;      }
	
	public HAPStoryElementEndPointIOVariable(HAPDataDefinition dataDefinition, String IOType) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_ENDPOINT_VARIABLE), IOType);
		this.m_dataDefinition = dataDefinition;
	}

	protected void cloneToStoryElement(HAPStoryElementEndPointIOVariable storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_dataDefinition = this.m_dataDefinition;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEndPointIOVariable out = new HAPStoryElementEndPointIOVariable();
		this.cloneToStoryElement(out);
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_dataDefinition!=null) {
			jsonMap.put(DATADEFINITION, this.m_dataDefinition.toStringValue(HAPSerializationFormat.JSON));
		}
	}
	
}

@Component
class HAPStoryElementEndPointIOVariable__HAPEntityParsable extends HAPStoryElementEndPointIOParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_ENDPOINT_VARIABLE;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementEndPointIOVariable element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		JSONObject dataJsonObj = jsonObj.optJSONObject(HAPStoryElementEndPointIOVariable.DATADEFINITION);
		if(dataJsonObj!=null) {
			element.setDataDefinition(HAPParserDataDefinition.parseDataDefinition(dataJsonObj, parseService));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementEndPointIOVariable out = new HAPStoryElementEndPointIOVariable();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
