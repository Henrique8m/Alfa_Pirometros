/*package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Motorista;

public class MotoristaRepository {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	

	public List<Motorista> findAllFirst() {
		List<Motorista> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");
			
			int i=0;
			while (rs.next() && i<10) {
				i++;
				list.add(parseMotorista(rs));
			}
				
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
	
		
	public Motorista findById(Long id) {
		conn = DB.getConnection();	
		Motorista moto = new Motorista();	
		selectFronMotorista();
		try {
			
			while (rs.next()) 
				if( rs.getLong("id") == id) {
					moto = parseMotorista(rs);
					DB.closeResultSet(rs);
					DB.closeStatement(st);	
					return moto;
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}					

		DB.closeResultSet(rs);
		DB.closeStatement(st);		
		return moto;
	}


	public List<Motorista> getByFila(Boolean fila) {
		List<Motorista> list = new ArrayList<>();
		//UPDATE `carvaodb`.`tb_motorista` SET `fila` = '0' WHERE (`id` = '3');
		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");
			
			
			while (rs.next()) 
				if( rs.getBoolean("fila") == true)
					list.add(parseMotorista(rs));
				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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

	public Long save(Motorista motorista) {
		Long cnh = 0l;
		Long id = 0l;
		if(motorista.getCnh()!=null) cnh = motorista.getCnh();
		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("INSERT INTO tb_motorista "
					+ "(cidade, cnh, data, estado, fila, hora, name, phone, placa) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);			
			
			pst.setString(1, motorista.getCidade());
			pst.setLong(2, cnh);
			pst.setString(3, motorista.getData());
			pst.setString(4, motorista.getEstado());
			pst.setBoolean(5, true);
			pst.setString(6, motorista.getHora());
			pst.setString(7, motorista.getName());
			pst.setString(8, motorista.getPhone());
			pst.setString(9, motorista.getPlaca());			
			
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

	public List<Motorista> getByPlaca(String placa) {
		List<Motorista> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");			
			
			while (rs.next())  
				if( rs.getString("placa").equalsIgnoreCase(placa))
					list.add(parseMotorista(rs));	
			
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

	public List<Motorista> getByData(String data) {
		List<Motorista> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");			
			
			while (rs.next())  
				if( rs.getString("data").equalsIgnoreCase(data))
					list.add(parseMotorista(rs));	
			
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

	public Boolean update(Long id) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("UPDATE tb_motorista "
											+ "SET fila = " + false 
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
	
	
	public Motorista parseMotorista(ResultSet rs) {
		Motorista moto = new Motorista();
		try {
			moto.setId( rs.getLong("Id") );	
			moto.setCidade( rs.getString("cidade") );
			moto.setCnh( rs.getLong("cnh") );  
			moto.setData( rs.getString("data") );
			moto.setEstado( rs.getString("estado") );
			moto.setFila(rs.getBoolean("fila"));
			moto.setHora(rs.getString("hora"));
			moto.setName(rs.getString("name"));	
			moto.setPhone( rs.getString("phone") );
			moto.setPlaca( rs.getString("placa") );	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moto;		
	}
	
	
	private void selectFronMotorista() {
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}	
}
*/