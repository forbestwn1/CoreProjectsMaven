package com.nosliw.core.application.division.story.design.wizzard;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;
import com.nosliw.core.application.division.story.design.HAPStoryParserEntityMetadataStep;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryDesignMetadataStepWizard extends HAPStoryDesignMetadataStep{

	
	@HAPAttribute
	public static final String STEPDEFINITION = "stepDefinition";
	
	@HAPAttribute
	public static final String QUESTIONAIR = "questionair";
	
	//step definition
	private HAPStoryWizzardStepDefinition m_stepDefinition;

	//questionair for this step
	private HAPStoryWizzardQuestionair m_questionair;
	
	public HAPStoryDesignMetadataStepWizard() {
		super(HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_WIZZARD);
	}
	
	public HAPStoryDesignMetadataStepWizard(HAPStoryWizzardStepDefinition stepDefinition) {
		this();
	    this.m_stepDefinition = stepDefinition;
	}
	
	public void setQuestionair(HAPStoryWizzardQuestionair questionair) {    this.m_questionair = questionair;  } 
	public HAPStoryWizzardQuestionair getQuestionair(){     return this.m_questionair;      }
	
	public void setStepDefinition(HAPStoryWizzardStepDefinition stepDefinition) {    this.m_stepDefinition = stepDefinition;       }
	public HAPStoryWizzardStepDefinition getStepDefinition() {     return this.m_stepDefinition;      }
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STEPDEFINITION, this.m_stepDefinition.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(QUESTIONAIR, HAPManagerSerialize.getInstance().toStringValue(this.m_questionair, HAPSerializationFormat.JSON));
	}

}

@Component
class HAPStoryDesignMetadataStepWizard_HAPEntityParsable extends HAPStoryParserEntityMetadataStep{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_STEP_METADATATYPE_WIZZARD;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		
		HAPStoryDesignMetadataStepWizard out = new HAPStoryDesignMetadataStepWizard();
		
		JSONObject stepDefJsonObj = jsonObj.optJSONObject(HAPStoryDesignMetadataStepWizard.STEPDEFINITION);
		HAPStoryWizzardStepDefinition stepDef = new HAPStoryWizzardStepDefinition();
		stepDef.buildObject(stepDefJsonObj, HAPSerializationFormat.JSON);
		out.setStepDefinition(stepDef);
		
        JSONObject questionairJsonObj = jsonObj.getJSONObject(HAPStoryDesignMetadataStepWizard.QUESTIONAIR);
        if(questionairJsonObj!=null) {
        	out.setQuestionair(HAPStoryWizzardQuestionair.parseWizzardQuestionair(questionairJsonObj, parseService));
        }
		
		return out;
	}

}

