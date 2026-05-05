package com.nosliw.core.runtime.execute;

public interface HAPInfoRuntimeTask {

	public static final String RUNTIMETASK_TYPE_EXECUTECONVERTER = "executeConverter";
	public static final String RUNTIMETASK_TYPE_EXECUTEDATAOPERATION = "executeDataOperation";
	public static final String RUNTIMETASK_TYPE_EXECUTESCRIPTEXPRESSION = "executeScriptExpression";
	public static final String RUNTIMETASK_TYPE_LOADRESOURCES = "loadResources";
	
	
    String getTaskType();

}
