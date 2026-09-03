package com.nosliw.core.application.common.structure;

import java.util.Map;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPElementStructureLeafNone extends HAPElementStructure{

	public HAPElementStructureLeafNone() {	}

	@Override
	public String getType() {		return HAPConstantShared.CONTEXT_ELEMENTTYPE_NONE;	}

	@Override
	public HAPElementStructure cloneStructureElement() {
		HAPElementStructureLeafNone out = new HAPElementStructureLeafNone();
		this.toStructureElement(out);
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

	@Override
	public void toStructureElement(HAPElementStructure out) {
		super.toStructureElement(out);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj)) {
			return false;
		}

		boolean out = false;
		if(obj instanceof HAPElementStructureLeafNone) {
			out = true;
		}
		return out;
	}

}
