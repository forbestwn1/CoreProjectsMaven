package com.nosliw.core.application.division.story.definition;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryUtilityStoryParse {

	public static HAPStoryStory parseStory(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		
		HAPStoryStory out = new HAPStoryStory();
		
		out.setIndex(jsonObj.getInt(HAPStoryStory.IDINDEX));
		
		JSONArray elementJsonArray = jsonObj.getJSONArray(HAPStoryStory.ELEMENT);
		for(int i=0; i<elementJsonArray.length(); i++) {
			HAPStoryElement element = parseElement(elementJsonArray.getJSONObject(i), entityParseService);
			out.addElement(element);
		}
		
		JSONObject aliasJsonMap = jsonObj.getJSONObject(HAPStoryStory.ELEMENTBYALIAS);
		for(Object key : aliasJsonMap.keySet()) {
			String alias = (String)key;
			HAPStoryIdElement eleId = new HAPStoryIdElement();
			eleId.parseKey(aliasJsonMap.getString(alias));
			out.setElementAlias(eleId, new HAPStoryAlias(alias));
		}
		
		return out;
	}

	public static HAPStoryElement parseElement(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		return (HAPStoryElement)entityParseService.parseEntityJSONImplicitAttribute(jsonObj, HAPStoryElement.ELEMENTTYPE, HAPStoryElement.PARSABLEENTITYDOMAIN);
	}
	
	public static HAPStoryRunnable parseRunnable(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		return (HAPStoryRunnable)entityParseService.parseEntityJSONImplicitAttribute(jsonObj, HAPStoryRunnable.RUNNABLETYPE, HAPStoryRunnable.PARSABLEENTITYDOMAIN);
	}
	
}
