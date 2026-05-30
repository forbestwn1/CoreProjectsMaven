package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

//root class for all element
public abstract class HAPStoryElement extends HAPSerializableImp{

	public static final String SEG_ELEMENT = "__element__";
	
	@HAPAttribute
	public static final String ELEMENTID = "elementId";
	
	@HAPAttribute
	public static final String ELEMENTTYPE = "elementType";
	
	private HAPStoryIdElement m_id;
	
	private HAPStoryIdElementType m_elementType; 
	
	private HAPStoryContainerChildrenElementsMap m_children;
	
	public HAPStoryElement(HAPStoryIdElementType elementType) {
		this.m_elementType = elementType;
		this.m_children = new HAPStoryContainerChildrenElementsMap();
	}
	
	public HAPStoryContainerChildrenElements getChildren() {
		return this.m_children;
	}
	
	public HAPStoryIdElement getElementId() {	return this.m_id;	}
	public void setElementId(HAPStoryIdElement elementId) {    this.m_id = elementId;       }
	
	public HAPStoryIdElementType getElementType() {     return this.m_elementType;       }
	protected void setElementType(HAPStoryIdElementType elementType) {    this.m_elementType = elementType;     }
	
	public boolean addChild(HAPStoryChildElement child, String childPath) {
		HAPStoryContainerChildrenElementsSingle singleContainer = new HAPStoryContainerChildrenElementsSingle(child);
		HAPStoryContainerChildrenElements currentContainer = this.m_children;
		String[] segs = HAPUtilityNamingConversion.parsePaths(childPath);
		for(int i=0; i<segs.length; i++) {
            String seg = segs[i];
			String containerType = currentContainer.getContainerType();

			if(i==segs.length-1) {
				if(seg.equals(SEG_ELEMENT)) {
					if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST.equals(containerType)) {
						((HAPStoryContainerChildrenElementsList)currentContainer).newChildContainer(singleContainer);
						return true;
					}
				}
				else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_MAP.equals(containerType)) {
					((HAPStoryContainerChildrenElementsMap)currentContainer).newChildContainer(seg, singleContainer);
					return true;
				}
				throw new RuntimeException();
			}

			HAPStoryContainerChildrenElements childContainer = null;
			if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_MAP.equals(containerType)) {
				childContainer = ((HAPStoryContainerChildrenElementsMap)currentContainer).getChildContainer(seg);
				if(childContainer==null) {
					childContainer = ((HAPStoryContainerChildrenElementsMap)currentContainer).newChildContainer(seg, newElementsChildrenContainerAccordingToSeg(segs[i+1]));
				}
			}
			else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST.equals(containerType)) {
				childContainer = ((HAPStoryContainerChildrenElementsList)currentContainer).getChildContainer(Integer.valueOf(seg));
				if(childContainer==null) {
					throw new RuntimeException();
				}
			}
			currentContainer = childContainer;
		}
		return true;
	}
	
	private HAPStoryContainerChildrenElements newElementsChildrenContainerAccordingToSeg(String seg) {
		HAPStoryContainerChildrenElements out = null;
		
		if(HAPUtilityBasic.isStringEmpty(seg)) {
		}
		else if(seg.equals(SEG_ELEMENT) || HAPUtilityBasic.isNumber(seg)) {
			out = new HAPStoryContainerChildrenElementsList();
		}
		else {
			out = new HAPStoryContainerChildrenElementsMap();
		}
		return out;
	}
	
	public HAPStoryResultElementChild tryGetChild(String childPath) {
		HAPPath remainingPath = new HAPPath();
		HAPStoryChildElement childElement = null;
		
		HAPStoryContainerChildrenElements currentContainer = this.m_children;
		String[] segs = HAPUtilityNamingConversion.parsePaths(childPath);
		boolean searching = true;
		for(String seg : segs) {
			if(searching==true) {
				String containerType = currentContainer.getContainerType();
				if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_MAP.equals(containerType)) {
					currentContainer = ((HAPStoryContainerChildrenElementsMap)currentContainer).getChildContainer(seg);
				}
				else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST.equals(containerType)) {
					if(HAPUtilityBasic.isNumber(seg)){
						currentContainer = ((HAPStoryContainerChildrenElementsList)currentContainer).getChildContainer(Integer.valueOf(seg));
					}
					else {
						currentContainer = ((HAPStoryContainerChildrenElementsList)currentContainer).getChildContainer(seg);
					}
				}
				
				if(currentContainer==null) {
					throw new RuntimeException();
				}
				
				containerType = currentContainer.getContainerType();
				if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_SINGLE.equals(containerType)) {
					childElement = ((HAPStoryContainerChildrenElementsSingle)currentContainer).getChildElement();
					searching = false;
				}
			}
			else {
				remainingPath.appendSegment(seg);
			}
		}
		return new HAPStoryResultElementChild(childElement, remainingPath);
	}
	
	public HAPStoryChildElement getChild(String childName) {
		HAPStoryResultElementChild result = tryGetChild(childName);
		if(!result.getRemainingPath().isEmpty()) {
			throw new RuntimeException();
		} else {
			return result.getChildElement();
		}
	}
	
	protected void cloneToStoryElement(HAPStoryElement storyEle) {
		if(this.m_id!=null) {
			storyEle.setElementId((HAPStoryIdElement)m_id.cloneElementReference());
		}
		storyEle.setElementType(this.m_elementType);
	}
	
	abstract public HAPStoryElement cloneStoryElement();
	
	protected HAPStoryContainerChildrenElements addChildContainer(String childName, HAPStoryContainerChildrenElements childrenContainer) {
		this.m_children.newChildContainer(childName, childrenContainer);
		return childrenContainer;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENTTYPE, this.m_elementType.getKey());
		if(this.m_id!=null) {
			jsonMap.put(ELEMENTID, this.m_id.getKey());
		}
	}
	
}
