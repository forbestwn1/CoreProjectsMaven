package com.nosliw.core.resource.imp.js;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.resource.HAPResourceDataJSValue;

public abstract class HAPResourceDataJSValueImp extends HAPSerializableImp implements HAPResourceDataJSValue{

	@Override
	public String toString() {   return this.getValue();  }
}
