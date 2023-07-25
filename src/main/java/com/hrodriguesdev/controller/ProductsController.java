package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.service.ProductsService;

import javafx.collections.ObservableList;

public class ProductsController {
	private ProductsService service = new ProductsService();

	public List<Product> findAll(){
		return service.findAll();
	}
	
		
	public ObservableList<Product> findAllObs(){
		return service.findAllObs();
	}

	public boolean createdNew(Product product) {
		return service.createdNew(product);
	}
	
	public boolean update(Product product) {
		return service.update(product);
	}
	
	public boolean delete(Long id) {
		return service.delete(id);
	}

	public ObservableList<Product> findAllOsByOrcamentoId(Long id) {
		return service.findAllOsByOrcamentoId(id);
	}

	public ObservableList<Product> findAllByOrcamentoId(Long id) {
		return service.findAllByOrcamentoId(id);
	}
	
}
