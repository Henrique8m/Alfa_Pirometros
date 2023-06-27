package com.hrodriguesdev.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hrodriguesdev.utilitary.Log;

public class Product {
	private Long id = 0l;
	private String name;
	private String descricao;
	private Double valor_pago;
	private Double valor_venda;
	private Double qtde;
	private Long unidade_medida;
	private String unidadeMedida;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescricao() {
		return descricao;
	}
	public Double getValor_pago() {
		return valor_pago;
	}
	public Double getValor_venda() {
		return valor_venda;
	}
	public Double getQtde() {
		return qtde;
	}
	public Long getUnidade_medida() {
		return unidade_medida;
	}
	
	public Product(String name, String descricao, Double valor_pago, Double valor_venda, Double qtde,
			Long unidade_medida) {
		this.name = name;
		this.descricao = descricao;
		this.valor_pago = valor_pago;
		this.valor_venda = valor_venda;
		this.qtde = qtde;
		this.unidade_medida = unidade_medida;
	}
	
	public Product(Long id, String name, String descricao, Double valor_pago, Double valor_venda, Double qtde,
			Long unidade_medida) {
		this.id = id;
		this.name = name;
		this.descricao = descricao;
		this.valor_pago = valor_pago;
		this.valor_venda = valor_venda;
		this.qtde = qtde;
		this.unidade_medida = unidade_medida;
	}
	
	public Product(ResultSet rs) {
		try {
			this.id = rs.getLong("id");
			this.name = rs.getString("name");
			this.descricao = rs.getString("descricao");
			this.valor_pago = rs.getDouble("valor_pago");
			this.valor_venda = rs.getDouble("valor_venda");
			this.qtde = rs.getDouble( "qtde");
			this.unidade_medida = rs.getLong("unidade_medida");
		}catch (SQLException e) {
			e.printStackTrace();
			Log.logString("Product", e.getMessage());
		}
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

}
