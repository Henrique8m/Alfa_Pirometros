package com.hrodriguesdev.service;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.UnidadeMedidaController;
import com.hrodriguesdev.dao.repository.RepositoryProducts;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.entities.DTO.ProductsDto;
import com.hrodriguesdev.entities.products.ProductsOs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductsService {
	private RepositoryProducts repository = new RepositoryProducts();
	private OSController osController = new OSController();
	
	public List<Product> findAll(){
		return repository.findAllProducts();
	}

	public ObservableList<Product> findAllObs() {
		UnidadeMedidaController serviceUnidade = new UnidadeMedidaController();		
		ObservableList<Product> obs = FXCollections.observableArrayList();
		List<Product> list = findAll();
		List<UnidadeMedida> listUnidade = serviceUnidade.findAll();
		
		List<ProductsOs> listIn = osController.findAllIn();		
		List<ProductsOs> listOut = osController.findAllOut();
		List<ProductsDto> listDto = new ArrayList<>();
		
		listIn.forEach(productIn ->{
			listDto.stream()
			.filter(dto -> dto.getProductId().equals(productIn.getProductId()))
			.findFirst()
			.ifPresentOrElse(dto -> {
				dto.setQtde(dto.getQtde() + productIn.getQtde());
			}, () -> {
				listDto.add(new ProductsDto(productIn.getProductId(), productIn.getQtde()));
			}); 
		});

		
		listOut.forEach(productOut ->{
			listDto.stream()
			.filter(dto -> dto.getProductId().equals(productOut.getProductId()))
			.findFirst()
			.ifPresentOrElse(dto -> {
				dto.setQtde(dto.getQtde() - productOut.getQtde());
			}, () -> {
				listDto.add(new ProductsDto(productOut.getProductId(), (productOut.getQtde() * (-1))  ));
			}); 
		});
		
		list.forEach(prod -> {
			prod.setUnidadeMedida(
					listUnidade
					.stream()
					.filter(uni -> uni.getId().equals(prod.getUnidade_medida()))
					.findFirst()
					.get()
					.getUnidade() 
					);
			
					listDto
					.stream()
					.filter(dto -> dto.getProductId().equals(prod.getId()))
					.findFirst()
					.ifPresent(dto -> {
						prod.setQtde(dto.getQtde());
					});
		});
		
		
		
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

		ObservableList<Product> listFinal = FXCollections.observableArrayList();
		
		List<ProductsOs> listProOs = osController.findAllOsByOrcamentoId(id);
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
		
		ObservableList<Product> listFinal = FXCollections.observableArrayList();
		List<ProductsOs> listProOs = osController.findAllByOrcamentoId(id);
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
