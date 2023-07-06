package com.hrodriguesdev.entities.products;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hrodriguesdev.utilitary.Log;

public class ProductsOs {
//	Id do orcamento, vai ser a pk da table
	private Long id_orcamento;
//	id do produto em si
	private Long productId;
	private Double qtde;
	private Long id;
	
	public ProductsOs(Long idProductsOs, Long productId, Double qtde) {
		super();
		this.id_orcamento = idProductsOs;
		this.productId = productId;
		this.qtde = qtde;
	}
	public ProductsOs(ResultSet rs) {
		try {
			this.id_orcamento = rs.getLong("id_orcamento");
			this.productId = rs.getLong("product_id");
			this.qtde = rs.getDouble("qtde");
			this.id = rs.getLong("id");
		}catch(SQLException e) {
			Log.logString("ProductsOS", e.getMessage());
			e.printStackTrace();
		}
	}
	public Long getIdOrcamento() {
		return id_orcamento;
	}
	public Long getProductId() {
		return productId;
	}
	public Double getQtde() {
		return qtde;
	}	
	
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	
	@Override
	public String toString() {
		return "PK igual o PK do orcamento " + id_orcamento 
				+ "\nProduct ID " + productId 
				+"\nQuantidade em double " + qtde;
	}
	public Long getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		ProductsOs other = (ProductsOs) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
	
}
