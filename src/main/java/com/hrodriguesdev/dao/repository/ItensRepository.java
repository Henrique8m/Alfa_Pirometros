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

public class ItensRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	ItensRepositoryUpdatede updated = new ItensRepositoryUpdatede();
	ItensRepositoryFind find = new ItensRepositoryFind();
	
	public Long saveEletricos(EstoqueEletricos eletricos) throws DbException {
		EstoqueEletricos obj = find.eletricosByOrcamentoId(eletricos.getOrcamento_id());
		if(obj != null) {
			eletricos.setId(obj.getId());
			updated.updatedeEletricos(eletricos);
			return eletricos.getId();
		}
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"INSERT INTO tb_itens_eletricos "
					+ "(orcamento_id, nfe, saida, font_carb_indic, font_carb_delta, pin_femea_ali_fii, pin_femea_ali_fiii, bat_fii_fiii, bat_descartavel, bat_inditemp, bat_litio, carr_ecil, carr_italterm , entrada) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",				
					Statement.RETURN_GENERATED_KEYS);
					
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
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected >0) {
				rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}else {
				throw new DbException("Erro ao salvar");
			}
					
		}catch (SQLException e) {
			throw new DbException("SQL error" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		throw new DbException("Erro desconhecido");
		
	}
	
	public Long saveEletronicos(EstoqueEletronicos eletronicos) throws DbException {
		EstoqueEletronicos obj = find.eletronicosByOrcamentoId(eletronicos.getOrcamento_id());
		if(obj != null) {
			eletronicos.setId(obj.getId());
			updated.updatedeEletronicos(eletronicos);
			return eletronicos.getId();
		}
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"INSERT INTO tb_itens_eletronicos "
					+ "(orcamento_id, nfe, saida, sirene, pci_fiii, pci_fkal, disp_fkal, fiii, indicmax, ci_fii, ci_indicmax , entrada) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)",				
					Statement.RETURN_GENERATED_KEYS);
					
//			11
			
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
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected > 0) {
				rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}else {
				throw new DbException("Erro ao salvar");
			}
					
		}catch (SQLException e) {
			throw new DbException("SQL error" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		throw new DbException("Erro desconhecido");
		
	}
	
	public Long saveConsumo(EstoqueConsumo consumo) throws DbException {
		EstoqueConsumo obj = find.consumoByOrcamentoId(consumo.getOrcamento_id());
		if(obj != null) {
			consumo.setId(obj.getId());
			updated.updatedeConsumo(consumo);
			return consumo.getId();
		}
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"INSERT INTO tb_itens_consumo "
					+ "(orcamento_id, nfe, saida, b_liga, b_m_forneros, caixa_bateria , entrada) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?)",				
					Statement.RETURN_GENERATED_KEYS);
					
//			6
			
			pst.setLong(1, consumo.getOrcamento_id());
			pst.setInt(2, consumo.getNfe());
			pst.setBoolean(3, consumo.getSaida());
			pst.setInt(4, consumo.getBotaoLiga());
			pst.setInt(5, consumo.getBoMeFIIFIIIIndicmax());
			pst.setInt(6, consumo.getCaixaBat());
			pst.setBoolean(7, consumo.getEntrada());
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected >0) {
				rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}else {
				throw new DbException("Erro ao salvar");
			}
					
		}catch (SQLException e) {
			throw new DbException("SQL error" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		throw new DbException("Erro desconhecido");
		
	}
	
	public Long saveEstetico(EstoqueEstetico estetico) throws DbException {
		EstoqueEstetico obj = find.esteticoByOrcamentoId(estetico.getOrcamento_id());
		if(obj != null) {
			estetico.setId(obj.getId());
			updated.updatedeEstetico(estetico);
			return estetico.getId();
		}

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"INSERT INTO tb_itens_estetico "
					+ "(orcamento_id, nfe, saida, mascara_fii, mascara_fkal, mascara_fiii, mascara_carbo, mascara_indic, etiq_lat_fii, etiq_lat_fiii, etiq_tras_fii, punho, entrada) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)",				
					Statement.RETURN_GENERATED_KEYS);
					
//			12
			
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
			pst.setBoolean(13, estetico.getEntrada() );
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected >0) {
				rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}else {
				throw new DbException("Erro ao salvar");
			}
					
		}catch (SQLException e) {
			throw new DbException("SQL error" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		throw new DbException("Erro desconhecido");
		
	}
	
	public Long saveSinal(EstoqueSinal sinal) throws DbException {
		EstoqueSinal obj = find.sinalByOrcamentoId(sinal.getOrcamento_id());
		if(obj != null) {
			sinal.setId(obj.getId());
			updated.updatedeSinal(sinal);
			return sinal.getId();
		}
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"INSERT INTO tb_itens_sinal "
					+ "(orcamento_id, saida, nfe, receptaculo_s, receptaculo_su, receptaculo_ecil, receptaculo_k, plug_fs, plug_ms, plug_mk, tomada_s, entrada) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)",				
					Statement.RETURN_GENERATED_KEYS);
					
//			11
			
			pst.setLong(1, sinal.getOrcamento_id());			
			pst.setBoolean(2, sinal.getSaida());
			pst.setInt(3, sinal.getNfe());
			pst.setInt(4, sinal.getReceptaculoS());
			pst.setInt(5, sinal.getReceptaculoSU());
			pst.setInt(6, sinal.getReceptaculoEcil());
			pst.setInt(7, sinal.getReceptaculoK());
			pst.setInt(8, sinal.getPlugFS());
			pst.setInt(9, sinal.getPlugMS());
			pst.setInt(10, sinal.getPlugMK());
			pst.setInt(11, sinal.getTomadaS());
			pst.setBoolean(12, sinal.getEntrada());
			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected >0) {
				rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}else {
				throw new DbException("Erro ao salvar");
			}
					
		}catch (SQLException e) {
			throw new DbException("SQL error" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		throw new DbException("Erro desconhecido");
		
	}
	
	public Long saveCabos(EstoqueCabos cabos) throws DbException {
		EstoqueCabos obj = find.cabosByOrcamentoId(cabos.getOrcamento_id());
		if(obj != null) {
			cabos.setId(obj.getId());
			updated.updatedeCabos(cabos);
			return cabos.getId();
		}
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"INSERT INTO tb_itens_cabos "
					+ "(orcamento_id, saida, nfe, s_borracha, s_miolo, s_extensao, k_borracha, k_miolo, k_extensao, entrada) "
					 
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?)",				
					Statement.RETURN_GENERATED_KEYS);
					
//			11
			
			pst.setLong(1, cabos.getOrcamento_id());			
			pst.setBoolean(2, cabos.getSaida());
			pst.setInt(3, cabos.getNfe());
			pst.setInt(4, cabos.getS_borracha());
			pst.setInt(5, cabos.getS_miolo());
			pst.setInt(6, cabos.getS_extensao());
			pst.setInt(7, cabos.getK_borracha());
			pst.setInt(8, cabos.getK_miolo());
			pst.setInt(9, cabos.getK_extensao());
			pst.setBoolean(10, cabos.getEntrada());

			
			int rowsAffected = pst.executeUpdate();
			conn.commit();
			
			if(rowsAffected >0) {
				rs = pst.getGeneratedKeys();
				while(rs.next())
					return rs.getLong(1);
			}else {
				throw new DbException("Erro ao salvar");
			}
					
		}catch (SQLException e) {
			throw new DbException("SQL error" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
			
		}
		throw new DbException("Erro desconhecido");
		
	}
	

}
