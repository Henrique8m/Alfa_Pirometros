package com.hrodriguesdev.controller;

import java.sql.SQLException;

import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.service.OrcamentoService;


public class OrcamentoController {
	private OrcamentoService orcamentoService = new OrcamentoService();
	
	public Long add(Orcamento orcamento) {
		return 	orcamentoService.addOrcamento(orcamento);
	}
	
	public Orcamento findById(Long id) throws SQLException {
		return orcamentoService.getOrcamento(id);
	}
	
	public boolean updatede(Orcamento orcamento) {
		return orcamentoService.updatedeOrcamento( orcamento );
	}

}
