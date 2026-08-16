package com.nosliw.core.application.division.manual.core;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPAdapter;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.brick.serialize.HAPParserBrick;
import com.nosliw.core.application.division.manual.common.valuecontext.HAPManualValueContext;
import com.nosliw.core.application.valueport.HAPContainerValuePorts;

public abstract class HAPManualBrick_parser extends HAPParserBrick{

	public HAPManualBrick_parser(Class<? extends HAPBrickImp> brickClass, HAPIdBrickType brickTypeId) {
		super(HAPManualBrick.PARSE_DOMAIN, brickClass, brickTypeId);
	}

	@Override
	protected HAPAttributeInBrick newAttributeObject() {
		return new HAPManualAttributeInBrick();
	}

	@Override
	protected HAPAdapter newAdapterObject() {
		return new HAPManualAdapter();
	}

	@Override
	protected void parseValuePort(JSONObject jsonObj, HAPBrickImp brick, HAPServiceParseEntity parseService) {
		HAPManualBrick manualBrick = (HAPManualBrick)brick;
		//value context
		HAPManualValueContext valueContext = new HAPManualValueContext();
		valueContext.buildObject(jsonObj.getJSONObject(HAPManualBrick.VALUECONTEXT), HAPSerializationFormat.JSON);
		manualBrick.setManualValueContext(valueContext);
		
		//other valueport
		HAPContainerValuePorts otherInternalValuePortsContainer = new HAPContainerValuePorts();
		otherInternalValuePortsContainer.buildObject(jsonObj.getJSONObject(HAPManualBrick.OTHERINTERNALVALUEPORTSCONTAINER), HAPSerializationFormat.JSON);
		manualBrick.setOtherInternalValuePortContainer(otherInternalValuePortsContainer);
		
		HAPContainerValuePorts otherExternalValuePortsContainer = new HAPContainerValuePorts();
		otherExternalValuePortsContainer.buildObject(jsonObj.getJSONObject(HAPManualBrick.OTHEREXTERNALVALUEPORTSCONTAINER), HAPSerializationFormat.JSON);
		manualBrick.setOtherExternalValuePortContainer(otherExternalValuePortsContainer);
	}

}