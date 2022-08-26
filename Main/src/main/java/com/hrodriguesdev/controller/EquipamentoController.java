package com.hrodriguesdev.controller;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.service.EquipamentoService;

import javafx.collections.ObservableList;

public class EquipamentoController {
	private EquipamentoService equipamentoService = new EquipamentoService();

	public ObservableList<Equipamento> findByName(String name) throws DbException, SQLException {
		return equipamentoService.findByName(name);
	}
	
	public ObservableList<Equipamento> findByIdEmpressa(Long id, Boolean laboratorio) throws DbException, SQLException {
		return equipamentoService.findByIdEmpressa(id, laboratorio);
	}
		
//		public ObservableList<Equipamento> findAllByLaboratorio(boolean laboratorio) throws DbException, SQLException {
//		return equipamentoService.findAllByLaboratorio(laboratorio);
//	}
	
	public ObservableList<Equipamento> findAll(Equipamento equipamento) {	
		return equipamentoService.findAll(equipamento);
	}	
	
	public ObservableList<Equipamento> findAll() {		
		return equipamentoService.findAll();	
	}

	public ObservableList<Equipamento> findById(List<Orcamento> orcamento) {
		return equipamentoService.findById(orcamento);
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
	
	public boolean updatede(Long id, Boolean laboratorio, Long orcamento_id) {
		return equipamentoService.updatede(id, laboratorio, orcamento_id);		
	}
	
	public boolean updated(Equipamento equipamento) {		
		return equipamentoService.updatede(equipamento);
	}

	public Boolean delete(Long id) {
		return equipamentoService.delete(id);		
	}


}
