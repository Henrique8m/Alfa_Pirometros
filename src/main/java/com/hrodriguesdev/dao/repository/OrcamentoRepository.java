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
	
	EquipamentoRepository repositoryEquipamento = new EquipamentoRepository();
			

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
		list.sort( (a, b) -> b.getData_chegada().compareTo(a.getData_chegada()));
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
//
//	public boolean updatede(Orcamento orcamento) {
//		boolean ok = false;		
//		try {
//			conn = DB.getConnection();
//			conn.setAutoCommit(false);
//			pst = conn.prepareStatement("UPDATE tb_orcamento "
//											+ "SET Item = ?"
//											+" WHERE "
//											+"(id = ?)");
//			
//			pst.setString( 1, orcamento.getItem() );
//			pst.setLong( 2, orcamento.getId() );
//			
//			int rowsAccepted = pst.executeUpdate();
//			conn.commit();
//			if(rowsAccepted>0)
//				ok=true;
//		
//		}catch(DbException | SQLException e) {
//			e.printStackTrace();
//			try {
//				conn.rollback();
//				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage() );
//			}catch (SQLException e1) {
//				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
//			}
//			
//		}
//		finally {
//			DB.closeStatement(pst);
//			DB.closeConnection();
//			
//		}
//		return ok;
//	}
	
	public boolean updatede(Orcamento orcamento) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_orcamento "
											+ "SET Item = ?,"
											+ "data_chegada = ?,"
											+ "coletor_id = ?,"
											+ "status = ?"
											+" WHERE "
											+"(id = ?)");
			
			pst.setString( 1, orcamento.getItem() );
			pst.setDate(2, orcamento.getData_chegada());
			pst.setLong(3, orcamento.getColetor_id());
			pst.setInt(4, orcamento.getStatus());
			pst.setLong(5, orcamento.getId() );
			
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
		boolean ok = false;
		if (status == 20 || status == 7) 
			orcamento.setLaboratorio(false);			
		else
			orcamento.setLaboratorio(true);		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_orcamento "
											+ "SET status = " + status + ", "
											+ "laboratorio = ? , "
											+ "relatorio = ? , "
											+ "data_chegada = ? "
											+ " WHERE "
											+ "(id = ?)");
			if(orcamento.getRelatorio() == null ) 
				orcamento.setRelatorio("");
			
			pst.setBoolean( 1, orcamento.getLaboratorio() );
			pst.setString(2, orcamento.getRelatorio());	
			pst.setDate(3, orcamento.getData_chegada());
			pst.setLong( 4, id );
			
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
	
	public boolean updateDataSaida(Orcamento orcamento) {

		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_orcamento "
											+ "SET data_saida = ?" 
											+ " WHERE "
											+ "(id = ?)");

			pst.setDate( 1, orcamento.getData_saida());
			pst.setLong( 2, orcamento.getId() );
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0)
				return true;
		
		}catch (SQLException e) {
			System.out.println(e.getMessage());	
			return false;
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}		
		return false;

	}
	
	public boolean insertColetorAndNfe(Orcamento orcamento) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_orcamento "
											+ "SET coletor_id = ?, "
											+ "nfe = ? "
											+" WHERE "
											+"(id = ?)");
			
			pst.setLong( 1, orcamento.getColetor_id() );
			pst.setInt(2, orcamento.getNfe());
			pst.setLong(3, orcamento.getId() );
			
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
	
	public Boolean delete(Long id) {
		boolean ok = false;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE FROM tb_orcamento WHERE (id = " + id + " )");

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				ok = true;

		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
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
		return ok;
	}
	
}
