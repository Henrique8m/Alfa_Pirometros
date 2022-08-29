package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.repository.EquipamentoRepository;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@Service
public class EquipamentoService {
	private EquipamentoRepository repository = new EquipamentoRepository();
	private OrcamentoService orcamentoService = new OrcamentoService();
	
//	public ObservableList<Equipamento> findAllByLaboratorio(boolean laboratorio) throws DbException, SQLException  {		
//		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
//		List<Equipamento> list = repository.findAllByLaboratorio(laboratorio);
//		list.sort( (a, b) -> a.getEmpressaName().compareTo(b.getEmpressaName()));
//		obs.addAll(list);
//		return obs;
//	}	
	
	public ObservableList<Equipamento> findAll(Equipamento equipamento) {
		List<Equipamento> equipamentolist = new ArrayList<>();		
		
		if( equipamento.getEmpressaName()!= null) {
			equipamentolist =  repository.findAll(equipamento.getEmpressaName() );			
		}
		if(equipamento.getNs()!= null) {
			if(equipamentolist.size()>0) {
				for(int i = 0; i<equipamentolist.size(); i++) {
					if( !equipamentolist.get(i).getNs().equalsIgnoreCase( equipamento.getNs() ) )
						equipamentolist.remove(i);
				}
			
			}else equipamentolist = repository.findAllNs(equipamento.getNs() );

		}		
		if(equipamento.getPat()!= null) {
			if(equipamentolist.size()>0) {
				for(int i = 0; i<equipamentolist.size(); i++) {
					if( !equipamentolist.get(i).getPat().equalsIgnoreCase( equipamento.getPat() ) )
						equipamentolist.remove(i);
				}		
			}else equipamentolist =  repository.findAllPat(equipamento.getPat() );

		}
		
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		if(equipamentolist!=null) {	
			obs.addAll(getListOrcamento(equipamentolist));
			return obs;
			
		}	
		
		return null;
	}

	public ObservableList<Equipamento> findAll() {	
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = repository.findAll() ;
		if(list!=null) {
			obs.addAll(getListOrcamento(list) );
			return obs;
		}
					
		return null;
	}
	
	public ObservableList<Equipamento> findByName(String name) {		
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = repository.findByName(name) ;
		if(list!=null) {
			obs.addAll(getListOrcamento(list));
			return obs;
		}
		return null;
	}	
	
	public ObservableList<Equipamento> findByIdEmpressa(Long id, Boolean laboratorio) {		
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Equipamento> list = repository.findByIdEmpressa(id, laboratorio) ;
		if(list!=null) {
			obs.addAll(getListOrcamento(list));
			return obs;
		}
		return null;
	}	
	
	public ObservableList<Equipamento> findById(List<Orcamento> orcamento) {			
		ObservableList<Equipamento> obs = FXCollections.observableArrayList();
		List<Long> equipamento_id = new ArrayList<>();
		for(Orcamento orc: orcamento) 
			equipamento_id.add(orc.getEquipamento_id());
		
		List<Equipamento> list = repository.findById(equipamento_id) ;
		list.forEach((equipamento)-> {
			orcamento.forEach( (orcament)-> {
				if( orcament.getEquipamento_id() == equipamento.getId() ) {
					equipamento.setOrcamento(orcament);
				}				
			});			
		});
		
		list.sort( (a, b) -> a.getEmpressaName().compareTo(b.getEmpressaName()));
		obs.addAll(list);
		return obs;
		
	}
	
	public Equipamento findByNs(String ns) {
		Equipamento equipamento = repository.findByNs(ns);			
		Orcamento orcamento;
		if(equipamento != null)
			if(equipamento.getOrcamento_id()!= null && equipamento.getOrcamento_id() != 0l) {
				try {
					orcamento = orcamentoService.getOrcamento( equipamento.getOrcamento_id() );
					equipamento.setOrcamento(orcamento);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		
		return 	null;
	}	
	
	public Long add(Equipamento equipamento) {		
		return repository.add(equipamento);
	}	

	public boolean updatede(Long id, Boolean laboratorioo, Long orcamento_id) {		
		return repository.updatede(id, laboratorioo, orcamento_id);
	}

	public boolean updatedeNsPatModelo(Equipamento equipamento) {
		return repository.updatedeNsPatModelo(equipamento);
	}

	public Boolean delete(Long id) {
		return repository.delete(id);
	}

	private List<Equipamento> getListOrcamento(List<Equipamento> equipamentoList){
		
		List<Long> orcamento_id = new ArrayList<>();			
		for(Equipamento equi: equipamentoList) 
			orcamento_id.add(equi.getOrcamento_id());
		
		List<Orcamento> orcamentolist = orcamentoService.findById(orcamento_id);
		equipamentoList.forEach((equip)-> {
			orcamentolist.forEach( (orcament)-> {
				if( orcament.getEquipamento_id() == equip.getId() ) {
					equip.setOrcamento(orcament);
				}				
			});			
		});
		return equipamentoList;
	}


}
