package com.nosliw.core.application.division.manual.core;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.brick.HAPAdapter;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.brick.HAPWrapperValue;
import com.nosliw.core.application.brick.HAPWrapperValueOfBrick;
import com.nosliw.core.application.common.brick.serialize.HAPUtilityExport;
import com.nosliw.core.application.common.manual.HAPManualContentProvider;
import com.nosliw.core.application.common.manual.HAPManualContentProviderFile;
import com.nosliw.core.application.common.manual.HAPManualDefinitionUtilityBrickLocation;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualInfoBrickType;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorAdapter;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlock;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualProcessBundle;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.application.entity.brick.HAPPluginDivision;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrickTraverse;
import com.nosliw.core.application.entity.brickcriteria.HAPManagerBrickCriteria;
import com.nosliw.core.application.entity.datarule.HAPManagerDataRule;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.expression.definition.HAPParserDataExpression;
import com.nosliw.core.resource.HAPManagerResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.runtime.HAPRuntimeManager;

@Component
public class HAPManualManagerBrick implements HAPPluginDivision{

	private Map<String, HAPManualDefinitionPluginParserBrick> m_brickParserPlugin;
	private Map<String, HAPManualPluginProcessorBrick> m_brickProcessorPlugin;
	private Map<String, HAPManualPluginProcessorBlock> m_blockProcessorPlugin;
	private Map<String, HAPManualPluginProcessorAdapter> m_adapterProcessorPlugin;
	private Map<String, HAPManualInfoBrickType> m_brickTypeInfo;
	
	private HAPManagerApplicationBrick m_brickManager;
	
	private HAPDataTypeHelper m_dataTypeHelper;
	
	private HAPManagerResource m_resourceMan;
	
	private HAPParserDataExpression m_dataExpressionParser;

	private HAPRuntimeManager m_runtimeMan;

	private HAPManagerDataRule m_dataRuleManager;
	
	private HAPManagerBrickCriteria m_brickCriteriaMan;
	
	private HAPServiceParseEntity m_parseService;
	
	private HAPManualConfigure m_manualConfigure;
	
	public HAPManualManagerBrick() {
		this.m_brickParserPlugin = new LinkedHashMap<String, HAPManualDefinitionPluginParserBrick>();
		this.m_brickProcessorPlugin = new LinkedHashMap<String, HAPManualPluginProcessorBrick>();
		this.m_blockProcessorPlugin = new LinkedHashMap<String, HAPManualPluginProcessorBlock>();
		this.m_adapterProcessorPlugin = new LinkedHashMap<String, HAPManualPluginProcessorAdapter>();
		this.m_brickTypeInfo = new LinkedHashMap<String, HAPManualInfoBrickType>();

		init();
	}
	
	@Autowired
	private void setBrickManager(HAPManagerApplicationBrick brickMan) {  this.m_brickManager = brickMan;  }
	
	@Autowired
	private void setDataTypeHelper(HAPDataTypeHelper dataTypeHelper) {   this.m_dataTypeHelper = dataTypeHelper;    }
	
	@Autowired
	private void setResourceManager(HAPManagerResource resourceMan) {    this.m_resourceMan = resourceMan;      }
	
	@Autowired
	private void setExpressionParser(HAPParserDataExpression dataExpressionParser) {    this.m_dataExpressionParser = dataExpressionParser;      }
	
	@Autowired
	private void setRuntimeManager(HAPRuntimeManager runtimeMan) {    this.m_runtimeMan = runtimeMan;      }
	
	@Autowired
	private void setBrickCriteriaManager(HAPManagerBrickCriteria brickCriteriaMan) {   this.m_brickCriteriaMan = brickCriteriaMan;        }
	
	@Autowired
	private void setDataRuleManager(HAPManagerDataRule dataRuleManager) {   this.m_dataRuleManager = dataRuleManager;        }
	
	@Autowired
	private void setParseService(HAPServiceParseEntity parseService) {   this.m_parseService = parseService;        }
	
	@Autowired
	private void setManualConfigure(HAPManualConfigure manualConfigure) {  this.m_manualConfigure = manualConfigure;  }
	
	
	@Override
	public String getDivisionName() {   return HAPConstantShared.BRICK_DIVISION_MANUAL;   }
	
	@Override
	public Set<HAPIdBrickType> getBrickTypes() {   return null;   }
	
