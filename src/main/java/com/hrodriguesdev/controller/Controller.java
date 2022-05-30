package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.service.EmpressaService;
import com.hrodriguesdev.service.EquipamentoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Controller {
	private EquipamentoService equipamentoService = new EquipamentoService();
	private EmpressaService empressaService = new EmpressaService();
	
	
		
	
	public ObservableList<Equipamento> getByLaboratorio(boolean laboratorio) {
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
		return empressaService.addEmpressa(empressa);
		
	}
	
	public ObservableList<String> getEmpressas(){	
		ObservableList<String> obs = FXCollections.observableArrayList();
		List<String> list = empressaService.getAllEmpressa();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	
	}

	public boolean updatedeEquipamento(Long id, int status) {
		return equipamentoService.updatedeEquipamento(id, status);
		
	}

	/*
	 * 
	public ObservableList<Equipamento> getByFila(Boolean fila){
		ObservableList<Motorista> obs = FXCollections.observableArrayList();		
		List<Motorista> list = motoristaService.getByFila(true);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return obs;
	}
	
	public ObservableList<Motorista> findAll(Motorista obj) {
		ObservableList<Motorista> obs = FXCollections.observableArrayList();
		List<Motorista> list = motoristaService.findAll(obj);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;			
	}

	public ObservableList<Motorista> findPageable() {
		ObservableList<Motorista> obs = FXCollections.observableArrayList();
		List<Motorista> list = motoristaService.findAllFirst();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;	
	}
	
	

	
	public ObservableList<Pesagem> getPesagemByMotoristaId(long id) {
		ObservableList<Pesagem> obs = FXCollections.observableArrayList();
		List<Pesagem> list = equipamentoService.getPesagemByMotoristaId( id );
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	}

	public Motorista getMotoristaById(Long id) {
		return motoristaService.getMotoristaById(id);		
	}
	

	
	public Long addPesagem(Pesagem pesagem) {
		return equipamentoService.addPesagem(pesagem);
	
	}
	
	public Boolean updateMotorista( Long idMotorista ) {
		return motoristaService.updateMotorista(idMotorista);
	}
	
	public Boolean editMotoristaDaPesagem(Long idPesagem, Long idMotorista) {
		return equipamentoService.updatePesagem(idPesagem, idMotorista);
		
	}*/

}
