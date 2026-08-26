package com.nosliw.api.statichost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HAPWebConfig implements WebMvcConfigurer {
	
	@Autowired
	private HAPConfigureStatic m_configure;

	@Autowired
	private HAPConfigureTemporary m_temporaryConfigure;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/temp/**")
                .addResourceLocations("file:///"+m_temporaryConfigure.getPath())
                .setCachePeriod(0);
        
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }
}
