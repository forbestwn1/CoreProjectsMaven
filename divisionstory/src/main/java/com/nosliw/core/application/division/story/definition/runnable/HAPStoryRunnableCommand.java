package com.nosliw.core.application.division.story.definition.runnable;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryParserRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryPath;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryRunnableCommand extends HAPStoryRunnable{

	public final static String PATHTOCOMMAND = "pathToCommand";
	
	public final static String DATAASSOCIATION = "dataAssociation";
	
	private HAPStoryPath m_pathToCommand;
	
	private HAPStoryDataAssociationForTask m_dataAssociation;
	
	public HAPStoryRunnableCommand() {
		super(HAPConstantShared.STORYNODE_TYPE_TASK_COMMAND);
	}

	public void setPathToCommand(HAPStoryPath path) {    this.m_pathToCommand = path;   }
	public HAPStoryPath getPathToCommand() {    return this.m_pathToCommand;     }
	
	public HAPStoryDataAssociationForTask getDataAssociation() {      return this.m_dataAssociation;        }
	public void setDataAssociation(HAPStoryDataAssociationForTask dataAssociation) {      this.m_dataAssociation = dataAssociation;         }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PATHTOCOMMAND, this.m_pathToCommand.toStringValue(HAPSerializationFormat.JSON));
		if(this.m_dataAssociation!=null) {
			jsonMap.put(DATAASSOCIATION, this.m_dataAssociation.toStringValue(HAPSerializationFormat.JSON));
		}
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
		
		JSONObject daJsonObj = jsonObj.optJSONObject(HAPStoryRunnableCommand.DATAASSOCIATION);
		if(daJsonObj!=null) {
			HAPStoryDataAssociationForTask da = new HAPStoryDataAssociationForTask();
			da.buildObject(daJsonObj, HAPSerializationFormat.JSON);
			runnable.setDataAssociation(da);
		}

	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryRunnableCommand out = new HAPStoryRunnableCommand();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
