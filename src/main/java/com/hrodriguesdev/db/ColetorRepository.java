package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hrodriguesdev.entities.Coletor;

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
					+ "(empressaName, nomeColetor, dataHoraColeta, equipamento_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, coletor.getEmpressaName());
			pst.setString(2, coletor.getNomeColetor() );
			pst.setString(3, coletor.getDataHoraColeta());
			pst.setLong(4, coletor.getEquipamento_id() );			
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected> 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getLong(1);
					
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

}
