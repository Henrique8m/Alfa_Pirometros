package com.hrodriguesdev.service;

import java.sql.SQLException;

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

	
}
