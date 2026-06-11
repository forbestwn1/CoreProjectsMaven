package com.nosliw.core.application.division.story.definition.runnable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryParserRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryPath;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryRunnableCommand extends HAPStoryRunnable{

	//command element id
	public final static String CHILD_COMMAND = "command";

	//data association for request
	public final static String CHILD_REQUESTDATAASSOCIATION = "requestDataAssociation";
	
	//data association for response
	public final static String CHILD_RESPONSEDATAASSOCIATION = "responseDataAssociation";

	public final static String PATHTOCOMMAND = "pathToCommand";
	
	public final static String REQUESTDATAASSOCIATION = "requestDataAssociation";
	
	public final static String RESPONSEDATAASSOCIATION = "responseDataAssociation";
	
	public HAPStoryPath m_pathToCommand;
	
	private HAPStoryDataAssociationComplex m_requestDataAssociation;
	
	private Map<String, HAPStoryDataAssociationComplex> m_responseDataAssociation;
	
	public HAPStoryRunnableCommand() {
		super(HAPConstantShared.STORYNODE_TYPE_TASK_COMMAND);
		this.m_responseDataAssociation = new LinkedHashMap<String, HAPStoryDataAssociationComplex>();
	}

	public void setPathToCommand(HAPStoryPath path) {    this.m_pathToCommand = path;   }
	public HAPStoryPath getPathToCommand() {    return this.m_pathToCommand;     }
	
	public HAPStoryDataAssociationComplex getRequestDataAssociation() {     return this.m_requestDataAssociation;     }
	public void setRequestDataAssociation(HAPStoryDataAssociationComplex requestDataAssociation) {     this.m_requestDataAssociation = requestDataAssociation;      }
	
    public Map<String, HAPStoryDataAssociationComplex> getResponseDataAssociations(){     return this.m_responseDataAssociation;         }
	public void addResponseDataAssociation(String name, HAPStoryDataAssociationComplex responseDataAssociation) {    this.m_responseDataAssociation.put(name, responseDataAssociation);       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PATHTOCOMMAND, this.m_pathToCommand.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(REQUESTDATAASSOCIATION, this.m_requestDataAssociation.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(RESPONSEDATAASSOCIATION, HAPManagerSerialize.getInstance().toStringValue(m_responseDataAssociation, HAPSerializationFormat.JSON));
	}
	
}

@Component
class HAPStoryElementRunnableCommand__HAPEntityParsable extends HAPStoryParserRunnable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_TASK_COMMAND;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnableCommand runnable, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, runnable, parseService);
		
		HAPStoryPath path = new HAPStoryPath();
		path.buildObject(jsonObj.getJSONObject(HAPStoryRunnableCommand.PATHTOCOMMAND), HAPSerializationFormat.JSON);
		runnable.setPathToCommand(path);

		JSONObject requestDAJson = jsonObj.getJSONObject(HAPStoryRunnableCommand.REQUESTDATAASSOCIATION);
		if(requestDAJson!=null) {
			HAPStoryDataAssociationComplex requestDataAssociation = new HAPStoryDataAssociationComplex(); 
			requestDataAssociation.buildObject(requestDAJson, HAPSerializationFormat.JSON);
			runnable.setRequestDataAssociation(requestDataAssociation);
		}

		JSONObject responseDAJsonMap = jsonObj.getJSONObject(HAPStoryRunnableCommand.RESPONSEDATAASSOCIATION);
		if(responseDAJsonMap!=null) {
			for(Object key : responseDAJsonMap.keySet()) {
				String resultName = (String)key;
				JSONObject responseDA = jsonObj.getJSONObject(resultName);
				HAPStoryDataAssociationComplex responseDataAssociation = new HAPStoryDataAssociationComplex(); 
				responseDataAssociation.buildObject(responseDA, HAPSerializationFormat.JSON);
				runnable.addResponseDataAssociation(resultName, responseDataAssociation);
			}
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryRunnableCommand out = new HAPStoryRunnableCommand();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
