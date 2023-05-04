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
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.utilitary.Geral;

public class ColetorRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Long addColetor(Coletor coletor) {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_coletor "
					+ "(empressaName, nomeColetor, orcamento_id, dateColeta, horaColeta) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, coletor.getEmpressaName());
			pst.setString(2, coletor.getNomeColetor() );
			pst.setLong(3, coletor.getOrcamento_id() );		
			pst.setDate(4, coletor.getDate());
			pst.setInt(5, coletor.getHoraColeta() );
			
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

	public void updatedeAllDate() {
		List<Coletor> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_coletor;");			
			
			while ( rs.next() ) {
				list.add(Coletor.parceColetor(rs));
			}
			
		}catch (SQLException e) {	
			e.printStackTrace();
		}

		try {
			for(Coletor coletor: list) {
				if(coletor.getDataHoraColeta() != null)
				{if(Geral.dateParceString(coletor.getDataHoraColeta() ) != null){
					
					pst = conn.prepareStatement("UPDATE tb_coletor "
													+ "SET dateColeta = ?"
													+ " WHERE "
													+ "(id = ?)");
					
					pst.setDate( 1, Geral.dateParceString(coletor.getDataHoraColeta() ) );
					pst.setLong( 2, coletor.getId() );
					pst.executeUpdate();
				}		
				}
			}
		
		
		}catch (SQLException e) {
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}		


	}
	
	public Coletor findColetor(Long coletor_id) {
		Coletor coletor = null;
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_coletor;");
			while (rs.next())  
				if( rs.getLong(1)== coletor_id)
					coletor = Coletor.parceColetor( rs );
		
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		conn = null;
		st = null;
		rs = null;
	
		
		return coletor;
	}

	public List<Coletor> findAll() {
		List<Coletor> coletor = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_coletor;");
			while (rs.next())  
				coletor.add(Coletor.parceColetor(rs));
		
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		conn = null;
		st = null;
		rs = null;
	
		
		return coletor;
	}

	public boolean update(Coletor coletor) {	
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_coletor "
											+ "SET nomeColetor = ?,"
											+ "dateColeta = ?"
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setString( 1, coletor.getNomeColetor() );
			pst.setDate(2, coletor.getDate());
			pst.setLong( 3, coletor.getId() );
			pst.executeUpdate();
			conn.commit();
			return true;
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());	
			try {
				conn.rollback();
				e.printStackTrace();
				return false;
			}catch (SQLException e1) {
				return false;
			}
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
		
		}	
		

		
	}

}
