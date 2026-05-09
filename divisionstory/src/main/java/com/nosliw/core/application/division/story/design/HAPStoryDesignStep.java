package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;

@HAPEntityWithAttribute
public class HAPStoryDesignStep extends HAPSerializableImp{

	@HAPAttribute
	public static final String METADATA = "metaData";
	
	@HAPAttribute
	public static final String ALLCHANGE = "allChange";
	
	@HAPAttribute
	public static final String REQUESTCHANGE = "requestChange";
	
	private HAPStoryDesignMetadataStep m_metaData;
	
	private List<HAPStoryChangeItem> m_requestChanges;
	
	//store all changes
	private List<HAPStoryChangeItem> m_allChanages;
	
	public HAPStoryDesignStep() {
		this.m_requestChanges = new ArrayList<HAPStoryChangeItem>();
		this.m_allChanages = new ArrayList<HAPStoryChangeItem>();
	}
	
	public HAPStoryDesignStep(HAPStoryDesignMetadataStep metaData) {
		this();
		this.m_metaData = metaData;
	}

	public void clear() {
		this.m_allChanages.clear();
		this.m_requestChanges.clear();
		this.m_metaData.clear();
	}

	public HAPStoryDesignMetadataStep getMetaData() {     return this.m_metaData;      }
	
	public void addChanges(List<HAPStoryChangeItem> requestChanges, List<HAPStoryChangeItem> changes) {
		this.m_allChanages.addAll(changes);
		this.m_requestChanges.addAll(requestChanges);
	}
	
    public List<HAPStoryChangeItem> getChanges() {    return this.m_allChanages;      }

	@Override
	protected boolean buildObjectByJson(Object json){  
		JSONObject jsonObj = (JSONObject)json;
		
		
		
		this.m_builderId = jsonObj.getString(BUILDERID);
		
		this.m_story.buildObject(jsonObj.getJSONObject(STORY), HAPSerializationFormat.JSON);
		
		JSONArray stepJsonArray = jsonObj.optJSONArray(STEP);
		if(stepJsonArray!=null) {
			for(int i=0; i<stepJsonArray.length(); i++) {
				HAPStoryDesignStep step = new HAPStoryDesignStep();
				step.buildObject(stepJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
				this.m_changeHistory.add(step);
			}
		}
		
		return true;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(METADATA, this.m_metaData.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ALLCHANGE, HAPUtilityJson.buildJson(this.m_allChanages, HAPSerializationFormat.JSON));
		jsonMap.put(REQUESTCHANGE, HAPUtilityJson.buildJson(this.m_requestChanges, HAPSerializationFormat.JSON));
	}

}
