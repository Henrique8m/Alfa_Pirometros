package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;

public class SaidaEquipamentoTransacao {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	Connection conn2 = null;
	Statement st2 = null;
	ResultSet rs2 = null;
	PreparedStatement pst2 = null;
	
	public boolean saidaEquipamento(Equipamento equipamento, Orcamento orcamento) {
		boolean ok = false;
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento "
											+ "SET laboratorio = " 
											+ equipamento.getLaboratorio()
											+ " WHERE "
											+"(id = ?)");		
	
			pst.setLong( 1, equipamento.getId() );			
			
			conn2 = DB.getConnection();
			conn2.setAutoCommit(false);
			pst2 = conn2.prepareStatement("UPDATE tb_orcamento "
												+ "SET data_saida = ?, "
												+ "coletor_id = ?, "
												+ "laboratorio = ?, "
												+ "status = ? "
												+" WHERE "
												+"(id = ?)");
										
			pst2.setDate( 1, orcamento.getData_saida() );
			pst2.setLong( 2, orcamento.getColetor_id() );
			pst2.setBoolean(3, orcamento.getLaboratorio() );
			pst2.setInt(4, orcamento.getStatus() );
			pst2.setLong(5, orcamento.getId() );
			
			int rowsAccepted2 = pst2.executeUpdate();			
			int rowsAccepted = pst.executeUpdate();
			
			conn.commit();
			conn2.commit();
			
			if(rowsAccepted>0)
				if(rowsAccepted2>0)
					ok=true;
		
		}catch (DbException | SQLException e) {
			ok=false;
			e.printStackTrace();
			try {
				conn.rollback();
				conn2.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage() );
			}catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
		}
		finally {
			DB.closeStatement(pst);
			DB.closeStatement(pst2);
			DB.closeConnection();
			
		}
		return ok;

	}
}
