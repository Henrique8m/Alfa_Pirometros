package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.EstoqueCabos;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;

public class ItensRepositoryUpdatede {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public boolean updatedeEletricos(EstoqueEletricos eletricos) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_itens_eletricos "
											+ "SET orcamento_id = ?,"
											+ " nfe = ?,"
											+ " saida =? ,"
											+ " font_carb_indic = ?,"
											+ " font_carb_delta = ?,"
											+ " pin_femea_ali_fii = ?,"
											+ " pin_femea_ali_fiii = ?,"
											+ " bat_fii_fiii = ?,"
											+ " bat_descartavel = ?,"
											+ " bat_inditemp = ?,"
											+ " bat_litio = ?,"
											+ " carr_ecil = ?,"
											+ " carr_italterm = ?,"
											+ " entrada = ? "											
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setLong(1, eletricos.getOrcamento_id());
			pst.setInt(2, eletricos.getNfe());
			pst.setBoolean(3, eletricos.getSaida());
			pst.setInt(4, eletricos.getFontCarbIndic());
			pst.setInt(5, eletricos.getFontCarbDelta());
			pst.setInt(6, eletricos.getPinFemeAliFII());
			pst.setInt(7, eletricos.getPinFemeAliFIII());
			pst.setInt(8, eletricos.getBatFIIFIII());
			pst.setInt(9, eletricos.getBatDescartavel());
			pst.setInt(10, eletricos.getBatInditemp());
			pst.setInt(11, eletricos.getBatLitio());
			pst.setInt(12, eletricos.getCarrEcil());
			pst.setInt(13, eletricos.getCarrItalterm());
			pst.setBoolean(14, eletricos.getEntrada());
			pst.setLong(15, eletricos.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
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
		return ok;
	}
	
	public boolean updatedeEletronicos(EstoqueEletronicos eletronicos) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_itens_eletronicos "
											+ "SET orcamento_id= ?,"
											+ " nfe= ?,"
											+ " saida= ?,"
											+ " sirene= ?,"
											+ " pci_fiii= ?,"
											+ " pci_fkal= ?,"
											+ " disp_fkal= ?,"
											+ " fiii= ?,"
											+ " indicmax= ?,"
											+ " ci_fii= ?,"
											+ " ci_indicmax = ?, "
											+ " entrada = ? "											
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setLong(1, eletronicos.getOrcamento_id());
			pst.setInt(2, eletronicos.getNfe());
			pst.setBoolean(3, eletronicos.getSaida());
			pst.setInt(4, eletronicos.getSirene());
			pst.setInt(5, eletronicos.getPCIFIII());
			pst.setInt(6, eletronicos.getPCIFKal());
			pst.setInt(7, eletronicos.getDispFKal());
			pst.setInt(8, eletronicos.getFIII());
			pst.setInt(9, eletronicos.getIndicmax());
			pst.setInt(10, eletronicos.getCIFII());
			pst.setInt(11, eletronicos.getCIIndicmax());
			pst.setBoolean(12, eletronicos.getEntrada());
			pst.setLong(13, eletronicos.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
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
		return ok;
	}
	
	public boolean updatedeConsumo(EstoqueConsumo consumo) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_itens_consumo "
											+ "SET orcamento_id = ? ,"
											+ " nfe = ?,"
											+ " saida = ?,"
											+ " b_liga = ?,"
											+ " b_m_forneros = ? ,"
											+ " caixa_bateria  = ? ,"
											+ " entrada = ? "
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setLong(1, consumo.getOrcamento_id());
			pst.setInt(2, consumo.getNfe());
			pst.setBoolean(3, consumo.getSaida());
			pst.setInt(4, consumo.getBotaoLiga());
			pst.setInt(5, consumo.getBoMeFIIFIIIIndicmax());
			pst.setInt(6, consumo.getCaixaBat());
			pst.setBoolean(7, consumo.getEntrada());
			pst.setLong(8, consumo.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
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
		return ok;
	}
	
