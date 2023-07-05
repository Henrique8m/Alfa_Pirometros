package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.dao.repository.OSRepository;
import com.hrodriguesdev.entities.products.ProductsOs;

public class OSService {

	private OSRepository repositoryOs = new OSRepository();
	
	public boolean createNewOs(List<ProductsOs> list) {
		if(list.size()>0)
			if(repositoryOs.findAllByOrcamentoId(list.get(0).getIdOrcamento(), "products_os ").isEmpty())
				return repositoryOs.createNewOs(list, "products_os ");
		return false;
	}
	
	public List<ProductsOs> findAll(){
		return repositoryOs.findAll("products_os ");
	}

	public List<ProductsOs> findAllOsByOrcamentoId(Long id) {
		return repositoryOs.findAllByOrcamentoId(id, "products_os ");
	}
	
	public List<ProductsOs> findAllByOrcamentoId(Long id) {
		List<ProductsOs> list = repositoryOs.findAllByOrcamentoId(id, "products_out ");
		list.addAll(repositoryOs.findAllByOrcamentoId(id, "products_inp "));
		if(list.size()==0)
			return repositoryOs.findAllByOrcamentoId(id, "products_os ");		
		return list;
	}
	
	public boolean createNewOSOut(List<ProductsOs> list) {
		if(list.size()>0)
			if(repositoryOs.findAllByOrcamentoId(list.get(0).getIdOrcamento(), "products_out ").isEmpty())
				return repositoryOs.createNewOs(list, "products_out ");
		return false;
	}

	public boolean createNewOSIn(List<ProductsOs> list) {
		if(list.size()>0)
			if(repositoryOs.findAllByOrcamentoId(list.get(0).getIdOrcamento(), "products_inp ").isEmpty())
				return repositoryOs.createNewOs(list, "products_inp ");
		return false;
	}

	public List<ProductsOs> findAllIn() {
		return repositoryOs.findAll("products_inp ");
	}	
	
}
