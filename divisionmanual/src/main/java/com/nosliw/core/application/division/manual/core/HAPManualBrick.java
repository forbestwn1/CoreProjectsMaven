package com.nosliw.core.application.division.manual.core;

import java.util.HashSet;
import java.util.Set;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.HAPWrapperValue;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.withvariable.HAPContainerVariableInfo;
import com.nosliw.core.application.division.manual.common.valuecontext.HAPManualValueContext;
import com.nosliw.core.application.division.manual.core.process.HAPManualInfoBrickType;
import com.nosliw.core.application.valueport.HAPContainerValuePorts;
import com.nosliw.core.application.valueport.HAPGroupValuePorts;
import com.nosliw.core.application.valueport.HAPValuePort;

public abstract class HAPManualBrick extends HAPBrickImp{

	public final static String ISCOMPLEX = "isComplex"; 

	private HAPManualValueContext m_valueContext;
	
	private HAPInfoTreeNode m_tempTreeNodeInfo;

	private HAPManualInfoBrickType m_brickTypeInfo;
	
	private HAPContainerVariableInfo m_varInfoContainer;
	
	private HAPContainerValuePorts m_otherInternalValuePortsContainer;
	
	private HAPContainerValuePorts m_otherExternalValuePortsContainer;
	
	private HAPManualManagerBrick m_manualBrickMan;

	private HAPBundleForBrick m_bundle; 

	public HAPManualBrick() {
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

}