	public boolean updatedeEstetico(EstoqueEstetico estetico) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_itens_estetico "
											+ "SET orcamento_id = ? ,"
											+ " nfe = ? ,"
											+ " saida = ? ,"
											+ " mascara_fii = ? ,"
											+ " mascara_fkal = ? ,"
											+ " mascara_fiii = ? ,"
											+ " mascara_carbo = ? ,"
											+ " mascara_indic = ? ,"
											+ " etiq_lat_fii = ? ,"
											+ " etiq_lat_fiii = ? ,"
											+ " etiq_tras_fii = ? ,"
											+ " punho = ?, "
											+ " entrada = ? "
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setLong(1, estetico.getOrcamento_id());
			pst.setInt(2, estetico.getNfe());
			pst.setBoolean(3, estetico.getSaida());
			pst.setInt(4, estetico.getMascaraFII());
			pst.setInt(5, estetico.getMascaraFKal());
			pst.setInt(6, estetico.getMascaraFIII());
			pst.setInt(7, estetico.getMascaraCarbo());
			pst.setInt(8, estetico.getMascaraIndic());
			pst.setInt(9, estetico.getEtiqLatFII());
			pst.setInt(10, estetico.getEtiqLatFIII());
			pst.setInt(11, estetico.getEtiqTrasFII());
			pst.setInt(12, estetico.getPunho());
			pst.setBoolean(13, estetico.getEntrada());
			pst.setLong(14, estetico.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
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
		return ok;
	}
	
	public boolean updatedeSinal(EstoqueSinal sinal) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_itens_sinal "
											+ "SET orcamento_id = ? ,"
											+ " saida = ? ,"
											+ " nfe = ? ,"
											+ " receptaculo_s = ? ,"
											+ " receptaculo_su = ? ,"
											+ " receptaculo_ecil = ? ,"
											+ " receptaculo_k = ? ,"
											+ " plug_fs = ? ,"
											+ " plug_ms = ? ,"
											+ " plug_mk = ? ,"
											+ " tomada_s  = ?, "
											+ " entrada = ? "
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setLong(1, sinal.getOrcamento_id());
			pst.setInt(2, sinal.getNfe());
			pst.setBoolean(3, sinal.getSaida());
			pst.setInt(4, sinal.getReceptaculoS());
			pst.setInt(5, sinal.getReceptaculoSU());
			pst.setInt(6, sinal.getReceptaculoEcil());
			pst.setInt(7, sinal.getReceptaculoK());
			pst.setInt(8, sinal.getPlugFS());
			pst.setInt(9, sinal.getPlugMS());
			pst.setInt(10, sinal.getPlugMK());
			pst.setInt(11, sinal.getTomadaS());
			pst.setBoolean(12, sinal.getEntrada());
			pst.setLong(13, sinal.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
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
		return ok;
	}
	
	public boolean updatedeCabos(EstoqueCabos cabos) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_itens_cabos "
											+ "SET orcamento_id = ? ,"
											+ " saida = ? ,"
											+ " nfe = ? ,"
											+ " s_borracha = ? ,"
											+ " s_miolo = ? ,"
											+ " s_extensao = ? ,"
											+ " k_borracha = ? ,"
											+ " k_miolo = ? ,"
											+ " k_extensao = ? ,"
											+ " entrada = ? "
											+ " WHERE "
											+ "(id = ?)");
			
			pst.setLong(1, cabos.getOrcamento_id());
			pst.setInt(2, cabos.getNfe());
			pst.setBoolean(3, cabos.getSaida());
			pst.setInt(4, cabos.getS_borracha());
			pst.setInt(5, cabos.getS_miolo());
			pst.setInt(6, cabos.getS_extensao());
			pst.setInt(7, cabos.getK_borracha());
			pst.setInt(8, cabos.getK_miolo());
			pst.setInt(9, cabos.getK_extensao());
			pst.setBoolean(10, cabos.getEntrada());
			pst.setLong(11, cabos.getId());
			
			int rowsAccepted = pst.executeUpdate();
			conn.commit();
			if(rowsAccepted>0)
				ok=true;
		
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
		return ok;
	}
	
}
