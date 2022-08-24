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
import com.hrodriguesdev.entities.Orcamento;

public class OrcamentoRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Long add(Orcamento orcamento) throws DbException {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_orcamento "
					+ "(equipamento_id, data_chegada, laboratorio) "
					+ "VALUES "
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setLong(1, orcamento.getEquipamento_id());
			pst.setDate(2, orcamento.getData_chegada());	
			pst.setBoolean(3, orcamento.getLaboratorio());
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected> 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getLong(1);
					
				}
				
			}
		
		}
		catch (DbException | SQLException e) {
			e.printStackTrace();
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
			DB.closeConnection();
			
		}
		return id;
	}
	
	public List<Long> findAllLaboratorio(boolean laboratorio) {
		List<Long> list = new ArrayList<>();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if( rs.getBoolean("laboratorio") == true) {
					list.add(rs.getLong("equipamento_id"));
				}
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return list;
	}

	public Orcamento getOrcamento(Long id) throws DbException {	
		Orcamento orcamento = null;
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if( rs.getLong("id") == id) {
					orcamento = new Orcamento(rs.getString("Item"), Integer.parseInt( rs.getString("quantidade") ) );
					orcamento.setId(id);
				}
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return orcamento;
	}

	public boolean updatede(Orcamento orcamento) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_orcamento "
											+ "SET Item = ?"
											+" WHERE "
											+"(id = ?)");
			
			pst.setString( 1, orcamento.getItem() );
			pst.setLong( 2, orcamento.getId() );
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage() );
			}catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
			
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		return ok;
	}

	public boolean existOrcamento(Long equipamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if( rs.getLong("equipamento_id") == equipamento_id)					
					return true;
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return false;
	}

}
