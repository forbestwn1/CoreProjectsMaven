package com.nosliw.api.statichost;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collections;

import org.json.JSONObject;
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
import com.nosliw.common.staticc.HAPStaticRequest;
import com.nosliw.common.staticc.HAPStaticRequestInfo;
import com.nosliw.common.staticc.HAPStaticResponse;
import com.nosliw.common.staticc.HAPStaticResponseInfo;
import com.nosliw.common.utils.HAPUtilityFile;


@RestController
@RequestMapping("/nosliw")
public class HAPStaticAPI {

	private static final String DOMAIN_JAVASCRIPT_INTERNAL = "data.javascript.library.internal";
	
	@Value("${application.directory.temp}")
	private String m_tempDir;
	
	@PostMapping("/static")
    public String gateway(@RequestBody String requestJson) throws IOException, URISyntaxException {
		HAPServiceData out = null;

		HAPStaticResponse response = new HAPStaticResponse();
 
		HAPStaticRequest request = new HAPStaticRequest();
		requestJson = URLDecoder.decode(requestJson);
		request.buildObject(new JSONObject(requestJson), HAPSerializationFormat.JSON);
		
		for(HAPStaticRequestInfo staticInfo : request.getStaticInfos()) {
			
			String domain = staticInfo.getDomain();

			if(HAPStaticRequestInfo.STATIC_TYPE_LIBRARY.equals(staticInfo.getType())) {
				PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
				String path = "static/" + getFilePathForStatic(domain, staticInfo.getName(), staticInfo.getVersion());
				Resource[] resources = resolver.getResources("classpath:"+path+"/*"); 
				for(Resource resource : resources) {
					response.addItem(new HAPStaticResponseInfo(new URI(getUriPathForStatic(domain, staticInfo.getName(), staticInfo.getVersion()) + "/" + resource.getFilename())));
				}
				Collections.sort(response.getItems(), (item1, item2)->item1.getURI().toString().compareTo(item2.getURI().toString()));
			}
			
		}
		
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
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
 	
}
