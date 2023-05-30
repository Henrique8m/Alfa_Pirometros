package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.gui.controller.EnsaioViewController;

public class EnsaioRepository {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	public Ensaios findByOrcamentoId(Long idOrcamento) {
		return find("orcamento_id", idOrcamento, "SELECT * FROM alfaestoque.tb_ensaio;");
	}	

	public Ensaios findById(Long id) {
		return find("id", id, "SELECT * FROM alfaestoque.tb_ensaio;");
	}
	
	
	public long saveEnsaio(Ensaios ensaio) {
		long id = 0;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO alfaestoque.tb_ensaio "
					+ "( equipamento_id, orcamento_id, referencia1, valor1, valor2, valor3 )"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, ensaio.getEquipamento_id());
			pst.setLong(2, ensaio.getOrcamento_id());
			pst.setString(3, ensaio.getReferencia());
			pst.setString(4, ensaio.getPrimeiro());
			pst.setString(5, ensaio.getSegundo());
			pst.setString(6, ensaio.getTerceiro());
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();			
			if(rowsAffected> 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}
			else 
				EnsaioViewController.logger.error("No rown affected");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());	
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage() );
			}catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
	
		}
		return id;	
		
	}
	
	public boolean updatedeEnsaio(Ensaios ensaio) {
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE alfaestoque.tb_ensaio "
					+ "SET referencia1 = ?,"
					+ "valor1 = ?,"
					+ "valor2 = ?,"
					+ "valor3 = ? "
					+ "WHERE (id = ?)");
			
			pst.setString(1, ensaio.getReferencia());
			pst.setString(2, ensaio.getPrimeiro());
			pst.setString(3, ensaio.getSegundo());
			pst.setString(4, ensaio.getTerceiro());
			pst.setLong(5, ensaio.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted > 0)
				return true;
		}catch(DbException| SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rollback! caused by: " + e.getMessage());
			}catch(SQLException e1) {
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
	
	public boolean isExistByOrcamentoId(Long id) {
		try {
			rs = getResultSet("SELECT * FROM alfaestoque.tb_ensaio;");			
			while(rs.next())
				if(rs.getLong("orcamento_id")==id)
					return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
	

	private Ensaios find(String campoNomeCompare, Long numeroCompare, String query) {
		Ensaios ensaio = null;
		ResultSet rs = getResultSet(query);		
		try {
			while(rs.next())
				if(rs.getLong(campoNomeCompare) == numeroCompare) 
					ensaio = new Ensaios(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}				
				
		return ensaio;
	}
	
	
	private ResultSet getResultSet(String query) {
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			
			conn = null;
			st = null;
			rs = null;
		}
		return rs;			
	}

}
