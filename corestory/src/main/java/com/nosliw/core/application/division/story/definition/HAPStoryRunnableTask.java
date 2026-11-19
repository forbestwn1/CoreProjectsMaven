package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryDataAssociationForTask;

public abstract class HAPStoryRunnableTask extends HAPStoryRunnable{

	public final static String DATAASSOCIATION = "dataAssociation";
	
	private HAPStoryDataAssociationForTask m_dataAssociation;
	
	public HAPStoryRunnableTask(String runnableType) {
		super(runnableType);
	}

	public HAPStoryDataAssociationForTask getDataAssociation() {      return this.m_dataAssociation;        }
	public void setDataAssociation(HAPStoryDataAssociationForTask dataAssociation) {      this.m_dataAssociation = dataAssociation;         }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_dataAssociation!=null) {
			jsonMap.put(DATAASSOCIATION, this.m_dataAssociation.toStringValue(HAPSerializationFormat.JSON));
		}
	}


}
