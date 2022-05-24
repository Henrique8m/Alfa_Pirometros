package com.hrodriguesdev.service;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Pesagem;

//@Service
public class PesagemService {
	
		
	public Pesagem getById(Long id) {
		return new Pesagem();		
	}

	public Long addPesagem(Pesagem pesagem) {
		return 0l;
	}

	public List<Pesagem> getByDescarregando(boolean descarregando) {
		return new ArrayList<>();
	}


	public Boolean updatePesagem(Long idPesagem, Long idMotorista) {
		return true;
	}

	public List<Pesagem> getPesagemByMotoristaId(Long id) {
		return new ArrayList<>();
	}
}
