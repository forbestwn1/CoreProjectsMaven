package com.nosliw.core.application.division.manual.brick.adapter.dataassociation;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.adapter.dataassociation.HAPAdapterDataAssociation;
import com.nosliw.core.application.common.dataassociation.HAPDataAssociation;
import com.nosliw.core.application.division.manual.brick.adapter.dataassociationfortask.HAPManualAdapterDataAssociationForTask;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualAdapterDataAssociation extends HAPManualBrickImp implements HAPAdapterDataAssociation{

	public HAPManualAdapterDataAssociation() {
		super(HAPEnumBrickType.DATAASSOCIATION_100);
	}

	@Override
	public HAPDataAssociation getDataAssociation() {   return (HAPDataAssociation)this.getAttributeValueOfValue(DATAASSOCIATION);     }

	public void setDataAssciation(HAPDataAssociation dataAssciation) {    this.setAttributeValueWithValue(DATAASSOCIATION, dataAssciation);    }

}

@Component
class HAPManualAdapterDataAssociation_parser extends HAPManualBrick_parser{

	public HAPManualAdapterDataAssociation_parser() {
		super(HAPManualAdapterDataAssociationForTask.class, HAPEnumBrickType.DATAASSOCIATION_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualAdapterDataAssociation out = new HAPManualAdapterDataAssociation();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}

	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName){
		case HAPAdapterDataAssociation.DATAASSOCIATION:
		{
			return HAPDataAssociation.parseDataAssociation(obj, parseService);
		}
		}
		return null;     
	}
}
