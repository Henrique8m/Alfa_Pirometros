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
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.utilitary.Log;

public class UnidadeMedidaRepository {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	public List<UnidadeMedida> findAll() {
		List<UnidadeMedida> list = new ArrayList<>();	
			try {
				rs = getResultSet("SELECT * FROM alfaestoque.unidade_medida;");		
				while(rs.next())
					list.add(new UnidadeMedida(rs));
					
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
				Log.logString("UnidadeMedidaRepository", e.getMessage());
			}		
			finally {
				DB.closeResultSet(rs);
			}					
			return list;
		}

	public boolean createNewUnit(UnidadeMedida unidadeMedida) {
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO alfaestoque.unidade_medida "
					+ "(unidade)"
					+ "VALUES "
					+ "(?)"	
					);
			pst.setString(1, unidadeMedida.getUnidade());
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			if(rowsAffected > 0)
				return true;
		} catch (SQLException | NullPointerException e) {
			Log.logString("UnidadeMedidaRepository", e.getMessage());
			e.printStackTrace();
		}		
		finally {
			DB.closeConnection();
			DB.closeStatement(pst);
		}
		return false;
				
	}
	

	public boolean updateUnidade(UnidadeMedida unidade) {
		conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE alfaestoque.unidade_medida "
					+ "SET unidade = ? "
					+ "WHERE (id = ?)");
			pst.setString(1, unidade.getUnidade());
			pst.setLong(2, unidade.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted > 0)
				return true;
		}catch(DbException| SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				Log.logString("UnidadeMedidaRepository", e.getMessage());
				throw new DbException("Transaction rollback! caused by: " + e.getMessage());
			}catch(SQLException e1) {
				Log.logString("UnidadeMedidaRepository", e.getMessage());
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage() );
			}
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		return false;
	}
	
	public boolean delete(Long id) {
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE FROM alfaestoque.unidade_medida "
					+ "WHERE "
					+ "(id = " + id +")");

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				Log.logString("UnidadeMedidaRepository", e.getMessage());
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
}
