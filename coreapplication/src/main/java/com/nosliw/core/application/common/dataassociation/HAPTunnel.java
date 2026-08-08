package com.nosliw.core.application.common.dataassociation;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.data.matcher.HAPMatchers;

@HAPEntityWithAttribute
public class HAPTunnel extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static String FROMENDPOINT = "fromEndPoint";

	@HAPAttribute
	public static String TOENDPOINT = "toEndPoint";

	@HAPAttribute
	public static String MATCHERS = "matchers";
	
	private HAPEndpointInTunnel m_fromEndPoint;
	
	private HAPEndpointInTunnel m_toEndPoint;
	
	private HAPMatchers m_matchers;

	public HAPTunnel() {}

	public HAPTunnel(HAPEndpointInTunnel fromEndPoint, HAPEndpointInTunnel toEndPoint, HAPMatchers matchers) {
		this.m_fromEndPoint = fromEndPoint;
		this.m_toEndPoint = toEndPoint;
		this.m_matchers = matchers;
	}

	public HAPEndpointInTunnel getFromEndPoint() {   return this.m_fromEndPoint;     }
	public void setFromEndPoint(HAPEndpointInTunnel endPoint) {    this.m_fromEndPoint = endPoint;      }
	
	public HAPEndpointInTunnel getToEndPoint() {    return this.m_toEndPoint;     }
	public void setToEndPoint(HAPEndpointInTunnel endPoint) {    this.m_toEndPoint = endPoint;      }
	
	public void setMatchers(HAPMatchers matchers) {      this.m_matchers = matchers;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(FROMENDPOINT, HAPUtilityJson.buildJson(m_fromEndPoint, HAPSerializationFormat.JSON));
		jsonMap.put(TOENDPOINT, HAPUtilityJson.buildJson(m_toEndPoint, HAPSerializationFormat.JSON));
		jsonMap.put(MATCHERS, HAPUtilityJson.buildJson(m_matchers, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPTunnel_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPTunnel.class.getName();     }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPTunnel out = new HAPTunnel();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		out.setFromEndPoint(HAPEndpointInTunnel.parseTunnelEndpoint(jsonObj.optJSONObject(HAPTunnel.FROMENDPOINT), parseService));
		out.setToEndPoint(HAPEndpointInTunnel.parseTunnelEndpoint(jsonObj.optJSONObject(HAPTunnel.TOENDPOINT), parseService));
		
		JSONObject matchersJson = jsonObj.optJSONObject(HAPTunnel.MATCHERS);
		if(matchersJson!=null) {
			HAPMatchers matchers = new HAPMatchers();
			matchers.buildObject(matchersJson, HAPSerializationFormat.JSON);
			out.setMatchers(matchers);
		}
		
		return out;
	}
	
}
