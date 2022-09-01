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
	
	public List<Orcamento> findAllLaboratorio(boolean laboratorio) {
		List<Orcamento> list = new ArrayList<>();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if( rs.getBoolean("laboratorio") == true) {
					list.add(new Orcamento(rs) );
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

	public List<Orcamento> findAllLaboratorio(List<Long> orcamento_id) {
		List<Orcamento> list = new ArrayList<>();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				for(Long id: orcamento_id)
					if(rs.getLong("id") == id ) 
						list.add( new Orcamento(rs) );	
						
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
	
	public List<Orcamento> findAllIdEquipamento(Long equipamento_id) {
		List<Orcamento> list = new ArrayList<>();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if(rs.getLong("equipamento_id") == equipamento_id)
						list.add( new Orcamento(rs) );	
						
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
	
	public List<Orcamento> findAll() {
		List<Orcamento> list = new ArrayList<>();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				list.add( new Orcamento(rs) );	
						
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
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if( rs.getLong("id") == id) {
					return new Orcamento(rs);
				}
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
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
	
	public boolean setIdItens(Orcamento orcamento) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			
			if(orcamento.getColetor_id()!= null && orcamento.getColetor_id()!= 0l) {
				pst = conn.prepareStatement("UPDATE tb_orcamento "
						+ "SET status = ?,"
						+ " eletricos = ?,"
						+ " consumo = ?,"
						+ " eletronicos = ?,"
						+ " estetico =? ,"
						+ " sinal = ?, "
						+ " Item = ?, "
						+ " cabos = ?, "
						+ "coletor_id = ?"
						+" WHERE "
						+"(id = ?)");
				
				pst.setInt(1, orcamento.getStatus());
				pst.setLong(2, orcamento.getEletricos());
				pst.setLong(3, orcamento.getConsumo());
				pst.setLong(4, orcamento.getEletronicos());
				pst.setLong(5, orcamento.getEstetico());
				pst.setLong(6, orcamento.getSinal());
				pst.setString(7, orcamento.getItem());
				pst.setLong(8, orcamento.getCabos());
				pst.setLong(9, orcamento.getColetor_id());
				pst.setLong(10, orcamento.getId());
				
			}else {
					pst = conn.prepareStatement("UPDATE tb_orcamento "
													+ "SET status = ?,"
													+ " eletricos = ?,"
													+ " consumo = ?,"
													+ " eletronicos = ?,"
													+ " estetico =? ,"
													+ " sinal = ?, "
													+ " Item = ?"
													+ " cabos = ?, "
													+" WHERE "
													+"(id = ?)");
					
					pst.setInt(1, orcamento.getStatus());
					pst.setLong(2, orcamento.getEletricos());
					pst.setLong(3, orcamento.getConsumo());
					pst.setLong(4, orcamento.getEletronicos());
					pst.setLong(5, orcamento.getEstetico());
					pst.setLong(6, orcamento.getSinal());
					pst.setString(7, orcamento.getItem());
					pst.setLong(8, orcamento.getCabos());
					pst.setLong(9, orcamento.getId());
				
			}
			
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

	public boolean updatedeStatusRelatorio(Long id, int status, Orcamento orcamento) {
		int statusEquip = orcamento.getStatus();
		boolean ok = false;
		if ( status == 4 ||	status == 5  ) {
			if ( statusEquip == 12 || statusEquip == 13)			
				status = 7;
		}		
		if (status == 7)
			orcamento.setLaboratorio(false);
		else
			orcamento.setLaboratorio(true);		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_orcamento "
											+ "SET status = " + status + ", "
											+ "laboratorio = ? , "
											+ "relatorio = ? "
											+ " WHERE "
											+ "(id = ?)");
			if(orcamento.getRelatorio() == null ) 
				orcamento.setRelatorio("");
			
			pst.setBoolean( 1, orcamento.getLaboratorio() );
			pst.setString(2, orcamento.getRelatorio());			
			pst.setLong( 3, id );
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0)
				ok=true;
		
		}catch (SQLException e) {
			ok=false;
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}		
		return ok;

	}




	
}
