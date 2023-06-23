package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.dao.repository.UnidadeMedidaRepository;
import com.hrodriguesdev.entities.UnidadeMedida;

public class UnidadeMedidaService {	
	private UnidadeMedidaRepository repository = new UnidadeMedidaRepository();
	
	public List<UnidadeMedida> findAll(){
		return repository.findAll();
	}
}
