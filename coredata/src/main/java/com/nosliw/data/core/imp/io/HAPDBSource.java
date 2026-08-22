package com.nosliw.data.core.imp.io;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.nosliw.common.configure.HAPConfigurableImp;

@Component
public class HAPDBSource  extends HAPConfigurableImp{

	private Connection m_connection;

	private DataSource m_dataSource;
	
	public HAPDBSource(DataSource dataSource) {
//		HAPConfigureImp configure = (HAPConfigureImp) HAPConfigureManager.getInstance().createConfigure()
//				.cloneChildConfigure("dataTypeManager.database");
//		this.setConfiguration(configure);

		this.m_dataSource = dataSource;
		setupDbConnection();
	}

	private void setupDbConnection() {
		try {
			this.m_connection = m_dataSource.getConnection();
			
//			Class.forName(this.getConfigureValue("jdbc.driver").getStringContent());
//			m_connection = DriverManager.getConnection(this.getConfigureValue("jdbc.url").getStringContent(),
//					this.getConfigureValue("username").getStringContent(),
//					this.getConfigureValue("password").getStringContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection(){
		try {
			if(this.m_connection.isClosed()) {
				this.setupDbConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.m_connection;
	}
	
	public void destroy() {
		try {
			this.m_connection.abort(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
