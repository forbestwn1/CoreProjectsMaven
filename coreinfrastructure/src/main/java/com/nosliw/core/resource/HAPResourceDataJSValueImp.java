package com.nosliw.core.resource;

import com.nosliw.common.serialization.HAPSerializableImp;

public abstract class HAPResourceDataJSValueImp extends HAPSerializableImp implements HAPResourceDataJSValue{

	@Override
	public String toString() {   return this.getValue();  }
}
