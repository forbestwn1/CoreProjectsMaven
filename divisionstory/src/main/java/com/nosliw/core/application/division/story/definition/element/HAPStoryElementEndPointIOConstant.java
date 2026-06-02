package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIOParser;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementEndPointIOConstant extends HAPStoryElementEndPointIO{

	public static final String DATA = "data";
	
	//constant value
	private HAPData m_data;
	
	public HAPStoryElementEndPointIOConstant() {
		this(null);
	}
	
	public HAPStoryElementEndPointIOConstant(HAPData data) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_ENDPOINT_CONSTANT), HAPConstantShared.IO_DIRECTION_OUT);
		this.m_data = data;
	}

	public HAPData getData() {    return this.m_data;      }
	public void setData(HAPData data) {     this.m_data = data;       }
	
	protected void cloneToStoryElement(HAPStoryElementEndPointIOConstant storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_data = this.m_data;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEndPointIOConstant out = new HAPStoryElementEndPointIOConstant();
		this.cloneToStoryElement(out);
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_data!=null) {
			jsonMap.put(DATA, this.m_data.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryElementEndPointIOConstant__HAPEntityParsable extends HAPStoryElementEndPointIOParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_ENDPOINT_CONSTANT;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementEndPointIOConstant element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		Object dataJsonObj = jsonObj.opt(HAPStoryElementEndPointIOConstant.DATA);
		if(dataJsonObj!=null) {
			element.setData(HAPUtilityData.buildDataWrapperFromObject(dataJsonObj));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementEndPointIOConstant out = new HAPStoryElementEndPointIOConstant();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
