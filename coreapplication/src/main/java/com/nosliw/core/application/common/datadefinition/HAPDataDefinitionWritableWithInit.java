package com.nosliw.core.application.common.datadefinition;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDataDefinitionWritableWithInit extends HAPDataDefinitionWritable{

	@HAPAttribute
	public static String INITDATA = "initData";

	private HAPData m_initData;

	public HAPDataDefinitionWritableWithInit() {
		super(HAPConstantShared.DATADEFINITION_TYPE_WRITEABLEWITHINIT);
	}
	
	public HAPDataDefinitionWritableWithInit(HAPDataDefinition dataDefinition) {
		this();
		dataDefinition.cloneToDataDefinition(this);
	}
	
	public HAPData getInitData() {    return this.m_initData;     }
	public void setInitData(HAPData initData) {    this.m_initData = initData;      }

	@Override
	public boolean equals(Object obj){
		boolean out = false;
		if(obj instanceof HAPDataDefinitionWritableWithInit){
			HAPDataDefinitionWritableWithInit dataInfo = (HAPDataDefinitionWritableWithInit)obj;
			if(super.equals(dataInfo) && HAPUtilityBasic.isEquals(this.getInitData(), dataInfo.getInitData()))
			{
				out = true;
			}
		}
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_initData!=null) {
			jsonMap.put(INITDATA, this.m_initData.toStringValue(HAPSerializationFormat.JSON));
		}
	}
	
	@Override
	public boolean buildObject(Object value, HAPSerializationFormat format) {
		super.buildObject(value, format);
		if(value instanceof String) {
		}
		else if(value instanceof JSONObject){
			JSONObject jsonValue = (JSONObject)value;
			
			Object initDataObj = jsonValue.opt(INITDATA);
			if(initDataObj!=null) {
				this.m_initData = HAPUtilityData.buildDataWrapperFromObject(initDataObj);
			}
		}
		return true;
	}
	
	protected void cloneToDataDefinition(HAPDataDefinitionWritableWithInit out) {
		super.cloneToDataDefinition(out);
		if(this.m_initData!=null) {
			out.m_initData = this.m_initData.cloneData();
		}
	}
	
	public HAPDataDefinitionWritableWithInit cloneDataDefinitionWritableWithInit() {
		HAPDataDefinitionWritableWithInit out = new HAPDataDefinitionWritableWithInit();
		this.cloneToDataDefinition(out);
		return out;
	}
	
}

@Component
class HAPDataDefinitionWritableWithInit__HAPEntityParsable extends HAPDataDefinitionWritable__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.DATADEFINITION_TYPE_WRITEABLEWITHINIT;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPDataDefinitionWritableWithInit dataDefinition, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, dataDefinition, parseService);
		Object initDataObj = jsonObj.opt(HAPDataDefinitionWritableWithInit.INITDATA);
		if(initDataObj!=null) {
			dataDefinition.setInitData(HAPUtilityData.buildDataWrapperFromObject(initDataObj));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataDefinitionWritableWithInit out = new HAPDataDefinitionWritableWithInit();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

