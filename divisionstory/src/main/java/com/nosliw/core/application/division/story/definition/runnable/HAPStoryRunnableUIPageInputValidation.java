package com.nosliw.core.application.division.story.definition.runnable;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryParserRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;

public class HAPStoryRunnableUIPageInputValidation extends HAPStoryRunnable{

	public static final String PAGE = "PAGE";
	
    private String m_page;
	
	public HAPStoryRunnableUIPageInputValidation() {
		super(HAPConstantShared.STORYNODE_TYPE_TASK_VALIDATEPAGEINPUT);
	}

	public HAPStoryRunnableUIPageInputValidation(String page) {
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
class HAPStoryRunnableUIPageInputValidation__HAPEntityParsable extends HAPStoryParserRunnable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_TASK_VALIDATEPAGEINPUT;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnableUIPagePresent runnable, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, runnable, parseService);
		runnable.setPage(jsonObj.getString(HAPStoryRunnableUIPageInputValidation.PAGE));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryRunnableUIPageInputValidation out = new HAPStoryRunnableUIPageInputValidation();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
