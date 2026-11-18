package com.nosliw.core.application.spec.imp.basic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.displayresource.HAPDisplayResourceNode;
import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.HAPBrickImpWithEntityInfo;
import com.nosliw.core.application.common.brick.HAPWrapperValueOfReferenceResource;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;
import com.nosliw.core.application.common.valueport.HAPContainerValuePorts;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;
import com.nosliw.core.application.spec.brick.service.profile.HAPBlockServiceProfile;
import com.nosliw.core.resource.HAPFactoryResourceId;
import com.nosliw.core.resource.HAPResourceId;

//contains all information related with service definition
@HAPEntityWithAttribute
public class HAPBasicBlockServiceProfile extends HAPBrickImpWithEntityInfo implements HAPBlockServiceProfile{

	public HAPBasicBlockServiceProfile(String division){
		super(HAPEnumBrickType.SERVICEPROFILE_100, division);
	} 
	
	@Override
	public void setTaskInterface(HAPEntityOrReference brickOrRef) {		this.setAttributeValueWithBrick(HAPWithBlockInteractiveTask.TASKINTERFACE, brickOrRef);	}
	@Override
	public HAPEntityOrReference getTaskInterface() {   return this.getAttributeValueOfBrickLocal(HAPWithBlockInteractiveTask.TASKINTERFACE);   }
	
	@Override
	public List<String> getTags(){   return (List<String>)this.getAttributeValueOfValue(TAG);    }
	public void setTags(List<String> tags) {    this.setAttributeValueWithValue(TAG, tags);       }
	
	@Override
	public HAPDisplayResourceNode getDisplayResource() {    return (HAPDisplayResourceNode)this.getAttributeValueOfValue(DISPLAY);  }
	public void setDisplayResource(HAPDisplayResourceNode displayResource) {    this.setAttributeValueWithValue(DISPLAY, displayResource);      }
 
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		this.buildEntityInfoByJson(jsonObj);

		Object displayObj = jsonObj.opt(DISPLAY);
		if(displayObj!=null) {
			HAPDisplayResourceNode displayResource = new HAPDisplayResourceNode();
			displayResource.buildObject(displayObj, HAPSerializationFormat.JSON);
			this.setDisplayResource(displayResource);
		}
		
		Object resourceObj = jsonObj.opt(HAPWrapperValueOfReferenceResource.RESOURCEID);
		if(resourceObj!=null) {
			HAPResourceId resourceId = HAPFactoryResourceId.tryNewInstance(HAPEnumBrickType.INTERACTIVETASKINTERFACE_100.getBrickType(), HAPEnumBrickType.INTERACTIVETASKINTERFACE_100.getVersion(), resourceObj);
			this.setTaskInterface(resourceId);
		}
		else {
			this.setTaskInterface(parseTaskInterfaceInterfaceBlock(jsonObj, null));
		}
		
		List<String> tags = new ArrayList<String>();
		JSONArray tagArray = jsonObj.optJSONArray(TAG);
		if(tagArray!=null) {
			for(int i=0; i<tagArray.length(); i++) {
				tags.add(tagArray.getString(i));
			}
		}
		this.setTags(tags);
		
		return true;  
	}

	@Override
	public HAPContainerValuePorts getInternalValuePorts() {  return null;   }

	@Override
	public HAPContainerValuePorts getExternalValuePorts() {   return null;   }

	public static HAPBlockInteractiveInterfaceTask parseTaskInterfaceInterfaceBlock(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		JSONObject serviceInterfaceJsonObj = jsonObj.optJSONObject(HAPWithBlockInteractiveTask.TASKINTERFACE);
		if(serviceInterfaceJsonObj==null) {
			serviceInterfaceJsonObj = jsonObj;
		}
		HAPBasicBlockInteractiveInterfaceTask interfaceBlock = new HAPBasicBlockInteractiveInterfaceTask();
		HAPInteractiveTask taskInterface = HAPInteractiveTask.parse(serviceInterfaceJsonObj, entityParseService); 
		interfaceBlock.setValue(taskInterface);
		return interfaceBlock;
	}

}

@Component
class HAPBlockServiceProfileImp_parser extends HAPBasicBrick_parser{

	public HAPBlockServiceProfileImp_parser() {
		super(HAPBasicBlockServiceProfile.class, HAPEnumBrickType.SERVICEPROFILE_100);
	}
	
}

