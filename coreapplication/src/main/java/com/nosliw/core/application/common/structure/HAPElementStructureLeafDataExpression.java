package com.nosliw.core.application.common.structure;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPIdBrickInBundle;

public class HAPElementStructureLeafDataExpression extends HAPElementStructure{

	@HAPAttribute
	public static final String EXPRESSIONID = "expressionId";

	@HAPAttribute
	public static final String BRICKID = "brickId";

	private String m_expressionId;
	
	private HAPIdBrickInBundle m_brickId;
	

	public HAPElementStructureLeafDataExpression() {}
	
	public HAPElementStructureLeafDataExpression(String expressionId, HAPIdBrickInBundle brickId) {
		this.m_expressionId = expressionId;
		this.m_brickId = brickId;
	}
	
	@Override
	public String getType() {	return HAPConstantShared.CONTEXT_ELEMENTTYPE_DATAEXPRESSION;	}

	public String getExpressionId() {     return this.m_expressionId;        }
	public void setExpressionId(String expressionId) {      this.m_expressionId = expressionId;       }
	
	public HAPIdBrickInBundle getBrickId() {      return this.m_brickId;         }
	public void setBrickId(HAPIdBrickInBundle brickId) {     this.m_brickId = brickId;       }
	
	
	@Override
	public HAPElementStructure cloneStructureElement() {
		HAPElementStructureLeafDataExpression out = new HAPElementStructureLeafDataExpression();
		this.toStructureElement(out);
		return out;
	}

	@Override
	public void toStructureElement(HAPElementStructure out) {
		super.toStructureElement(out);
		HAPElementStructureLeafDataExpression deOut = (HAPElementStructureLeafDataExpression)out;
		deOut.m_brickId = this.m_brickId;
		deOut.m_expressionId = this.m_expressionId;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(EXPRESSIONID, this.m_expressionId);
		jsonMap.put(BRICKID, this.m_brickId.toStringValue(HAPSerializationFormat.JSON));
	}


	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj)) {
			return false;
		}

		boolean out = false;
		if(obj instanceof HAPElementStructureLeafDataExpression) {
			out = true;
		}
		return out;
	}
}
