package com.hrodriguesdev.dao.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.utilitary.Log;

public class DB {

	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadPropertties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

// "C:\\db.properties"
	private static Properties loadPropertties() {
		try (FileInputStream fs = new FileInputStream(AlfaPirometrosApplication.URL_CONEXAO_PRODUCAO)) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			try (FileInputStream fs = new FileInputStream(AlfaPirometrosApplication.URL_CONEXAO_2)) {
				Properties props = new Properties();
				props.load(fs);
				return props;
			} catch (IOException e1) {
				try (FileInputStream fs = new FileInputStream(AlfaPirometrosApplication.URL_CONEXAO_DESENVOLVIMENTO)) {
					Properties props = new Properties();
					props.load(fs);
					return props;
				} catch (IOException e2) {
					Log.logString("DB", e.getMessage());
					throw new DbException(e1.getMessage());

				}

			}
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
