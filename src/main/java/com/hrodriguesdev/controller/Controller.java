package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;
import com.hrodriguesdev.service.MotoristaService;
import com.hrodriguesdev.service.PesagemService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Controller {
	
	//@Autowired
	private MotoristaService motoristaService = new MotoristaService();
	
	//@Autowired
	private PesagemService pesagemService = new PesagemService();
		
	
	public ObservableList<Motorista> getByFila(Boolean fila){
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
	
	public ObservableList<Pesagem> getByDescarregando(boolean descarregando) {
		ObservableList<Pesagem> obs = FXCollections.observableArrayList();
		List<Pesagem> list = pesagemService.getByDescarregando(descarregando);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	}
	
	public ObservableList<Pesagem> getPesagemByMotoristaId(long id) {
		ObservableList<Pesagem> obs = FXCollections.observableArrayList();
		List<Pesagem> list = pesagemService.getPesagemByMotoristaId( id );
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	}

	public Motorista getMotoristaById(Long id) {
		return motoristaService.getMotoristaById(id);		
	}
	
	public Long addMotorista(Motorista motorista) {
		return motoristaService.addMotorista(motorista);
		
	}
	
	public Long addPesagem(Pesagem pesagem) {
		return pesagemService.addPesagem(pesagem);
	
	}
	
	public Boolean updateMotorista( Long idMotorista ) {
		return motoristaService.updateMotorista(idMotorista);
	}
	
	public Boolean editMotoristaDaPesagem(Long idPesagem, Long idMotorista) {
		return pesagemService.updatePesagem(idPesagem, idMotorista);
		
	}

}
