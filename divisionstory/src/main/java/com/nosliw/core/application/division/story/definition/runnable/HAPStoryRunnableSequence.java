package com.nosliw.core.application.division.story.definition.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryParserRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryRunnableSequence extends HAPStoryRunnable{

	public static final String RUNNABLES = "runnables"; 
	
	private List<String> m_runnables;
	
	public HAPStoryRunnableSequence() {
		super(HAPConstantShared.STORYNODE_TYPE_TASK_SEQUENCE);
		this.m_runnables = new ArrayList<String>();
	}

	public void addRunnable(String id) {        this.m_runnables.add(id);       }
	public List<String> getRunnables(){      return this.m_runnables;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(RUNNABLES, HAPManagerSerialize.getInstance().toStringValue(this.m_runnables, HAPSerializationFormat.JSON));
	}
	
}

@Component
class HAPStoryRunnableSequence__HAPEntityParsable extends HAPStoryParserRunnable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_TASK_SEQUENCE;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnableSequence runnable, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, runnable, parseService);
		
		JSONArray runnableJsonArray = jsonObj.getJSONArray(HAPStoryRunnableSequence.RUNNABLES);
		for(int i=0; i<runnableJsonArray.length(); i++) {
			runnable.addRunnable(runnableJsonArray.getString(i));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryRunnableSequence out = new HAPStoryRunnableSequence();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
