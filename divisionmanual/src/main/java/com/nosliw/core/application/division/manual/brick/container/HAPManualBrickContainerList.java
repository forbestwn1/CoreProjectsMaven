package com.nosliw.core.application.division.manual.brick.container;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainer;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainerList;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualBrickContainerList extends HAPManualBrickContainer implements HAPBrickContainerList{

	public HAPManualBrickContainerList() {
		super(HAPEnumBrickType.CONTAINER_100);
	}
	
	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(HAPBrickContainerList.ATTRSORT, new ArrayList<String>());
	}
	
	public List<String> getSort(){   return (List<String>)this.getAttributeValueOfValue(HAPBrickContainerList.ATTRSORT);     }
	
	@Override
	public List<HAPAttributeInBrick> getElements(){
		List<HAPAttributeInBrick> out = new ArrayList<HAPAttributeInBrick>();
		List<String> sorted = this.getSort();
		for(String attr : sorted) {
			out.add(this.getAttribute(attr));
		}
		return out;
	}
	 
}

@Component
class HAPManualBrickContainerList_parser extends HAPManualBrick_parser{

	public HAPManualBrickContainerList_parser() {
		super(HAPManualBrickContainerList.class, HAPEnumBrickType.CONTAINERLIST_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBrickContainerList out = new HAPManualBrickContainerList();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}

	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		if(attrName.equals(HAPBrickContainer.ATTRINDEX)) {
			return obj;
		}
		else if(attrName.equals(HAPBrickContainerList.ATTRSORT)) {
			List<String> out = new ArrayList<String>();
			JSONArray sorts = (JSONArray)obj;
			for(int i=0; i<sorts.length(); i++) {
				out.add(sorts.getString(i));
			}
			return out;
		}
		
		return null;     
	}

}
