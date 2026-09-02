package com.nosliw.core.application.division.manual.brick.adapter.dataassociationfortask;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.adapter.dataassociationfortask.HAPAdapterDataAssociationForTask;
import com.nosliw.core.application.common.dataassociation.HAPDataAssociationForTask;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualAdapterDataAssociationForTask extends HAPManualBrickImp implements HAPAdapterDataAssociationForTask{

	public HAPManualAdapterDataAssociationForTask() {
		super(HAPEnumBrickType.DATAASSOCIATIONFORTASK_100);
	}
	
	public void setDataAssciation(HAPDataAssociationForTask dataAssciation) {    this.setAttributeValueWithValue(DATAASSOCIATION, dataAssciation);    }
	@Override
	public HAPDataAssociationForTask getDataAssociation() {   return (HAPDataAssociationForTask)this.getAttributeValueOfValue(DATAASSOCIATION);     }
}

@Component
class HAPManualAdapterDataAssociationForTask_parser extends HAPManualBrick_parser{

	public HAPManualAdapterDataAssociationForTask_parser() {
		super(HAPManualAdapterDataAssociationForTask.class, HAPEnumBrickType.DATAASSOCIATIONFORTASK_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualAdapterDataAssociationForTask out = new HAPManualAdapterDataAssociationForTask();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}

	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName){
		case HAPAdapterDataAssociationForTask.DATAASSOCIATION:
		{
			return parseService.parseEntityJSONExplicit((JSONObject)obj, HAPDataAssociationForTask.ENTITYNAMEFORSERIALIZE);
		}
		}
		return null;     
	}
}
