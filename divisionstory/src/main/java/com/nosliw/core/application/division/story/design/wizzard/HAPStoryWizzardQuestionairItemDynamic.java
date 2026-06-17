package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionairItemDynamic extends HAPStoryWizzardQuestionairItem{

	@HAPAttribute
	public static final String DEFAULTVALUE = "defaultValue";

	@HAPAttribute
	public static final String CHANGEDVALUE = "changedValue";

	@HAPAttribute
	public static final String ISDIRTY = "isDirty";

	@HAPAttribute
	public static final String ERROR = "error";

	private HAPStoryWizzardValueInQuestionair m_defaultValue;
	
	private HAPStoryWizzardValueInQuestionair m_changedValue;
	
	private HAPStoryWizzardErrorInQuestionair m_error;

	private boolean m_isDirty;

	public HAPStoryWizzardQuestionairItemDynamic() {
    	super(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC);
		this.m_isDirty = false;
	}
	
	public HAPStoryWizzardQuestionairItemDynamic(HAPStoryWizzardValueInQuestionair defaultValue) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC);
		this.m_defaultValue = defaultValue;
		this.m_isDirty = false;
	}
	
	public HAPStoryWizzardQuestionairItemDynamic(HAPStoryWizzardValueInQuestionair defaultValue, String tag) {
		super(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC, tag);
		this.m_defaultValue = defaultValue;
		this.m_isDirty = false;
	}
	
	@Override
	public String getValueType() {    return this.m_defaultValue.getValueType();      } 
	
	
	public HAPStoryWizzardValueInQuestionair getValue() {		return this.isDirty()? this.getChangedValue():this.getDefaultValue();	}
	
	public HAPStoryWizzardValueInQuestionair getDefaultValue() {     return this.m_defaultValue;     }
	public void setDefaultValue(HAPStoryWizzardValueInQuestionair value) {    this.m_defaultValue = value;     }
	
	public HAPStoryWizzardValueInQuestionair getChangedValue() {     return this.m_changedValue;     }
	public void setChangedValue(HAPStoryWizzardValueInQuestionair value) {     this.m_changedValue = value;       }
	
	public HAPStoryWizzardErrorInQuestionair getError() {		return this.m_error;	}
	public void setError(HAPStoryWizzardErrorInQuestionair error) {    this.m_error = error;      }
	
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
class HAPStoryWizzardQuestionairItemDynamic_HAPEntityParsable extends HAPStoryWizzardQuestionair_Parsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC;  }

    @Override
	protected void parseQuestionair(HAPStoryWizzardQuestionair questionair, JSONObject jsonObj, HAPServiceParseEntity parseService) {
    	super.parseQuestionair(questionair, jsonObj, parseService);

    	HAPStoryWizzardQuestionairItemDynamic out = (HAPStoryWizzardQuestionairItemDynamic)questionair;

        Boolean isDirtyBoolean = (Boolean)jsonObj.get(HAPStoryWizzardQuestionairItemDynamic.ISDIRTY);
        if(isDirtyBoolean!=null) {
        	out.setIsDirty(isDirtyBoolean.booleanValue());
        }
        
        JSONObject errorObj = jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemDynamic.ERROR);
        if(errorObj!=null) {
        	HAPStoryWizzardErrorInQuestionair error = new HAPStoryWizzardErrorInQuestionair();
        	error.buildObject(errorObj, HAPSerializationFormat.JSON);
        	out.setError(error);
        }
        
        out.setDefaultValue(HAPStoryWizzardValueInQuestionair.parseEntity(jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemDynamic.DEFAULTVALUE), parseService));
        out.setChangedValue(HAPStoryWizzardValueInQuestionair.parseEntity(jsonObj.optJSONObject(HAPStoryWizzardQuestionairItemDynamic.CHANGEDVALUE), parseService));
    }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionairItemDynamic out = new HAPStoryWizzardQuestionairItemDynamic();
		this.parseQuestionair(out, (JSONObject)obj, parseService);
		return out;
	}

}
