package com.hrodriguesdev.service;

import com.hrodriguesdev.db.ColetorRepository;
import com.hrodriguesdev.entities.Coletor;

public class ColetorService {
	private ColetorRepository repository = new ColetorRepository();

	public Long addColetor(Coletor coletor) {
		return repository.addColetor(coletor);
	}

	public Coletor findColetor(Long coletor_id) {
		return repository.findColetor(coletor_id);
	}
	
	
}
