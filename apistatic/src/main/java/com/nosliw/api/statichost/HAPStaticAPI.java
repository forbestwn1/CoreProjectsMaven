package com.nosliw.api.statichost;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.service.staticresource.HAPStaticRequest;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfo;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfoConfigure;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfoFolder;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfoLibrary;
import com.nosliw.core.service.staticresource.HAPStaticResponse;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfo;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfoContent;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfoData;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfoUrl;

@RestController
@RequestMapping("/nosliw/static")
public class HAPStaticAPI {

	public static final String TEMP_DOMAIN_CONSOLIDATION = "consolidation";
	
	@Autowired
	private HAPServiceParseEntity m_paserEntity;

	@Autowired
	private HAPConfigureApp m_appConfigure;
	
	@Autowired
	private HAPConfigureStatic m_configure;
	
	@Autowired
	private HAPConfigureTemporary m_temporaryConfigure;
	
	@PostMapping("/fetch")
    public String fetch(@RequestBody String requestJson)  throws IOException, URISyntaxException{
		HAPStaticResponse response = new HAPStaticResponse();
 
		HAPStaticRequest request = HAPStaticRequest.parseStaticRequest(new JSONObject(URLDecoder.decode(requestJson)), this.m_paserEntity);
		if(request.isScriptFileConsolidated()==null) {
			request.isScriptFileConsolidated(this.m_configure.getConsolidate());
		}

        //processing data first
		for(HAPStaticRequestInfo staticInfo : request.getStaticInfos()) {
			response.addItems(this.fetchData(staticInfo));
		}

		HAPStaticResponseInfoUrl cachedRespnse = null;
		if(request.isScriptFileConsolidated()) {
			cachedRespnse = isContentAvailableForTemp(TEMP_DOMAIN_CONSOLIDATION, request.getRequestId());
		}
		if(cachedRespnse!=null) {
			response.addItem(cachedRespnse);
		}
		else {
			StringBuffer content = new StringBuffer();
			for(HAPStaticRequestInfo staticInfo : request.getStaticInfos()) {
			    List<HAPStaticResponseInfo> responseItems = this.fetchScript(staticInfo, request.isScriptFileConsolidated());
				for(HAPStaticResponseInfo responseItem : responseItems) {
    			    if(responseItem.getType().equals(HAPConstantShared.STATIC_RESPONSE_TYPE_CONTENT)) {
	    				content.append(((HAPStaticResponseInfoContent)responseItem).getContent());
		    		}
			    	else {
				    	response.addItem(responseItem);
					}
				}
			}
			if(!content.isEmpty()) {
				response.addItem(this.uploadContentToTemp(content.toString(), TEMP_DOMAIN_CONSOLIDATION, request.getRequestId()));
			}
		}
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}

	private List<HAPStaticResponseInfo> fetchData(HAPStaticRequestInfo staticInfo){
		List<HAPStaticResponseInfo> out = new ArrayList<HAPStaticResponseInfo>();
		if(HAPConstantShared.STATIC_REQUEST_TYPE_CONFIGURE.equals(staticInfo.getType())) {
			HAPStaticRequestInfoConfigure staticInfoConfigure = (HAPStaticRequestInfoConfigure)staticInfo;
			String configureName = staticInfoConfigure.getName();
			if(configureName.equals("core")) {
				Map<String, String> urlData = new LinkedHashMap<String, String>();
				urlData.put("gatewayUrl", "http://localhost:8080/");
				urlData.put("staticUrl", "http://localhost:8081/");
				out.add(new HAPStaticResponseInfoData(urlData));
			}
			if(configureName.equals("scriptreproduce")) {
				Map<String, String> urlData = new LinkedHashMap<String, String>();
				urlData.put("gatewayUrl", "http://localhost:8080/");
				urlData.put("staticUrl", "http://localhost:8081/");
				out.add(new HAPStaticResponseInfoData(urlData));
            }
			else if(configureName.equals("story")) {
				Map<String, String> urlData = new LinkedHashMap<String, String>();
				urlData.put("gatewayUrl", "http://localhost:8080/");
				urlData.put("staticUrl", "http://localhost:8081/");
				urlData.put("storyUrl", "http://localhost:8083/");
				out.add(new HAPStaticResponseInfoData(urlData));
			}
		}
		return out;
	}
	
