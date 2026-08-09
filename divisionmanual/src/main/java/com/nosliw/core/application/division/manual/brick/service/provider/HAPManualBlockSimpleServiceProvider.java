package com.nosliw.core.application.division.manual.brick.service.provider;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.service.provider.HAPBlockServiceProvider;
import com.nosliw.core.application.brick.spec.service.provider.HAPKeyService;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualBlockSimpleServiceProvider extends HAPManualBrickImp implements HAPBlockServiceProvider{

	public HAPManualBlockSimpleServiceProvider() {
		super(HAPEnumBrickType.SERVICEPROVIDER_100);
	}

	@Override
	public HAPKeyService getServiceKey() {	return (HAPKeyService)this.getAttributeValueOfValue(SERVICEID);	}

	public void setServiceKey(HAPKeyService serviceKey) {	this.setAttributeValueWithValue(SERVICEID, serviceKey);	}

}

@Component
class HAPManualBlockSimpleServiceProvider_parser extends HAPManualBrick_parser{

	public HAPManualBlockSimpleServiceProvider_parser() {
		super(HAPManualBlockSimpleServiceProvider.class, HAPEnumBrickType.SERVICEPROVIDER_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBlockSimpleServiceProvider out = new HAPManualBlockSimpleServiceProvider();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}
	
	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		if(attrName.equals(HAPBlockServiceProvider.SERVICEID)) {
			HAPKeyService serviceKey = new HAPKeyService();
			serviceKey.buildObject(obj, HAPSerializationFormat.JSON);
			return serviceKey;
		}
		
		return null;     
	}
	
}
