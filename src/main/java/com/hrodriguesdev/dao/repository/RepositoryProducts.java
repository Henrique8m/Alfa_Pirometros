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
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.utilitary.Log;

public class RepositoryProducts {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	
	public List<String> findAllNameProducts() {
		List<String> list = new ArrayList<>();		
		rs = getResultSet("SELECT * FROM alfaestoque.product;");		
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
		rs = getResultSet("SELECT * FROM alfaestoque.product;");		
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


	public boolean createdNew(Product product) {
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO alfaestoque.product "
					+ "(name, descricao, valor_pago, valor_venda, unidade_medida) "
					+ "VALUES "
					+ "(?,?,?,?,?)");
			pst.setString(1, product.getName());
			pst.setString(2, product.getDescricao());
			pst.setDouble(3, product.getValor_pago());
			pst.setDouble(4, product.getValor_venda());
			pst.setLong(6, product.getUnidade_medida());
			
			int rownsAffected = pst.executeUpdate();
			conn.commit();
			if(rownsAffected > 0)
				return true;
			else
				return false;
		}catch(SQLException e) {
			Log.logString("RepositoryProducts", e.getMessage());
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rollback! caused by: " + e.getMessage());
			} catch (SQLException e1) {
				Log.logString("RepositoryProducts - Rollback", e1.getMessage());
				e1.printStackTrace();
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage() );
			}
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(pst);
		}
	}


	public boolean update(Product product) {
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE alfaestoque.product SET "
					+ "name = ?, "
					+ "descricao = ?, "
					+ "valor_pago = ?, "
					+ "valor_venda = ?, "
					+ "unidade_medida = ? "
					+ "WHERE "
					+ "(id = ?)");
			pst.setString(1, product.getName());
			pst.setString(2, product.getDescricao());
			pst.setDouble(3, product.getValor_pago());
			pst.setDouble(4, product.getValor_venda());
			pst.setLong(6, product.getUnidade_medida());
			pst.setLong(7, product.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted > 0)
				return true;
			else 
				return false;
		}catch(DbException| SQLException e) {
			e.printStackTrace();
			Log.logString("RepositoryProducts", e.getMessage());
			try {
				conn.rollback();
				Log.logString("RepositoryProducts", e.getMessage());
				throw new DbException("Transaction rollback! caused by: " + e.getMessage());
			}catch(SQLException e1) {
				Log.logString("RepositoryProducts", e.getMessage());
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage() );
			}
		}			
		finally {
			DB.closeConnection();
			DB.closeStatement(pst);
		}
	}


	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
