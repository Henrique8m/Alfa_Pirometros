package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.EquipamentoRepository;
import com.hrodriguesdev.entities.Equipamento;

//@Service
public class EquipamentoService {
	private EquipamentoRepository repository = new EquipamentoRepository();
	
	public List<Equipamento> getByLaboratorio(boolean laboratorio) throws DbException, SQLException  {		
		return repository.getByLaboratorio(laboratorio);
	}	
	
	public Long addEquipamento(Equipamento equipamento) {		
		return repository.addEquipamento(equipamento);
	}
	
	
		
	public Equipamento getById(Long id) {
		return new Equipamento();		
	}

	public Long addPesagem(Equipamento pesagem) {
		return 0l;
	}

	public List<Equipamento> getPesagemByMotoristaId(Long id) {
		return new ArrayList<>();
	}

	public boolean updatedeEquipamento(Long id, int status, Equipamento equipamento) {
		return repository.updatedeEquipamento(id, status, equipamento);
	}

	public boolean updatedeEquipamentoOrcamento(Long id, Long idOrcamento) {
		
		return repository.updatedeEquipamentoOrcamento(id, idOrcamento);
	}

	public Equipamento findEquipamentoNs(String ns) {
		return repository.findEquipamentoNs( ns );
		
	}

	public boolean UpdatedEquipamento(Equipamento equipamento) {
		// TODO Auto-generated method stub
		return repository.updatedEquipamento(equipamento);
	}

	public List<Equipamento> findAll(Equipamento obj) {
		List<Equipamento> list = new ArrayList<>();
//		&& obj.getNs()!= null &&  obj.getPat()!= null
		if( obj.getEmpressaName()!= null) {
			list =  repository.getByEmpressa(obj.getEmpressaName() );			
		}
		if(obj.getNs()!= null) {
			if(list.size()>0) {
				for(int i = 0; i<list.size(); i++) {
					if( !list.get(i).getNs().equalsIgnoreCase( obj.getNs() ) )
						list.remove(i);
				}
			
			}else list =  repository.getByNs(obj.getNs() );

		}		
		if(obj.getPat()!= null) {
			if(list.size()>0) {
				for(int i = 0; i<list.size(); i++) {
					if( !list.get(i).getPat().equalsIgnoreCase( obj.getPat() ) )
						list.remove(i);
				}		
			}else list =  repository.getByPat(obj.getPat() );

		}
		

		return list;
	}

	public List<Equipamento> findAllFirst() {	
		return repository.findAllFirst();
	}

	public Boolean deleteEquipamento(Long id) {

		return repository.deleteEquipamento(id);
	}


}
