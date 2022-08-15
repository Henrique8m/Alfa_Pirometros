package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.EquipamentoRepository;
import com.hrodriguesdev.entities.Equipamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@Service
public class EquipamentoService {
	private EquipamentoRepository repository = new EquipamentoRepository();
	
	public ObservableList<Equipamento> findAllByLaboratorio(boolean laboratorio) throws DbException, SQLException  {		
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = repository.findAllByLaboratorio(laboratorio);
		list.sort( (a, b) -> a.getEmpressaName().compareTo(b.getEmpressaName()));
		obs.addAll(list);
		return obs;
	}	
	
	public ObservableList<Equipamento> findAll(Equipamento equipamento) {
		List<Equipamento> list = new ArrayList<>();
		if( equipamento.getEmpressaName()!= null) {
			list =  repository.findAll(equipamento.getEmpressaName() );			
		}
		if(equipamento.getNs()!= null) {
			if(list.size()>0) {
				for(int i = 0; i<list.size(); i++) {
					if( !list.get(i).getNs().equalsIgnoreCase( equipamento.getNs() ) )
						list.remove(i);
				}
			
			}else list = repository.findAllNs(equipamento.getNs() );

		}		
		if(equipamento.getPat()!= null) {
			if(list.size()>0) {
				for(int i = 0; i<list.size(); i++) {
					if( !list.get(i).getPat().equalsIgnoreCase( equipamento.getPat() ) )
						list.remove(i);
				}		
			}else list =  repository.findAllPat(equipamento.getPat() );

		}
		
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}	
		
		return null;
	}

	public ObservableList<Equipamento> findAll() {	
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = repository.findAll() ;
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
					
		return null;
	}
	
	public ObservableList<Equipamento> findByName(String name) {		
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = repository.findByName(name) ;
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	}	
	
	public Equipamento findByNs(String ns) {
		return repository.findByNs(ns);		
	}	
	
	public Long add(Equipamento equipamento) {		
		return repository.add(equipamento);
	}	

	public boolean updatede(Long id, int status, Equipamento equipamento) {
		return repository.updatede(id, status, equipamento);
	}

	public boolean updatede(Long id, Long idOrcamento) {		
		return repository.updatede(id, idOrcamento);
	}

	public boolean updatede(Equipamento equipamento) {
		return repository.updatede(equipamento);
	}

	public Boolean delete(Long id) {
		return repository.delete(id);
	}

}
