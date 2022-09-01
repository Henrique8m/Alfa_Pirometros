package com.hrodriguesdev.controller;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.service.OrcamentoService;

import javafx.collections.ObservableList;


public class OrcamentoController {
	private OrcamentoService orcamentoService = new OrcamentoService();
	private EquipamentoController equipamentoController = new EquipamentoController();
	
	public Long add(Orcamento orcamento) {
		return 	orcamentoService.addOrcamento(orcamento);
	}
	
	public Orcamento findById(Long id) throws SQLException {
		return orcamentoService.getOrcamento(id);
	}
	
	public boolean updatede(Orcamento orcamento) {
		return orcamentoService.updatedeOrcamento( orcamento );
	}

	public ObservableList<Equipamento> findAllLaboratorio(boolean laboratorio) {
		List<Orcamento> orcamento = orcamentoService.findAllLaboratorio(laboratorio);
		return equipamentoController.findById(orcamento);
		
	}
	
	public ObservableList<Orcamento> findAllIdEquipamento(Long equipamento_id) {	
		return orcamentoService.findAllIdEquipamento(equipamento_id);
		
	}
	
	public ObservableList<Orcamento> findAll() {
		return orcamentoService.findAll();
	}
	
	public boolean existOrcamento(Long equipamento_id) {		
		return orcamentoService.existOrcamento(equipamento_id);
	}
	
	public boolean updatedeStatusRelatorio(Long id, int status, Orcamento orcamento) {
		return orcamentoService.updatedeStatusRelatorio(id, status, orcamento);
	}

	public Orcamento getOrcamento(Long id) throws SQLException {
		return orcamentoService.getOrcamento(id);
	}


	
}
