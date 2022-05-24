package com.hrodriguesdev.service;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Motorista;

//@Service
public class MotoristaService {



	
	
	public Motorista getMotoristaById(Long id) {
		return new Motorista();		
	}

	//Foi Concluido
	public List<Motorista> getByFila(Boolean fila) {		
		return new ArrayList<>();
	}

	public Long addMotorista(Motorista motorista) {
			
		return 0l;	
	}
	
	
	public List<Motorista> findAll(Motorista obj) {
		List<Motorista> list = new ArrayList<>();	
	
		/*
		if(obj.getPlaca()!=null && obj.getName()!= null && obj.getData()!=null) {
			System.out.println("Entrou 1");
			return repository.getByPlacaAndNameAndData(obj.getPlaca(), obj.getName(), obj.getData());			
			
		}else if(obj.getPlaca()!=null && obj.getName()!= null) {
			System.out.println("Entrou 2");
			return repository.getByPlacaAndName(obj.getPlaca(), obj.getName());
			
		}else if(obj.getData()!=null && obj.getName()!= null) {
			System.out.println("Entrou 3");
			return repository.getByDataAndName(obj.getData(), obj.getName());			
			
		}else if(obj.getPlaca()!=null && obj.getData()!=null) {
			System.out.println("Entrou 4");
			return repository.getByPlacaAndData(obj.getPlaca(),  obj.getData());
			
		
		}else if(obj.getName()!= null) {
			System.out.println("Entrou 5");
			return repository.getByName(obj.getName());	
			
		
		
		
		}else if(obj.getPlaca()!= null) {
			System.out.println("Entrou 6");
			return repository.getByPlaca(obj.getPlaca());	
			
		}else if(obj.getData()!= null) {
			System.out.println("Entrou 7");
			return repository.getByData(obj.getData());	
			
		}			
		;
		if(obj.getData()!= null) {
			//System.out.println("Entrou 7");
			list.addAll( repository.getByData(obj.getData()) );	
		}
		if(obj.getPlaca()!= null) {
			//System.out.println("Entrou 6");
			list.addAll( repository.getByPlaca(obj.getPlaca()) );
		}*/
		return new ArrayList<>();
	}

	public void saveMotorista(Motorista motorista) {
		
		
	}

	public Boolean updateMotorista(Long idMotorista) {
		return true;
		
	}

	public List<Motorista> findAllFirst() {
		
		return new ArrayList<>();
	}


		
		
		
		
		
}
