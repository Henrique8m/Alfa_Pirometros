package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.db.DbException;
import com.hrodriguesdev.db.EquipamentoRepository;
import com.hrodriguesdev.entities.Equipamento;

//@Service
public class EquipamentoService {
	private EquipamentoRepository repository = new EquipamentoRepository();
	
	public List<Equipamento> getByLaboratorio(boolean laboratorio) throws DbException, SQLException  {		
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

	public List<Equipamento> getPesagemByMotoristaId(Long id) {
		return new ArrayList<>();
	}

	public boolean updatedeEquipamento(Long id, int status) {
		return repository.updatedeEquipamento(id, status);
	}

	public boolean updatedeEquipamentoOrcamento(Long id, Long idOrcamento) {
		
		return repository.updatedeEquipamentoOrcamento(id, idOrcamento);
	}

	public Equipamento findEquipamentoNs(String ns) {
		return repository.findEquipamentoNs( ns );
		
	}


}
