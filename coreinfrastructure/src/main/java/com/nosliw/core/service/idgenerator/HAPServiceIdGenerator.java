package com.nosliw.core.service.idgenerator;

import org.springframework.stereotype.Component;

@Component
public class HAPServiceIdGenerator {

	private static long m_index = 0;//System.currentTimeMillis();
	
	public String generateIdStr() {
		m_index++;
		return m_index + "";
	}

	public long generateIdInt() {
		m_index++;
		return this.m_index;
	}
	
}
