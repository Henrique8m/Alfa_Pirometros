package com.hrodriguesdev.service;

import java.sql.SQLException;

import com.hrodriguesdev.db.OrcamentoRepository;
import com.hrodriguesdev.entities.Orcamento;

public class OrcamentoService {
	private OrcamentoRepository repository = new OrcamentoRepository();

	public Long addOrcamento(Orcamento orcamento) {
		
		return repository.addOrcamento( orcamento );
	}

	public Orcamento getOrcamento(Long id) throws SQLException {

		return repository.getOrcamento(id);
	}

	
}
