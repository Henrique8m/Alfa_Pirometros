package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.controller.UnidadeMedidaController;
import com.hrodriguesdev.dao.repository.RepositoryProducts;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.UnidadeMedida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductsService {
	private RepositoryProducts repository = new RepositoryProducts();
	
	public List<Product> findAll(){
		return repository.findAllProducts();
	}

	public ObservableList<Product> findAllObs() {
		UnidadeMedidaController serviceUnidade = new UnidadeMedidaController();		
		List<Product> list = repository.findAllProducts();
		List<UnidadeMedida> listUnidade = serviceUnidade.findAll();
		
		list.forEach(prod -> {
			prod.setUnidadeMedida(listUnidade.stream().filter(uni -> uni.getId().equals(prod.getUnidade_medida())).findFirst().get().getUnidade() );
		});
		
		ObservableList<Product> obs = FXCollections.observableArrayList();
		obs.addAll(list);
		return obs;
	}

	public boolean createdNew(Product product) {
		return repository.createdNew(product);
	}

	public boolean update(Product product) {
		return repository.update(product);
	}

	public boolean delete(Long id) {
		return repository.delete(id);
	}

}
