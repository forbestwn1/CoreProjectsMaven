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

import org.json.JSONArray;
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
 
		HAPStaticRequest request = parseStaticRequest(new JSONObject(URLDecoder.decode(requestJson)));
		if(request.isScriptFileConsolidated()==null) {
			request.isScriptFileConsolidated(this.m_configure.getConsolidate());
		}

		boolean ccached = false;
		HAPStaticResponseInfoUrl cachedRespnse = isContentAvailable(TEMP_DOMAIN_CONSOLIDATION, request.getRequestId());
		if(cachedRespnse!=null) {
			response.addItem(cachedRespnse);
			ccached = true;
		}
		
		StringBuffer content = new StringBuffer();
		for(HAPStaticRequestInfo staticInfo : request.getStaticInfos()) {
			List<HAPStaticResponseInfo> responseItems = this.fetch(staticInfo, !ccached, request.isScriptFileConsolidated());
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
			response.addItem(this.uploadContent(content.toString(), TEMP_DOMAIN_CONSOLIDATION, HAPUtilityNamingConversion.cascadeNameSegment(request.getRequestId(), m_appConfigure.getVersion())));
		}

		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}

	private List<HAPStaticResponseInfo> fetch(HAPStaticRequestInfo staticInfo, boolean processScrip, boolean consolidate)  throws IOException, URISyntaxException{
		List<HAPStaticResponseInfo> out = new ArrayList<HAPStaticResponseInfo>();
		
		if(HAPConstantShared.STATIC_REQUEST_TYPE_LIBRARY.equals(staticInfo.getType())) {
			if(processScrip) {
				HAPStaticRequestInfoLibrary staticInfoLib = (HAPStaticRequestInfoLibrary)staticInfo;
				PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
				String domain = staticInfoLib.getDomain();
				String path = "static/" + getFilePathForStatic(domain, staticInfoLib.getName(), staticInfoLib.getVersion());
				Resource[] resources = resolver.getResources("classpath:"+path+"/*"); 

				if(consolidate) {
					for(Resource resource : resources) {
						out.add(new HAPStaticResponseInfoContent(HAPUtilityFileNio.readFile(resource.getInputStream())));
					}
				}
				else {
					for(Resource resource : resources) {
						out.add(new HAPStaticResponseInfoUrl(new URI(getUriPathForStatic(domain, staticInfoLib.getName(), staticInfoLib.getVersion()) + "/" + resource.getFilename())));
					}
//					Collections.sort(response.getItems(), (item1, item2)->item1.getURI().toString().compareTo(item2.getURI().toString()));
				}
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
				if(processScrip) {
					out.addAll(this.fetch(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "core", null), processScrip, consolidate));
					out.addAll(this.fetch(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "runtimebrowserinit", null), processScrip, consolidate));
				}

				Map<String, String> urlData = new LinkedHashMap<String, String>();
				urlData.put("gatewayUrl", "http://localhost:8080/");
				urlData.put("staticUrl", "http://localhost:8081/");
				out.add(new HAPStaticResponseInfoData(urlData));
			}
			if(configureName.equals("scriptreproduce")) {
				if(processScrip) {
				   out.addAll(this.fetch(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "core", null), processScrip, consolidate));
				   out.addAll(this.fetch(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "runtimebrowserinit", null), processScrip, consolidate));
				}

				Map<String, String> urlData = new LinkedHashMap<String, String>();
				urlData.put("gatewayUrl", "http://localhost:8080/");
				urlData.put("staticUrl", "http://localhost:8081/");
				out.add(new HAPStaticResponseInfoData(urlData));
            }
			else if(configureName.equals("story")) {
				if(processScrip) {
    				out.addAll(this.fetch(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "core", null), processScrip, consolidate));
	    			out.addAll(this.fetch(new HAPStaticRequestInfoLibrary(HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL, "runtimebrowserinit", null), processScrip, consolidate));
				}

				Map<String, String> urlData = new LinkedHashMap<String, String>();
				urlData.put("gatewayUrl", "http://localhost:8080/");
				urlData.put("staticUrl", "http://localhost:8081/");
				urlData.put("storyUrl", "http://localhost:8083/");
				out.add(new HAPStaticResponseInfoData(urlData));
			}
		}
		return out;
	}
	
	private String getUriPathForTemp(String path) {
		return this.normaliizePath(m_temporaryConfigure.getUrl()+ path);
	}

	private String getFilePathForStatic(String domain, String name, String version) {
		return this.domainToPath(domain)+"/" + name + (version==null?"":"/"+version);
	}
	
	private String getUriPathForStatic(String domain, String name, String version) {
		return m_configure.getUrl() + this.domainToPath(domain)+"/" + name + (version==null?"":"/"+version);
	}

	@PostMapping("/upload")
    public String upload(@RequestBody String content, @RequestParam String domain, @RequestParam String name) throws IOException, URISyntaxException {
		HAPStaticResponse response = new HAPStaticResponse();
        HAPStaticResponseInfo responsInfo = uploadContent(content, domain, name);
        response.addItem(responsInfo);
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}

	private HAPStaticResponseInfoUrl isContentAvailable(String domain, String name)  throws IOException, URISyntaxException {
    	String path = getUploadFilePath(domain, name);
		if(HAPUtilityFileNio.isPathExists(HAPUtilityFileNio.buildPath(path))){
			return new HAPStaticResponseInfoUrl(new URI(getUriPathForTemp(domain, name)));
		}
		else {
			return null;
		}
	}
	
    private HAPStaticResponseInfo uploadContent(String content, String domain, String name) throws IOException, URISyntaxException {
    	String path = getUploadFilePath(domain, name);
        HAPUtilityFileNio.writeFile(HAPUtilityFileNio.buildPath(path), content);
        HAPStaticResponseInfo out = new HAPStaticResponseInfoUrl(new URI(getUriPathForTemp(domain, name)));
        return out;
	}
	

    private String getUploadFilePath(String domain, String name) {
		String path = this.m_temporaryConfigure.getPath() +"/"+ getFilePathForTemp(domain, name);
		return path;
    }
    
    
	private HAPStaticRequest parseStaticRequest(JSONObject requestJsonObj) {
		HAPStaticRequest out = new HAPStaticRequest();
		JSONArray statiInfoArray = requestJsonObj.getJSONArray(HAPStaticRequest.STATICINFO);
        for(int i=0; i<statiInfoArray.length(); i++) {
        	HAPStaticRequestInfo requestInfo = (HAPStaticRequestInfo)this.m_paserEntity.parseEntityJSONImplicitAttribute(statiInfoArray.getJSONObject(i), HAPStaticRequestInfo.TYPE, HAPStaticRequestInfo.DOMAIN_PARSE);
        	out.addStaticInfo(requestInfo);
        }
        out.setRequestId((String)requestJsonObj.opt(HAPStaticRequest.REQUESTID));
        
        Object consolidateBooleanValue = requestJsonObj.opt(HAPStaticRequest.ISSCRIPTFILECONSOLIDATED);
        if(consolidateBooleanValue!=null) {
        	out.isScriptFileConsolidated((Boolean)consolidateBooleanValue);
        }
        
        return out;		
	}
	
	private String getFilePathForTemp(String domain, String name) {
		String path = this.domainToPath(domain);
		return path==null?"":(path+"/") + name;
	}
	
	private String getUriPathForTemp(String domain, String name) {
		String path = this.domainToPath(domain);
		return m_temporaryConfigure.getUrl() + (path==null?"":(path+"/")) + name;
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
