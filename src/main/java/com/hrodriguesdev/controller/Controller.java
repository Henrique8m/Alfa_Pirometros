package com.hrodriguesdev.controller;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.service.EmpressaService;
import com.hrodriguesdev.service.OrcamentoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Controller {
	
	private EmpressaService empresaService = new EmpressaService();
	private OrcamentoService orcamentoService = new OrcamentoService();

	
	public Empressa findEmpresa(Long empressa) {
		return empresaService.findEmpressa(empressa);
	}
	

	
	public Long addEmpressa(Empressa empressa) {
		return empresaService.addEmpressa(empressa);
		
	}
	
	public ObservableList<String> getEmpressas(){	
		ObservableList<String> obs = FXCollections.observableArrayList();
		List<String> list = empresaService.getAllEmpressa();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	
	}


	public Long addOrcamento(Orcamento orcamento) {
		return 	orcamentoService.addOrcamento(orcamento);
	}
	

	public Orcamento getOrcamento(Long id) throws SQLException {
		return orcamentoService.getOrcamento(id);
	}

	public Long findEmpresaId(String empresaName) {
		return empresaService.findEmpresaId(empresaName);
		
	}



	public boolean updatedeOrcamento(Orcamento orcamento) {
		return orcamentoService.updatedeOrcamento( orcamento );
	}




}
