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

	public List<Equipamento> findAllNs(String ns) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();					
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())  
				if( rs.getString(7)!=null )
					if( rs.getString(7).equalsIgnoreCase(ns) )
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
					+ "(empressaName, modelo, status, dataChegada, ns, pat, ultimaCalib, laboratorio, empresa_id, dateChegada) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
			pst.setDate(10, equipamento.getDateChegada());
			
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

	public boolean updatede(Long id, Long idOrcamento) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET orcamento_id = " + idOrcamento 
											+ ", "
											+ "status = 3"
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

	public boolean updatede(Equipamento equipamento) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
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
		
			
			
			
			
			
			
			