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
	

	private OrcamentoService orcamentoService = new OrcamentoService();

	
	



	public Long addOrcamento(Orcamento orcamento) {
		return 	orcamentoService.addOrcamento(orcamento);
	}
	

	public Orcamento getOrcamento(Long id) throws SQLException {
		return orcamentoService.getOrcamento(id);
	}




	public boolean updatedeOrcamento(Orcamento orcamento) {
		return orcamentoService.updatedeOrcamento( orcamento );
	}




}
