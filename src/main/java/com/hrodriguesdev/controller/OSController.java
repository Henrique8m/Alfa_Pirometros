package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.service.OSService;

public class OSController {

	private OSService service = new OSService();
	
	public boolean createNewOS(List<ProductsOs> list) {
		return service.createNewOs(list);
	}

	public List<ProductsOs> findAllOsByOrcamentoId(Long id) {
		return service.findAllOsByOrcamentoId(id);
	}
	
	public List<ProductsOs> findAllByOrcamentoId(Long id) {
		return service.findAllByOrcamentoId(id);
	}
	
	public boolean createNewOSOut(List<ProductsOs> list) {
		return service.createNewOSOut(list);
	}

	public boolean createNewOSIn(List<ProductsOs> list) {
		return service.createNewOSIn(list);
	}

	public List<ProductsOs> findAllIn() {
		return service.findAllIn();
	}
	
	public List<ProductsOs> findAllOut() {
		return service.findAllOut();
	}
	
	public List<ProductsOs> findAllOs() {
		return service.findAllOs();
	}
	
	public boolean updatOS(List<ProductsOs> listProductsOs) {
		return service.updatOS(listProductsOs);
	}

	public boolean isContentOs(Long id) {
		return service.isContentOs(id);
	}

	public boolean isContentOsOut(Long id) {
		return service.isContentOsOut(id);
	}


	
}
