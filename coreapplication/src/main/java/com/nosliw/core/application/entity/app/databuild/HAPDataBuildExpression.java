package com.nosliw.core.application.entity.app.databuild;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPDataBuildExpression extends HAPSerializableImp{

	@HAPAttribute
	public static final String OPERAND = "operand";
	
	private HAPDataBuildOperand m_operand;
	
	
	
	
}
