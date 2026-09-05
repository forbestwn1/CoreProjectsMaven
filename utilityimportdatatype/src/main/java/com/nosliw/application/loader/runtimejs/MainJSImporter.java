package com.nosliw.application.loader.runtimejs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.data.core.imp.HAPModuleDataType;
import com.nosliw.data.core.imp.runtime.js.HAPModuleRuntimeJS;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
@ConfigurationPropertiesScan
public class MainJSImporter {

	@Autowired
	DataSource m_dataSource;
	
	public static void main(String[] args) {
		
		new SpringApplicationBuilder(MainJSImporter.class)
	    .run(args);
    }

//	@PostConstruct
    public void init() {
		
		HAPModuleDataType dataTypeModule = new HAPModuleDataType().init(HAPValueInfoManager.getInstance());;

		HAPModuleRuntimeJS runtimeJSModule = new HAPModuleRuntimeJS(dataTypeModule);

		HAPJSImporter jsImporter = new HAPJSImporter(runtimeJSModule.getRuntimeJSDataAccess(), runtimeJSModule.getDataTypeDataAccess());
		jsImporter.loadFromFolder("C:\\MyWork\\CoreProjects\\DataType");
		jsImporter.loadFromFolder("C:\\MyWork\\CoreProjects\\DataTypeTest");

//		HAPDBAccess.getInstance().close();
	}
}
