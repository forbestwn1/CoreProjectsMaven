package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardQuestionairItemDynamic extends HAPStoryWizzardQuestionairItem{

	@HAPAttribute
	public static final String DEFAULTVALUE = "defaultValue";

	@HAPAttribute
	public static final String CHANGEDVALUE = "changedValue";

	@HAPAttribute
	public static final String ISDIRTY = "isDirty";

	@HAPAttribute
	public static final String ERROR = "error";

	private Object m_defaultValue;
	
	private Object m_changedValue;
	
	private HAPStoryWizzardQuestionairError m_error;

	private boolean m_isDirty;

	public HAPStoryWizzardQuestionairItemDynamic(Object defaultValue) {
    	super(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC);
		this.m_defaultValue = defaultValue;
		this.m_isDirty = false;
	}
	
	public HAPStoryWizzardQuestionairItemDynamic() {
		this(null);
	}
	
	public Object getDefaultValue() {     return this.m_defaultValue;     }
	public void setDefaultValue(Object value) {    this.m_defaultValue = value;     }
	
	public Object getChangedValue() {     return this.m_changedValue;     }
	public void setChangedValue(Object value) {     this.m_changedValue = value;       }
	
	public HAPStoryWizzardQuestionairError getError() {		return this.m_error;	}
	public void setError(HAPStoryWizzardQuestionairError error) {    this.m_error = error;      }
	
	public boolean isDirty() {     return this.m_isDirty;     }
	public void setIsDirty(boolean isDirty) {     this.m_isDirty = isDirty;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(DEFAULTVALUE, HAPManagerSerialize.getInstance().toStringValue(this.m_defaultValue, HAPSerializationFormat.JSON));
		jsonMap.put(CHANGEDVALUE, HAPManagerSerialize.getInstance().toStringValue(this.m_changedValue, HAPSerializationFormat.JSON));
		
		jsonMap.put(ISDIRTY, this.m_isDirty+"");
		typeJsonMap.put(ISDIRTY, Boolean.class);
		
		jsonMap.put(ERROR, HAPManagerSerialize.getInstance().toStringValue(this.m_error, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPStoryWizzardQuestionairItemDynamic_HAPEntityParsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPStoryWizzardQuestionair.PARSE_DOMAIN;  }

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		
		HAPStoryWizzardQuestionairItemDynamic out = new HAPStoryWizzardQuestionairItemDynamic();
		
        Boolean isDirtyBoolean = (Boolean)jsonObj.get(HAPStoryWizzardQuestionairItemDynamic.ISDIRTY);
        if(isDirtyBoolean!=null) {
        	out.setIsDirty(isDirtyBoolean.booleanValue());
        }
        
        JSONObject errorObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemDynamic.ERROR);
        if(errorObj!=null) {
        	HAPStoryWizzardQuestionairError error = new HAPStoryWizzardQuestionairError();
        	error.buildObject(errorObj, HAPSerializationFormat.JSON);
        	out.setError(error);
        }
        
        out.setDefaultValue(jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemDynamic.DEFAULTVALUE));
        out.setChangedValue(jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemDynamic.CHANGEDVALUE));
		
		return out;
	}

}
