package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.service.EmpressaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpressaController {
	private EmpressaService empresaService = new EmpressaService();
	
	public ObservableList<String> findAll(){	
		ObservableList<String> obs = FXCollections.observableArrayList();
		List<String> list = empresaService.getAllEmpressa();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	
	}
	
	public Long add(Empressa empressa) {
		return empresaService.addEmpressa(empressa);
		
	}
	public Empressa find(Long empressa) {
		return empresaService.findEmpressa(empressa);
	}

	public Long isExist(String empresaName) {
		return empresaService.findEmpresaId(empresaName);
		
	}
}
