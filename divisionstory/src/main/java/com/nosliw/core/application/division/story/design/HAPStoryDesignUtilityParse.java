package com.nosliw.core.application.division.story.design;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityStoryParse;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryDesignUtilityParse {

	public static HAPStoryDesign parseStoryDesign(File file, HAPServiceParseEntity entityParseService) {
		HAPStoryDesign out = new HAPStoryDesign();
		
		String content = HAPUtilityFile.readFile(file);
		
		JSONObject designJsonObj = new JSONObject(content);
		
		out.buildEntityInfoByJson(designJsonObj);
		
		out.setBuilderId(designJsonObj.getString(HAPStoryDesign.BUILDERID));

		out.setStory(HAPStoryUtilityStoryParse.parseStory(designJsonObj.getJSONObject(HAPStoryDesign.STORY), entityParseService));
		
		JSONArray stepJsonArray = designJsonObj.optJSONArray(HAPStoryDesign.STEP);
		if(stepJsonArray!=null) {
			for(int i=0; i<stepJsonArray.length(); i++) {
				HAPStoryDesignStep step = parseDesignStep(stepJsonArray.getJSONObject(i), entityParseService);
				out.addStep(step);
			}
		}
		
		return out;
	}
	
	private static HAPStoryDesignStep parseDesignStep(JSONObject stepJsonObj, HAPServiceParseEntity entityParseService) {
		HAPStoryDesignStep out = new HAPStoryDesignStep();
		
		JSONObject metaDataJsonObj = stepJsonObj.optJSONObject(HAPStoryDesignStep.METADATA);
		if(metaDataJsonObj!=null) {
			out.setMetaData((HAPStoryDesignMetadataStep)entityParseService.parseEntityJSONImplicitAttribute(metaDataJsonObj, HAPStoryDesignMetadataStep.TYPE, HAPStoryDesignMetadataStep.PARSABLEENTITYDOMAIN));
		}
		
		JSONArray requestChangesJsonArray = stepJsonObj.optJSONArray(HAPStoryDesignStep.REQUESTCHANGE);
		if(requestChangesJsonArray!=null) {
			for(int i=0; i<requestChangesJsonArray.length(); i++) {
				out.addRequestChange((HAPStoryChangeItem)entityParseService.parseEntityJSONImplicitAttribute(requestChangesJsonArray.getJSONObject(i), HAPStoryChangeItem.CHANGETYPE, HAPStoryChangeItem.PARSABLEENTITYDOMAIN));
			}
		}
		
		JSONArray allChangesJsonArray = stepJsonObj.optJSONArray(HAPStoryDesignStep.ALLCHANGE);
		if(allChangesJsonArray!=null) {
			for(int i=0; i<allChangesJsonArray.length(); i++) {
				out.addAllChange((HAPStoryChangeItem)entityParseService.parseEntityJSONImplicitAttribute(allChangesJsonArray.getJSONObject(i), HAPStoryChangeItem.CHANGETYPE, HAPStoryChangeItem.PARSABLEENTITYDOMAIN));
			}
		}
		
		return out;
	}
	
}
