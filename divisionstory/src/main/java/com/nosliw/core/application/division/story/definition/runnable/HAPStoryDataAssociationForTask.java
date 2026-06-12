package com.nosliw.core.application.division.story.definition.runnable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

public class HAPStoryDataAssociationForTask extends HAPSerializableImp{

	public final static String REQUESTDATAASSOCIATION = "requestDataAssociation";
	
	public final static String RESPONSEDATAASSOCIATION = "responseDataAssociation";
	
	private HAPStoryDataAssociationComplex m_requestDataAssociation;
	
	private Map<String, HAPStoryDataAssociationComplex> m_responseDataAssociation;
	
	public HAPStoryDataAssociationForTask() {
		this.m_responseDataAssociation = new LinkedHashMap<String, HAPStoryDataAssociationComplex>();
	}

	public HAPStoryDataAssociationComplex getRequestDataAssociation() {     return this.m_requestDataAssociation;     }
	public void setRequestDataAssociation(HAPStoryDataAssociationComplex requestDataAssociation) {     this.m_requestDataAssociation = requestDataAssociation;      }
	
    public Map<String, HAPStoryDataAssociationComplex> getResponseDataAssociations(){     return this.m_responseDataAssociation;         }
	public void addResponseDataAssociation(String name, HAPStoryDataAssociationComplex responseDataAssociation) {    this.m_responseDataAssociation.put(name, responseDataAssociation);       }
	
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(REQUESTDATAASSOCIATION, this.m_requestDataAssociation.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(RESPONSEDATAASSOCIATION, HAPManagerSerialize.getInstance().toStringValue(m_responseDataAssociation, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){  
		JSONObject jsonObj = (JSONObject)json;
		JSONObject requestDAJson = jsonObj.getJSONObject(REQUESTDATAASSOCIATION);
		if(requestDAJson!=null) {
			HAPStoryDataAssociationComplex requestDataAssociation = new HAPStoryDataAssociationComplex(); 
			requestDataAssociation.buildObject(requestDAJson, HAPSerializationFormat.JSON);
			this.setRequestDataAssociation(requestDataAssociation);
		}

		JSONObject responseDAJsonMap = jsonObj.getJSONObject(RESPONSEDATAASSOCIATION);
		if(responseDAJsonMap!=null) {
			for(Object key : responseDAJsonMap.keySet()) {
				String resultName = (String)key;
				JSONObject responseDA = jsonObj.getJSONObject(resultName);
				HAPStoryDataAssociationComplex responseDataAssociation = new HAPStoryDataAssociationComplex(); 
				responseDataAssociation.buildObject(responseDA, HAPSerializationFormat.JSON);
				this.addResponseDataAssociation(resultName, responseDataAssociation);
			}
		}
		
		return true;  
	}
	

}
