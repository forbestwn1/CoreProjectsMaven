package com.nosliw.core.data.expression;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.core.data.HAPData;
import com.nosliw.core.runtime.execute.HAPInfoRuntimeTask;

public class HAPInfoRuntimeTaskExecuteDataExpresion implements HAPInfoRuntimeTask{

	private HAPExpressionData m_dataExpression;
	
	private Map<String, HAPData> m_variableDatas;
	
	private Map<String, HAPData> m_constantDatas;
	
	public HAPInfoRuntimeTaskExecuteDataExpresion() {
		this.m_variableDatas = new LinkedHashMap<String, HAPData>();
		this.m_constantDatas = new LinkedHashMap<String, HAPData>();
	}
	
	public HAPExpressionData getDataExpression() {     return this.m_dataExpression;   }
	public void setDataExpression(HAPExpressionData dataExpression) {   this.m_dataExpression = dataExpression;     }
	
	public Map<String, HAPData> getVariableDatas(){     return this.m_variableDatas;     }
	public void addVariableData(String name, HAPData varData) {    this.m_variableDatas.put(name, varData);     }
	
	public Map<String, HAPData> getConstantDatas(){    return this.m_constantDatas;       }
	
	
	@Override
	public String getTaskType() {   return HAPInfoRuntimeTask.RUNTIMETASK_TYPE_EXECUTEDATAEXPRESSION;   }

}
