package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;

@HAPEntityWithAttribute
public class HAPStoryDesignStep extends HAPSerializableImp{

	@HAPAttribute
	public static final String STEPTYPE = "stepType";
	
	@HAPAttribute
	public static final String METADATA = "metaData";
	
	@HAPAttribute
	public static final String ALLCHANGE = "allChange";
	
	@HAPAttribute
	public static final String REQUESTCHANGE = "requestChange";
	
	private String m_stepType;
	
	private HAPStoryDesignMetadataStep m_metaData;
	
	private List<HAPStoryChangeItem> m_requestChanges;
	
	//store all changes
	private List<HAPStoryChangeItem> m_allChanages;
	
	public HAPStoryDesignStep() {
		this.m_requestChanges = new ArrayList<HAPStoryChangeItem>();
		this.m_allChanages = new ArrayList<HAPStoryChangeItem>();
	}
	
	public HAPStoryDesignStep(String stepType, HAPStoryDesignMetadataStep metaData) {
		this();
		this.m_metaData = metaData;
	}

	public void clear() {
		this.m_allChanages.clear();
		this.m_requestChanges.clear();
		this.m_metaData.clear();
	}

	public String getStepType() {   return this.m_stepType;     }
	public void setStepType(String type) {     this.m_stepType = type;       }
	
	public HAPStoryDesignMetadataStep getMetaData() {     return this.m_metaData;      }
	public void setMetaData(HAPStoryDesignMetadataStep metaData) {    this.m_metaData = metaData;     }
	
	public void addRequestChange(HAPStoryChangeItem change) {    this.m_requestChanges.add(change);       }
	public void addAllChange(HAPStoryChangeItem change) {    this.m_allChanages.add(change);       }
	
	public void addChanges(List<HAPStoryChangeItem> requestChanges, List<HAPStoryChangeItem> changes) {
		this.m_allChanages.addAll(changes);
		this.m_requestChanges.addAll(requestChanges);
	}
	
    public List<HAPStoryChangeItem> getChanges() {    return this.m_allChanages;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STEPTYPE, this.m_stepType);
		jsonMap.put(METADATA, this.m_metaData.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ALLCHANGE, HAPUtilityJson.buildJson(this.m_allChanages, HAPSerializationFormat.JSON));
		jsonMap.put(REQUESTCHANGE, HAPUtilityJson.buildJson(this.m_requestChanges, HAPSerializationFormat.JSON));
	}

}
