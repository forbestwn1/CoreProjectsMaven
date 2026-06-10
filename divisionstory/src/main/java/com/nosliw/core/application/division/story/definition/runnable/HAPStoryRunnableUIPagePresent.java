package com.nosliw.core.application.division.story.definition.runnable;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryParserRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryRunnableUIPagePresent extends HAPStoryRunnable{

	public static final String PAGE = "PAGE";
	
    private String m_page;
	
	public HAPStoryRunnableUIPagePresent() {
		super(HAPConstantShared.STORYNODE_TYPE_TASK_PRESENTPAGE);
	}

	public HAPStoryRunnableUIPagePresent(String page) {
		this();
		this.m_page = page;
	}

	public String getPage() {     return this.m_page;      }
	public void setPage(String page) {     this.m_page = page;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PAGE, this.m_page);
	}

}

@Component
class HAPStoryRunnableUIPagePresent__HAPEntityParsable extends HAPStoryParserRunnable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_TASK_PRESENTPAGE;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnableUIPagePresent runnable, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, runnable, parseService);
		runnable.setPage(jsonObj.getString(HAPStoryRunnableUIPagePresent.PAGE));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryRunnableUIPagePresent out = new HAPStoryRunnableUIPagePresent();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
