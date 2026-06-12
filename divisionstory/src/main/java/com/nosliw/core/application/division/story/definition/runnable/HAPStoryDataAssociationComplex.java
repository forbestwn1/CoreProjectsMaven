package com.nosliw.core.application.division.story.definition.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

public class HAPStoryDataAssociationComplex extends HAPSerializableImp{

	public static final String ITEMS = "items";
	
	private List<HAPStoryDataAssociation> m_dataAssocitaions;

	public HAPStoryDataAssociationComplex() {
		this.m_dataAssocitaions = new ArrayList<HAPStoryDataAssociation>();
	}
	
	public List<HAPStoryDataAssociation> getDataAssociations(){		return this.m_dataAssocitaions; 	}
	public void addDataAssociation(HAPStoryDataAssociation dataAssociation) {    this.m_dataAssocitaions.add(dataAssociation);      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ITEMS, HAPManagerSerialize.getInstance().toStringValue(m_dataAssocitaions, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
	
		JSONArray itemJsonArray = jsonObj.getJSONArray(ITEMS);
		for(int i=0; i<itemJsonArray.length(); i++) {
			HAPStoryDataAssociation dataAssociation = new HAPStoryDataAssociation();
			dataAssociation.buildObject(itemJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
			this.addDataAssociation(dataAssociation);
		}
		return true;  
	}
}
