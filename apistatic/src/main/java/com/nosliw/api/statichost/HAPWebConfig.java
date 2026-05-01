package com.nosliw.api.statichost;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HAPWebConfig implements WebMvcConfigurer {
	
	@Value("${application.directory.temp}")
	private String m_tempDir;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/temp/**")
                .addResourceLocations("file:///"+m_tempDir)
                .setCachePeriod(0);
        
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }
}
