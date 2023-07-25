package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Certificado;

public class CertificadoRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public List<Certificado> findAllByEquipamento(Long equipamento_id) {
		List<Certificado> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_certificado;");			
			
			while (rs.next())  
				if( rs.getLong("equipamento_id") == equipamento_id)
					list.add(new Certificado(rs));
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
		}

		return list;
	}
		
	public long add(Certificado certificado) {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_certificado "
					+ "(equipamento_id, date_cal, numero, ensaio_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setLong(1, certificado.getEquipamento_id());
			pst.setDate(2, certificado.getDate_cal());
			pst.setInt(3, certificado.getNumero());
			pst.setLong(4, certificado.getEnsaio_id());
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected> 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while(rs.next()) {
					return rs.getLong(1);
					
				}
				
			}
			else System.out.println("No rown affected");
		}
		catch (SQLException e) {
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

	public boolean updateEquipamento(Long certificado_id, Long equipamento_id, Date cal) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET certificado = "
											+ certificado_id + ", "
											+ "ultimaCalibDate = ? "
											+ " WHERE "
											+"(id = " + equipamento_id +")");		
	
			pst.setDate(1, cal);
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			
			if(rowsAccepted>0)
				return true;
		
		}catch (DbException | SQLException e) {
			ok=false;
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

	public boolean delete(Certificado certificado) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE FROM tb_certificado WHERE (id = "+ certificado.getId() +" )");	
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			
			if(rowsAccepted>0)
				ok=true;
		
		}catch (SQLException e) {
			ok=false;
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
	
	public boolean updateEnsaio(Long ensaio_id, Long certificado_id) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_certificado "
											+ "SET ensaio_id = "  + ensaio_id
											+ " WHERE "
											+"(id = " + certificado_id +")");		
	
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			
			if(rowsAccepted>0)
				return true;
		
		}catch (DbException | SQLException e) {
			ok=false;
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

	public Certificado findById(Long id) {	
		return find("id", id, "SELECT * FROM alfaestoque.tb_certificado;");
	}
	
	private Certificado find(String campoNomeCompare, Long numeroCompare, String query) {
		Certificado certificado = null;
		rs = getResultSet(query);		
		try {
			while(rs.next())
				if(rs.getLong(campoNomeCompare) == numeroCompare) 
					certificado = new Certificado(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
				
		return certificado;
	}	
	
	public List<Certificado> findAll() {
		List<Certificado> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_certificado;");			
			
			while (rs.next())  
				list.add(new Certificado(rs));
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
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

	
}
