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
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;

public class EquipamentoRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public List<Equipamento> findAllByLaboratorio(boolean laboratorio) throws DbException, SQLException{
		List<Equipamento> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();	
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");			
			
			while (rs.next())  
				if( rs.getBoolean("laboratorio")== true)
					list.add(Equipamento.parseEquipamento(rs));	
			
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}finally{
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return list;
		
	}
	
	public List<Equipamento> findAll(String empressaName) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(2)!=null )
					if( rs.getString(2).equalsIgnoreCase(empressaName) )
						list.add( Equipamento.parseEquipamento( rs ) );	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return list;

	}

	public List<Equipamento> findByName(String name) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(2)!= null ) {
					String nameEmpresa = rs.getString(2).toUpperCase();
					if( nameEmpresa.contains(name.toUpperCase()) && rs.getBoolean("laboratorio") == true) 
						list.add( Equipamento.parseEquipamento( rs ) );	
				}

		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;
	}

	public List<Equipamento> findByIdEmpressa(Long id, Boolean laboratorio) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString("empresa_id")!= null ) {					
					if(rs.getLong("empresa_id") == id ) 
						if(laboratorio) {
								if(!rs.getBoolean( "laboratorio" ))
									list.add( Equipamento.parseEquipamento( rs ) );
						}else 
							list.add( Equipamento.parseEquipamento( rs ) );
						
				}

		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;
	}

	public List<Equipamento> findAllNs(String ns) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString("ns")!=null )
					if( rs.getString("ns").equalsIgnoreCase(ns) )
						list.add( Equipamento.parseEquipamento( rs ) );	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;

	}

	public List<Equipamento> findAllPat(String pat) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(8)!=null )
					if( rs.getString(8).equalsIgnoreCase(pat) )
						list.add( Equipamento.parseEquipamento( rs ) );	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return list;

	}
	
	public List<Equipamento> findById(List<Long> equipamento_id) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  		
				for(Long id: equipamento_id)
					if(rs.getLong("id") == id ) 
						list.add( Equipamento.parseEquipamento( rs ) );							

		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;
	}
	
	public List<Equipamento> findAll() {
		List<Equipamento> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");			

			int i=0;			
			while ( rs.next() ) {}			
			while (rs.previous() && i<100) {				
//					if(rs.getBoolean("fila") == false){
						i++;
						list.add(Equipamento.parseEquipamento(rs));
//					}				
				}	
		}catch (SQLException e) {	
			e.printStackTrace();
			return null;
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}	
		return list;
	}

	public Equipamento findByNs(String ns) {
		Equipamento equipamento = null;
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(7)!=null )
					if( rs.getString("ns").equalsIgnoreCase(ns) )
						equipamento = Equipamento.parseEquipamento( rs );	
		
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return equipamento;
	}
	
	public Long add(Equipamento equipamento) {
		Long id = 0l;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_equipamento "
					+ "(empressaName, modelo, ns, pat, empresa_id, laboratorio) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, equipamento.getEmpressaName());
			pst.setString(2, equipamento.getModelo());
			pst.setString(3, equipamento.getNs());
			pst.setString(4, equipamento.getPat());
			pst.setLong(5, equipamento.getEmpressa());
			pst.setBoolean(6, false);
			
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
			DB.closeConnection();

		}		
		return id;
	}
	
	public boolean updatede(Equipamento equipamento) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET empressaName = ? , "
											+ "modelo = ? ,"
											+ " ns = ? , pat = ? ,"
											+ " empresa_id = ?,"										
											+ " WHERE "
											+"(id = ?)");		
	
			pst.setString(1, equipamento.getEmpressaName());
			pst.setString(2, equipamento.getModelo());
			pst.setString(3, equipamento.getNs());
			pst.setString(4, equipamento.getPat());
			pst.setLong(5, equipamento.getEmpressa());
			pst.setLong( 6, equipamento.getId() );
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			
			if(rowsAccepted>0)
				ok=true;
		
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
	
	public boolean updatede(Long id, int status, Equipamento equipamento) {
		int statusEquip = equipamento.getStatus();
		boolean ok = false;
		if ( status == 4 ||	status == 5  ) {
			if ( statusEquip == 12 || statusEquip == 13)			
				status = 7;
		}		
		if (status == 7)
			equipamento.setLaboratorio(false);
		else
			equipamento.setLaboratorio(true);		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET status = " 
											+ status + ", "
											+ "laboratorio = ?" + ", "
											+ "relatorio = ? "
											+ " WHERE "
											+ "(id = ?)");
			if(equipamento.getRelatorio() == null ) 
				equipamento.setRelatorio("");
			
			pst.setBoolean( 1, equipamento.getLaboratorio() );
			pst.setString(2, equipamento.getRelatorio());			
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
	

	public void updatedeAllDate() {
		List<Equipamento> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");			
			
			while ( rs.next() ) {
				list.add(Equipamento.parseEquipamento(rs));
			}
			
		}catch (SQLException e) {	
			e.printStackTrace();
		}

//		try {
			OrcamentoRepository repo = new OrcamentoRepository();
			Orcamento or;
			for(Equipamento equipamento: list) {
				
				if(equipamento.getOrcamento_id() == null || equipamento.getOrcamento_id() == 0) {
					or = new Orcamento();
					or.setItem("Item");
					or.setEquipamento_id(equipamento.getId());
					or.setData_chegada(equipamento.getDateChegada());
					or.setLaboratorio(equipamento.getLaboratorio() );
					
					repo.add(or);
					
					}	
				
			}			
			
		
//		try {
//			for(Equipamento equipamento: list) {				
//			if(equipamento.getDataSaida() != null) {
//				pst = conn.prepareStatement("UPDATE tb_equipamento "
//												+ "SET dateChegada = ?" + ", "
//												+ "dateSaida = ?"
//												+ " WHERE "
//												+ "(id = ?)");
//				
//				pst.setDate( 1, Geral.dateParceString(equipamento.getDataChegada() ) );
//				pst.setDate(2, Geral.dateParceString( equipamento.getDataSaida() ) );			
//				pst.setLong( 3, equipamento.getId() );
//			}else {
//				pst = conn.prepareStatement("UPDATE tb_equipamento "
//						+ "SET dateChegada = ?"
//						+ " WHERE "
//						+ "(id = ?)");
//				
//				pst.setDate( 1, Geral.dateParceString(equipamento.getDataChegada() ) );			
//				pst.setLong( 2, equipamento.getId() );
//			}
//
//			
//			pst.executeUpdate();
//			}
		
//		}catch (SQLException e) {
//		System.out.println(e.getMessage());	
//		}
//		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
//			
//		}		


	}
	
	public boolean updatede(Long id, Boolean laboratorioo) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET laboratorio = " + laboratorioo 
											+" WHERE "
											+"(id = ?)");
			
			pst.setLong( 1, id );			
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			
			if(rowsAccepted>0)
				ok=true;
		
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

	
	
	public Boolean delete(Long id) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE FROM tb_equipamento WHERE (id = "+ id +" )");	
			
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



}
		
			
			
			
			
			
			
			