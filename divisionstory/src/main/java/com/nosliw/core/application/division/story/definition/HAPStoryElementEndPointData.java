package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;

//end point element ()
public class HAPStoryElementEndPointData extends HAPStoryElementImp{

	//data definition
	private HAPDataDefinition m_dataDefinition;

	public HAPStoryElementEndPointData(HAPStoryIdElementType elementType) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_ENDPOINT_DATA));
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
