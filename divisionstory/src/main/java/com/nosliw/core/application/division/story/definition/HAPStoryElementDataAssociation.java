package com.nosliw.core.application.division.story.definition;

import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;

//data association between entity
public class HAPStoryElementDataAssociation extends HAPStoryElementImpWithEntityInfo{

	//entity 1
	private HAPStoryReferenceElement m_entity1;
	private HAPPath m_path1;
	
	//entity 2
	private HAPStoryReferenceElement m_entity2;
	private HAPPath m_path2;
	
	//data direction (in, out, both)
	private String m_direction;
	
	//mutiple tunnel among data association
	private List<HAPStoryTunnel> m_tunnels;
	
	public HAPStoryElementDataAssociation(HAPStoryReferenceElement entity1, HAPStoryReferenceElement entity2, String direction) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_DATAASSOCIATION));
	}

	@Override
	public void addChild(HAPStoryElement ele, HAPPath path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HAPStoryReferenceElement getChild(HAPPath path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPStoryElementImp cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
