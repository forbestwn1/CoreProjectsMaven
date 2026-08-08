package com.nosliw.core.application.division.manual.brick.container;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNosliw;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.container.HAPBrickContainer;
import com.nosliw.core.application.division.manual.core.HAPManualBrickWithEntityInfo;
import com.nosliw.core.application.division.manual.core.HAPManualBrickWithEntityInfo_parser;

public class HAPManualBrickContainer extends HAPManualBrickWithEntityInfo implements HAPBrickContainer{

	protected HAPManualBrickContainer(HAPIdBrickType brickTypeId){
		super(brickTypeId);
	}
	
	public HAPManualBrickContainer() {
		super(HAPEnumBrickType.CONTAINER_100);
	}
	
	@Override
	public void init() {
		super.init();
		this.setAttributeIndex(0);
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
		index++;
		this.setAttributeValueWithValue(HAPBrickContainer.ATTRINDEX, index);
		return HAPConstantShared.PREFIX_ELEMENTID_COTAINER+index+"";
	}
	
	private void setAttributeIndex(Integer index) {		this.setAttributeValueWithValue(HAPBrickContainer.ATTRINDEX, index);	}
}

@Component
class HAPManualBrickContainer_parser extends HAPManualBrickWithEntityInfo_parser{

	public HAPManualBrickContainer_parser(HAPManagerApplicationBrick brickManager) {
		super(brickManager, HAPManualBrickContainerList.class, HAPEnumBrickType.CONTAINER_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBrickContainer out = new HAPManualBrickContainer();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}

	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		Object out = super.parseValueInAttribute(attrName, obj, parseService);
		if(out!=null) {
			return out;
		}
		if(attrName.equals(HAPBrickContainer.ATTRINDEX)) {
			return obj;
		}
		
		return null;     
	}

}
