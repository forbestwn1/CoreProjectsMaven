package com.nosliw.core.application.spec.brick.data;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.data.HAPData;

//contains all information related with service definition
@HAPEntityWithAttribute
public interface HAPBlockData extends HAPBrick{

	@HAPAttribute
	public static final String DATA = "data";
	
	HAPData getData();

}
