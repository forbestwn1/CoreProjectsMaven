package com.nosliw.core.application.resource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPDomainValueStructure;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.system.HAPSystem;

public class HAPUtilityExport {

	private static final String SESSIONID = "sessionId";
	
	public static void exportBundle(HAPResourceIdSimple resourceId, HAPBundleForBrick bundle, Path exportRootPath) {
		exportBundle(resourceId, bundle, HAPSystem.id, exportRootPath);
	}
	
	public static void exportBundle(HAPResourceIdSimple resourceId, HAPBundleForBrick bundle, String sessionId, Path exportRootPath) {
		Path mainFolderSession = getResourceFolder(getRootFolderSession(exportRootPath, sessionId), resourceId);
		exportBundleToFolder(bundle, mainFolderSession);
		
		Path rootFolderTmp = getRootFolderTemp(sessionId, exportRootPath);
		Path mailFolderTemp = getResourceFolder(rootFolderTmp, resourceId);
		exportBundleToFolder(bundle, mailFolderTemp);
	}
	
	private static Path getResourceFolder(Path baseFolder, HAPResourceIdSimple resourceId) {
		return HAPUtilityFileNio.buildPath(baseFolder, resourceId.toStringValue(HAPSerializationFormat.LITERATE).replaceAll("[^a-zA-Z0-9-_\\.]", "_"));
	}

	private static Path getRootFolderSession(Path exportRootPath, String sessionId){  
		return HAPUtilityFileNio.buildPath(exportRootPath, sessionId);
	}

