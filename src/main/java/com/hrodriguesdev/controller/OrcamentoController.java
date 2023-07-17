package com.hrodriguesdev.controller;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTORelatorio;
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
	
	public boolean update(Orcamento orcamento) {
		return orcamentoService.update( orcamento );
	}
	
	public boolean insertColetorAndNfe(Orcamento orcamento) {
		return orcamentoService.insertColetorAndNfe( orcamento );
	}
	

	public ObservableList<Equipamento> findAllLaboratorio(boolean laboratorio) {
		List<Orcamento> orcamento = orcamentoService.findAllLaboratorio(laboratorio);
		return equipamentoController.findByIdOrcamento(orcamento);
		
	}
	
	public ObservableList<Orcamento> findAllIdEquipamento(Long equipamento_id) {	
		return orcamentoService.findAllIdEquipamento(equipamento_id);
		
	}
	
//	public ObservableList<Orcamento> findAll(boolean entrada, boolean saida, boolean mRealizada, boolean mCurso) {
//		return orcamentoService.findAll(entrada, saida, mRealizada, mCurso);
//	}
	
	public List<Orcamento> findAll(){
		return orcamentoService.findAll();
	}
	
	public List<OrcamentoDTORelatorio> findAllDTORelatorio(){
		return orcamentoService.findAllDTORelatorio();
	}
	
	public List<OrcamentoDTOEquipamento> findAllDTOEquipamento(){
		return orcamentoService.findAllDTOEquipamento();
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

	public ObservableList<Orcamento> findAllNoCertificado(Long id) {
		return orcamentoService.findAllNoCertificado(id);
	}

	public boolean updateDataSaida(Orcamento orcamento) {
		return orcamentoService.updateDataSaida(orcamento);
	}


	
}
