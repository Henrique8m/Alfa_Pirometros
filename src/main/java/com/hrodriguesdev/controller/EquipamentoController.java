package com.hrodriguesdev.controller;

import java.sql.SQLException;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.service.EquipamentoService;

import javafx.collections.ObservableList;

public class EquipamentoController {
	private EquipamentoService equipamentoService = new EquipamentoService();
	
	public ObservableList<Equipamento> findAllByLaboratorio(boolean laboratorio) throws DbException, SQLException {
		return equipamentoService.findAllByLaboratorio(laboratorio);
	}
	
	public ObservableList<Equipamento> findAll(Equipamento equipamento) {	
		return equipamentoService.findAll(equipamento);
	}	
	
	public ObservableList<Equipamento> findAll() {		
		return equipamentoService.findAll();	
	}
	
	public Equipamento findByNs(String ns) {
		if( ns != null && ns != "")
			return equipamentoService.findByNs(ns);	
		throw new IllegalArgumentException();
	}	
	
	public Long add(Equipamento equipamento) {
		if( equipamento != null )
			return equipamentoService.add(equipamento);
		throw new IllegalArgumentException();
	}
	
	public boolean updatede(Long id, int status, Equipamento equipamento) {
		return equipamentoService.updatede(id, status, equipamento);		
	}
	
	public boolean updatede(Long id, Long idOrcamento) {
		return equipamentoService.updatede(id, idOrcamento);		
	}
	
	public boolean updated(Equipamento equipamento) {		
		return equipamentoService.updatede(equipamento);
	}

	public Boolean delete(Long id) {
		return equipamentoService.delete(id);		
	}
}
