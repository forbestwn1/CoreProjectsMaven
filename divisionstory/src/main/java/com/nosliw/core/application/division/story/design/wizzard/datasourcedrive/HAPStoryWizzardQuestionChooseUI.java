package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import org.springframework.stereotype.Component;

import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardQuestionChooseUI{

	
	
}


@Component
class HAPEntityParsableMy implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPStoryWizzardQuestionChooseService.PARSABLEENTITYTYPE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
	}

}


