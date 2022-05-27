package com.hrodriguesdev.service;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.db.EquipamentoRepository;
import com.hrodriguesdev.entities.Equipamento;

//@Service
public class EquipamentoService {
	private EquipamentoRepository repository = new EquipamentoRepository();
	
	public List<Equipamento> getByLaboratorio(boolean laboratorio) {		
		return repository.getByLaboratorio(laboratorio);
	}	
	
	public Long addEquipamento(Equipamento equipamento) {		
		return repository.addEquipamento(equipamento);
	}
	
	
		
	public Equipamento getById(Long id) {
		return new Equipamento();		
	}

	public Long addPesagem(Equipamento pesagem) {
		return 0l;
	}

	public Boolean updatePesagem(Long idPesagem, Long idMotorista) {
		return true;
	}

	public List<Equipamento> getPesagemByMotoristaId(Long id) {
		return new ArrayList<>();
	}


}
