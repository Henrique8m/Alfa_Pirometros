package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.dao.repository.EmpressaRepository;
import com.hrodriguesdev.entities.Empresa;

//@Service
public class EmpressaService {
	private EmpressaRepository repository = new EmpressaRepository();
	
	public Long addEmpressa(Empresa empressa) {
		return repository.addEmpressa(empressa);
		
	}

	public List<String> getAllEmpressa() {
		return repository.getAllEmpressa();
	}

	public Long findEmpresaId(String empresaName) {
		return repository.findEmpresaId(empresaName);
		
	}

	public Empresa findEmpressa(Long empressa) {
		return repository.findEmpressa(empressa);
	}
	
	
}
