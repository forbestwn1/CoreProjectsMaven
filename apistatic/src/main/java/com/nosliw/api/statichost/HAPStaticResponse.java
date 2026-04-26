package com.nosliw.api.statichost;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;

@HAPEntityWithAttribute
public class HAPStaticResponse extends HAPSerializableImp{

	@HAPAttribute
	public static final String URI = "uri";

	private List<URI> m_staticURI;
	
	public HAPStaticResponse() {
		this.m_staticURI = new ArrayList<URI>();
	}
	
	public void addURI(URI uri) {
		this.m_staticURI.add(uri);
	}
	
	public List<URI> getURIs(){
		return this.m_staticURI;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		JSONArray uriArray = jsonObj.getJSONArray(URI);
        for(int i=0; i<uriArray.length(); i++) {
        	try {
				this.m_staticURI.add(new URI(uriArray.getString(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
        }
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(URI, HAPUtilityJson.buildJson(m_staticURI, HAPSerializationFormat.JSON));
	}
	
}
