package com.nosliw.core.application.entity.uitag;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.matcher.HAPMatchers;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.system.HAPSystemFolderUtility;

@Component
public class HAPManagerUITag{

	private Map<String, HAPUITagDefinitionData> m_dataTagDefs;
	private Map<String, HAPUITagDefinition> m_otherTagDefs;

	@Autowired
	private HAPDataTypeHelper m_dataTypeHelper;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	public HAPUITagDefinition getUITagDefinition(String tagId, String version) {
		if(version==null) {
			version = "1.0.0";
		}
		String fileName = getUITagFolder(tagId, version) + "definition.json";
		JSONObject jsonObj = new JSONObject(HAPUtilityFile.readFile(new File(fileName)));
		return HAPUITagUtilityDefinitionParser.parseUITagDefinition(jsonObj, tagId, version, this.m_entityParseService);
	}
	
	public HAPUITagInfo getDefaultUITagData(HAPUITageQueryData query) {
		HAPUITagInfo result = null;
		HAPUITagQueryResultSet resultSet = this.queryUITagData(query);
		List<HAPUITagQueryResult> items = resultSet.getItems();
		if(items!=null && items.size()>=1) {
			result = items.get(0).getUITagInfo();
		}
		return result;
	}
	
	public void clearCache() {
		this.m_dataTagDefs = null;
		this.m_otherTagDefs = null;
	}
	
	private String getUITagFolder(String tagId, String version) {
		return HAPSystemFolderUtility.getTagDefinitionFolder() + version + "/" + tagId +"/";
	}

	private void readAllTags() {
		this.m_dataTagDefs = new LinkedHashMap<String, HAPUITagDefinitionData>();
		this.m_otherTagDefs = new LinkedHashMap<String, HAPUITagDefinition>();
		
		for(File versionFolder : HAPUtilityFile.getChildrenFolder(HAPSystemFolderUtility.getTagDefinitionFolder())) {
			String version = versionFolder.getName();
			for(File tagFolder : HAPUtilityFile.getChildrenFolder(versionFolder)) {
				HAPUITagDefinition uiTagDef = getUITagDefinition(tagFolder.getName(), version);
//				uiTagDef.setSourceFile(file);
				String type = uiTagDef.getType();
				if(HAPConstantShared.UITAG_TYPE_DATA.equals(type)) {
					this.m_dataTagDefs.put(uiTagDef.getName(), (HAPUITagDefinitionData)uiTagDef);
				}
				else {
					this.m_otherTagDefs.put(uiTagDef.getName(), uiTagDef);
				}
			}
		}
	}
	
	public HAPUITagQueryResultSet queryUITagData(HAPUITageQueryData query) {
		if(this.m_dataTagDefs==null) {
			this.readAllTags();
		}
		
		List<HAPUITagCandidate> candidates = new ArrayList<HAPUITagCandidate>();
		HAPDataTypeCriteria queryDataTypeCriteria = query.getDataTypeCriterai();
		for(String name : this.m_dataTagDefs.keySet()) {
			HAPUITagDefinitionData uiTagDef = this.m_dataTagDefs.get(name);
			List<Pair<String, HAPDataTypeCriteria>> tagsDataTypeCriteria = this.getDataTypeCriteriaForUITagData(uiTagDef);
			for(Pair<String, HAPDataTypeCriteria> tagDataTypeCriteria : tagsDataTypeCriteria) {
				HAPMatchers matchers = this.m_dataTypeHelper.convertable(queryDataTypeCriteria, tagDataTypeCriteria.getRight());
				if(matchers!=null) {
					double score = matchers.getScore();
					if(score>0) {
						candidates.add(new HAPUITagCandidate(uiTagDef, tagDataTypeCriteria.getLeft(), score, matchers));
					}
				}
			}
		}
		HAPUITagCandidate[] candiateArray = candidates.toArray(new HAPUITagCandidate[0]);
		Arrays.sort(candiateArray, new Comparator<HAPUITagCandidate>() {
			@Override
			public int compare(HAPUITagCandidate arg0, HAPUITagCandidate arg1) {
				if(arg0.getScore()>arg1.getScore()) {
					return -1;
				}
				if(arg0.getScore()<arg1.getScore()) {
					return 1;
				}
				return 0;
			}
		});

		HAPUITagQueryResultSet out = new HAPUITagQueryResultSet();
		for(HAPUITagCandidate candidate : candiateArray) {
			HAPUITagInfo result = new HAPUITagInfo(candidate.getUITagDef());
			result.setAttributeForData(candidate.getDataAttributeName());
			result.addMatchers("internal_data", candidate.getMatchers());
			HAPUITagQueryResult resultInfo = new HAPUITagQueryResult(result, candidate.getScore());
			out.addItem(resultInfo);
		}
		
		return out;
	}

	public String getUITagDefinitionContent(String tagId) {
		String fileName = HAPSystemFolderUtility.getTagDefinitionFolder() + tagId + "/definition.json";
		File file = new File(fileName);
		return HAPUtilityFile.readFile(file);
	}
	
	private List<Pair<String, HAPDataTypeCriteria>> getDataTypeCriteriaForUITagData(HAPUITagDefinitionData uiTagDef) {
		List<Pair<String, HAPDataTypeCriteria>> out = new ArrayList<Pair<String, HAPDataTypeCriteria>>();
		List<String> attrNames = uiTagDef.getAttributeForData();
		for(String attrName : attrNames) {
			HAPUITagDefinitionAttributeVariable attrDef = (HAPUITagDefinitionAttributeVariable)uiTagDef.getAttributeDefition(attrName);
			out.add(Pair.of(attrName, attrDef.getDataDefinition().getCriteria()));
		}
		return out;
	}
	
	class HAPUITagCandidate{
		
		private HAPUITagDefinition m_uiTagDef;
		
		private String m_dataAttrName;
		
		private double m_score;
		
		private HAPMatchers m_matchers;
		
		public HAPUITagCandidate(HAPUITagDefinition uiTagDef, String dataAttrName, double score, HAPMatchers matchers) {
			this.m_uiTagDef = uiTagDef;
			this.m_dataAttrName = dataAttrName;
			this.m_score = score;
			this.m_matchers = matchers;
		}
		
		public HAPUITagDefinition getUITagDef() {		return this.m_uiTagDef;		}
		public String getDataAttributeName() {     return this.m_dataAttrName;       }
		public double getScore() {   return this.m_score;    }
		public HAPMatchers getMatchers() {    return this.m_matchers;    }
	}
}
