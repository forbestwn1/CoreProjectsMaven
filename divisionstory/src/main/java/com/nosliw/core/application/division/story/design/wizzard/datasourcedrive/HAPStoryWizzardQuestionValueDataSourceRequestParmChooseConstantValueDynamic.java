package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardParserValueInQuestion;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionairImp;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.data.expression.definition.HAPDefinitionRawDataExpression;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic extends HAPStoryWizzardValueInQuestionairImp{

	@HAPAttribute
	public static final String CONSTANTDATA = "constantData";
	
	@HAPAttribute
	public static final String EXPRESSION = "expression";
	
	private HAPData m_constantData;
	
	private HAPDefinitionRawDataExpression m_expression;
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic() {
		super(HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE);
	}
	
	public HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic(HAPData constantData) {
		this();
		this.setConstantData(constantData);
	}
		
	public HAPData getConstantData() {    return this.m_constantData;     }
	public void setConstantData(HAPData data) {    this.m_constantData = data;       }
	
	public HAPDefinitionRawDataExpression getExpression() {     return this.m_expression;     }
	public void setExpression(HAPDefinitionRawDataExpression expression) {     this.m_expression = expression;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_constantData!=null) {
			jsonMap.put(CONSTANTDATA, HAPManagerSerialize.getInstance().toStringValue(m_constantData, HAPSerializationFormat.JSON));
		}
		
		if(this.m_expression!=null) {
			jsonMap.put(EXPRESSION, this.m_expression.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic_HAPEntityParsable extends HAPStoryWizzardParserValueInQuestion{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic out = new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		Object dataConstantObj = jsonObj.opt(HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic.CONSTANTDATA);
		if(dataConstantObj!=null) {
			out.setConstantData(HAPUtilityData.buildDataWrapperFromObject(dataConstantObj));
		}
		
		Object expressionObj = jsonObj.opt(HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic.EXPRESSION);
		if(expressionObj!=null) {
			HAPDefinitionRawDataExpression expression = new HAPDefinitionRawDataExpression();
			expression.buildObject(expressionObj, HAPSerializationFormat.JSON);
			out.setExpression(expression);
		}
		
		return out;
	}

}
