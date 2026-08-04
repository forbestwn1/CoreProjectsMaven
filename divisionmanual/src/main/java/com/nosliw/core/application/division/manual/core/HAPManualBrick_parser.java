package com.nosliw.core.application.division.manual.core;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPAdapter;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.HAPValueOfDynamic;
import com.nosliw.core.application.HAPWrapperValue;
import com.nosliw.core.application.HAPWrapperValueOfBrick;
import com.nosliw.core.application.HAPWrapperValueOfDynamic;
import com.nosliw.core.application.HAPWrapperValueOfReferenceResource;
import com.nosliw.core.application.HAPWrapperValueOfValue;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.division.manual.common.valuecontext.HAPManualValueContext;
import com.nosliw.core.application.dynamic.HAPDynamicExecuteInputContainer;
import com.nosliw.core.application.valueport.HAPContainerValuePorts;
import com.nosliw.core.resource.HAPFactoryResourceId;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public abstract class HAPManualBrick_parser extends HAPParserEntityImpWithDomain{

	private HAPManagerApplicationBrick m_brickManager;
	
	public HAPManualBrick_parser(HAPManagerApplicationBrick brickManager) {
		this.m_brickManager = brickManager;
	}
	
	@Override
	public String getDomain() {     return HAPManualBrick.PARSE_DOMAIN;    }

	protected void parseBrickJson(JSONObject jsonObj, HAPManualBrick brick, HAPServiceParseEntity parseService) {

		//attributes
		JSONObject attrsJsonObj = jsonObj.optJSONObject(HAPBrick.ATTRIBUTE);
		if(attrsJsonObj!=null) {
			for(Object key : attrsJsonObj.keySet()) {
				String attrName = (String) key;
				JSONObject attrJsonObj = attrsJsonObj.getJSONObject(attrName);
			
				//value in attribute
				HAPWrapperValue valueWrapperInAttr = this.parseValueWrapper(attrJsonObj.getJSONObject(HAPAttributeInBrick.VALUEWRAPPER), parseService); 
				HAPAttributeInBrick attribute = new HAPAttributeInBrick(attrName, valueWrapperInAttr);
				attribute.buildEntityInfoByJson(attrsJsonObj);
				
				//adapter
				JSONArray adaptersJsonArray = attrJsonObj.optJSONArray(HAPAttributeInBrick.ADAPTER);
				for(int i=0; i<adaptersJsonArray.length(); i++) {
					JSONObject adapterJsonObj = adaptersJsonArray.getJSONObject(i);

					HAPManualAdapter adapter = new HAPManualAdapter();
					adapter.buildEntityInfoByJson(adapterJsonObj);
					adapter.setValueWrapper(this.parseValueWrapper(adapterJsonObj.getJSONObject(HAPAdapter.VALUEWRAPPER), parseService));
					attribute.addAdapter(adapter);
				}
				
				brick.setAttribute(attribute);
			}
		}
		
		//value context
		HAPManualValueContext valueContext = new HAPManualValueContext();
		valueContext.buildObject(jsonObj.getJSONObject(HAPManualBrick.VALUECONTEXT), HAPSerializationFormat.JSON);
		brick.setManualValueContext(valueContext);
		
		//other valueport
		HAPContainerValuePorts otherInternalValuePortsContainer = new HAPContainerValuePorts();
		otherInternalValuePortsContainer.buildObject(jsonObj.getJSONObject(HAPManualBrick.OTHERINTERNALVALUEPORTSCONTAINER), HAPSerializationFormat.JSON);
		brick.setOtherInternalValuePortContainer(otherInternalValuePortsContainer);
		
		HAPContainerValuePorts otherExternalValuePortsContainer = new HAPContainerValuePorts();
		otherExternalValuePortsContainer.buildObject(jsonObj.getJSONObject(HAPManualBrick.OTHEREXTERNALVALUEPORTSCONTAINER), HAPSerializationFormat.JSON);
		brick.setOtherExternalValuePortContainer(otherExternalValuePortsContainer);
		
		
		//command expose
		JSONObject commandJsonObj = jsonObj.optJSONObject(HAPBrick.EXPORTCOMMAND);
		if(commandJsonObj!=null) {
			for(Object key : commandJsonObj.keySet()) {
				brick.addCommandExport(HAPCommandProcess.parseCommandProcess(commandJsonObj.getJSONObject((String)key), parseService));
			}
		}
		
		//event id
		JSONArray eventIds = jsonObj.optJSONArray(HAPBrick.EVENTID);
		if(eventIds!=null) {
			for(int i=0; i<eventIds.length(); i++) {
				brick.addEventId(eventIds.getString(i));
			}
		}

	}
	
	abstract protected Object parseValueInAttribute(Object obj, HAPServiceParseEntity parseService);

	protected HAPWrapperValue parseValueWrapper(JSONObject valueWrapperJsonObj, HAPServiceParseEntity parseService) {
		HAPWrapperValue valueWrapperInAttr = null;
		
		String valueType = valueWrapperJsonObj.getString(HAPWrapperValue.VALUETYPE);
		
		switch(valueType) {
		case HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_BRICK:
			HAPWrapperValueOfBrick brickWrapper = new HAPWrapperValueOfBrick();
			JSONObject brickJsonObj = valueWrapperJsonObj.optJSONObject(HAPWrapperValueOfBrick.BRICK);
			brickWrapper.setBrick(this.m_brickManager.deserializeBrick(brickJsonObj, HAPSerializationFormat.JSON, brickJsonObj.getString(HAPBrick.DIVISION)));
			valueWrapperInAttr = brickWrapper;
			break;
		case HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_VALUE:
			HAPWrapperValueOfValue valueWrapper = new HAPWrapperValueOfValue();
			valueWrapper.setValue(this.parseValueInAttribute(valueWrapperJsonObj.opt(HAPWrapperValueOfValue.VALUE), parseService));
			valueWrapperInAttr = valueWrapper;
			break;
		case HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_RESOURCEID:
			HAPWrapperValueOfReferenceResource resourceWrapper = new HAPWrapperValueOfReferenceResource();
			JSONObject resourceIdJsonObj = valueWrapperJsonObj.optJSONObject(HAPWrapperValueOfReferenceResource.RESOURCEID);
			resourceWrapper.setResourceId(HAPFactoryResourceId.newInstance(resourceIdJsonObj));
			
			HAPDynamicExecuteInputContainer dynamicInputContainer = new HAPDynamicExecuteInputContainer();
			dynamicInputContainer.buildObject(valueWrapperJsonObj.opt(HAPWrapperValueOfReferenceResource.DYNAMICINPUT), HAPSerializationFormat.JSON);
			resourceWrapper.setDynamicInput(dynamicInputContainer);
			
			valueWrapperInAttr = resourceWrapper;
			break;
		case HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_DYNAMIC:
			HAPWrapperValueOfDynamic dynamicWrapper = new HAPWrapperValueOfDynamic();
			HAPValueOfDynamic dynamicValue = new HAPValueOfDynamic();
			dynamicValue.buildObject(valueWrapperJsonObj.getJSONObject(HAPWrapperValueOfDynamic.DYNAMIC), HAPSerializationFormat.JSON);
			dynamicWrapper.setDynamicValue(dynamicValue);
			valueWrapperInAttr = dynamicWrapper;
			break;
		}
		return valueWrapperInAttr;
	}
	
}