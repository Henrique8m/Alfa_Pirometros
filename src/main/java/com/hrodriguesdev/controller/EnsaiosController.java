package com.hrodriguesdev.controller;

import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.service.EnsaiosService;

public class EnsaiosController {
	private EnsaiosService service = new EnsaiosService();
	
	public Ensaios getEnsaio(Long idOrcamento) {
		return service.getEnsaio(idOrcamento);
	}
	
	public long saveEnsaio(Ensaios ensaio) {
		return service.saveEnsaio(ensaio);
	}

	public boolean isExistByOrcamentoId(Long orcamentoId) {
		return service.isExistByOrcamentoId(orcamentoId);
	}

	public boolean updatedeEnsaio(Ensaios ensaio) {
		return service.updatedeEnsaio(ensaio);
	}
}
