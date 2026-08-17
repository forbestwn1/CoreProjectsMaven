package com.nosliw.core.application.entity.uitag;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.uitag.HAPGatewayUITag;
import com.nosliw.core.application.common.uitag.HAPUITagDefinition;
import com.nosliw.core.application.common.uitag.HAPUITagInfo;
import com.nosliw.core.application.common.uitag.HAPUITageQueryData;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.gateway.HAPGatewayImp;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
@Component
public class HAPGatewayUITagImp extends HAPGatewayImp implements HAPGatewayUITag{

	private HAPManagerUITag m_uiTagMan;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	public HAPGatewayUITagImp(HAPManagerUITag uiTagMan) {
		this.m_uiTagMan = uiTagMan;
	}
	
	@Override
	public String getName() {   return HAPConstantShared.GATEWAY_UITAG;    }

	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) {
		HAPServiceData out = null;
		try{
			switch(command){
			case COMMAND_GETTAG:
			{
				String name = parms.getString(COMMAND_GETTAG_NAME);
				String version = (String)parms.opt(COMMAND_GETTAG_VERSION);
				if(version==null) {
					version = "1.0.0";
				}
				HAPUITagDefinition tagDef = this.m_uiTagMan.getUITagDefinition(name, version);
				out = this.createSuccessWithObject(tagDef);
				break;
			}
			case COMMAND_GETDEFAULTTAG:
			{
				HAPUITageQueryData query = null;
				Object queryDataObj = parms.opt(COMMAND_GETDEFAULTTAG_CRITERIA);
				if(queryDataObj!=null) {
					if(queryDataObj instanceof String) {
						query = new HAPUITageQueryData(HAPUtilityCriteria.parseCriteria((String)queryDataObj));
					}
					else if(queryDataObj instanceof JSONObject) {
						query = HAPUITageQueryData.parseUITagQueryData((JSONObject)queryDataObj, m_entityParseService);
					}
				}
				
				HAPUITagInfo result = this.m_uiTagMan.getDefaultUITagData(query);
				out = this.createSuccessWithObject(result);
				break;
			}
			case COMMAND_QUERYTAG:
			{
				HAPUITageQueryData query = new HAPUITageQueryData(HAPUtilityCriteria.parseCriteria(parms.getString(COMMAND_QUERYTAG_CRITERIA)));
				HAPUITagQueryResultSet result = this.m_uiTagMan.queryUITagData(query);
				out = this.createSuccessWithObject(result);
				break;
			}
			case COMMAND_CLEARCHACHE:
				this.m_uiTagMan.clearCache();
				break;
			}
		}
		catch(Exception e){
			out = HAPServiceData.createFailureData(e, "");
			e.printStackTrace();
		}
		return out;
	}

}
