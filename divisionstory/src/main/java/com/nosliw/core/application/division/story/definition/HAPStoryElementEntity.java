package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

//entity element (module, page, datasource, ....)
public abstract class HAPStoryElementEntity extends HAPStoryElementImp{

	public static HAPPath CHILD_PATH_VARIABLE = new HAPPath("variable");
	
	public HAPStoryElementEntity(HAPStoryIdElementType elementType) {
		super(elementType);
	}

}
