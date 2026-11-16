package com.nosliw.data.core.imp.io;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HAPConfigureDataSourceDataType {

    @Bean(name = "dataTypeDataSource")
    @ConfigurationProperties(prefix = "datasource.datatype")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }	
	
}
