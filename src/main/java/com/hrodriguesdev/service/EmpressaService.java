package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.db.EmpressaRepository;
import com.hrodriguesdev.entities.Empressa;

//@Service
public class EmpressaService {
	private EmpressaRepository repository = new EmpressaRepository();
	
	public Long addEmpressa(Empressa empressa) {
		return repository.addEmpressa(empressa);
		
	}

	public List<String> getAllEmpressa() {
		return repository.getAllEmpressa();
	}

	public Long findEmpresaId(String empresaName) {
		return repository.findEmpresaId(empresaName);
		
	}
	
	
}
