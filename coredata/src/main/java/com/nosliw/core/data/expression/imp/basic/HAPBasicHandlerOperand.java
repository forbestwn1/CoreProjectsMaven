package com.nosliw.core.data.expression.imp.basic;

public abstract class HAPBasicHandlerOperand {

	abstract public boolean processOperand(HAPBasicWrapperOperand operandWrapper, Object data);

	public void postPross(HAPBasicWrapperOperand operandWrapper, Object data){}
	
}
