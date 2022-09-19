package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.Orcamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrcamentoService {
	private OrcamentoRepository repository = new OrcamentoRepository();
	private ItensRepositoryFind itensFind = new ItensRepositoryFind();	

	public Long addOrcamento(Orcamento orcamento) {
		
		return repository.add( orcamento );
	}

	public Orcamento getOrcamento(Long id) throws SQLException {
		return repository.getOrcamento(id);
	}

	public boolean updatedeOrcamento(Orcamento orcamento) {
		return repository.updatede( orcamento );
	}

	public List<Orcamento> findAllLaboratorio(boolean laboratorio) {	
		return repository.findAllLaboratorio(laboratorio);	
		
	}
	
	public ObservableList<Orcamento> findAllIdEquipamento(Long equipamento_id) {	
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		obs.addAll(repository.findAllIdEquipamento(equipamento_id));
		return obs;
		
	}
	
	public ObservableList<Orcamento> findAll() {
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		obs.addAll(repository.findAll());
		List<EstoqueConsumo> consumoList = itensFind.findAllConsumo();
		obs.forEach((orcamento)->{
			
			consumoList.forEach((consumo)->{
				
				if(consumo.getOrcamento_id() == orcamento.getId()) {
					if(consumo.getNfe()!= null)
						orcamento.setNfe(consumo.getNfe());
					if(consumo.getEntrada())
						orcamento.setSituation("Entrada para Estoque");
					else if(orcamento.getEquipamento_id() != 0) {
						if(orcamento.getStatus() == 20)
							orcamento.setSituation("Manutenção de equipamento na área");
						else 
							orcamento.setSituation("Manutenção de equipamento em laboratorio");
					}						
					else if(consumo.getSaida() && orcamento.getEquipamento_id() == 0)
						orcamento.setSituation("Manutenção em area ou venda");

				}

			});
			
		});
		
		return obs;
	}
	
	public List<Orcamento> findById(List<Long> orcamento_id) {		
		return repository.findAllLaboratorio(orcamento_id);	
	}

	public boolean existOrcamento(Long equipamento_id) {
		return repository.existOrcamento(equipamento_id);
	}
	
	public boolean updatedeStatusRelatorio(Long id, int status, Orcamento orcamento) {
		return repository.updatedeStatusRelatorio(id, status, orcamento);
	}


	
}
