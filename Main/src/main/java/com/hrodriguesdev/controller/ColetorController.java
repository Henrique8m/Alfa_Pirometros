package com.hrodriguesdev.controller;

import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.service.ColetorService;

public class ColetorController {
	private ColetorService coletorService = new ColetorService();
	
	public Coletor findById(Long id) {
		return coletorService.findColetor(id);
	}
	public Long add(Coletor coletor) {		
		return coletorService.addColetor(coletor);
	}
}
