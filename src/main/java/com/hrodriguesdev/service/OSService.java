package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.dao.repository.OSRepository;
import com.hrodriguesdev.entities.products.ProductsOs;

public class OSService {

	private OSRepository repository = new OSRepository();

	public boolean createNewOs(List<ProductsOs> list) {
		if(list.size()>0)
			if(repository.findAllByOrcamentoId(list.get(0).getIdProductsOs()).isEmpty())
				return repository.createNewOs(list);
		return false;
	}
	
	
}
