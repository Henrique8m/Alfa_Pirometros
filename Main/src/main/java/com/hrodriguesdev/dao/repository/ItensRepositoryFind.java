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

public class ItensRepositoryFind {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public EstoqueEletricos eletricosByOrcamentoId(Long orcamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_eletricos;");			
			
			while (rs.next())  
				if( rs.getLong("orcamento_id") == orcamento_id) 
					return new EstoqueEletricos(rs);
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
	}
	
	public EstoqueEletricos eletricosEstoque() {
		EstoqueEletricos eletrico = new EstoqueEletricos();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_eletricos;");			
			
			while (rs.next())  
				if( rs.getBoolean("saida")) 
					eletrico.remove(rs);
				else if(rs.getBoolean("entrada"))
					eletrico.add(rs);
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return eletrico;
	}
	
	public EstoqueEletronicos eletronicosByOrcamentoId(Long orcamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_eletronicos;");			
			
			while (rs.next())  
				if( rs.getLong("orcamento_id") == orcamento_id) 
					return new EstoqueEletronicos(rs);
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
	}
	
	public EstoqueEletronicos eletronicosEstoque() {
		EstoqueEletronicos eletronicos = new EstoqueEletronicos();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_eletronicos;");			
			
			while (rs.next())  
				if( rs.getBoolean("saida")) 
					eletronicos.remove(rs);
				else if(rs.getBoolean("entrada"))
					eletronicos.add(rs);
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return eletronicos;
	}
	
	public EstoqueConsumo consumoByOrcamentoId(Long orcamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_consumo;");			
			
			while (rs.next())  
				if( rs.getLong("orcamento_id") == orcamento_id) 
					return new EstoqueConsumo(rs);
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
	}
		
	public EstoqueConsumo consumoEstoque() {
		EstoqueConsumo consumo = new EstoqueConsumo();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_consumo;");			
			
			while (rs.next())  
				if( rs.getBoolean("saida")) 
					consumo.remove(rs);
				else if(rs.getBoolean("entrada"))
					consumo.add(rs);
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return consumo;
	}

	public EstoqueEstetico esteticoByOrcamentoId(Long orcamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_estetico;");			
			
			while (rs.next())  
				if( rs.getLong("orcamento_id") == orcamento_id) 
					return new EstoqueEstetico(rs);
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
	}
	
	public EstoqueEstetico esteticoEstoque() {
		EstoqueEstetico estetico = new EstoqueEstetico();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_estetico;");			
			
			while (rs.next())  
				if( rs.getBoolean("saida")) 
					estetico.remove(rs);
				else if(rs.getBoolean("entrada"))
					estetico.add(rs);
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return estetico;
	}
	
	public EstoqueSinal sinalByOrcamentoId(Long orcamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_sinal;");			
			
			while (rs.next())  
				if( rs.getLong("orcamento_id") == orcamento_id) 
					return new EstoqueSinal(rs);
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
	}

	public EstoqueSinal sinalEstoque() {
		EstoqueSinal sinal = new EstoqueSinal();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_sinal;");			
			
			while (rs.next())  
				if( rs.getBoolean("saida")) 
					sinal.remove(rs);
				else if(rs.getBoolean("entrada"))
					sinal.add(rs);
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return sinal;
	}
	
	public EstoqueCabos cabosByOrcamentoId(Long orcamento_id) {
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_cabos;");			
			
			while (rs.next())  
				if( rs.getLong("orcamento_id") == orcamento_id) 
					return new EstoqueCabos(rs);
						
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return null;
	}

	public EstoqueCabos cabosEstoque() {
		EstoqueCabos cabos = new EstoqueCabos();
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_itens_cabos;");			
			
			while (rs.next())  
				if( rs.getBoolean("saida")) 
					cabos.remove(rs);
				else if(rs.getBoolean("entrada"))
					cabos.add(rs);
		}catch(DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
		return cabos;
	}
	
}
