package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Ensaios;

public class EnsaioRepository {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	public Ensaios getEnsaio(Long idOrcamento) {
		Ensaios ensaio = new Ensaios();
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_ensaio;");
			
			while(rs.next())
				if(rs.getLong("id")== idOrcamento) 
					ensaio = new Ensaios(rs);
				
					
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			
			conn = null;
			st = null;
			rs = null;
		}
		return ensaio;
	}
	
	public long saveEnsaio(Ensaios ensaio) {
		long id = 0;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO alfaestoque.tb_ensaio "
					+ "( orcamento_id, referencia1, valor1, valor2, valor3 )"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, ensaio.getOrcamento_id());
			pst.setString(2, ensaio.getReferencia());
			pst.setString(3, ensaio.getPrimeiro());
			pst.setString(4, ensaio.getSegundo());
			pst.setString(5, ensaio.getTerceiro());
			
			conn.commit();
			ResultSet rs = pst.getGeneratedKeys();
			while(rs.next())
				return rs.getLong(1);
			
			
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
	
	public boolean editEnsaio(Ensaios ensaio) {
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE alfaestoque.tb_ensaio "
					+ "SET referencia1 = ?,"
					+ "SET valor1 = ?,"
					+ "SET valor2 = ?,"
					+ "SET valor3 = ? "
					+ "WHERE (id = ?)");
			
			pst.setString(1, ensaio.getReferencia());
			pst.setString(2, ensaio.getPrimeiro());
			pst.setString(3, ensaio.getSegundo());
			pst.setString(4, ensaio.getTerceiro());
			
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
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_ensaio;");
			
			while(rs.next())
				if(rs.getLong("id")==id)
					return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		return false;
		
		
	}

}
