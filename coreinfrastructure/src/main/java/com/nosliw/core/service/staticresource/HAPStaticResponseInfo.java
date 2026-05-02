package com.nosliw.core.service.staticresource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPStaticResponseInfo extends HAPSerializableImp{

	@HAPAttribute
	public static final String URI = "uri";

	private URI m_staticURI;
	
	public HAPStaticResponseInfo() {	}
	
	public HAPStaticResponseInfo(URI uri) {
		this.m_staticURI = uri; 
	}

    public URI getURI() {   return this.m_staticURI;     }
    public void setURI(URI uri) {      this.m_staticURI = uri;        }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		try {
			this.m_staticURI = new URI(jsonObj.getString(URI));
		} catch (JSONException | URISyntaxException e) {
			e.printStackTrace();
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(URI, this.m_staticURI.toString());
	}
	
}
