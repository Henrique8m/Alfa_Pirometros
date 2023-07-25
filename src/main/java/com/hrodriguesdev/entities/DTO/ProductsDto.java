package com.hrodriguesdev.entities.DTO;

public class ProductsDto {
	private Long productId;
	private Double qtde;
	
	public ProductsDto(Long productId, Double qtde) {
		super();
		this.productId = productId;
		this.qtde = qtde;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Double getQtde() {
		return qtde;
	}
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	
}
