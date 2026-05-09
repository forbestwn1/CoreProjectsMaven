package com.nosliw.core.application.division.story.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;

@RestController
@RequestMapping("/nosliw/story")
@HAPEntityWithAttribute
public class HAPAPIStory {

	@Autowired
	private HAPStoryManagerDesign m_designManager;
	
	@PostMapping("/new")
    public String newStory(@RequestParam String builderId) {
		HAPStoryDesign design = m_designManager.newStoryDesign(builderId, null);
		HAPServiceData out = HAPServiceData.createSuccessData(design.getId());
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	
}
