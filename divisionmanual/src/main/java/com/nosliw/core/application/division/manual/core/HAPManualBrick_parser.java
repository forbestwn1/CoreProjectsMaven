package com.nosliw.core.application.division.manual.core;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPAdapter;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPIdBrickType;
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

public abstract class HAPManualBrick_parser extends HAPParserEntityImpWithDomain{

	private HAPManagerApplicationBrick m_brickManager;
	
	private Class<? extends HAPManualBrick> m_manualBrickClass;
	
	private HAPIdBrickType m_brickTypeId;
	
	public HAPManualBrick_parser(HAPManagerApplicationBrick brickManager, Class<? extends HAPManualBrick> manualBrickClass, HAPIdBrickType brickTypeId) {
		this.m_brickManager = brickManager;
		this.m_manualBrickClass = manualBrickClass;
		this.m_brickTypeId = brickTypeId;
	}
	
	@Override
	public String getDomain() {     return HAPManualBrick.PARSE_DOMAIN;    }

	@Override
	public String getSubName() {    return this.m_brickTypeId.getKey();    }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBrick out = null;
		try {
			out = this.m_manualBrickClass.newInstance();
			this.parseBrickJson((JSONObject)obj, out, parseService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	protected void parseBrickJson(JSONObject jsonObj, HAPManualBrick brick, HAPServiceParseEntity parseService) {

		//division
		brick.setDivision(jsonObj.getString(HAPBrick.DIVISION));
		
		//brick type
		HAPIdBrickType brickTypeId = new HAPIdBrickType();
		brickTypeId.buildObject(jsonObj.getJSONObject(HAPBrick.BRICKTYPE), HAPSerializationFormat.JSON);
		brick.setBrickType(brickTypeId);
		
		//attributes
		JSONArray attrsJsonArray = jsonObj.optJSONArray(HAPBrick.ATTRIBUTE);
		if(attrsJsonArray!=null) {
			for(int j=0; j<attrsJsonArray.length(); j++) {
				JSONObject attrJsonObj = attrsJsonArray.getJSONObject(j);

				//attribute
				HAPManualAttributeInBrick attribute = new HAPManualAttributeInBrick();
				attribute.buildEntityInfoByJson(attrJsonObj);

				//value in attribute
				HAPWrapperValue valueWrapperInAttr = this.parseValueWrapper(attribute.getName(), attrJsonObj.getJSONObject(HAPAttributeInBrick.VALUEWRAPPER), parseService);
				attribute.setValueWrapper(valueWrapperInAttr);
				
				//adapter
				JSONArray adaptersJsonArray = attrJsonObj.optJSONArray(HAPAttributeInBrick.ADAPTER);
				for(int i=0; i<adaptersJsonArray.length(); i++) {
					JSONObject adapterJsonObj = adaptersJsonArray.getJSONObject(i);

					HAPManualAdapter adapter = new HAPManualAdapter();
					adapter.buildEntityInfoByJson(adapterJsonObj);
					adapter.setValueWrapper(this.parseValueWrapper(null, adapterJsonObj.getJSONObject(HAPAdapter.VALUEWRAPPER), parseService));
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
	
	protected HAPWrapperValue parseValueWrapper(String attrName, JSONObject valueWrapperJsonObj, HAPServiceParseEntity parseService) {
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
			Object valueObj = valueWrapperJsonObj.opt(HAPWrapperValueOfValue.VALUE);
			if(valueObj!=null) {
				valueWrapper.setValue(this.parseValueInAttribute(attrName, valueObj, parseService));
			}
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
	
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {     return null;     }

}