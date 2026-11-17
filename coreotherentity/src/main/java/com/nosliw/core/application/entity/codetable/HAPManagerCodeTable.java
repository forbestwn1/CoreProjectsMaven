package com.nosliw.core.application.entity.codetable;

import java.nio.file.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.common.codetable.HAPCodeTable;
import com.nosliw.core.application.common.codetable.HAPCodeTableId;
import com.nosliw.core.application.common.codetable.HAPConfigureCodeTable;

@Component
public class HAPManagerCodeTable {

	@Autowired
	private HAPConfigureCodeTable m_codeTableConfigure;
	
	public HAPCodeTable getCodeTable(HAPCodeTableId codeId){
		//read content
		//parse content
		Path codeTablePath = HAPUtilityFileNio.buildPath(HAPUtilityFileNio.buildPath(this.m_codeTableConfigure.getDataPath()), codeId.getId()+".json");
		return parseCodeTable(new JSONObject(HAPUtilityFileNio.readFile(codeTablePath)));
	}
	
	private HAPCodeTable parseCodeTable(JSONObject codeTableJson){
		HAPCodeTable out = new HAPCodeTable();
		out.buildObject(codeTableJson, HAPSerializationFormat.JSON);
		return out;
	}
	
}
