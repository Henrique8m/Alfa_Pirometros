package com.hrodriguesdev.entities.products;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hrodriguesdev.utilitary.Log;

public class ProductsOs {
//	Id do orcamento, vai ser a pk da table
	private Long idProductsOs;
//	id do produto em si
	private Long productId;
	private Double qtde;
	private Long id;
	
	public ProductsOs(Long idProductsOs, Long productId, Double qtde) {
		super();
		this.idProductsOs = idProductsOs;
		this.productId = productId;
		this.qtde = qtde;
	}
	public ProductsOs(ResultSet rs) {
		try {
			this.idProductsOs = rs.getLong("id_orcamento");
			this.productId = rs.getLong("product_id");
			this.qtde = rs.getDouble("qtde");
			this.id = rs.getLong("id");
		}catch(SQLException e) {
			Log.logString("ProductsOS", e.getMessage());
			e.printStackTrace();
		}
	}
	public Long getIdProductsOs() {
		return idProductsOs;
	}
	public Long getProductId() {
		return productId;
	}
	public Double getQtde() {
		return qtde;
	}
	
	@Override
	public String toString() {
		return "PK igual o PK do orcamento " + idProductsOs 
				+ "\nProduct ID " + productId 
				+"\nQuantidade em double " + qtde;
	}
	public Long getId() {
		return id;
	}
}
