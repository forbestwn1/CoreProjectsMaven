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
import com.nosliw.core.application.brick.HAPIdBrickInBundle;

@HAPEntityWithAttribute
public class HAPEndPointInTunnelDataExpression extends HAPEndpointInTunnel{

	@HAPAttribute
	public static final String EXPRESSIONID = "expressionId";

	@HAPAttribute
	public static final String BRICKID = "brickId";

	private String m_expressionId;
	
	private HAPIdBrickInBundle m_brickId;

	public HAPEndPointInTunnelDataExpression() {
		super(HAPConstantShared.TUNNELENDPOINT_TYPE_DATAEXPRESSION);
	}

	public HAPEndPointInTunnelDataExpression(String expressionId, HAPIdBrickInBundle brickId) {
		this();
		this.m_expressionId = expressionId;
		this.m_brickId = brickId;
	}
	
	public String getExpressionId() {     return this.m_expressionId;        }
	public void setExpressionId(String expressionId) {      this.m_expressionId = expressionId;       }
	
	public HAPIdBrickInBundle getBrickId() {      return this.m_brickId;         }
	public void setBrickId(HAPIdBrickInBundle brickId) {     this.m_brickId = brickId;       }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(EXPRESSIONID, this.m_expressionId);
		jsonMap.put(BRICKID, this.m_brickId.toStringValue(HAPSerializationFormat.JSON));
	}
}

@Component
class HAPEndPointInTunnelDataExpression_parser extends HAPEndpointInTunnel_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPEndPointInTunnelDataExpression out = new HAPEndPointInTunnelDataExpression();

		JSONObject jsonObj = (JSONObject)obj;
		out.setExpressionId((String)jsonObj.opt(HAPEndPointInTunnelDataExpression.EXPRESSIONID));
		
		Object valueObj = jsonObj.opt(HAPEndPointInTunnelDataExpression.BRICKID);
		HAPIdBrickInBundle brickId = new HAPIdBrickInBundle();
		brickId.buildObject(valueObj, HAPSerializationFormat.JSON);
		out.setBrickId(brickId);
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.TUNNELENDPOINT_TYPE_DATAEXPRESSION;   }
	
}
