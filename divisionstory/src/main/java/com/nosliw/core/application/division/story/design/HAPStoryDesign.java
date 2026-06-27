package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;

//dynamic, 
//    store all the information about how story was build
//    support transaction
@HAPEntityWithAttribute
public class HAPStoryDesign extends HAPEntityInfoImp{

	@HAPAttribute
	public static final String BUILDERID = "builderId";
	
	@HAPAttribute
	public static final String ROOTBRICKTYPE = "rootBrickType";
	
	@HAPAttribute
	public static final String STORY = "story";
	
	@HAPAttribute
	public static final String STEP = "step";
	
	private HAPStoryManagerChange m_changeMan;

	private String m_builderId;
	
	private HAPIdBrickType m_rootBrickType;
	
	private HAPStoryStory m_story;
	
	private List<HAPStoryDesignStep> m_changeHistory;
	
	public HAPStoryDesign(HAPStoryManagerChange changeMan) {
		this.m_story = new HAPStoryStory();
		this.m_changeHistory = new ArrayList<HAPStoryDesignStep>();
		this.m_changeMan = changeMan;
	}
	
	public HAPStoryDesign(String id, HAPIdBrickType rootBrickType, String builderId, HAPStoryManagerChange changeMan) {
		this(changeMan);
		this.setId(id);
		this.m_rootBrickType = rootBrickType;
		this.m_builderId = builderId;
	}
	
	public HAPIdBrickType getRootBrickType() {    return this.m_rootBrickType;      }
	public void setRootBrickType(HAPIdBrickType brickType) {      this.m_rootBrickType = brickType;       }
	public HAPIdBrick getBrickId() {    return new HAPIdBrick(this.m_rootBrickType, HAPConstantShared.BRICK_DIVISION_STORY, this.getId());      }
	
	public String getBuilderId() {  return this.m_builderId;     }
	public void setBuilderId(String builderId) {     this.m_builderId = builderId;     }
	
	public HAPStoryStory getStory() {    return this.m_story;     }
	public void setStory(HAPStoryStory story) {      this.m_story = story;      }

	public void addStep(HAPStoryDesignStep step) {    this.m_changeHistory.add(step);     }
	
	public void newInitStep() {
		HAPStoryDesignStep step = new HAPStoryDesignStep(new HAPStoryDesignMetadataStepInit());
		this.addStep(step);
	}
	
	public void newStep(HAPStoryDesignMetadataStep metaData) {
		HAPStoryDesignStep step = new HAPStoryDesignStep(metaData);
		this.addStep(step);
	}

	public HAPStoryDesignSessionChange newChangeReqestSession() {     return this.newChangeReqestSession(null);	}
	
	public HAPStoryDesignSessionChange newChangeReqestSession(HAPStoryDesignConfigureSessionChange configure) {
		return new HAPStoryDesignSessionChange(this, configure);
	}
	
	protected List<HAPStoryChangeItem> applyChanges(List<HAPStoryChangeItem> changesRequest, HAPStoryDesignConfigureSessionChange configure){
		List<HAPStoryChangeItem> changes = new ArrayList<HAPStoryChangeItem>();
		if(configure.isExtend()) {
			this.m_changeMan.applyChanges(this.m_story, changesRequest, changes);
		}
		else {
			this.m_changeMan.applyChanges(this.m_story, changesRequest);
			changes.addAll(changesRequest);
		}

		HAPStoryDesignStep step = this.getCurrentStep();
		step.addChanges(changesRequest, changes);
		return changes;
	}
	
	public void removeStep() {
		this.rollBackStep();
		this.m_changeHistory.remove(this.m_changeHistory.size()-1);
	}

	public void rollBackStep() {
		HAPStoryDesignStep step = this.getCurrentStep();
		this.m_changeMan.revertChange(m_story, step.getChanges());
		step.clear();
	}
	
	public List<HAPStoryDesignMetadataStep> getStepInfos(){
		List<HAPStoryDesignMetadataStep> out = new ArrayList<HAPStoryDesignMetadataStep>();
		for(int i=1; i<this.m_changeHistory.size(); i++) {
			out.add(m_changeHistory.get(i).getMetaData());
		}
		return out;      
	}
	
	public int getCurrentStepIndex() {    return this.m_changeHistory.size()-1;      }
	
	public HAPStoryDesignStep getCurrentStep() {     return this.m_changeHistory.get(this.getCurrentStepIndex());    }
	public boolean isFirstStep() {    return this.m_changeHistory.size()==2;      }
	
	@Override
	protected boolean buildObjectByJson(Object json){  
		JSONObject jsonObj = (JSONObject)json;
		this.m_builderId = jsonObj.getString(BUILDERID);
		
		Object rootBrickTypeObj = jsonObj.opt(ROOTBRICKTYPE);
		if(rootBrickTypeObj!=null) {
			this.m_rootBrickType = new HAPIdBrickType();
			this.m_rootBrickType.buildObject(rootBrickTypeObj, HAPSerializationFormat.JSON);
		}
		
		this.m_story.buildObject(jsonObj.getJSONObject(STORY), HAPSerializationFormat.JSON);
		
		JSONArray stepJsonArray = jsonObj.optJSONArray(STEP);
		if(stepJsonArray!=null) {
			for(int i=0; i<stepJsonArray.length(); i++) {
				HAPStoryDesignStep step = new HAPStoryDesignStep();
				step.buildObject(stepJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
				this.m_changeHistory.add(step);
			}
		}
		
		return true;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ROOTBRICKTYPE, this.m_rootBrickType.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(BUILDERID, this.m_builderId);
		jsonMap.put(STORY, this.m_story.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(STEP, HAPUtilityJson.buildJson(this.m_changeHistory, HAPSerializationFormat.JSON));
	}
	
}
