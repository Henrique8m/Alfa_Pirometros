package com.hrodriguesdev.controller;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.service.EquipamentoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EquipamentoController {
	private EquipamentoService equipamentoService = new EquipamentoService();
	
	public ObservableList<Equipamento> getByLaboratorio(boolean laboratorio) throws DbException, SQLException {
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = equipamentoService.getByLaboratorio(laboratorio);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	}
	
	public Long addEquipamento(Equipamento equipamento) {
		return equipamentoService.addEquipamento(equipamento);
	}
	
	public boolean updatedeEquipamento(Long id, int status, Equipamento equipamento) {
		return equipamentoService.updatedeEquipamento(id, status, equipamento);		
	}
	
	public boolean updatedeEquipamentoOrcamento(Long id, Long idOrcamento) {
		return equipamentoService.updatedeEquipamentoOrcamento(id, idOrcamento);		
	}
	
	public boolean UpdatedEquipamento(Equipamento equipamento) {		
		return equipamentoService.UpdatedEquipamento(equipamento);
	}
	
	public Equipamento findEquipamentoNs(String ns) {
		return equipamentoService.findEquipamentoNs(ns);		
	}

	public 
	ObservableList<Equipamento> findAll(Equipamento obj) {
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = equipamentoService.findAll(obj);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}					
		return null;
	}

	public ObservableList<Equipamento> findPageable() {
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = equipamentoService.findAllFirst();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
					
		return null;
	
	}

	public Boolean deleteEquipamento(Long id) {
		return equipamentoService.deleteEquipamento(id);		
	}
}
