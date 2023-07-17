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
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
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
	
	public Product(String name, String descricao, Double valor_pago, Double valor_venda, Long unidade_medida) {
		this.name = name;
		this.descricao = descricao;
		this.valor_pago = valor_pago;
		this.valor_venda = valor_venda;
		this.unidade_medida = unidade_medida;
	}
	
	public Product(Long id, String name, String descricao, Double valor_pago, Double valor_venda, Long unidade_medida) {
		this.id = id;
		this.name = name;
		this.descricao = descricao;
		this.valor_pago = valor_pago;
		this.valor_venda = valor_venda;
		this.unidade_medida = unidade_medida;
	}
	
	public Product(ResultSet rs) {
		try {
			this.id = rs.getLong("id");
			this.name = rs.getString("name");
			this.descricao = rs.getString("descricao");
			this.valor_pago = rs.getDouble("valor_pago");
			this.valor_venda = rs.getDouble("valor_venda");
			this.unidade_medida = rs.getLong("unidade_medida");
		}catch (SQLException e) {
			e.printStackTrace();
			Log.logString("Product", e.getMessage());
		}
	}
	public Product() {
		this.qtde = 0d;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((valor_pago == null) ? 0 : valor_pago.hashCode());
		result = prime * result + ((valor_venda == null) ? 0 : valor_venda.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (valor_pago == null) {
			if (other.valor_pago != null)
				return false;
		} else if (!valor_pago.equals(other.valor_pago))
			return false;
		if (valor_venda == null) {
			if (other.valor_venda != null)
				return false;
		} else if (!valor_venda.equals(other.valor_venda))
			return false;
		return true;
	}

	
	
}
