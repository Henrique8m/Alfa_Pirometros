package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.utilitary.Log;

public class OSRepository {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	public boolean createNewOs(List<ProductsOs> list, String table ) {
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);			
			for(ProductsOs productOs: list) {					
				pst = conn.prepareStatement("INSERT INTO alfaestoque." + table
						+ "(id_orcamento, product_id, qtde)"
						+ "VALUES "
						+ "(?, ?, ?)");
				pst.setLong(1, productOs.getIdOrcamento());
				pst.setLong(2, productOs.getProductId());
				pst.setDouble(3, productOs.getQtde());
				pst.executeUpdate();					
				conn.commit();
			}		
			
		}catch (SQLException e) {
			Log.logString("OSRepository", e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(pst);
		}
		return true;
	}
	
	public List<ProductsOs> findAll(String table){
		List<ProductsOs> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);							
			rs = getResultSet("SELECT * FROM alfaestoque." + table);
			while(rs.next()) 	
				list.add(new ProductsOs(rs));
			
		}catch (SQLException e) {
			Log.logString("OSRepository", e.getMessage());
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return list;
	}
	
	public List<ProductsOs> findAllByOrcamentoId(Long orcamentoId, String table){
		List<ProductsOs> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);							
			rs = getResultSet("SELECT * FROM alfaestoque."+ table);
			while(rs.next()) 
				if(rs.getLong("id_orcamento") == orcamentoId)
					list.add(new ProductsOs(rs));
			
		}catch (SQLException e) {
			Log.logString("OSRepository", e.getMessage());
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return list;
	}
	
	public boolean delete(Long id, String table) {
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE  FROM alfaestoque." + table
					+ "WHERE "
					+ "(id = " + id +")");

			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if (rowsAccepted > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
			Log.logString("OSRepository", e.getMessage());
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}

		} finally {
			DB.closeStatement(pst);
			DB.closeConnection();

		}			
		return false;
	}
	
	private ResultSet getResultSet(String query) {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			return st.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;			
	}
	
	public void updateProd(String table, ProductsOs productOs) {
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);							
				pst = conn.prepareStatement("UPDATE alfaestoque." + table
						+ "SET id_orcamento = ?, "
						+ "product_id = ?, "
						+ "qtde = ? "
						+ "WHERE "
						+ "(id = ?)");
				pst.setLong(1, productOs.getIdOrcamento());
				pst.setLong(2, productOs.getProductId());
				pst.setDouble(3, productOs.getQtde());
				pst.setLong(4, productOs.getId());
				pst.executeUpdate();					
				conn.commit();					
			
		}catch (SQLException e) {
			Log.logString("OSRepository", e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(pst);
		}
	}
}
