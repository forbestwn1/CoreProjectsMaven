package com.nosliw.core.application.entity.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.displayresource.HAPDisplayResourceNode;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPWrapperValueOfReferenceResource;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.resource.HAPFactoryResourceId;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPIdResourceType;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPServiceProfile extends HAPEntityInfoImp{

	@HAPAttribute
	public static String INTERFACE = "taskInterface";

	@HAPAttribute
	public static String TAG = "tag";

	@HAPAttribute
	public static String DISPLAY = "display";

	
	private HAPInteractiveTask m_interface;
	
	//interface may defined as seperate resource
	private HAPResourceId m_interfaceResourceId;
	
	private List<String> m_tags;
	
	private HAPDisplayResourceNode m_displayResource;
	
	public HAPServiceProfile() {
		this.m_tags = new ArrayList<String>();
	}
	
	public void setInerfaceResourceId(HAPResourceId resourceId) {    this.m_interfaceResourceId = resourceId;	}
	public HAPResourceId getInterfaceResourceId() {     return this.m_interfaceResourceId;       }
	
	public HAPInteractiveTask getInterface() {    return this.m_interface;      }
	public void setInterface(HAPInteractiveTask taskInterface) {   this.m_interface = taskInterface;    }
	
	public List<String> getTags(){   return this.m_tags;  }
	public void setTags(List<String> tags) {
		if(tags!=null) {
			this.m_tags.addAll(tags);
		}      
	}

	public HAPDisplayResourceNode getDisplayResource() {    return this.m_displayResource;      }
	public void setDisplayResource(HAPDisplayResourceNode displayResource) {     this.m_displayResource = displayResource;      }
	
	public static HAPServiceProfile parse(JSONObject jsonObj, HAPServiceParseEntity entityParseService){
		HAPServiceProfile out = new HAPServiceProfile();
		out.buildEntityInfoByJson(jsonObj);

		Object displayObj = jsonObj.opt(DISPLAY);
		if(displayObj!=null) {
			HAPDisplayResourceNode displayResource = new HAPDisplayResourceNode();
			displayResource.buildObject(displayObj, HAPSerializationFormat.JSON);
			out.setDisplayResource(displayResource);
		}
		
		Object resourceObj = jsonObj.opt(HAPWrapperValueOfReferenceResource.RESOURCEID);
		if(resourceObj!=null) {
			HAPIdResourceType resourceTypeId = HAPFactoryResourceTypeId.newInstance(HAPConstantShared.RUNTIME_RESOURCE_TYPE_SERVICEINTERFACE);
			HAPResourceId resourceId = HAPFactoryResourceId.tryNewInstance(resourceTypeId.getResourceType(), resourceTypeId.getVersion(), resourceObj);
			out.setInerfaceResourceId(resourceId);
		}
		else {
			HAPInteractiveTask taskInterface = HAPInteractiveTask.parse(jsonObj.getJSONObject(HAPServiceProfile.INTERFACE), entityParseService);
			out.setInterface(taskInterface);
		}
		
		List<String> tags = new ArrayList<String>();
		JSONArray tagArray = jsonObj.optJSONArray(TAG);
		if(tagArray!=null) {
			for(int i=0; i<tagArray.length(); i++) {
				tags.add(tagArray.getString(i));
			}
		}
		out.setTags(tags);
		
		return out;  
	}


	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(INTERFACE, HAPManagerSerialize.getInstance().toStringValue(this.m_interface, HAPSerializationFormat.JSON));
		jsonMap.put(TAG, HAPManagerSerialize.getInstance().toStringValue(this.m_tags, HAPSerializationFormat.JSON));
		jsonMap.put(DISPLAY, HAPManagerSerialize.getInstance().toStringValue(this.m_displayResource, HAPSerializationFormat.JSON));
	}
	
}
