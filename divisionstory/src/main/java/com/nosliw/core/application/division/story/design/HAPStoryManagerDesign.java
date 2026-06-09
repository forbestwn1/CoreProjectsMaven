package com.nosliw.core.application.division.story.design;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.division.manual.core.process.HAPManualContentProviderText;
import com.nosliw.core.application.division.manual.core.process.HAPManualUtilityExporterContentProviderText;
import com.nosliw.core.application.division.story.converter.manual.HAPStoryConverterToManual;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.service.idgenerator.HAPServiceIdGenerator;
import com.nosliw.core.system.HAPSystemFolderUtility;

@Component
public class HAPStoryManagerDesign {

	@Autowired
	private HAPServiceIdGenerator m_idGenerator;
	
	@Autowired
	private HAPStoryManagerChange m_changeMan;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
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
	
	public HAPStoryDesign getDesign(String designId) {   
		HAPStoryDesign out = this.m_designs.get(designId);      
		out = null;
		if(out==null) {
			out = this.loadDesign(designId);
			if(out!=null) {
				this.m_designs.put(out.getId(), out);
			}
		}
		return out;
	}
	
	public HAPStoryBuilderResponseNew newStoryDesign(String builderId, String designId) {
		HAPStoryBuilder storyBuilder = this.getBuilder(builderId);
		HAPStoryDesign design = new HAPStoryDesign(designId!=null?designId:this.generateId(), builderId, this.m_changeMan);
		HAPStoryBuilderResponseNew out = storyBuilder.initDesign(design);
		this.saveStoryDesign(design);
		return out;
	}
	
	public HAPStoryBuilderResponseBuild designStory(HAPStoryBuilderRequest designRequest) {
		String designId = designRequest.getDesignId();
		HAPStoryDesign design = this.getDesign(designId);
		HAPStoryBuilder builder = this.getBuilder(design.getBuilderId());
		HAPStoryBuilderResponseBuild out = builder.buildStory(design, designRequest);
		this.saveStoryDesign(design);
		return out;
	}

	public HAPManualContentProviderText convertDesignToManual(String designId) {
		HAPStoryDesign design = this.getDesign(designId);
		HAPManualContentProviderText contentProvider = HAPStoryConverterToManual.convert(design.getStory());
		HAPManualUtilityExporterContentProviderText.export(contentProvider, getDesignFolder(designId).getAbsolutePath()+"/manual");
		return contentProvider;
	}
	
	private HAPStoryDesign loadDesign(String designId) {
		HAPStoryDesign out = null;
		File dir = this.getDesignFolder(designId);
		if(dir.exists()) {
			List<File> children = HAPUtilityFile.getChildrenSortedByName(dir);
			out = HAPStoryDesignUtilityParse.parseStoryDesign(children.get(children.size()-1), this.m_entityParseService, this.m_changeMan);
		}
		else {
			throw new RuntimeException();
		}
		return out;
	}
	
	private File getDesignFolder(String designId) {
		return new File(HAPSystemFolderUtility.getStoryDesignFolder() + "/" + designId);
	}
	
	private void saveStoryDesign(HAPStoryDesign storyDesign) {  
		String seperator = "__";
		
		String designId = storyDesign.getId();
		this.m_designs.put(designId, storyDesign);

		File dir = HAPUtilityFile.getOrCreateFolder(this.getDesignFolder(designId));
		List<File> children = HAPUtilityFile.getChildrenSortedByName(dir);
		int indx = 100;
		if(children.size()>0) {
			String name = children.get(children.size()-1).getName();
			int i1 = name.indexOf(seperator);
			int i2 = name.indexOf(".");
			indx = Integer.valueOf(name.substring(i1+seperator.length(), i2));
			indx++;
		}
		
		HAPUtilityFile.writeJsonFile(dir.getAbsolutePath(), "version"+seperator+indx+".json", storyDesign.toStringValue(HAPSerializationFormat.JSON));
	}
	
	private HAPStoryBuilder getBuilder(String builderId) {	return this.m_builders.get(builderId);	}
	
	private String generateId() {		
		return System.currentTimeMillis()+"";
//		return this.m_idGenerator.generateIdStr();	
	}
	
}
