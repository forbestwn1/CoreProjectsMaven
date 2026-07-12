package com.nosliw.api.statichost;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.service.staticresource.HAPStaticRequest;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfo;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfoFolder;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfoLibrary;
import com.nosliw.core.service.staticresource.HAPStaticResponse;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfo;


@RestController
@RequestMapping("/nosliw")
public class HAPStaticAPI {

	private static final String DOMAIN_JAVASCRIPT_INTERNAL = "data.javascript.library.internal";
	
	@Autowired
	private HAPServiceParseEntity m_paserEntity;
	
	@Value("${application.directory.temp}")
	private String m_tempDir;
	
	@PostMapping("/static")
    public String gateway(@RequestBody String requestJson) throws IOException, URISyntaxException {
		HAPServiceData out = null;

		HAPStaticResponse response = new HAPStaticResponse();
 
		HAPStaticRequest request = parseStaticRequest(new JSONObject(URLDecoder.decode(requestJson)));
		
		for(HAPStaticRequestInfo staticInfo : request.getStaticInfos()) {
			if(HAPStaticRequestInfoLibrary.STATIC_TYPE_LIBRARY.equals(staticInfo.getType())) {
				HAPStaticRequestInfoLibrary staticInfoLib = (HAPStaticRequestInfoLibrary)staticInfo;
				PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
				String domain = staticInfoLib.getDomain();
				String path = "static/" + getFilePathForStatic(domain, staticInfoLib.getName(), staticInfoLib.getVersion());
				Resource[] resources = resolver.getResources("classpath:"+path+"/*"); 
				for(Resource resource : resources) {
					response.addItem(new HAPStaticResponseInfo(new URI(getUriPathForStatic(domain, staticInfoLib.getName(), staticInfoLib.getVersion()) + "/" + resource.getFilename())));
				}
				Collections.sort(response.getItems(), (item1, item2)->item1.getURI().toString().compareTo(item2.getURI().toString()));
			}
			else if(HAPStaticRequestInfoLibrary.STATIC_TYPE_FOLDER.equals(staticInfo.getType())) {
				HAPStaticRequestInfoFolder staticInfoFolder = (HAPStaticRequestInfoFolder)staticInfo;
				
				for(File childFile : HAPUtilityFile.getChildren(staticInfoFolder.getFolder())) {
					String folderPath = childFile.getAbsolutePath();
					String relativePath = folderPath.substring(m_tempDir.length());
					response.addItem(new HAPStaticResponseInfo(new URI(getUriPathForTemp(relativePath))));
				}
			}
		}
		
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}
	
	private String getUriPathForTemp(String path) {
		return this.normaliizePath("http://localhost:8081/temp/"+ path);
	}

	private HAPStaticRequest parseStaticRequest(JSONObject requestJsonObj) {
		HAPStaticRequest out = new HAPStaticRequest();
		JSONArray statiInfoArray = requestJsonObj.getJSONArray(HAPStaticRequest.STATICINFO);
        for(int i=0; i<statiInfoArray.length(); i++) {
        	HAPStaticRequestInfo requestInfo = (HAPStaticRequestInfo)this.m_paserEntity.parseEntityJSONImplicitAttribute(statiInfoArray.getJSONObject(i), HAPStaticRequestInfo.TYPE, HAPStaticRequestInfo.DOMAIN_PARSE);
        	out.addStaticInfo(requestInfo);
        }
        return out;		
	}
	
	
	private String getFilePathForStatic(String domain, String name, String version) {
		return this.domainToPath(domain)+"/" + name + (version==null?"":"/"+version);
	}
	
	private String getUriPathForStatic(String domain, String name, String version) {
		return "http://localhost:8081/static/"+this.domainToPath(domain)+"/" + name + (version==null?"":"/"+version);
	}

	
	@PostMapping("/upload")
    public String upload(@RequestBody String content, @RequestParam String domain, @RequestParam String name) throws IOException, URISyntaxException {
		HAPStaticResponse response = new HAPStaticResponse();

		String path = m_tempDir + "/" + getFilePathForTemp(domain, name);
        HAPUtilityFile.writeFile(path, content);
		
        HAPStaticResponseInfo responsInfo = new HAPStaticResponseInfo(new URI(getUriPathForTemp(domain, name)));
        response.addItem(responsInfo);
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}
	
	private String getFilePathForTemp(String domain, String name) {
		String path = this.domainToPath(domain);
		return path==null?"":(path+"/") + name;
	}
	
	private String getUriPathForTemp(String domain, String name) {
		String path = this.domainToPath(domain);
		return "http://localhost:8081/temp/"+ (path==null?"":(path+"/")) + name;
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
