package com.nosliw.core.application.division.story.design;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;
import com.nosliw.core.service.idgenerator.HAPServiceIdGenerator;
import com.nosliw.core.system.HAPSystemFolderUtility;

@Component
public class HAPStoryManagerDesign {

	@Autowired
	private HAPServiceIdGenerator m_idGenerator;
	
	@Autowired
	private HAPStoryManagerChange m_changeMan;
	
    private Map<String, HAPStoryBuilder> m_builders;	
	
    private Map<String, HAPStoryDesign> m_designs;
    
    public HAPStoryManagerDesign() {
    	this.m_builders = new LinkedHashMap<String, HAPStoryBuilder>();
    	this.m_designs = new LinkedHashMap<String, HAPStoryDesign>();
    }
    
	@Autowired
	void setBuilders(List<HAPStoryBuilder> builders) {
		for(HAPStoryBuilder builder : builders) {
			this.m_builders.put(builder.getBuilderId(), builder);
		}
	}
	
	public HAPStoryDesign newStoryDesign(String builderId, String designId) {
		HAPStoryBuilder storyBuilder = this.getBuilder(builderId);
		HAPStoryDesign out = new HAPStoryDesign(designId!=null?designId:this.generateId(), builderId, this.m_changeMan);
		storyBuilder.initDesign(out);
		this.saveStoryDesign(out);
		return out;
	}
	
	public HAPServiceData designStory(String designId, HAPStoryBuilderRequest designRequest) {
		HAPStoryDesign design = this.getDesign(designId);
		HAPStoryBuilder builder = this.getBuilder(design.getBuilderId());
		HAPServiceData out = builder.buildStory(design, designRequest);
		if(out.isSuccess()) {
			this.saveStoryDesign(design);
		}
		return out;
	}	

	private void saveStoryDesign(HAPStoryDesign storyDesign) {  
		this.m_designs.put(storyDesign.getId(), storyDesign);
        
		HAPUtilityFile.writeJsonFile(HAPSystemFolderUtility.getStoryDesignFolder(), storyDesign.getId()+".json", storyDesign.toStringValue(HAPSerializationFormat.JSON));
	}

	private HAPStoryDesign getDesign(String designId) {   return this.m_designs.get(designId);      }
	
	private HAPStoryBuilder getBuilder(String builderId) {	return this.m_builders.get(builderId);	}
	
	private String generateId() {		return this.m_idGenerator.generateIdStr();	}
	
}
