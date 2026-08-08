package com.nosliw.core.application.common.dataassociation;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.valueport.HAPIdValuePortInBundle;

@HAPEntityWithAttribute
public class HAPEndPointInTunnelValuePort extends HAPEndpointInTunnel{

	@HAPAttribute
	public static String VALUEPORTREF = "valuePortRef";
	@HAPAttribute
	public static String VALUESTRUCTUREID = "valueStructureId";
	@HAPAttribute
	public static String ITEMPATH = "itemPath";

	private HAPIdValuePortInBundle m_valuePortRef;
	private String m_valueStructureId;
	private String m_itemPath;

	public HAPEndPointInTunnelValuePort() {
		super(HAPConstantShared.TUNNELENDPOINT_TYPE_VALUEPORT);
	}
	
	public HAPEndPointInTunnelValuePort(HAPIdValuePortInBundle valuePortRef, String valueStructureId, String itemPath) {
		this();
		this.m_valuePortRef = valuePortRef;
		this.m_valueStructureId = valueStructureId;
		this.m_itemPath = itemPath;
	}
	
	public void setValuePortRef(HAPIdValuePortInBundle valuePortRef) {     this.m_valuePortRef = valuePortRef;      }
	public void setValueStructureId(String valueStructureId) {     this.m_valueStructureId = valueStructureId;      }
	public void setItemPath(String itemPath) {     this.m_itemPath = itemPath;          }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUEPORTREF, this.m_valuePortRef.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(VALUESTRUCTUREID, this.m_valueStructureId);
		jsonMap.put(ITEMPATH, this.m_itemPath);
	}
}

@Component
class HAPEndPointInTunnelValuePort_parser extends HAPEndpointInTunnel_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPEndPointInTunnelValuePort out = new HAPEndPointInTunnelValuePort();

		JSONObject jsonObj = (JSONObject)obj;

		out.setItemPath((String)jsonObj.opt(HAPEndPointInTunnelValuePort.ITEMPATH));
		out.setValueStructureId((String)jsonObj.opt(HAPEndPointInTunnelValuePort.VALUESTRUCTUREID));
		
		JSONObject valuePortRefJsonObj = jsonObj.optJSONObject(HAPEndPointInTunnelValuePort.VALUEPORTREF);
		if(valuePortRefJsonObj!=null) {
			HAPIdValuePortInBundle valuePortRef = new HAPIdValuePortInBundle();
			valuePortRef.buildObject(valuePortRefJsonObj, HAPSerializationFormat.JSON);
			out.setValuePortRef(valuePortRef);
		}
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.TUNNELENDPOINT_TYPE_VALUEPORT;   }
	
}
