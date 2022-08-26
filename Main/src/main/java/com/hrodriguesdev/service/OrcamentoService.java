package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.Orcamento;

public class OrcamentoService {
	private OrcamentoRepository repository = new OrcamentoRepository();
	

	public Long addOrcamento(Orcamento orcamento) {
		
		return repository.add( orcamento );
	}

	public Orcamento getOrcamento(Long id) throws SQLException {
		return repository.getOrcamento(id);
	}

	public boolean updatedeOrcamento(Orcamento orcamento) {
		return repository.updatede( orcamento );
	}

	public List<Orcamento> findAllLaboratorio(boolean laboratorio) {	
		return repository.findAllLaboratorio(laboratorio);	
		
	}
	
	public List<Orcamento> findById(List<Long> orcamento_id) {		
		return repository.findAllLaboratorio(orcamento_id);	
	}

	public boolean existOrcamento(Long equipamento_id) {
		return repository.existOrcamento(equipamento_id);
	}
	
	public boolean updatedeStatusRelatorio(Long id, int status, Orcamento orcamento) {
		return repository.updatedeStatusRelatorio(id, status, orcamento);
	}

	
}
