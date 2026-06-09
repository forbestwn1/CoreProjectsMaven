package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryAlias;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityStoryParse;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryChangeItemRunnableNew extends HAPStoryChangeItem{

	@HAPAttribute
	public static final String ALIAS = "alias";

	@HAPAttribute
	public static final String RUNNABLE = "runnable";

	private HAPStoryAlias m_alias;
	
	private HAPStoryRunnable m_storyRunnable;
	
	public HAPStoryChangeItemRunnableNew() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_RUNNABLE_NEW);
	}
	
	public HAPStoryChangeItemRunnableNew(HAPStoryRunnable storyRunnable, HAPStoryAlias alias) {
		this();
		this.m_alias = alias;
		this.m_storyRunnable = storyRunnable;
	}
	
	public HAPStoryChangeItemRunnableNew(HAPStoryRunnable storyRunnable) {
		this(storyRunnable, null);
	}

	public HAPStoryAlias getAlias() {	return this.m_alias;	}
	public void setAlias(HAPStoryAlias alias) {    this.m_alias = alias;     }

	public HAPStoryRunnable getRunnable() {  return this.m_storyRunnable;  }
	public void setRunnable(HAPStoryRunnable storyRunnable) {    this.m_storyRunnable = storyRunnable;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(RUNNABLE, this.getRunnable().toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ALIAS, HAPUtilityJson.buildJson(this.m_alias, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPStoryChangeItemRunnableNew_HAPEntityParsable extends HAPStoryChangeItem__HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_CHANGETYPE_RUNNABLE_NEW;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeItemRunnableNew out = new HAPStoryChangeItemRunnableNew();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemRunnableNew changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		
		JSONObject aliasObj = jsonObj.optJSONObject(HAPStoryChangeItemRunnableNew.ALIAS);
		if(aliasObj!=null) {
			HAPStoryAlias alias = new HAPStoryAlias();
			alias.buildObject(aliasObj, HAPSerializationFormat.JSON);
			changeItem.setAlias(alias);
		}
		
		JSONObject eleObj = jsonObj.optJSONObject(HAPStoryChangeItemRunnableNew.RUNNABLE);
		if(eleObj!=null) {
			changeItem.setRunnable(HAPStoryUtilityStoryParse.parseRunnable(eleObj, parseService));
		}
	}
	
}

