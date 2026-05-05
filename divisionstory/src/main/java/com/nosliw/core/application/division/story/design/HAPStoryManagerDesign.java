package com.nosliw.core.application.division.story.design;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;

@Component
public class HAPStoryManagerDesign {

	private long m_idIndex;

    private Map<String, HAPStoryBuilderDesign> m_builders;	
	
    private Map<String, HAPStoryDesignStory> m_designs;
    
    public HAPStoryManagerDesign() {
    	this.m_builders = new LinkedHashMap<String, HAPStoryBuilderDesign>();
    	this.m_designs = new LinkedHashMap<String, HAPStoryDesignStory>();
    }
    
	@Autowired
	void setBuilders(List<HAPStoryBuilderDesign> builders) {
		for(HAPStoryBuilderDesign builder : builders) {
			this.m_builders.put(builder.getBuilderId(), builder);
		}
	}
	
	public HAPStoryDesignStory newStoryDesign(String builderId, String designId) {
		HAPStoryBuilderDesign storyBuilder = this.getBuilder(builderId);
		HAPStoryDesignStory out = new HAPStoryDesignStory(designId!=null?designId:this.generateId(), builderId);
		storyBuilder.initDesign(out);
		this.saveStoryDesign(out);
		return out;
	}
	
	
	public HAPServiceData designStory(String designId, HAPStoryRequestDesign designRequest) {
		HAPStoryDesignStory design = this.getDesign(designId);
		HAPStoryBuilderDesign builder = this.getBuilder(design.getBuilderId());
		HAPServiceData out = builder.buildStory(design, designRequest);
		if(out.isSuccess()) {
			this.saveStoryDesign(design);
		}
		return out;
	}	

	private void saveStoryDesign(HAPStoryDesignStory storyDesign) {  this.m_designs.put(storyDesign.getId(), storyDesign);	}

	private HAPStoryDesignStory getDesign(String designId) {   return this.m_designs.get(designId);      }
	
	private HAPStoryBuilderDesign getBuilder(String builderId) {	return this.m_builders.get(builderId);	}
	
	private String generateId() {		return (this.m_idIndex++) + "";	}
	
}
