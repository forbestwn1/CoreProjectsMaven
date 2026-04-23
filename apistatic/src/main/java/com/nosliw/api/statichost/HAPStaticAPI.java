package com.nosliw.api.statichost;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;


@RestController
@RequestMapping("/nosliw")
public class HAPStaticAPI {

	private static final String DOMAIN_JAVASCRIPT_INTERNAL = "data.javascript.library.internal";
	
	@PostMapping("/static")
    public String gateway(@RequestBody String requestJson) throws IOException, URISyntaxException {
		HAPServiceData out = null;
		
		HAPStaticResponse response = new HAPStaticResponse();
 
		HAPStaticRequest request = new HAPStaticRequest();
		request.buildObject(new JSONObject(requestJson), HAPSerializationFormat.JSON);
		
		for(HAPStaticInfo staticInfo : request.getStaticInfos()) {
			
			String domain = staticInfo.getDomain();
			
			if(DOMAIN_JAVASCRIPT_INTERNAL.equals(domain)) {
				if(HAPStaticInfo.STATIC_TYPE_LIBRARY.equals(staticInfo.getType())) {
					PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
					String path = getFilePathForStatic(DOMAIN_JAVASCRIPT_INTERNAL, staticInfo.getName());
					Resource[] resources = resolver.getResources("classpath:"+path+"/*"); 
					for(Resource resource : resources) {
						response.addURI(new URI("http://localhost:8080/"+getUriPathForStatic(DOMAIN_JAVASCRIPT_INTERNAL, staticInfo.getName()) + "/" + resource.getFilename()));
					}
					Collections.sort(response.getURIs());
				}
			}
		}
		
		return HAPServiceData.createSuccessData(response).toStringValue(HAPSerializationFormat.JSON);
	}
	
	private String getFilePathForStatic(String domain, String name) {
		return "static/" + this.domainToPath(domain)+"/"+name;
	}
	
	private String getUriPathForStatic(String domain, String name) {
		return this.domainToPath(domain)+"/"+name;
	}
	
	private String domainToPath(String domain) {
		String path = domain.replace(".", "/");
        return path;
	}
 	
//	private List<File> getCoreFileName(HAPJSLibraryId libraryId){
//		String path = libraryId.getName().replace(".", "/");
//		String folder = HAPSystemFolderUtility.getJSLibraryFolder() + path + (HAPUtilityBasic.isStringEmpty(libraryId.getVersion()) ? "" : "/" + libraryId.getVersion());
//		Set<File> files = HAPUtilityFile.getAllFiles(folder);
//		List<File> out = new ArrayList<File>(files);
//		//make file sorted by name
//		Collections.sort(out);
//		return out;
//	}
	
	
	
}
