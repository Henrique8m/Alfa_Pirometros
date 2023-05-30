package com.hrodriguesdev.service;

import com.hrodriguesdev.dao.repository.EnsaioRepository;
import com.hrodriguesdev.entities.Ensaios;

public class EnsaiosService {
	private EnsaioRepository repository = new EnsaioRepository();
	
	public Ensaios findByOrcamentoId(Long idOrcamento) {
		return repository.findByOrcamentoId(idOrcamento);
	}
	
	public long saveEnsaio(Ensaios ensaio) {
		return repository.saveEnsaio(ensaio);
	}

	public boolean isExistByOrcamentoId(Long orcamentoId) {
		return repository.isExistByOrcamentoId(orcamentoId);
	}

	public boolean updatedeEnsaio(Ensaios ensaio) {
		return repository.updatedeEnsaio(ensaio);
	}

	public Ensaios findById(Long id) {		
		return repository.findById(id);
	}
	
	

}
