package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.dao.repository.EmpresaRepository;
import com.hrodriguesdev.entities.Empresa;

//@Service
public class EmpresaService {
	private EmpresaRepository repository = new EmpresaRepository();
	
	public Long addEmpressa(Empresa empressa) {
		return repository.addEmpressa(empressa);
		
	}

	public List<String> getAll() {
		return repository.getAll();
	}

	public Long findEmpresaId(String empresaName) {
		return repository.findEmpresaId(empresaName);
		
	}

	public Empresa findEmpressa(Long empressa) {
		return repository.findEmpressa(empressa);
	}

	public List<Empresa> getAllEmpresa() {
		return repository.getAllEmpresa();
	}
	
	
}
