package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityStoryParse;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryChangeItemNew extends HAPStoryChangeItem{

	@HAPAttribute
	public static final String ALIAS = "alias";

	@HAPAttribute
	public static final String ELEMENT = "element";

	private HAPStoryAliasElement m_alias;
	
	private HAPStoryElement m_storyElement;
	
	public HAPStoryChangeItemNew() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_NEW);
	}
	
	public HAPStoryChangeItemNew(HAPStoryElement storyElement, HAPStoryAliasElement alias) {
		this();
		this.m_alias = alias;
		this.m_storyElement = storyElement;
	}
	
	public HAPStoryChangeItemNew(HAPStoryElement storyElement) {
		this(storyElement, null);
	}

	public HAPStoryAliasElement getAlias() {	return this.m_alias;	}
	public void setAlias(HAPStoryAliasElement alias) {    this.m_alias = alias;     }

	public HAPStoryElement getElement() {  return this.m_storyElement;  }
	
	public HAPStoryIdElement getElementId() {     return this.m_storyElement.getElementId();      } 
	
	public void setElement(HAPStoryElement storyEle) {    this.m_storyElement = storyEle;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENT, this.getElement().toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ALIAS, HAPUtilityJson.buildJson(this.m_alias, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPStoryChangeItemNew_HAPEntityParsable extends HAPStoryChangeItem__HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_CHANGETYPE_NEW;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeItemNew out = new HAPStoryChangeItemNew();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemNew changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		
		JSONObject aliasObj = jsonObj.optJSONObject(HAPStoryChangeItemNew.ALIAS);
		if(aliasObj!=null) {
			HAPStoryAliasElement alias = new HAPStoryAliasElement();
			alias.buildObject(aliasObj, HAPSerializationFormat.JSON);
			changeItem.setAlias(alias);
		}
		
		JSONObject eleObj = jsonObj.optJSONObject(HAPStoryChangeItemNew.ELEMENT);
		if(eleObj!=null) {
			changeItem.setElement(HAPStoryUtilityStoryParse.parseElement(eleObj, parseService));
		}
	}
	
}

