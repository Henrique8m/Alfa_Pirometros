package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.service.UnidadeMedidaService;

import javafx.collections.ObservableList;

public class UnidadeMedidaController {

	private UnidadeMedidaService service = new UnidadeMedidaService();
	
	public ObservableList<UnidadeMedida> findAllUnidadeMedidasObs() {		
		return service.findAllUnidadeMedida();
	}

	public boolean createNewUnit(UnidadeMedida unidadeMedida) {
		return service.createNewUnit(unidadeMedida);
	}

	public boolean updateUnidade(UnidadeMedida unidade) {
		return service.updateUnidade(unidade);
	}

	public boolean delete(Long id) throws ExceptionAlfa{		
		return service.delete(id);
	}

	public List<UnidadeMedida> findAll() {
		return service.findAll();
	}

}
