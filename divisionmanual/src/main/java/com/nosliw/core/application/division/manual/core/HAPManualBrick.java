package com.nosliw.core.application.division.manual.core;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPAdapter;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.HAPValueOfDynamic;
import com.nosliw.core.application.HAPWrapperValue;
import com.nosliw.core.application.HAPWrapperValueOfBrick;
import com.nosliw.core.application.HAPWrapperValueOfDynamic;
import com.nosliw.core.application.HAPWrapperValueOfReferenceResource;
import com.nosliw.core.application.HAPWrapperValueOfValue;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.withvariable.HAPContainerVariableInfo;
import com.nosliw.core.application.division.manual.common.valuecontext.HAPManualValueContext;
import com.nosliw.core.application.division.manual.core.process.HAPManualInfoBrickType;
import com.nosliw.core.application.dynamic.HAPDynamicExecuteInputContainer;
import com.nosliw.core.application.valueport.HAPContainerValuePorts;
import com.nosliw.core.application.valueport.HAPGroupValuePorts;
import com.nosliw.core.application.valueport.HAPValuePort;
import com.nosliw.core.resource.HAPFactoryResourceId;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPManualBrick extends HAPBrickImp{

	public final static String PARSE_DOMAIN = "brick.division.manual";
	
	public final static String ISCOMPLEX = "isComplex"; 

	@HAPAttribute
	public final static String OTHERINTERNALVALUEPORTSCONTAINER = "otherInternalValuePortsContainer"; 

	@HAPAttribute
	public final static String OTHEREXTERNALVALUEPORTSCONTAINER = "otherExternalValuePortsContainer"; 

	@HAPAttribute
	public final static String VALUECONTEXT = "valueContext"; 

	
	private HAPManualValueContext m_valueContext;
	
	private HAPInfoTreeNode m_tempTreeNodeInfo;

	private HAPManualInfoBrickType m_brickTypeInfo;
	
	private HAPContainerVariableInfo m_varInfoContainer;
	
	private HAPContainerValuePorts m_otherInternalValuePortsContainer;
	
	private HAPContainerValuePorts m_otherExternalValuePortsContainer;
	
	private HAPManualManagerBrick m_manualBrickMan;

	private HAPBundleForBrick m_bundle; 

	public HAPManualBrick() {
		super(HAPConstantShared.BRICK_DIVISION_MANUAL);
		this.m_valueContext = new HAPManualValueContext(); 
		this.m_otherInternalValuePortsContainer = new HAPContainerValuePorts();
		this.m_otherExternalValuePortsContainer = new HAPContainerValuePorts();
	}
	
	public void init() {
		this.m_varInfoContainer = new HAPContainerVariableInfo(this, this.m_bundle.getValueStructureDomain()); 
	}

	public void setBundle(HAPBundleForBrick bundle) {    this.m_bundle = bundle;      }
	
	public HAPManualValueContext getManualValueContext() {    return this.m_valueContext;    }
	
	public HAPContainerVariableInfo getVariableInfoContainer() {    return this.m_varInfoContainer;      }
	public void setVariableInfoContainer(HAPContainerVariableInfo varInfoContainer) {     this.m_varInfoContainer = varInfoContainer;      }
	
	protected HAPManualManagerBrick getManualBrickManager() {    return this.m_manualBrickMan;      }
	public void setManualBrickManager(HAPManualManagerBrick manualBrickMan) {    this.m_manualBrickMan = manualBrickMan;       }
	
	public HAPInfoTreeNode getTreeNodeInfo() {  return this.m_tempTreeNodeInfo;  }
	public void setTreeNodeInfo(HAPInfoTreeNode treeNodeInfo) {   this.m_tempTreeNodeInfo = treeNodeInfo;     }

	public HAPManualInfoBrickType getBrickTypeInfo() {    return this.m_brickTypeInfo;     }
	public void setBrickTypeInfo(HAPManualInfoBrickType brickTypeInfo) {    this.m_brickTypeInfo = brickTypeInfo;     }
	
	@Override
	public void setAttribute(HAPAttributeInBrick attribute) {
		super.setAttribute(attribute);
		
		HAPManualAttributeInBrick manualAttr = (HAPManualAttributeInBrick)attribute; 
		
		HAPInfoTreeNode treeNodeInfo = new HAPInfoTreeNode(new HAPPath(this.getTreeNodeInfo().getPathFromRoot()).appendSegment(manualAttr.getName()), this);
		manualAttr.setTreeNodeInfo(treeNodeInfo);
	}
	
	public void setAttributeValueWithBrickNew(String attributeName, HAPIdBrickType brickTypeId) {
		this.setAttributeValueWithBrick(attributeName, this.getManualBrickManager().newBrick(brickTypeId));
	}
	
	@Override
	protected HAPAttributeInBrick newAttribute(String attrName, HAPWrapperValue valueWrapper) {
		return new HAPManualAttributeInBrick(attrName, valueWrapper);
	}
	
//	public List<HAPManualPartInValueContext> getValueContextInhertanceDownstream(){
//		List<HAPManualPartInValueContext> out = new ArrayList<HAPManualPartInValueContext>();
//		for(HAPManualPartInValueContext part : this.getManualValueContext().getParts()) {
//			out.add(HAPManualUtilityValueContextProcessor1.inheritFromParent(part, HAPManualUtilityValueContext.getInheritableCategaries()));
//		}
//		return out;
//	}

	public HAPContainerValuePorts getOtherInternalValuePortContainer() {   return this.m_otherInternalValuePortsContainer;    }
	public HAPContainerValuePorts getOtherExternalValuePortContainer() {   return this.m_otherExternalValuePortsContainer;    }
	
	public void setOtherInternalValuePortContainer(HAPContainerValuePorts vpContainer) {   this.m_otherInternalValuePortsContainer = vpContainer;    }
	public void setOtherExternalValuePortContainer(HAPContainerValuePorts vpContainer) {   this.m_otherExternalValuePortsContainer = vpContainer;    }
	
	@Override
	public HAPContainerValuePorts getInternalValuePorts(){
		HAPContainerValuePorts out = new HAPContainerValuePorts();
		
		HAPGroupValuePorts valueContextValuePortGroup = this.getValueContextValuePortGroup(null);
		if(valueContextValuePortGroup!=null) {
			out.addValuePortGroup(valueContextValuePortGroup);
		}
		
		for(HAPGroupValuePorts group : this.getOtherInternalValuePortContainer().getValuePortGroups()) {
			out.addValuePortGroup(group);
		}
		return out;
	}

	@Override
	public HAPContainerValuePorts getExternalValuePorts(){
		HAPContainerValuePorts out = new HAPContainerValuePorts();
		
		Set<String> scopes = new HashSet<>();
		scopes.add(HAPConstantShared.UIRESOURCE_CONTEXTTYPE_PUBLIC);
		
		HAPGroupValuePorts valueContextValuePortGroup = this.getValueContextValuePortGroup(scopes);
		if(valueContextValuePortGroup!=null) {
			out.addValuePortGroup(valueContextValuePortGroup);
		}
		
		for(HAPGroupValuePorts group : this.getOtherExternalValuePortContainer().getValuePortGroups()) {
			out.addValuePortGroup(group);
		}
		return out;
	}
	
	private HAPGroupValuePorts getValueContextValuePortGroup(Set<String> scopes) {
		HAPGroupValuePorts out = null; 
		if(!this.getManualValueContext().isEmpty(this.m_bundle.getValueStructureDomain())) {
			out = new HAPGroupValuePorts(HAPConstantShared.VALUEPORT_TYPE_VALUECONTEXT);
			out.setName(HAPConstantShared.VALUEPORT_TYPE_VALUECONTEXT);

			HAPValuePort valuePort = new HAPValuePort(HAPConstantShared.VALUEPORT_TYPE_VALUECONTEXT, HAPConstantShared.IO_DIRECTION_BOTH);
			valuePort.setValueStructuredSorted(this.getManualValueContext().getValueStructuresSorted(scopes));
			out.addValuePort(valuePort);
		}
		return out;
	}
	
	abstract public boolean buildBrick(Object value, HAPSerializationFormat format, HAPManagerApplicationBrick brickMan);

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(OTHERINTERNALVALUEPORTSCONTAINER, this.m_otherInternalValuePortsContainer.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(OTHEREXTERNALVALUEPORTSCONTAINER, this.m_otherExternalValuePortsContainer.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(VALUECONTEXT, this.m_valueContext.toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(OTHERINTERNALVALUEPORTSCONTAINER, this.m_otherInternalValuePortsContainer.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(OTHEREXTERNALVALUEPORTSCONTAINER, this.m_otherExternalValuePortsContainer.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(VALUECONTEXT, this.m_valueContext.toStringValue(HAPSerializationFormat.JSON));
	}
}

abstract class HAPManualBrick_parser extends HAPParserEntityImpWithDomain{

	private HAPManagerApplicationBrick m_brickManager;
	
	
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
