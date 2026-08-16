package com.nosliw.application.datasource.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.common.datasource.HAPAIPRequestExectueDataSource;
import com.nosliw.core.application.common.datasource.HAPQueryServiceDefinition;
import com.nosliw.core.application.entity.datasource.HAPManagerService;

@RestController
@RequestMapping("/nosliw/datasource")
@HAPEntityWithAttribute
public class HAPAPIDataSource {

	@Autowired
	private HAPManagerService m_serviceManager;
	
	@GetMapping("instance/profile/{id}")
    public String getDataSourceProfile(@PathVariable String id) {
		return HAPServiceData.createSuccessData(this.m_serviceManager.getServiceProfile(id)).toStringValue(HAPSerializationFormat.JSON);
	}	

	@PostMapping("instance/profile/search")
    public String searchDataSource(@RequestBody String requestBody) {
		JSONObject queryJsonObj = new JSONObject(requestBody);
		HAPQueryServiceDefinition query = new HAPQueryServiceDefinition();
		query.buildObject(queryJsonObj, HAPSerializationFormat.JSON);
		return HAPServiceData.createSuccessData(this.m_serviceManager.queryDefinition(query)).toStringValue(HAPSerializationFormat.JSON);
	}	

	@PostMapping("instance/exectue")
    public String executeDataSource(@RequestBody String requestBody) {
		HAPAIPRequestExectueDataSource request = new HAPAIPRequestExectueDataSource();
		request.buildObject(new JSONObject(requestBody), HAPSerializationFormat.JSON);
		return HAPServiceData.createSuccessData(this.m_serviceManager.execute(request.getServiceQuery(), request.getParms())).toStringValue(HAPSerializationFormat.JSON);
	}	

}
