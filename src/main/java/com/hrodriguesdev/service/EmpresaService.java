package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.dao.repository.EmpresaRepository;
import com.hrodriguesdev.entities.Empresa;

//@Service
public class EmpresaService {
	private EmpresaRepository repository = new EmpresaRepository();
	
	public Long addEmpresa(Empresa empressa) {
		return repository.addEmpressa(empressa);
		
	}

	public List<String> getAll() {
		return repository.getAll();
	}

	public Long findEmpresaId(String empresaName) {
		return repository.findEmpresaId(empresaName);
		
	}

	public Empresa findEmpresa(Long empressa) {
		return repository.findEmpressa(empressa);
	}

	public List<Empresa> findAll() {
		return repository.getAllEmpresa();
	}

	public boolean updateEmpresa(Empresa empresa, Boolean nameUpdate) {
		return repository.updateEmpresa(empresa, nameUpdate);
	}

	public boolean exist(String name) {
		return repository.exist(name);
	}

	public boolean delete(Empresa empresa) {
		return repository.delete(empresa);
	}
	
	
}
