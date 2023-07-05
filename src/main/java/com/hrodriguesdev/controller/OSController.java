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

}
