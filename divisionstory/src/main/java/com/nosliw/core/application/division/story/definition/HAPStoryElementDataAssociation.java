package com.nosliw.core.application.division.story.definition;

import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;

//data association between entity
public class HAPStoryElementDataAssociation extends HAPStoryElementImp{

	public static final String CHILD_ENTITY1 = "entity1";
	public static final String CHILD_ENTITY2 = "entity2";
	
	//entity 1
	private HAPStoryIdElement m_entity1;
	private HAPPath m_path1;
	
	//entity 2
	private HAPStoryIdElement m_entity2;
	private HAPPath m_path2;
	
	//data direction (in, out, both)
	private String m_direction;
	
	//mutiple tunnel among data association
	private List<HAPStoryTunnel> m_tunnels;
	
	public HAPStoryElementDataAssociation() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_DATAASSOCIATION));
	}
	
	public HAPStoryElementDataAssociation(HAPStoryIdElement entity1, HAPPath path1, HAPStoryIdElement entity2, HAPPath path2, String direction) {
		this();
		this.m_entity1 = entity1;
		this.m_entity2 = entity2;
		this.m_path1 = path1;
		this.m_path2 = path2;
		this.m_direction = direction;
	}

	public void addTunnel(HAPStoryTunnel tunnel) {      this.m_tunnels.add(tunnel);      }
	
	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		if(CHILD_ENTITY1.equals(childName)) {
			this.m_entity1 = ele.getElementId();
			return true;
		}
		if(CHILD_ENTITY2.equals(childName)) {
			this.m_entity2 = ele.getElementId();
			return true;
		}
        return false;		
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		if(CHILD_ENTITY1.equals(childName)) {
			return this.m_entity1;
		}
		if(CHILD_ENTITY2.equals(childName)) {
			return this.m_entity2;
		}
		return null;
	}

	protected void cloneToStoryElement(HAPStoryElementDataAssociation storyEle) {
		
	}
	
	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementDataAssociation out = new HAPStoryElementDataAssociation();
		this.cloneToStoryElement(out);
		return out;
	}

}
