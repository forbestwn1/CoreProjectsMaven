package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIO;
import com.nosliw.core.application.division.story.definition.HAPStoryElementEndPointIOParser;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;

public class HAPStoryElementEndPointIODataExpression extends HAPStoryElementEndPointIO{

	public static final HAPStoryIdElementType TYPE = new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_ENDPOINT_DATAEXPRESSION);
	
	@HAPAttribute
	public static final String VALUESTR = "valueStr";
	
	@HAPAttribute
	public static final String VALUEOBJ = "valueObj";
	
	private String m_dataExpressionStr;
	
	private HAPDefinitionDataExpression m_dataExpressionDefinition;
	
	
	public HAPStoryElementEndPointIODataExpression() {
		super(TYPE, HAPConstantShared.IO_DIRECTION_OUT);
	}
	
	public String getValueStr() {     return this.m_dataExpressionStr;       }
	public void setValueStr(String valueStr) {      this.m_dataExpressionStr = valueStr;         }
	
	public HAPDefinitionDataExpression getValueObj() {     return this.m_dataExpressionDefinition;       }
	public void setValueObj(HAPDefinitionDataExpression valueObj) {      this.m_dataExpressionDefinition = valueObj;         }
	
	
	protected void cloneToStoryElement(HAPStoryElementEndPointIODataExpression storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_dataExpressionStr = this.m_dataExpressionStr;
		storyEle.m_dataExpressionDefinition = this.m_dataExpressionDefinition;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEndPointIODataExpression out = new HAPStoryElementEndPointIODataExpression();
		this.cloneToStoryElement(out);
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUESTR, m_dataExpressionStr);
		if(this.m_dataExpressionDefinition!=null) {
			jsonMap.put(VALUEOBJ, this.m_dataExpressionDefinition.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryElementEndPointIODataExpression__HAPEntityParsable extends HAPStoryElementEndPointIOParser{

	@Override
	public String getSubName() {    return HAPStoryElementEndPointIODataExpression.TYPE.getElementType();    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementEndPointIODataExpression element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	    element.setValueStr((String)jsonObj.opt(HAPStoryElementEndPointIODataExpression.VALUESTR));
	    element.setValueObj(HAPDefinitionDataExpression.buildDataExpressionDefinition(jsonObj.optJSONObject(HAPStoryElementEndPointIODataExpression.VALUEOBJ), parseService));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementEndPointIODataExpression out = new HAPStoryElementEndPointIODataExpression();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