	@Override
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPBundleForBrick out = null;
		
//		out = HAPUtilityExport.importBundle(bundleFolder.getAbsolutePath(), HAPSerializationFormat.JSON, m_parseService);
//		if(out==null) {
//			out = this.buildBundle(new HAPManualContentProviderFile(brickId, HAPManualDefinitionUtilityBrickLocation.getBrickLocationInfo(brickId),  this.m_brickCriteriaMan, this.m_parseService), runtimeInfo);
//			HAPUtilityExport.exportBundle(out, bundle.getAbsolutePath(), HAPSerializationFormat.JSON);
//		}

		out = this.buildBundle(new HAPManualContentProviderFile(brickId, HAPManualDefinitionUtilityBrickLocation.getBrickLocationInfo(getSrouceRootPath(), brickId),  this.m_brickCriteriaMan, this.m_parseService), runtimeInfo);

		HAPUtilityExport.exportBundle(out, this.getBundleExportFolder(brickId), HAPSerializationFormat.JSON);
		HAPBundleForBrick out1 = HAPUtilityExport.importBundle(this.getBundleExportFolder(brickId), HAPSerializationFormat.JSON, m_parseService);
		if(out1!=null) {
			for(String rootName : out1.getAllRootBrickName()) {
				HAPUtilityOtherBrickTraverse.traverseTreeWithLocalBrick(out1, rootName, new HAPHandlerDownward() {

					@Override
					public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
						
						HAPBrick brick = HAPUtilityBrick.getDescdentBrickLocal(bundle, path);
						if(brick instanceof HAPManualBrick) {
							HAPManualBrick manualBrick = (HAPManualBrick)brick;
							manualBrick.setBundle(out1);
							manualBrick.setManualBrickManager((HAPManualManagerBrick)data);
						}
						
						HAPAttributeInBrick attr = HAPUtilityBrick.getDescendantAttribute(bundle, path);
						if(attr!=null) {
							for(HAPAdapter adapter : attr.getAdapters()) {
								HAPWrapperValue valueWrapper = adapter.getValueWrapper();
								if(HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_BRICK.equals(valueWrapper.getValueType())) {
									HAPBrick brickInAdapter = ((HAPWrapperValueOfBrick)	valueWrapper).getBrick();
									if(brickInAdapter instanceof HAPManualBrick) {
										HAPManualBrick manualBrick = (HAPManualBrick)brickInAdapter;
										manualBrick.setBundle(out1);
										manualBrick.setManualBrickManager((HAPManualManagerBrick)data);
									}
								}
							}
						}
						
						return true;
					}

					@Override
					public void postProcessBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
					}
					
				}, m_brickManager, this);
			}
		}
		
		HAPUtilityExport.exportBundle(out1, HAPUtilityFileNio.buildPath(this.getBundleExportFolder(brickId), "out1"), HAPSerializationFormat.JSON);
		
		return out1;
	}
	
	public HAPBundleForBrick buildBundle(HAPManualContentProvider contentProvider, HAPRuntimeInfo runtimeInfo) {
		return HAPManualProcessBundle.buildBundle(contentProvider, runtimeInfo, this, m_runtimeMan, m_brickManager, m_dataTypeHelper, m_resourceMan, m_dataRuleManager, m_dataExpressionParser);
	}
	
	@Autowired
	private void setBickInfoProviders(List<HAPManualProviderBrickInfo> brickInfoProviders) {
		for(HAPManualProviderBrickInfo brickInfoProvider : brickInfoProviders) {
			this.addBrickInfoProvider(brickInfoProvider);
		}
	}
	
	@Autowired
	private void setBrickInfoProvidersMultiple(List<HAPManualProviderBrickInfoMultiple> providers) {
		for(HAPManualProviderBrickInfoMultiple providerMultiple : providers) {
			for(HAPManualProviderBrickInfo brickInfoProvider : providerMultiple.getProviders()) {
				this.addBrickInfoProvider(brickInfoProvider);
			}
		}
	}
	
	private void addBrickInfoProvider(HAPManualProviderBrickInfo brickInfoProvider) {
		HAPIdBrickType brickTypeId = brickInfoProvider.getBrickTypeId();
		if(brickInfoProvider.getBrickParser()!=null) {
			this.m_brickParserPlugin.put(brickTypeId.getKey(), brickInfoProvider.getBrickParser());
		}
		
		HAPManualPluginProcessorBrick processor = brickInfoProvider.getBrickProcessor();
		if(processor!=null) {
			this.m_brickProcessorPlugin.put(brickTypeId.getKey(), processor);
			if(processor instanceof HAPManualPluginProcessorAdapter) {
				this.m_adapterProcessorPlugin.put(brickTypeId.getKey(), (HAPManualPluginProcessorAdapter)processor);
			}
			else if(processor instanceof HAPManualPluginProcessorBlock) {
				this.m_blockProcessorPlugin.put(brickTypeId.getKey(), (HAPManualPluginProcessorBlock)processor);
			}
		}
		
		if(brickInfoProvider.getBrickTypeInfo()!=null) {
			this.m_brickTypeInfo.put(brickTypeId.getKey(), brickInfoProvider.getBrickTypeInfo());
		}
	}
	
	public void registerBrickTypeInfo(HAPIdBrickType brickTypeId,  HAPManualInfoBrickType brickTypeInfo) {	this.m_brickTypeInfo.put(brickTypeId.getKey(), brickTypeInfo); 	}
	
	public void registerBlockPluginInfo(HAPIdBrickType brickTypeId, HAPManualInfoBrickType brickTypeInfo, HAPManualDefinitionPluginParserBrick brickParserPlugin, HAPManualPluginProcessorBlock blockProcessPlugin) {
		this.m_brickParserPlugin.put(brickTypeId.getKey(), brickParserPlugin);
		this.m_brickProcessorPlugin.put(brickTypeId.getKey(), blockProcessPlugin);
		this.m_blockProcessorPlugin.put(brickTypeId.getKey(), blockProcessPlugin);
		this.registerBrickTypeInfo(brickTypeId, brickTypeInfo);
	}

	public void registerAdapterPluginInfo(HAPIdBrickType brickTypeId, HAPManualInfoBrickType brickTypeInfo, HAPManualDefinitionPluginParserBrick brickParserPlugin, HAPManualPluginProcessorAdapter adapterProcessPlugin) {
		this.m_brickParserPlugin.put(brickTypeId.getKey(), brickParserPlugin);
		this.m_brickProcessorPlugin.put(brickTypeId.getKey(), adapterProcessPlugin);
		this.m_adapterProcessorPlugin.put(brickTypeId.getKey(), adapterProcessPlugin);
		this.registerBrickTypeInfo(brickTypeId, brickTypeInfo);
	}

	public HAPManualInfoBrickType getBrickTypeInfo(HAPIdBrickType brickTypeId) {	return this.m_brickTypeInfo.get(brickTypeId.getKey());	}
	public HAPManualDefinitionBrick newBrickDefinition(HAPIdBrickType brickType) {    
		return this.getBrickParsePlugin(brickType).newBrick();      
	}
	public HAPManualBrick newBrick(HAPIdBrickType brickType, HAPBundleForBrick bundle) {    return this.getBrickProcessPlugin(brickType).newInstance(bundle, this);      }
	public HAPManualBrick newBrickWithInit(HAPIdBrickType brickType, HAPBundleForBrick bundle) {
		HAPManualBrick out = this.newBrick(brickType, bundle);
		out.init();
		return out;
	}
	
	public HAPManualDefinitionPluginParserBrick getBrickParsePlugin(HAPIdBrickType entityTypeId) {   return this.m_brickParserPlugin.get(entityTypeId.getKey());    }
	public HAPManualPluginProcessorBrick getBrickProcessPlugin(HAPIdBrickType entityTypeId) {   return this.m_brickProcessorPlugin.get(entityTypeId.getKey());    }
	public HAPManualPluginProcessorBlock getBlockProcessPlugin(HAPIdBrickType entityTypeId) {   return this.m_blockProcessorPlugin.get(entityTypeId.getKey());    }
	public HAPManualPluginProcessorAdapter getAdapterProcessPlugin(HAPIdBrickType entityTypeId) {   return this.m_adapterProcessorPlugin.get(entityTypeId.getKey());    }


	public Path getSrouceRootPath() {		return HAPUtilityFileNio.buildPath(this.m_manualConfigure.getSourcePath());	}
	public Path getBundleRootPath() {		return HAPUtilityFileNio.buildPath(this.m_manualConfigure.getPath());	}
	
	public Path getBundleExportFolder(HAPIdBrick brickId) {
		return HAPUtilityFileNio.buildPath(getBundleRootPath(), brickId.getBrickTypeId().getKey(), brickId.getId());
	}
	
	private void init() {

	}

}
