package com.nosliw.core.service.idgenerator;

import org.springframework.stereotype.Component;

@Component
public class HAPServiceIdGenerator {

	private static int m_index = 0;
	
	public String generateId() {
		m_index++;
		return m_index + "";
	}
	
}
