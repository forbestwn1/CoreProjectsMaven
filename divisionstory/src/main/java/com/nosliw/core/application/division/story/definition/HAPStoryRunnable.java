package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;

public abstract class HAPStoryRunnable extends HAPEntityInfoImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.definition.runnable";

	public static final String RUNNABLETYPE = "runnableType";
	
	private String m_runnableType;
	
	public HAPStoryRunnable(String runnableType) {
		this.m_runnableType = runnableType;
	}
	
	public String getRunnableType() {     return this.m_runnableType;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(RUNNABLETYPE, this.getRunnableType());
	}

}
