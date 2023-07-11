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
	
	public Long add(Empresa empresa) {
		return empresaService.addEmpresa(empresa);
		
	}
	public Empresa find(Long empresa) {
		return empresaService.findEmpresa(empresa);
	}

	public Long isExist(String empresaName) {
		return empresaService.findEmpresaId(empresaName);
		
	}

	public Long findEmpresaId(String name) {
		return empresaService.findEmpresaId(name);
	}

	public Empresa findEmpresa(Long id) {
		return empresaService.findEmpresa(id);
	}

	public boolean updateEmpresa(Empresa empresa, Boolean nameUpdate) {
		return empresaService.updateEmpresa(empresa, nameUpdate);
	}

	public void addEmpresa(Empresa empresa) {
		empresaService.addEmpresa(empresa);		
	}

	public boolean exist(String name) {
		return empresaService.exist(name);
	}

	public boolean delete(Empresa empresa) {
		return empresaService.delete(empresa);
	}
}
