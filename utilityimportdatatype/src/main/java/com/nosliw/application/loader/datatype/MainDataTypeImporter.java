package com.nosliw.application.loader.datatype;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.nosliw.application.loader.runtimejs.HAPJSImporter;
import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.data.core.imp.HAPDataAccessDataType;
import com.nosliw.data.core.imp.HAPModuleDataType;
import com.nosliw.data.core.imp.io.HAPDBSource;
import com.nosliw.data.core.imp.runtime.js.HAPModuleRuntimeJS;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
@ConfigurationPropertiesScan
public class MainDataTypeImporter {

	@Autowired
	DataSource m_dataSource;
	
	public static void main(String[] args) {
		
		new SpringApplicationBuilder(MainDataTypeImporter.class)
	    .run(args);
    }

	 @Bean
	    public CommandLineRunner standardRunner() {
	        return args -> {
	    		HAPModuleDataType dataTypeModule = new HAPModuleDataType(new HAPDBSource(m_dataSource)).init(HAPValueInfoManager.getInstance());;

	    		HAPDataTypeImporter dataTypeImporter = new HAPDataTypeImporter(new HAPDataAccessDataType(HAPValueInfoManager.getInstance(), new HAPDBSource(m_dataSource)));
	    		dataTypeImporter.loadAllDataType();
	    		dataTypeImporter.buildDataTypePictures();
	    		dataTypeImporter.buildDataTypeOperations();
	    	
	    		HAPModuleRuntimeJS runtimeJSModule = new HAPModuleRuntimeJS(dataTypeModule);

	    		HAPJSImporter jsImporter = new HAPJSImporter(runtimeJSModule.getRuntimeJSDataAccess(), runtimeJSModule.getDataTypeDataAccess());
	    		jsImporter.loadFromFolder("C:\\MyWork\\CoreProjectsMaven\\librarydatatype");
//	    		jsImporter.loadFromFolder("C:\\MyWork\\CoreProjectsMaven\\DataTypeTest");
	    		
	    		
	        };
	    }
	
//	@PostConstruct
    public void init() {
		
		HAPModuleDataType dataTypeModule = new HAPModuleDataType(new HAPDBSource(m_dataSource)).init(HAPValueInfoManager.getInstance());;

		HAPDataTypeImporter dataTypeImporter = new HAPDataTypeImporter(new HAPDataAccessDataType(HAPValueInfoManager.getInstance(), new HAPDBSource(m_dataSource)));
		dataTypeImporter.loadAllDataType();
		dataTypeImporter.buildDataTypePictures();
		dataTypeImporter.buildDataTypeOperations();
	
		HAPModuleRuntimeJS runtimeJSModule = new HAPModuleRuntimeJS(dataTypeModule);

		HAPJSImporter jsImporter = new HAPJSImporter(runtimeJSModule.getRuntimeJSDataAccess(), runtimeJSModule.getDataTypeDataAccess());
		jsImporter.loadFromFolder("C:\\MyWork\\CoreProjectsMaven\\librarydatatype");
//		jsImporter.loadFromFolder("C:\\MyWork\\CoreProjectsMaven\\DataTypeTest");
		
		
//		HAPDBAccess.getInstance().close();
	}

}
