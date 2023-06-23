package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.utilitary.Log;

@SuppressWarnings("unused")
public class RepositoryProducts {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	
	public List<String> findAllNameProducts() {
		List<String> list = new ArrayList<>();		
		rs = DB.getResultSet("SELECT * FROM alfaestoque.product;");		
			try {
				while(rs.next())
					list.add(rs.getString("name"));
					
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
				Log.logString("RepositoryProducts", e.getMessage());
			}		
			finally {
				DB.closeResultSet(rs);
			}
					
			return list;
		}


	public List<Product> findAllProducts() {
		List<Product> list = new ArrayList<>();		
		rs = DB.getResultSet("SELECT * FROM alfaestoque.product;");		
			try {
				while(rs.next())
					list.add(new Product(rs));
					
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
				Log.logString("RepositoryProducts", e.getMessage());
			}		
			finally {
				DB.closeResultSet(rs);
			}
					
			return list;
		}
	
}