	private List<HAPStaticResponseInfo> fetchScript(HAPStaticRequestInfo staticInfo, boolean consolidate)  throws IOException, URISyntaxException{
		List<HAPStaticResponseInfo> out = new ArrayList<HAPStaticResponseInfo>();
		
		if(HAPConstantShared.STATIC_REQUEST_TYPE_LIBRARY.equals(staticInfo.getType())) {
			HAPStaticRequestInfoLibrary staticInfoLib = (HAPStaticRequestInfoLibrary)staticInfo;
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			String domain = staticInfoLib.getDomain();
			String path = "static/" + getFilePathForLibrary(domain, staticInfoLib.getName(), staticInfoLib.getVersion());
			Resource[] resources = resolver.getResources("classpath:"+path+"/*"); 

			if(consolidate) {
				for(Resource resource : resources) {
					out.add(new HAPStaticResponseInfoContent(HAPUtilityFileNio.readFile(resource.getInputStream())));
				}
			}
			else {
				for(Resource resource : resources) {
					out.add(new HAPStaticResponseInfoUrl(new URI(getUriPathForLibrary(domain, staticInfoLib.getName(), staticInfoLib.getVersion()) + "/" + resource.getFilename())));
				}
//				Collections.sort(response.getItems(), (item1, item2)->item1.getURI().toString().compareTo(item2.getURI().toString()));
			}
		}
		else if(HAPConstantShared.STATIC_REQUEST_TYPE_FOLDER.equals(staticInfo.getType())) {
			HAPStaticRequestInfoFolder staticInfoFolder = (HAPStaticRequestInfoFolder)staticInfo;
			
			Path folderPath = HAPUtilityFileNio.buildPath(this.m_temporaryConfigure.getPath(), staticInfoFolder.getFolder());
			
			for(Path childPath : HAPUtilityFileNio.getChildrenPath(folderPath)) {
				String fileName = HAPUtilityFileNio.getLastNameOfPath(childPath);
				out.add(new HAPStaticResponseInfoUrl(new URI(getUriPathForTemp(staticInfoFolder.getFolder()+"/"+fileName))));
			}
		}
		else if(HAPConstantShared.STATIC_REQUEST_TYPE_CONFIGURE.equals(staticInfo.getType())) {
			HAPStaticRequestInfoConfigure staticInfoConfigure = (HAPStaticRequestInfoConfigure)staticInfo;
			String configureName = staticInfoConfigure.getName();
			if(configureName.equals("core")) {
				out.addAll(this.fetchScript(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "core", null), consolidate));
				out.addAll(this.fetchScript(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "runtimebrowserinit", null), consolidate));
			}
			if(configureName.equals("scriptreproduce")) {
			    out.addAll(this.fetchScript(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "core", null), consolidate));
                out.addAll(this.fetchScript(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "runtimebrowserinit", null), consolidate));
            }
			else if(configureName.equals("story")) {
				out.addAll(this.fetchScript(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "core", null), consolidate));
    			out.addAll(this.fetchScript(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "runtimebrowserinit", null), consolidate));
			}
		}
		return out;
	}
	
	private String getFilePathForLibrary(String domain, String name, String version) {
		return this.domainToPath(domain)+"/" + name + (version==null?"":"/"+version);
	}
	
	private String getUriPathForLibrary(String domain, String name, String version) {
		return m_configure.getUrl() + this.domainToPath(domain)+"/" + name + (version==null?"":"/"+version);
	}

	@PostMapping("/upload")
    public String upload(@RequestBody String content, @RequestParam String domain, @RequestParam String name) throws IOException, URISyntaxException {
		HAPStaticResponse response = new HAPStaticResponse();
        HAPStaticResponseInfo responsInfo = uploadContentToTemp(content, domain, name);
        response.addItem(responsInfo);
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}

	private HAPStaticResponseInfoUrl isContentAvailableForTemp(String domain, String name)  throws IOException, URISyntaxException {
    	String path = getFilePathForTemp(domain, name);
		if(HAPUtilityFileNio.isPathExists(HAPUtilityFileNio.buildPath(path))){
			return new HAPStaticResponseInfoUrl(new URI(getUriPathForTemp(domain, name)));
		}
		else {
			return null;
		}
	}
	
	private String getUriPathForTemp(String path) {
		return this.normaliizePath(m_temporaryConfigure.getUrl()+ path);
	}

    private HAPStaticResponseInfo uploadContentToTemp(String content, String domain, String name) throws IOException, URISyntaxException {
    	String path = getFilePathForTemp(domain, name);
        HAPUtilityFileNio.writeFile(HAPUtilityFileNio.buildPath(path), content);
        HAPStaticResponseInfo out = new HAPStaticResponseInfoUrl(new URI(getUriPathForTemp(domain, name)));
        return out;
	}
	
    private String getFilePathForTemp(String domain, String name) {
		String path = this.domainToPath(domain);
    	return this.m_temporaryConfigure.getPath() + "/" + (path==null?"":(path+"/")) + this.buildName(name);
    }
    
	private String getUriPathForTemp(String domain, String name) {
		String path = this.domainToPath(domain);
		return m_temporaryConfigure.getUrl() + (path==null?"":(path+"/")) + this.buildName(name);
	}

	private String buildName(String name) {
		return HAPUtilityNamingConversion.cascadeNameSegment(name, m_appConfigure.getVersion());
	}
	
	private String domainToPath(String domain) {
		if(domain==null) {
			return null;
		}
		String path = domain.replace(".", "/");
        return path;
	}
 	
	private String normaliizePath(String path) {
		return path.replace("\\", "/");
	}
	
}
