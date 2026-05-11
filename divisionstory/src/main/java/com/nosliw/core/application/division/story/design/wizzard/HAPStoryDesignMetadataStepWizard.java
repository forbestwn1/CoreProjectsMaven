package com.nosliw.core.application.division.story.design.wizzard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryDesignMetadataStepWizard extends HAPSerializableImp implements HAPStoryDesignMetadataStep, HAPEntityParsable{

	@HAPAttribute
	public static final String PARSABLEENTITYTYPE = "story.wizzard.step.metadata";
	
	@HAPAttribute
	public static final String STEPDEFINITION = "stepDefinition";
	
	@HAPAttribute
	public static final String QUESTIONAIR = "questionair";
	
	//step definition
	private HAPStoryWizzardStepDefinition m_stepDefinition;

	//questionair for this step
	private List<HAPStoryWizzardQuestionair> m_questionairs;
	
	public HAPStoryDesignMetadataStepWizard() {}
	
	public HAPStoryDesignMetadataStepWizard(HAPStoryWizzardStepDefinition stepDefinition) {
		this.m_questionairs = new ArrayList<HAPStoryWizzardQuestionair>();
	    this.m_stepDefinition = stepDefinition;
	}
	
	public void addQuestionair(HAPStoryWizzardQuestionair questionair) {
		this.m_questionairs.add(questionair);
	}
	
	public void setStepDefinition(HAPStoryWizzardStepDefinition stepDefinition) {    this.m_stepDefinition = stepDefinition;       }
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STEPDEFINITION, this.m_stepDefinition.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(QUESTIONAIR, HAPManagerSerialize.getInstance().toStringValue(this.m_questionairs, HAPSerializationFormat.JSON));
	}

}

@Component
class HAPStoryDesignMetadataStepWizard_HAPEntityParsable implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPStoryDesignMetadataStepWizard.PARSABLEENTITYTYPE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		
		HAPStoryDesignMetadataStepWizard out = new HAPStoryDesignMetadataStepWizard();
		
		JSONObject stepDefJsonObj = jsonObj.optJSONObject(HAPStoryDesignMetadataStepWizard.STEPDEFINITION);
		HAPStoryWizzardStepDefinition stepDef = new HAPStoryWizzardStepDefinition();
		stepDef.buildObject(stepDefJsonObj, HAPSerializationFormat.JSON);
		out.setStepDefinition(stepDef);
		
        JSONArray questionairArray = jsonObj.getJSONArray(HAPStoryDesignMetadataStepWizard.QUESTIONAIR);
        for(int i=0; i<questionairArray.length(); i++) {
        	JSONObject questionairJson = questionairArray.getJSONObject(i);
        	HAPStoryWizzardQuestionair questionair = (HAPStoryWizzardQuestionair)parseService.parseEntityJSONImplicitAttribute(questionairJson, HAPStoryWizzardQuestionair.TYPE, HAPStoryWizzardQuestionair.PARSE_DOMAIN);
        	out.addQuestionair(questionair);
        }
		
		return out;
	}

}

