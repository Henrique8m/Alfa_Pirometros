package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.dao.repository.UnidadeMedidaRepository;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.UnidadeMedida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UnidadeMedidaService {	
	private UnidadeMedidaRepository repository = new UnidadeMedidaRepository();

	
	public List<UnidadeMedida> findAll(){
		return repository.findAll();
	}

	public ObservableList<UnidadeMedida> findAllUnidadeMedida() {
		ObservableList<UnidadeMedida> obs = FXCollections.observableArrayList();
		List<UnidadeMedida> listUnidade = repository.findAll();
		obs.addAll(listUnidade);
		return obs;
	}
	
	public boolean createNewUnit(UnidadeMedida unidadeMedida) {
		return repository.createNewUnit(unidadeMedida);
	}

	public boolean updateUnidade(UnidadeMedida unidade) {
		return repository.updateUnidade(unidade);
	}

	public boolean delete(Long id)throws ExceptionAlfa {
		ProductsController productController = new ProductsController();		
		List<Product> prodList = productController.findAll();
		
		for(Product prod: prodList) {
			if(prod.getUnidade_medida().equals(id))
				throw new ExceptionAlfa("Unidade em uso em produtos");
		}
		
		return repository.delete(id);
	}
	
}
