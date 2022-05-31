package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hrodriguesdev.entities.Orcamento;

public class OrcamentoRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Long addOrcamento(Orcamento orcamento) {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("INSERT INTO tb_orcamento "
					+ "(Item, quantidade) "
					+ "VALUES "
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, orcamento.getItem());
			pst.setInt(2, orcamento.getQuantidade());			
			int rowsAffected = pst.executeUpdate();
			
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
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);

		}
		return id;
	}

	public Orcamento getOrcamento(Long id) {	
		Orcamento orcamento = null;
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
			
			while (rs.next())  
				if( rs.getLong("id") == id) {
					orcamento = new Orcamento(rs.getString("Item"), Integer.parseInt( rs.getString("quantidade") ) );
				}
						
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
		}

		
		return orcamento;
	}

}
