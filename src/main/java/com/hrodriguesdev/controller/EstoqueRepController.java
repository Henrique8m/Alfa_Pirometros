package com.hrodriguesdev.controller;

import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.service.EstoqueService;

import javafx.collections.ObservableList;

public class EstoqueRepController {
	private EstoqueService service = new EstoqueService();
	
	public String[] findAllProducts() {
		return service.findAllNameProducts();
	}
	
	public boolean saidaDoEstoque(Long orcamento_id) {
		return service.saidaDoEstoque(orcamento_id);
	}

	public void refreshObsProducts() {
		service.refreshObsProducts();
		
	}

	public ObservableList<Product> findAllProductsobs() {
		return service.findAllProductsObs();
	}
	

}
