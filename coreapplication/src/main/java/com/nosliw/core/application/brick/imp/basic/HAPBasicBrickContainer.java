package com.nosliw.core.application.brick.imp.basic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNosliw;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainer;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBrickContainer;

public class HAPBasicBrickContainer extends HAPBasicBrick implements HAPBrickContainer{

	public HAPBasicBrickContainer() {
		super(HAPEnumBrickType.CONTAINER_100);
	}
	
	@Override
	public List<HAPAttributeInBrick> getElements(){
		List<HAPAttributeInBrick> out = new ArrayList<HAPAttributeInBrick>();
		for(HAPAttributeInBrick attr : this.getAttributes()) {
			if(HAPUtilityNosliw.getNosliwCoreName(attr.getName())==null) {
				out.add(attr);
			}
		}
		return out;
	}
	
	public boolean isEmpty() {   return this.getElements().isEmpty();     }
	
	public String addElementWithBrickOrReference(HAPEntityOrReference brickOrRef) {
		return this.addElementAnom(brickOrRef);
	}
	
	private String addElementAnom(HAPEntityOrReference brickOrRef) {
		String attrName = this.generateAttributeName();
		this.setAttributeValueWithBrick(attrName, brickOrRef);
		return attrName;
	}

	private String generateAttributeName() {
		Integer index = (Integer)this.getAttributeValueOfValue(HAPBrickContainer.ATTRINDEX);
		if(index==null) {
			index = 0;
		}
		index++;
		this.setAttributeValueWithValue(HAPBrickContainer.ATTRINDEX, index);
		return HAPConstantShared.PREFIX_ELEMENTID_COTAINER+index+"";
	}
	
	private void setAttributeIndex(Integer index) {		this.setAttributeValueWithValue(HAPBrickContainer.ATTRINDEX, index);	}
}

@Component
class HAPBasicBrickContainer_parser extends HAPBasicBrickWithEntityInfo_parser{

	public HAPBasicBrickContainer_parser() {
		super(HAPBasicBrickContainer.class, HAPEnumBrickType.CONTAINER_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBrickContainer());
	}

}
