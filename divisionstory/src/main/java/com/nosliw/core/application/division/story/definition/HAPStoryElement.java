package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.service.entityparse.HAPEntityParsable;

//root class for all element
public abstract class HAPStoryElement extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.item.definition";
	
	public static final String SEG_ELEMENT = "__element__";
	
	@HAPAttribute
	public static final String ELEMENTID = "elementId";
	
	@HAPAttribute
	public static final String ELEMENTTYPE = "elementType";

	public static final String CHILDREN = "children";
	
	private HAPStoryIdElement m_id;
	
	private HAPStoryIdElementType m_elementType; 
	
	private HAPStoryContainerChildrenElementsAttributes m_children;
	
	public HAPStoryElement(HAPStoryIdElementType elementType) {
		this.m_children = new HAPStoryContainerChildrenElementsAttributes();
		this.m_elementType = elementType;
	}
	
	public HAPStoryContainerChildrenElements getChildren() {		return this.m_children;	   }
	public void setChildren(HAPStoryContainerChildrenElementsAttributes children) {     this.m_children = children;      }
	
	public HAPStoryIdElement getElementId() {	return this.m_id;	}
	public void setElementId(HAPStoryIdElement elementId) {    this.m_id = elementId;       }
	
	public HAPStoryIdElementType getElementType() {     return this.m_elementType;       }
	protected void setElementType(HAPStoryIdElementType elementType) {    this.m_elementType = elementType;     }
	
	public HAPPath addChild(HAPStoryChildElement child, String childPath) {
		return this.addChild(child, new HAPPath(childPath));
	}

	public HAPStoryContainerChildrenElementsWrapper removeChild(HAPPath childPath) {
		HAPStoryContainerChildrenElements currentContainer = this.m_children;
		String[] segs = childPath.getPathSegments();
		for(int i=0; i<segs.length; i++) {
            String seg = segs[i];
            if(i==segs.length-1) {
            	return ((HAPStoryContainerChildrenElementsMultiple)currentContainer).removeChild(seg);
            }
            else {
            	currentContainer = this.getChildContainer(currentContainer, seg);
            }
		}
		return null;
	}
	
	public HAPPath addChild(HAPStoryChildElement child, HAPPath childPath) {
		HAPPath out = new HAPPath();
		HAPStoryContainerChildrenElementsWrapper singleContainer = new HAPStoryContainerChildrenElementsWrapper(child);
		HAPStoryContainerChildrenElements currentContainer = this.m_children;
		String[] segs = childPath.getPathSegments();
		for(int i=0; i<segs.length; i++) {
            String seg = segs[i];
			String containerType = currentContainer.getContainerType();

			if(i==segs.length-1) {
				if(seg.startsWith(SEG_ELEMENT)) {
					if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION.equals(containerType)) {
						String childAlias = null;
						if(!seg.equals(SEG_ELEMENT)) {
							childAlias = seg.substring(SEG_ELEMENT.length()+HAPConstantShared.SEPERATOR_DETAIL.length());
						}
						out.appendSegment(((HAPStoryContainerChildrenElementsCollection)currentContainer).newChildContainer(child, childAlias).getId());
					}
				}
				else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_ATTRIBUTES.equals(containerType)) {
					((HAPStoryContainerChildrenElementsAttributes)currentContainer).newChildContainer(seg, singleContainer);
					out.appendSegment(seg);
				}
				else {
					throw new RuntimeException();
				}
			}
			else {
				HAPStoryContainerChildrenElements childContainer = null;
				if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_ATTRIBUTES.equals(containerType)) {
					HAPStoryContainerChildrenElementsAttributes mapContainer = (HAPStoryContainerChildrenElementsAttributes)currentContainer;
					childContainer = mapContainer.getChildContainer(seg);
					if(childContainer==null) {
						childContainer = mapContainer.newChildContainer(seg, newElementsChildrenContainerAccordingToSeg(segs[i+1]));
					}
					out.appendSegment(seg);
				}
				else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION.equals(containerType)) {
					HAPStoryContainerChildrenElementsCollection listContainer = (HAPStoryContainerChildrenElementsCollection)currentContainer;
					if(HAPUtilityBasic.isNumber(seg)){
						childContainer = listContainer.getChildContainer(Integer.valueOf(seg));
						out.appendSegment(((HAPStoryContainerChildrenElementsWrapper)childContainer).getId());
					}
					else {
						childContainer = listContainer.getChildContainer(seg);
						out.appendSegment(((HAPStoryContainerChildrenElementsWrapper)childContainer).getId());
					}
					
					if(childContainer==null) {
						throw new RuntimeException();
					}
				}
				currentContainer = childContainer;
			}

		}
		return out;
	}
	
	private HAPStoryContainerChildrenElements newElementsChildrenContainerAccordingToSeg(String seg) {
		HAPStoryContainerChildrenElements out = null;
		
		if(HAPUtilityBasic.isStringEmpty(seg)) {
		}
		else if(seg.startsWith(SEG_ELEMENT) || HAPUtilityBasic.isNumber(seg)) {
			out = new HAPStoryContainerChildrenElementsCollection();
		}
		else {
			out = new HAPStoryContainerChildrenElementsAttributes();
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
				currentContainer = this.getChildContainer(currentContainer, seg);
				
				if(currentContainer==null) {
					throw new RuntimeException();
				}
				
				if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER.equals(currentContainer.getContainerType())) {
					childElement = ((HAPStoryContainerChildrenElementsWrapper)currentContainer).getChildElement();
					searching = false;
				}
			}
			else {
				remainingPath.appendSegment(seg);
			}
		}
		return new HAPStoryResultElementChild(childElement, remainingPath);
	}
	
	private HAPStoryContainerChildrenElements getChildContainer(HAPStoryContainerChildrenElements currentContainer, String seg) {
		HAPStoryContainerChildrenElements out = null;
		String containerType = currentContainer.getContainerType();
		if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_ATTRIBUTES.equals(containerType)) {
			out = ((HAPStoryContainerChildrenElementsAttributes)currentContainer).getChildContainer(seg);
		}
		else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION.equals(containerType)) {
			if(HAPUtilityBasic.isNumber(seg)){
				out = ((HAPStoryContainerChildrenElementsCollection)currentContainer).getChildContainer(Integer.valueOf(seg));
			}
			else {
				out = ((HAPStoryContainerChildrenElementsCollection)currentContainer).getChildContainer(seg);
			}
		}
		return out;
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
		jsonMap.put(CHILDREN, this.m_children.toStringValue(HAPSerializationFormat.JSON));
	}
	
}
