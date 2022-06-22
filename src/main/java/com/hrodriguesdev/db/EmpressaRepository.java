package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Equipamento;

public class EmpressaRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	


	public Empressa findEmpressa(Long empressaId) {
		Empressa empressa = new Empressa();		
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
	
	
	private Empressa empressaParce(ResultSet rs2) {
		Empressa empressa = new Empressa();
		
		try {
			empressa.setName(rs.getString(2));
			empressa.setCidade(rs.getString(3));
			empressa.setEstado(rs.getString(4));
			empressa.setEndereço(rs.getString(5));
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
	

public Long addEmpressa(Empressa empressa) {
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
		pst.setString(4, empressa.getEndereço());			
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
	
	public Equipamento parseEquipamento(ResultSet rs) {
		Equipamento obj = new Equipamento();
		try {
			obj.setId( rs.getLong("Id") );	
			obj.setEmpressaName( rs.getString("empressaName") );				
			obj.setModelo( rs.getString("modelo") );  
			obj.setStatus( rs.getInt("status") );
			obj.setDataChegada( rs.getString("dataChegada") );
			obj.setDataSaida( rs.getString("dataSaida") );
			obj.setNs(rs.getString("ns"));
			obj.setPat(rs.getString("pat"));
			obj.setUltimaCalib(rs.getString("ultimaCalib"));	
			obj.setCertificado(rs.getString("certificado") );
			obj.setValor( rs.getDouble("valor") );	
			//estoque 
			//empresa
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;		
	}








	
	
}
		
			
			
			
			
			
			
			