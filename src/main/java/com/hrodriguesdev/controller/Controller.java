package com.hrodriguesdev.controller;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.db.DbException;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.service.ColetorService;
import com.hrodriguesdev.service.EmpressaService;
import com.hrodriguesdev.service.EquipamentoService;
import com.hrodriguesdev.service.OrcamentoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Controller {
	private EquipamentoService equipamentoService = new EquipamentoService();
	private EmpressaService empresaService = new EmpressaService();
	private OrcamentoService orcamentoService = new OrcamentoService();

	
		
	
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

	public boolean updatedeEquipamento(Long id, int status, Equipamento equipamento) {
		return equipamentoService.updatedeEquipamento(id, status, equipamento);
		
	}
	
	public boolean updatedeEquipamentoOrcamento(Long id, Long idOrcamento) {
		return equipamentoService.updatedeEquipamentoOrcamento(id, idOrcamento);
		
	}
	
	public boolean UpdatedEquipamento(Equipamento equipamento) {
		
		return equipamentoService.UpdatedEquipamento(equipamento);
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

	public Equipamento findEquipamentoNs(String ns) {
		return equipamentoService.findEquipamentoNs( ns );
		
	}

	public Empressa findEmpresa(Long empressa) {
		return empresaService.findEmpressa(empressa);
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

	public boolean updatedeOrcamento(Orcamento orcamento) {
		return orcamentoService.updatedeOrcamento( orcamento );
	}




}
