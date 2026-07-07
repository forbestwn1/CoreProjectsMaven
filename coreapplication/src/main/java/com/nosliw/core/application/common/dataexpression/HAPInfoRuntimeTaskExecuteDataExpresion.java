package com.nosliw.core.application.common.dataexpression;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.core.data.HAPData;
import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;

public class HAPInfoRuntimeTaskExecuteDataExpresion implements HAPInfoRuntimeTask{

	private String m_dataExpression;
	
	private Map<String, HAPData> m_variableDatas;
	
	private Map<String, HAPData> m_constantDatas;
	
	public HAPInfoRuntimeTaskExecuteDataExpresion() {
		this.m_variableDatas = new LinkedHashMap<String, HAPData>();
		this.m_constantDatas = new LinkedHashMap<String, HAPData>();
	}
	
	public String getDataExpression() {     return this.m_dataExpression;   }
	public void setDataExpression(String dataExpression) {   this.m_dataExpression = dataExpression;     }
	
	public Map<String, HAPData> getVariableDatas(){     return this.m_variableDatas;     }
	
	public Map<String, HAPData> getConstantDatas(){    return this.m_constantDatas;       }
	
	
	@Override
	public String getTaskType() {   return HAPInfoRuntimeTask.RUNTIMETASK_TYPE_EXECUTEDATAEXPRESSION;   }

}
