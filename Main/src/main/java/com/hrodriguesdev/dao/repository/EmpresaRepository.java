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
					empressa = new Empresa(rs);
			
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
	

	public List<String> getAll() {
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
	
	public List<Empresa> getAllEmpresa() {
		List<Empresa> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_empressa;");			
			
			while (rs.next())  
					list.add(new Empresa(rs));	
			
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
		if(exist(empressa.getName()))
			return 0l;
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


	public boolean delete(Empresa empresa) {	
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if(rs.getLong("empresa_id") == empresa.getId())
					return false;
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE FROM tb_empressa WHERE (id = "+ empresa.getId() +" )");	
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			
			if(rowsAccepted>0)
				return true;
		
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage() );
			}catch (SQLException | DbException e2) {
				e.printStackTrace();
				return false;
			}
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		return false;
	}


	public boolean exist(String text) {
		String text2 = text.replaceAll("[^A-Z]+", "");
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_empressa;");			
			
			while (rs.next())  {
				if( rs.getString(2).equalsIgnoreCase(text) )
					return true;
				else {
					String busca = rs.getString(2).replaceAll("[^A-Z]+", "");
					if(busca.equalsIgnoreCase(text2))
						return true;
				}
			}
			return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
	
			conn = null;
			st = null;
			rs = null;
		}
	}


	public boolean updateEmpresa(Empresa empresa) {		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_empressa "
											+ "SET name = ?,"
											+ " cidade = ?,"
											+ " estado = ?,"
											+ " endereco = ?,"
											+ " cep = ?"
											+" WHERE "
											+"(id = ?)");		
	
			pst.setString(1, empresa.getName());
			pst.setString(2, empresa.getCidade());
			pst.setString(3, empresa.getEstado());
			pst.setString(4, empresa.getEndereco());			
			pst.setString(5, empresa.getCep());
			pst.setLong(6, empresa.getId());
					
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				return true;
		
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
		return false;
		
	}
		
}
		
			
			
			
			
			
			
			