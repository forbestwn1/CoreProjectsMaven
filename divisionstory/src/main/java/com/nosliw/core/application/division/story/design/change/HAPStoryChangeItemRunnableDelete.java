package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryChangeItemRunnableDelete extends HAPStoryChangeItem{

	public static final String RUNNABLEID = "runnableId";
	
	private String m_runnableId;
	
	public HAPStoryChangeItemRunnableDelete() {
		super(HAPConstantShared.STORYDESIGN_CHANGETYPE_RUNNABLE_DELETE);
	}
	
	public HAPStoryChangeItemRunnableDelete(String runnableId) {
		this();
		this.m_runnableId = runnableId;
	}
	
	public String getRunnableId() {     return this.m_runnableId;       }
	public void setRunnableId(String runnable) {     this.m_runnableId = runnable;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(RUNNABLEID, this.m_runnableId);
	}
}

@Component
class HAPStoryChangeItemRunnableDelete_HAPEntityParsable extends HAPStoryChangeItem__HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYDESIGN_CHANGETYPE_RUNNABLE_DELETE;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeItemRunnableDelete out = new HAPStoryChangeItemRunnableDelete();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItemRunnableDelete changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		changeItem.setRunnableId(jsonObj.getString(HAPStoryChangeItemRunnableDelete.RUNNABLEID));
	}
}
