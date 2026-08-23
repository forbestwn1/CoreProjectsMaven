package com.nosliw.core.service.staticresource;

import java.net.URI;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPStaticResponseInfoFile extends HAPStaticResponseInfo{

	@HAPAttribute
	public static final String URI = "uri";

	private URI m_staticURI;
	
	public HAPStaticResponseInfoFile() {	
		super(HAPConstantShared.STATIC_RESPONSE_TYPE_FILE);
	}
	
	public HAPStaticResponseInfoFile(URI uri) {
		this();
		this.m_staticURI = uri; 
	}

    public URI getURI() {   return this.m_staticURI;     }
    public void setURI(URI uri) {      this.m_staticURI = uri;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(URI, this.m_staticURI.toString());
	}
}

@Component
class HAPStaticResponseInfoFile__HAPEntityParsable extends HAPStaticResponseInfo__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.STATIC_RESPONSE_TYPE_FILE;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticResponseInfoFile staticResponseInfoFolder, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticResponseInfoFolder, parseService);
		try {
			staticResponseInfoFolder.setURI(new URI(jsonObj.getString(HAPStaticResponseInfoFile.URI)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticResponseInfoFile out = new HAPStaticResponseInfoFile();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
