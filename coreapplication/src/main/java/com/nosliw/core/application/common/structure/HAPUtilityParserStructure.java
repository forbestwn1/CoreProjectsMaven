package com.nosliw.core.application.common.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public class HAPUtilityParserStructure {

	static public HAPValueContextDefinitionImp parseValueContext(Object obj, HAPServiceParseEntity entityParseService) {
		HAPValueContextDefinitionImp valueContext = new HAPValueContextDefinitionImp();
		
		JSONArray valueStructuresArray = null;
		if(obj instanceof JSONObject) {
			valueStructuresArray = ((JSONObject)obj).optJSONArray(HAPValueContextDefinition.ITEM);
		}
		else if(obj instanceof JSONArray) {
			valueStructuresArray = (JSONArray)obj;
		}
		
		if(valueStructuresArray!=null) {
			for(int i=0; i<valueStructuresArray.length(); i++) {
				JSONObject valueStructureWrapperObj = valueStructuresArray.getJSONObject(i);
				HAPWrapperValueStructureDefinitionImp valueStructureWrapper = HAPUtilityParserStructure.parseValueStructureWrapper(valueStructureWrapperObj, entityParseService); 
				valueContext.getValueStructures().add(valueStructureWrapper);
			}
		}
		return valueContext;
	}

	static public HAPWrapperValueStructureDefinitionImp parseValueStructureWrapper(JSONObject wrapperObj, HAPServiceParseEntity entityParseService) {
		HAPWrapperValueStructureDefinitionImp valueStructureWrapper = new HAPWrapperValueStructureDefinitionImp();
		
		JSONObject vsInfoJsonObj = wrapperObj.optJSONObject(HAPWrapperValueStructureDefinition.VALUESTRUCTUREINFO);
		if(vsInfoJsonObj==null) {
			vsInfoJsonObj = wrapperObj;
		}
		HAPUtilityParserStructure.parseValueStructureWrapperOtherData(valueStructureWrapper, vsInfoJsonObj);
		
		HAPValueStructure valueStructure = new HAPValueStructureImp();
		HAPUtilityParserStructure.parseValueStructureJson(wrapperObj.getJSONObject(HAPWrapperValueStructureDefinition.VALUESTRUCTURE), valueStructure, entityParseService);
		valueStructureWrapper.setValueStructure(valueStructure);
		return valueStructureWrapper;
	}
	
	static public void parseValueStructureWrapperOtherData(HAPWrapperValueStructureDefinition valueStructureWrapper, JSONObject wrapperObj) {
		HAPUtilityEntityInfo.buildEntityInfoByJson(wrapperObj, valueStructureWrapper);

		HAPInfoStructureInWrapper structureInfo = new HAPInfoStructureInWrapper();
		
		String scope = (String)wrapperObj.opt(HAPInfoStructureInWrapper.SCOPE);
		if(scope==null) {
			scope = HAPUtilityScope.DEFAULT_SCOPE;
		}
		structureInfo.setScope(scope);

		String inheritMode = (String)wrapperObj.opt(HAPInfoStructureInWrapper.INHERITMODE);
		if(inheritMode!=null) {
			structureInfo.setInheritMode(inheritMode);
		}
		valueStructureWrapper.setStructureInfo(structureInfo);
	}
	
	static public void parseValueStructureJson(Object structureJsonObj, HAPValueStructure valueStructure, HAPServiceParseEntity entityParseService) {
		if(structureJsonObj!=null) {
			if(structureJsonObj instanceof JSONObject) {
				JSONObject structureJson = (JSONObject)structureJsonObj;
				if(structureJson.opt(HAPStructure.ROOT)!=null) {
					valueStructure.setInitValue(structureJson.opt(HAPValueStructure.INITVALUE));
				}
			}
			parseStuctureJson(structureJsonObj, valueStructure, entityParseService);
	    }
	}
	
	static public void parseStuctureJson(Object structureJsonObj, HAPStructure structure, HAPServiceParseEntity entityParseService) {
		if(structureJsonObj!=null) {
			Object rootsObj = null;
			if(structureJsonObj instanceof JSONObject) {
				JSONObject structureJson = (JSONObject)structureJsonObj;
				rootsObj = structureJson.opt(HAPStructure.ROOT);
				if(rootsObj==null) {
					rootsObj = structureJson;
				}
			}
			else if(structureJsonObj instanceof JSONArray) {
				rootsObj = structureJsonObj;
			}

			List<HAPRootInStructure> roots = parseStructureRoots(rootsObj, entityParseService);
			for(HAPRootInStructure root : roots) {
				structure.addRoot(root);
			}
		}
	}
	
	static private List<HAPRootInStructure> parseStructureRoots(Object rootsObj, HAPServiceParseEntity entityParseService){
		List<HAPRootInStructure> out = new ArrayList<HAPRootInStructure>();
		if(rootsObj instanceof JSONObject) {
			JSONObject elementsJson = (JSONObject)rootsObj;
			Iterator<String> it = elementsJson.keys();
			while(it.hasNext()){
				String eleName = it.next();
				JSONObject eleDefJson = elementsJson.optJSONObject(eleName);
				HAPRootInStructure root = parseStructureRootFromJson(eleDefJson, entityParseService);
				if(root!=null) {
					root.setName(eleName);
					out.add(root);
				}
			}
		}
		else if(rootsObj instanceof JSONArray) {
			JSONArray elementsArray = (JSONArray)rootsObj;
			for(int i=0; i<elementsArray.length(); i++) {
				JSONObject eleDefJson = elementsArray.getJSONObject(i);
				HAPRootInStructure root = parseStructureRootFromJson(eleDefJson, entityParseService);
				out.add(root);
			}
		}
		return out;
	}
	
	//parse context root
	static private HAPRootInStructure parseStructureRootFromJson(JSONObject eleDefJson, HAPServiceParseEntity entityParseService){
		HAPRootInStructure out = new HAPRootInStructure();

		//info
		out.buildEntityInfoByJson(eleDefJson);
		if(!HAPUtilityEntityInfo.isEnabled(out)) {
			return null;
		}

		//definition
		JSONObject defJsonObj = eleDefJson.optJSONObject(HAPRootInStructure.DEFINITION);
		if(defJsonObj!=null) {
			out.setDefinition(HAPUtilityParserElement.parseStructureElement(defJsonObj, entityParseService));
		} else{
			//if no definition, then treat it as data leaf
			out.setDefinition(new HAPElementStructureLeafData());
		}
		return out;
	}
}
