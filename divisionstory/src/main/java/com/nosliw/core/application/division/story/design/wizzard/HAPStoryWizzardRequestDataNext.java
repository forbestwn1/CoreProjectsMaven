package com.nosliw.core.application.division.story.design.wizzard;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;

public class HAPStoryWizzardRequestDataNext extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static final String ENTITYNAMEFORSERIALIZE = "story.wizzard.requestdata.next";
	
	@HAPAttribute
	public static final String STEP = "step";
	
    private HAPStoryDesignMetadataStepWizard m_stepData;
    
    public void setStepData(HAPStoryDesignMetadataStepWizard stepData) {   	this.m_stepData = stepData;    }
	public HAPStoryDesignMetadataStepWizard getStepData() {    return this.m_stepData;    }
	
}

@Component
class HAPStoryWizzardRequestDataNext_HAPEntityParsable implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPStoryWizzardRequestDataNext.ENTITYNAMEFORSERIALIZE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPStoryWizzardRequestDataNext out = new HAPStoryWizzardRequestDataNext();
		
		out.setStepData((HAPStoryDesignMetadataStepWizard)HAPStoryDesignMetadataStep.parseDesignMetadata(jsonObj.getJSONObject(HAPStoryWizzardRequestDataNext.STEP), parseService));
		
//		out.setStepData((HAPStoryDesignMetadataStepWizard)parseService.parseEntityJSONExplicit(jsonObj.getJSONObject(HAPStoryWizzardRequestDataNext.STEP), HAPStoryDesignMetadataStepWizard.PARSABLEENTITYTYPE));
		return out;
	}

}