	private static Path getRootFolderTemp(String sessionId, Path exportRootPath){
		
		Path tempFolder = HAPUtilityFileNio.buildPath(exportRootPath, "temp");
		Path infoFilePath = HAPUtilityFileNio.buildPath(tempFolder, "info.properties");
		
		String si = null; 
		try {
			Properties prop = new Properties();
			InputStream inputStream = Files.newInputStream(infoFilePath);
			prop.load(inputStream);
			si = prop.getProperty(SESSIONID);
			inputStream.close();
		}
		catch(Exception e) {
//			e.printStackTrace();
		}

		Path out = null;
		if(!sessionId.equals(si)) {
			HAPUtilityFileNio.deletePath(tempFolder);
			out = HAPUtilityFileNio.getOrCreateFolder(tempFolder);
			try {
				Properties prop = new Properties();
				prop.setProperty(SESSIONID, sessionId);
				prop.store(Files.newOutputStream(infoFilePath), null);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			out = tempFolder;
		}
		return out;
	}
	
	private static void exportBundleToFolder(HAPBundleForBrick bundle, Path bundleFolder) {
		HAPUtilityFileNio.deletePath(bundleFolder);
		
		//write value structure domain
		HAPDomainValueStructure valueStructureDomain = bundle.getValueStructureDomain();
		if(valueStructureDomain!=null) {
			HAPUtilityFileNio.writeJsonFile(bundleFolder, "valuestructure.json", valueStructureDomain.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		} else {
			HAPUtilityFileNio.writeJsonFile(bundleFolder, "valuestructure.json", "");
		}

		//bundle infor
		Map<String, String> bundleJsonMap = new LinkedHashMap<String, String>();
		bundleJsonMap.put(HAPBundleForBrick.DYNAMIC, bundle.getDynamicInfo().toStringValue(HAPSerializationFormat.JSON));
		bundleJsonMap.put(HAPBundleForBrick.ALIASMAPPING, HAPManagerSerialize.getInstance().toStringValue(bundle.getAliasMappings(), HAPSerializationFormat.JSON));
		bundleJsonMap.put(HAPBundleForBrick.EXPORTEVENT, HAPManagerSerialize.getInstance().toStringValue(bundle.getExportEvents(), HAPSerializationFormat.JSON));
		bundleJsonMap.put(HAPBundleForBrick.EXPORTCOMMAND, HAPManagerSerialize.getInstance().toStringValue(bundle.getCommandExorts(), HAPSerializationFormat.JSON));
		HAPUtilityFileNio.writeJsonFile(bundleFolder, "bundle.json", HAPUtilityJson.buildMapJson(bundleJsonMap));
		
		//write package definition
		HAPUtilityFileNio.writeJsonFile(bundleFolder, "extra.json", HAPManagerSerialize.getInstance().toStringValue(bundle.getExtraData(), HAPSerializationFormat.JSON));
		
		//write main executable
		HAPUtilityFileNio.writeJsonFile(bundleFolder, "executable.json", HAPManagerSerialize.getInstance().toStringValue(bundle.getMainBrickWrapper(), HAPSerializationFormat.JAVASCRIPT));

		//write branch executable
		HAPUtilityFileNio.writeJsonFile(bundleFolder, "branches.json", HAPManagerSerialize.getInstance().toStringValue(bundle.getBranchBrickWrappers(), HAPSerializationFormat.JAVASCRIPT));

		//external complex entity dependency
		Set<HAPResourceIdSimple> dependency = bundle.getResourceDependency();
		List<String> dependencyArray = new ArrayList<String>();
		for(HAPResourceIdSimple dependencyId : dependency) {
			dependencyArray.add(dependencyId.toStringValue(HAPSerializationFormat.LITERATE));
		}
		HAPUtilityFileNio.writeJsonFile(bundleFolder, "dependency.json", HAPUtilityJson.buildArrayJson(dependencyArray.toArray(new String[0])));
	}

/*	
	public static void exportEntityPackage(HAPApplicationPackage executablePackage, HAPManagerApplicationBrick entityManager, HAPRuntimeInfo runtimeInfo) {
		String mainFolderUnique = getRootFolderUnique();
		exportExecutablePackage(executablePackage, mainFolderUnique, entityManager, runtimeInfo);

		String mainFolderTemp = getRootFolderTemp();
		exportExecutablePackage(executablePackage, mainFolderTemp, entityManager, runtimeInfo);
	}
	
	private static void exportExecutablePackage(HAPApplicationPackage executablePackage, String mainFolder, HAPManagerApplicationBrick entityManager, HAPRuntimeInfo runtimeInfo) {
		HAPUtilityFile.deleteFolder(mainFolder);
		
		//writer main info
		Map<String, String> mainInfoJson = new LinkedHashMap<String, String>();
		mainInfoJson.put(HAPExecutablePackage.MAINENTITYREF, executablePackage.getMainResourceId().toStringValue(HAPSerializationFormat.JSON));
		HAPUtilityFile.writeJsonFile(mainFolder, "mainInfo.json", HAPUtilityJson.buildMapJson(mainInfoJson));
		
		//write package group
		String packageGroupFolder = getExecutablePackageGroupFolder(mainFolder);
		
		Set<HAPResourceId> resourceIds = new HashSet<HAPResourceId>();
		resourceIds.add(executablePackage.getMainResourceId());
		resourceIds.addAll(executablePackage.getDependency());
		for(HAPResourceId resourceId : resourceIds) {
			HAPResourceIdSimple rootResourceId = null;
			String structure = resourceId.getStructure();
			if(structure.equals(HAPConstantShared.RESOURCEID_TYPE_SIMPLE)) {
				rootResourceId = (HAPResourceIdSimple)resourceId;
			} else if(structure.equals(HAPConstantShared.RESOURCEID_TYPE_EMBEDED)) {
				rootResourceId = ((HAPResourceIdEmbeded)resourceId).getParentResourceId();
			}
			
			HAPBundle bundle = HAPUtilityBrick.getBrickBundle(rootResourceId, entityManager); 
			String packageFolder = getExecutablePackageFolder(packageGroupFolder, resourceId);
			
			//write attachment domain
//			HAPDomainAttachment attachmentDomain = bundle.getAttachmentDomain();
//			HAPUtilityFile.writeJsonFile(packageFolder, "attachment.json", attachmentDomain.toStringValue(HAPSerializationFormat.JSON));
			
			//write value structure domain
			HAPDomainValueStructure valueStructureDomain = bundle.getValueStructureDomain();
			if(valueStructureDomain!=null) {
				HAPUtilityFile.writeJsonFile(packageFolder, "valuestructure.json", valueStructureDomain.toStringValue(HAPSerializationFormat.JAVASCRIPT));
			} else {
				HAPUtilityFile.writeJsonFile(packageFolder, "valuestructure.json", "");
			}

			//write package definition
			HAPUtilityFile.writeJsonFile(packageFolder, "extra.json", HAPManagerSerialize.getInstance().toStringValue(bundle.getExtraData(), HAPSerializationFormat.JSON));
			
			//write package executable
			HAPUtilityFile.writeJsonFile(packageFolder, "executable.json", HAPManagerSerialize.getInstance().toStringValue(bundle.getBrickWrapper(), HAPSerializationFormat.JAVASCRIPT));
			
			//external complex entity dependency
			Set<HAPResourceIdSimple> dependency = bundle.getResourceDependency();
			List<String> dependencyArray = new ArrayList<String>();
			for(HAPResourceIdSimple dependencyId : dependency) {
				dependencyArray.add(dependencyId.toStringValue(HAPSerializationFormat.LITERATE));
			}
			HAPUtilityFile.writeJsonFile(packageFolder, "dependency.json", HAPUtilityJson.buildArrayJson(dependencyArray.toArray(new String[0])));
		}
	}
	
	
	private static String getRootFolderUnique(){  
		return HAPUtilityFile.getValidFolder(HAPUtilityFile.buildFullFolderPath(HAPSystemFolderUtility.getExecutablePackageExportFolder(), System.currentTimeMillis()+""));  
	}

	private static String getRootFolderTemp(){  
		return HAPUtilityFile.getValidFolder(HAPUtilityFile.buildFullFolderPath(HAPSystemFolderUtility.getExecutablePackageExportFolder(), "temp"));  
	}

	private static String getExecutablePackageGroupFolder(String parentFolder){   return HAPUtilityFile.getValidFolder(HAPUtilityFile.buildFullFolderPath(parentFolder, "resourcebundles"));  }

	private static String getExecutablePackageFolder(String parentFolder, HAPResourceId resourceId){   
		return HAPUtilityFile.getValidFolder(HAPUtilityFile.buildFullFolderPath(parentFolder, resourceId.toStringValue(HAPSerializationFormat.LITERATE)));  
	}
	
	private static String toExpandedJsonStringDefintionDomain(HAPExecutableBundle resourceBundle) {
		HAPDomainEntityDefinitionGlobal definitionDomainGlobal = resourceBundle.getDefinitionDomain();
		return definitionDomainGlobal.getEntityInfoDefinition(resourceBundle.getDefinitionRootEntityId()).toExpandedJsonString(definitionDomainGlobal);
	}

	private static String toExpandedJsonStringExecutableDomain(HAPExecutableBundle resourceBundle) {
		HAPDomainEntityExecutableResourceComplex executableDomain = resourceBundle.getExecutableDomain();
		return executableDomain.getRootEntity().toExpandedJsonString(executableDomain);		
	}

	private static String toResourceJsonStringExecutableDomain(HAPExecutableBundle resourceBundle, HAPRuntimeInfo runtimeInfo) {
		HAPDomainEntityExecutableResourceComplex executableDomain = resourceBundle.getExecutableDomain();
		return executableDomain.getRootEntity().toResourceData(runtimeInfo).toString();
	}
*/	
}
