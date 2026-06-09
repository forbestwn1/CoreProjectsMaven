package com.nosliw.core.application.division.story.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.service.entityparse.HAPEntityParsable;

//root class for all element
public abstract class HAPStoryElement extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.definition.element";
	
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
	
	public HAPStoryIdElement getElementId() {	return this.m_id;	}
	public void setElementId(HAPStoryIdElement elementId) {    this.m_id = elementId;       }
	
	public HAPStoryIdElementType getElementType() {     return this.m_elementType;       }
	protected void setElementType(HAPStoryIdElementType elementType) {    this.m_elementType = elementType;     }
	
	public HAPStoryContainerChildrenElementsAttributes getChildren() {		return this.m_children;	   }
	public void setChildren(HAPStoryContainerChildrenElementsAttributes children) {     this.m_children = children;      }
	
	public HAPPath addChild(HAPStoryChildElement child, String childPath) {
		return this.addChild(child, new HAPPath(childPath));
	}

	public HAPStoryContainerChildrenElementsWrapper removeChild(HAPPath childPath) {
		HAPStoryContainerChildrenElements currentContainer = this.m_children;
		return HAPStoryUtilityContainer.removeChild(currentContainer, childPath);		
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
						childContainer = mapContainer.newChildContainer(seg, HAPStoryUtilityContainer.newElementsChildrenContainerAccordingToSeg(segs[i+1]));
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
	
	public HAPStoryChildElement getChildElement(String childPath) {
		HAPStoryResultContainerChild result = HAPStoryUtilityContainer.tryGetChildElement(this.m_children, childPath);
		if(!result.getRemainingPath().isEmpty()) {
			throw new RuntimeException();
		} else {
			return result.getChildContainer()==null?null:((HAPStoryContainerChildrenElementsWrapper)result.getChildContainer()).getChildElement();
		}
	}

	public List<HAPStoryContainerChildrenElementsWrapper> getChildCollection(String childPath) {
		HAPStoryResultContainerChild result = HAPStoryUtilityContainer.tryGetChildCollection(this.m_children, childPath);
		if(!result.getRemainingPath().isEmpty()) {
			throw new RuntimeException();
		} else {
			return result.getChildContainer()==null? new ArrayList<HAPStoryContainerChildrenElementsWrapper>() : ((HAPStoryContainerChildrenElementsCollection)result.getChildContainer()).getChildren();
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
