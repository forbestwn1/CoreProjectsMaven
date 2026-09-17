package com.nosliw.core.service.staticresource;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPStaticResponseInfoContent extends HAPStaticResponseInfo{

	@HAPAttribute
	public static final String CONTENT = "content";

	private String m_content;
	
	public HAPStaticResponseInfoContent() {	
		super(HAPConstantShared.STATIC_RESPONSE_TYPE_CONTENT);
	}
	
	public HAPStaticResponseInfoContent(String content) {
		this();
		this.m_content = content; 
	}

    public String getContent() {   return this.m_content;     }
    public void setContent(String content) {      this.m_content = content;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONTENT, this.m_content);
	}
}

@Component
class HAPStaticResponseInfoContent__HAPEntityParsable extends HAPStaticResponseInfo__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.STATIC_RESPONSE_TYPE_CONTENT;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticResponseInfoContent staticResponseInfoContent, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticResponseInfoContent, parseService);
		try {
			staticResponseInfoContent.setContent(jsonObj.getString(HAPStaticResponseInfoContent.CONTENT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticResponseInfoContent out = new HAPStaticResponseInfoContent();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
