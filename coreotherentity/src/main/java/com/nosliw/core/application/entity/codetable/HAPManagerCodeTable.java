package com.nosliw.core.application.entity.codetable;

import java.nio.file.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFileNio;

@Component
public class HAPManagerCodeTable {

	@Autowired
	private HAPCodeTableConfigure m_codeTableConfigure;
	
	public HAPCodeTable getCodeTable(HAPCodeTableId codeId){
		//read content
		//parse content
		Path codeTablePath = HAPUtilityFileNio.buildPath(HAPUtilityFileNio.buildPath(this.m_codeTableConfigure.getPath()), codeId.getId()+".res");
		return parseCodeTable(new JSONObject(HAPUtilityFileNio.readFile(codeTablePath)));
	}
	
	private HAPCodeTable parseCodeTable(JSONObject codeTableJson){
		HAPCodeTable out = new HAPCodeTable();
		out.buildObject(codeTableJson, HAPSerializationFormat.JSON);
		return out;
	}
	
}
