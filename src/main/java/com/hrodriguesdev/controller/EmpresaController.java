package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.service.EmpresaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpresaController {
	private EmpresaService empresaService = new EmpresaService();
	
	public ObservableList<String> findAll(){	
		ObservableList<String> obs = FXCollections.observableArrayList();
		List<String> list = empresaService.getAll();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	
	}
	
	public ObservableList<Empresa> findAllEmpresa(){	
		ObservableList<Empresa> obs = FXCollections.observableArrayList();
		List<Empresa> list = empresaService.getAllEmpresa();
		list.sort((x,y)-> x.getName().compareTo(y.getName()) );
		if(list!=null) 
			obs.addAll(list);
		return obs;
	
	}
	
	public Long add(Empresa empressa) {
		return empresaService.addEmpressa(empressa);
		
	}
	public Empresa find(Long empressa) {
		return empresaService.findEmpressa(empressa);
	}

	public Long isExist(String empresaName) {
		return empresaService.findEmpresaId(empresaName);
		
	}
}