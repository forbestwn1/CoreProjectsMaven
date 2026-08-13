package com.nosliw.common.location;

import java.nio.file.Path;

import com.nosliw.common.serialization.HAPSerializableImp;

//this object store information for path base for local resource id reference defined in component
public class HAPPathLocationBase extends HAPSerializableImp{

	private Path m_path;
	
	public HAPPathLocationBase(Path path) {
		this.m_path = path;
	}
	
	public HAPPathLocationBase() {	}

	public Path getPath() {    return this.m_path;    }

	public HAPPathLocationBase cloneLocalReferenceBase() {
		return new HAPPathLocationBase(this.m_path);
	}
	
	@Override
	protected String buildLiterate(){  return this.m_path.toString(); }

	@Override
	protected boolean buildObjectByLiterate(String literateValue){	
		this.m_path = Path.of(literateValue);
		return true;  
	}

}
