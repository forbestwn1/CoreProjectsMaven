package com.nosliw.core.application.division.manual.brick.data;

import com.nosliw.com.application.spec.common.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.spec.data.HAPBlockData;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.data.HAPData;

public class HAPManualDefinitionBlockData extends HAPManualDefinitionBrick{

	public HAPManualDefinitionBlockData() {
		super(HAPEnumBrickType.DATA_100);
	}

	public HAPData getData() {    return (HAPData)this.getAttributeValueOfValue(HAPBlockData.DATA);     }

	public void setData(HAPData data) {    this.setAttributeValueWithValue(HAPBlockData.DATA, data);      }
	
}
