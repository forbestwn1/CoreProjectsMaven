package com.nosliw.core.application.common.brick.serialize;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPWithDomain;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPAdapter;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.brick.HAPValueOfDynamic;
import com.nosliw.core.application.brick.HAPWrapperValue;
import com.nosliw.core.application.brick.HAPWrapperValueOfBrick;
import com.nosliw.core.application.brick.HAPWrapperValueOfDynamic;
import com.nosliw.core.application.brick.HAPWrapperValueOfReferenceResource;
import com.nosliw.core.application.brick.HAPWrapperValueOfValue;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.dataexpression.HAPContainerDataExpression;
import com.nosliw.core.application.dynamic.HAPDynamicExecuteInputContainer;
import com.nosliw.core.application.valueport.HAPContainerValuePorts;
import com.nosliw.core.application.valueport.HAPWithExternalValuePort;
import com.nosliw.core.application.valueport.HAPWithInternalValuePort;
import com.nosliw.core.resource.HAPFactoryResourceId;

abstract public class HAPParserBrick extends HAPParserEntityImpWithDomain{

	private String m_domainName;
	
	private Class<? extends HAPBrickImp> m_brickClass;
	
	private HAPIdBrickType m_brickTypeId;
	
	private List<HAParserPValueInAttribute> m_attrValueParsers;
	
	public HAPParserBrick(String domainName, Class<? extends HAPBrickImp> brickClass, HAPIdBrickType brickTypeId) {
		this.m_attrValueParsers = new ArrayList<HAParserPValueInAttribute>();
		this.m_domainName = domainName;
		this.m_brickClass = brickClass;
		this.m_brickTypeId = brickTypeId;
	}
	
	@Override
	public String getDomain() {     return this.m_domainName;    }

	@Override
	public String getSubName() {    return this.m_brickTypeId.getKey();    }

	public void addAttributeValueParser(HAParserPValueInAttribute parser) {   this.m_attrValueParsers.add(parser);       }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPBrickImp out = null;
		try {
			out = this.m_brickClass.newInstance();
			this.parseBrickJson((JSONObject)obj, out, parseService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	protected void parseBrickJson(JSONObject jsonObj, HAPBrickImp brick, HAPServiceParseEntity parseService) {

		//domain
		brick.setParseDomain(jsonObj.getString(HAPWithDomain.PARSEDOMAIN));
		
		//brick type
		HAPIdBrickType brickTypeId = new HAPIdBrickType();
		brickTypeId.buildObject(jsonObj.getJSONObject(HAPBrick.BRICKTYPE), HAPSerializationFormat.JSON);
		brick.setBrickType(brickTypeId);
		
		//attributes
		this.parseAttribute(jsonObj, brick, parseService);

		this.parseValuePort(jsonObj, brick, parseService);
		
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
		
		//data expression
		JSONObject dataExpressionsJsonObj = jsonObj.optJSONObject(HAPBrick.DATAEXPRESSIONS);
		if(dataExpressionsJsonObj!=null) {
			HAPContainerDataExpression dataExpressionContainer = (HAPContainerDataExpression)parseService.parseEntityJSONExplicit(dataExpressionsJsonObj, HAPContainerDataExpression.class.getName());
			brick.setDataExpressionsContainer(dataExpressionContainer);
		}

	}

	protected void parseValuePort(JSONObject jsonObj, HAPBrickImp brick, HAPServiceParseEntity parseService) {
		JSONObject internalValuePortJsonObj = jsonObj.optJSONObject(HAPWithInternalValuePort.INTERNALVALUEPORT);
		if(internalValuePortJsonObj!=null) {
			HAPContainerValuePorts internalValuePortsContainer = new HAPContainerValuePorts();
			internalValuePortsContainer.buildObject(internalValuePortJsonObj, HAPSerializationFormat.JSON);
			brick.setInternalValuePorts(internalValuePortsContainer);
		}
		
		JSONObject externalValuePortJsonObj = jsonObj.optJSONObject(HAPWithExternalValuePort.EXTERNALVALUEPORT);
		if(externalValuePortJsonObj!=null) {
			HAPContainerValuePorts externalValuePortsContainer = new HAPContainerValuePorts();
			externalValuePortsContainer.buildObject(externalValuePortJsonObj, HAPSerializationFormat.JSON);
			brick.setExternalValuePorts(externalValuePortsContainer);
		}
	}

	protected void parseAttribute(JSONObject jsonObj, HAPBrickImp brick, HAPServiceParseEntity parseService) {
		//attributes
		JSONArray attrsJsonArray = jsonObj.optJSONArray(HAPBrick.ATTRIBUTE);
		if(attrsJsonArray!=null) {
			for(int j=0; j<attrsJsonArray.length(); j++) {
				JSONObject attrJsonObj = attrsJsonArray.getJSONObject(j);

				//attribute
				HAPAttributeInBrick attribute = this.newAttributeObject();
				attribute.buildEntityInfoByJson(attrJsonObj);

				//value in attribute
				HAPWrapperValue valueWrapperInAttr = this.parseValueWrapper(attribute.getName(), attrJsonObj.getJSONObject(HAPAttributeInBrick.VALUEWRAPPER), parseService);
				attribute.setValueWrapper(valueWrapperInAttr);
				
				//adapter
				JSONArray adaptersJsonArray = attrJsonObj.optJSONArray(HAPAttributeInBrick.ADAPTER);
				for(int i=0; i<adaptersJsonArray.length(); i++) {
					JSONObject adapterJsonObj = adaptersJsonArray.getJSONObject(i);

					HAPAdapter adapter = this.newAdapterObject();
					adapter.buildEntityInfoByJson(adapterJsonObj);
					adapter.setValueWrapper(this.parseValueWrapper(null, adapterJsonObj.getJSONObject(HAPAdapter.VALUEWRAPPER), parseService));
					attribute.addAdapter(adapter);
				}
				
				brick.setAttribute(attribute);
			}
		}
	}
	
	abstract protected HAPAttributeInBrick newAttributeObject();

	abstract protected HAPAdapter newAdapterObject();

	protected HAPWrapperValue parseValueWrapper(String attrName, JSONObject valueWrapperJsonObj, HAPServiceParseEntity parseService) {
		HAPWrapperValue valueWrapperInAttr = null;
		
		String valueType = valueWrapperJsonObj.getString(HAPWrapperValue.VALUETYPE);
		
		switch(valueType) {
		case HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_BRICK:
			HAPWrapperValueOfBrick brickWrapper = new HAPWrapperValueOfBrick();
			JSONObject brickJsonObj = valueWrapperJsonObj.optJSONObject(HAPWrapperValueOfBrick.BRICK);
			brickWrapper.setBrick(HAPUtilityExport.parseBrickJson(brickJsonObj, parseService));
			valueWrapperInAttr = brickWrapper;
			break;
		case HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_VALUE:
			HAPWrapperValueOfValue valueWrapper = new HAPWrapperValueOfValue();
			Object valueObj = valueWrapperJsonObj.opt(HAPWrapperValueOfValue.VALUE);
			if(valueObj!=null) {
				valueWrapper.setValue(this.parseValueInBrickAttribute(attrName, valueObj, parseService));
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

	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {  return null;   }

	protected Object parseValueInBrickAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		Object v = this.parseValueInAttribute(attrName, obj, parseService);
		if(v!=null) {
			return v;
		}
		
		for(HAParserPValueInAttribute parser : this.m_attrValueParsers) {
			Object value = parser.parseValueInAttribute(attrName, obj, parseService);
			if(value!=null) {
				return value;
			}
		}
		
		return null;     
	}	
	
}
