package com.nosliw.core.application.division.story.design.wizzard;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.division.story.design.HAPStoryDesignMetadataStep;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardRequestDataNext extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static final String PARSABLEENTITYTYPE = "story.wizzard.requestdata.next";
	
	@HAPAttribute
	public static final String STEP = "step";
	
    private HAPStoryDesignMetadataStepWizard m_stepData;
    
    public void setStepData(HAPStoryDesignMetadataStepWizard stepData) {   	this.m_stepData = stepData;    }
	public HAPStoryDesignMetadataStepWizard getStepData() {    return this.m_stepData;    }
	
}

@Component
class HAPStoryWizzardRequestDataNext_HAPEntityParsable implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPStoryWizzardRequestDataNext.PARSABLEENTITYTYPE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPStoryWizzardRequestDataNext out = new HAPStoryWizzardRequestDataNext();
		
		out.setStepData((HAPStoryDesignMetadataStepWizard)HAPStoryDesignMetadataStep.parseDesignMetadata(jsonObj.getJSONObject(HAPStoryWizzardRequestDataNext.STEP), parseService));
		
//		out.setStepData((HAPStoryDesignMetadataStepWizard)parseService.parseEntityJSONExplicit(jsonObj.getJSONObject(HAPStoryWizzardRequestDataNext.STEP), HAPStoryDesignMetadataStepWizard.PARSABLEENTITYTYPE));
		return out;
	}

}

