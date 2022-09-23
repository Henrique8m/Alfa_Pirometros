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
import com.hrodriguesdev.entities.Empresa;

public class EmpresaRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	


	public Empresa findEmpressa(Long empressaId) {
		Empresa empressa = new Empresa();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_empressa;");			
			
			while (rs.next())  
				if( rs.getLong("id") == empressaId)
					empressa = empressaParce( rs );	
			
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

		return empressa;
	}
	
	
	private Empresa empressaParce(ResultSet rs2) {
		Empresa empressa = new Empresa();
		
		try {
			empressa.setName(rs.getString(2));
			empressa.setCidade(rs.getString(3));
			empressa.setEstado(rs.getString(4));
			empressa.setEndereco(rs.getString(5));
			empressa.setCep(rs.getString(6));  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empressa;
	}


	public List<String> getAllEmpressa() {
		List<String> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_empressa;");			
			
			while (rs.next())  
				if( !rs.getString("name").isEmpty() )
					list.add(rs.getString("name"));	
			
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
	

	public Long addEmpressa(Empresa empressa) {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_empressa "
					+ "(name, cidade, estado, endereco, cep) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, empressa.getName());
			pst.setString(2, empressa.getCidade());
			pst.setString(3, empressa.getEstado());
			pst.setString(4, empressa.getEndereco());			
			pst.setString(5, empressa.getCep());
			
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
	
	
	public Long findEmpresaId(String empresaName) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_empressa;");			
			
			while (rs.next())  
				if( rs.getString(2).equalsIgnoreCase(empresaName) )
					return rs.getLong(1);
			
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
	
		return null;
	}
		
		
}
		
			
			
			
			
			
			
			