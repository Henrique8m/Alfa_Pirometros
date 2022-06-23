package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Equipamento;

public class EquipamentoRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public List<Equipamento> getByLaboratorio(boolean laboratorio) throws DbException, SQLException{
		List<Equipamento> list = new ArrayList<>();		
		
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");			
			
			while (rs.next())  
				if( rs.getBoolean("laboratorio")== true)
					list.add(parseEquipamento(rs));	
			
	
		
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
		

		return list;
	}

	public Equipamento findEquipamentoNs(String ns) {
		Equipamento equipamento = null;
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(7)!=null )
					if( rs.getString("ns").equalsIgnoreCase(ns) )
						equipamento = parseEquipamento( rs );	
		
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
	
		
		return equipamento;
	}
	
	public Long addEquipamento(Equipamento equipamento) {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_equipamento "
					+ "(empressaName, modelo, status, dataChegada, ns, pat, ultimaCalib, laboratorio, empresa_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, equipamento.getEmpressaName());
			pst.setString(2, equipamento.getModelo());
			pst.setInt(3, equipamento.getStatus());
			pst.setString(4, equipamento.getDataChegada());			
			pst.setString(5, equipamento.getNs());
			pst.setString(6, equipamento.getPat());
			pst.setString(7, equipamento.getUltimaCalib());
			pst.setBoolean(8, true);	
			pst.setLong(9, equipamento.getEmpressa());
			
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
	
	public Equipamento parseEquipamento(ResultSet rs) {
		Equipamento obj = new Equipamento();
		try {
			obj.setOrcamento_id( rs.getLong("orcamento_id"));
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
			obj.setEmpressa( rs.getLong( "empresa_id" ) );
			//estoque 
			//empresa
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;		
	}

	public boolean updatedeEquipamento(Long id, int status) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET status = " + status 
											+" WHERE "
											+"(id = ?)");
			
			pst.setLong( 1, id );
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0)
				ok=true;
		
		}catch (SQLException e) {
			ok=false;
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);
			
		}
		return ok;

	}

	public boolean updatedeEquipamentoOrcamento(Long id, Long idOrcamento) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET orcamento_id = " + idOrcamento 
											+" WHERE "
											+"(id = ?)");
			
			pst.setLong( 1, id );
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0)
				ok=true;
		
		}catch (SQLException e) {
			ok=false;
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);
			
		}
		return ok;

	}
	
	public Boolean deleteEquipamento(Long id) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("DELETE FROM tb_equipamento WHERE (id = "+ id +" )");	
			
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0)
				ok=true;
		
		}catch (SQLException e) {
			ok=false;
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);
			
		}
		return ok;
	}


	public boolean updatedEquipamento(Equipamento equipamento) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET empressaName = ? , "
											+ "modelo = ? ,"
											+ " status = ? ,"
											+ " dataChegada = ? ,"
											+ " ns = ? , pat = ? ,"
											+ " ultimaCalib = ? ,"
											+ " empresa_id = ?,"
											+ " dataSaida = ?,"
											+ " coletor_id = ?,"
											+ " laboratorio = ? "											
											+ " WHERE "
											+"(id = ? )");		
	
			pst.setString(1, equipamento.getEmpressaName());			
			pst.setString(2, equipamento.getModelo());
			pst.setInt(3, equipamento.getStatus());
			pst.setString(4, equipamento.getDataChegada());			
			pst.setString(5, equipamento.getNs());
			pst.setString(6, equipamento.getPat());
			pst.setString(7, equipamento.getUltimaCalib());
			pst.setLong(8, equipamento.getEmpressa() );				
			pst.setString(9, equipamento.getDataSaida() );
			pst.setLong(10, equipamento.getColetor_id() );
			pst.setBoolean(11, equipamento.getLaboratorio());
			pst.setLong( 12, equipamento.getId() );
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0)
				ok=true;
		
		}catch (SQLException e) {
			ok=false;
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);
			
		}
		return ok;

	}

	public List<Equipamento> findAll(Equipamento obj) {
		//List<Equipamento> list = new ArrayList<>();
		// TODO Auto-generated method stub
		return null;
	}

	public List<Equipamento> getByEmpressa(String empressaName) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(2)!=null )
					if( rs.getString(2).equalsIgnoreCase(empressaName) )
						list.add( parseEquipamento( rs ) );	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		conn = null;
		st = null;
		rs = null;
	
		
		return list;

	}

	public List<Equipamento> getByNs(String ns) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(7)!=null )
					if( rs.getString(7).equalsIgnoreCase(ns) )
						list.add( parseEquipamento( rs ) );	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		conn = null;
		st = null;
		rs = null;
	
		
		return list;

	}

	public List<Equipamento> getByPat(String pat) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(8)!=null )
					if( rs.getString(8).equalsIgnoreCase(pat) )
						list.add( parseEquipamento( rs ) );	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		conn = null;
		st = null;
		rs = null;
	
		
		return list;

	}

	public List<Equipamento> findAllFirst() {
		List<Equipamento> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");			

			int i=0;			
			while ( rs.next() ) {}			
			while (rs.previous() && i<10) {				
	//			if(rs.getBoolean("fila") == false){
					i++;
					list.add(parseEquipamento(rs));
	//			}				
			}	
		}catch (SQLException e) {	
			e.printStackTrace();
			return null;
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

		conn = null;
		st = null;
		rs = null;
	

	return list;
}





	
	
}
		
			
			
			
			
			
			
			