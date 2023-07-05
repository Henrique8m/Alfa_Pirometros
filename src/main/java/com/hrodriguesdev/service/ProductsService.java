package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.UnidadeMedidaController;
import com.hrodriguesdev.dao.repository.RepositoryProducts;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.entities.products.ProductsOs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductsService {
	private RepositoryProducts repository = new RepositoryProducts();
	
	public List<Product> findAll(){
		return repository.findAllProducts();
	}

	public ObservableList<Product> findAllObs() {
		UnidadeMedidaController serviceUnidade = new UnidadeMedidaController();		
		List<Product> list = findAll();
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

	public ObservableList<Product> findAllOsByOrcamentoId(Long id) {
		OSController controller = new OSController();
		ObservableList<Product> listFinal = FXCollections.observableArrayList();
		List<ProductsOs> listProOs = controller.findAllOsByOrcamentoId(id);
		List<Product> listProdu = findAllObs();
		
		listProOs.forEach(productOs ->{
			listProdu.stream().filter((prod) -> prod.getId().equals(productOs.getProductId())).findAny().ifPresent(prod -> {
				prod.setQtde(productOs.getQtde());
				listFinal.add(prod);

			});
		});
		
		return listFinal;
	}

	public ObservableList<Product> findAllByOrcamentoId(Long id) {
		OSController controller = new OSController();
		ObservableList<Product> listFinal = FXCollections.observableArrayList();
		List<ProductsOs> listProOs = controller.findAllByOrcamentoId(id);
		List<Product> listProdu = findAllObs();
		
		listProOs.forEach(productOs ->{
			listProdu.stream().filter((prod) -> prod.getId().equals(productOs.getProductId())).findAny().ifPresent(prod -> {
				prod.setQtde(productOs.getQtde());
				listFinal.add(prod);
			});
		});
		
		return listFinal;
	}

}
