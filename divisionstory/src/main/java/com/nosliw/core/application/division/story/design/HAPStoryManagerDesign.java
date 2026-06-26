package com.nosliw.core.application.division.story.design;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualContentProviderText;
import com.nosliw.core.application.division.manual.core.process.HAPManualUtilityExporterContentProviderText;
import com.nosliw.core.application.division.story.converter.manual.HAPStoryConverterToManual;
import com.nosliw.core.application.division.story.converter.manual.HAPStoryUtilityConverter;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.service.idgenerator.HAPServiceIdGenerator;

@Component
public class HAPStoryManagerDesign {

	@Autowired
	private HAPServiceIdGenerator m_idGenerator;
	
	@Autowired
	private HAPStoryManagerChange m_changeMan;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
    private Map<String, HAPStoryBuilder> m_builders;	
	
    private Map<String, Map<String, HAPStoryDesign>> m_designs;
    
    public HAPStoryManagerDesign() {
    	this.m_builders = new LinkedHashMap<String, HAPStoryBuilder>();
    	this.m_designs = new LinkedHashMap<String, Map<String, HAPStoryDesign>>();
    }
    
	@Autowired
	void setBuilders(List<HAPStoryBuilder> builders) {
		for(HAPStoryBuilder builder : builders) {
			this.m_builders.put(builder.getBuilderId(), builder);
		}
	}
	
	public HAPStoryDesign getDesign(HAPIdBrick brickId) {   
		HAPStoryDesign out = null;
		Map<String, HAPStoryDesign> byIds = this.m_designs.get(brickId.getBrickTypeId().getKey());
		if(byIds!=null) {
			out = byIds.get(brickId.getId());      
		}
		if(out==null) {
			out = HAPStoryDesignUtilityExport.loadDesign(brickId, this.m_entityParseService, this.m_changeMan);
			if(out!=null) {
				this.setDesign(out);
			}
		}
		return out;
	}
	
	public HAPStoryBuilderResponseNew newStoryDesign(HAPIdBrickType brickTypeId, String builderId, String designId) {
		HAPStoryBuilder storyBuilder = this.getBuilder(builderId);
		HAPStoryDesign design = new HAPStoryDesign(designId!=null?designId:this.generateId(), brickTypeId, builderId, this.m_changeMan);
		HAPStoryBuilderResponseNew out = storyBuilder.initDesign(design);
	
		this.setDesign(design);
		
		HAPStoryDesignUtilityExport.saveStoryDesign(design);
		return out;
	}
	
	public HAPStoryBuilderResponseBuild designStory(HAPStoryBuilderRequest designRequest) {
		HAPStoryDesign design = this.getDesign(designRequest.getBrickId());
		HAPStoryBuilder builder = this.getBuilder(design.getBuilderId());
		HAPStoryBuilderResponseBuild out = builder.buildStory(design, designRequest);
		
		this.setDesign(design);

		HAPStoryDesignUtilityExport.saveStoryDesign(design);
		return out;
	}

	public String convertDesignToManual(HAPIdBrick brickId) {
		HAPStoryDesign design = this.getDesign(brickId);
		HAPManualContentProviderText contentProvider = HAPStoryConverterToManual.convert(design.getStory());
		String exportFolder = HAPStoryUtilityConverter.getDesignConverToManualFolder(brickId);
		HAPManualUtilityExporterContentProviderText.export(contentProvider, exportFolder);
		return exportFolder;
	}
	

	private void setDesign(HAPStoryDesign design) {
		String brickTypeKey = design.getRootBrickType().getKey();
		Map<String, HAPStoryDesign> byIds = this.m_designs.get(brickTypeKey);
		if(byIds == null) {
			byIds = new LinkedHashMap<String, HAPStoryDesign>();
			this.m_designs.put(brickTypeKey, byIds);
		}
		byIds.put(design.getId(), design);
	}
	
	private HAPStoryBuilder getBuilder(String builderId) {	return this.m_builders.get(builderId);	}
	
	private String generateId() {		
		return System.currentTimeMillis()+"";
//		return this.m_idGenerator.generateIdStr();	
	}
	
}
