package com.nosliw.data.core.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.data.core.imp.io.HAPDBSource;

//runtime module for DataType
@Component
public class HAPModuleDataType {

	@Autowired
	private HAPDBSource m_dbSource;
	
	private HAPDataAccessDataType m_dataAccess;
	
	public HAPModuleDataType init(HAPValueInfoManager valueInfoManager){
		//value info
		valueInfoManager.importFromClassFolder(this.getClass());

		this.m_dataAccess = new HAPDataAccessDataType(valueInfoManager, m_dbSource);
		
		return this;
	}
	
	public HAPDataAccessDataType getDataAccess(){  return this.m_dataAccess;  }
	
}
