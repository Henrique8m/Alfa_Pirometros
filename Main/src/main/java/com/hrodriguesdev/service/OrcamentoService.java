package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.List;

import com.hrodriguesdev.dao.repository.ColetorRepository;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.EstoqueCabos;
import com.hrodriguesdev.entities.Orcamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrcamentoService {
	private OrcamentoRepository repository = new OrcamentoRepository();
	private ItensRepositoryFind itensFind = new ItensRepositoryFind();
	private ColetorRepository coletorRepository = new ColetorRepository();
	

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
		obs.forEach((orcamento)->{
			EstoqueCabos cabos = itensFind.cabosByOrcamentoId(orcamento.getId());
			orcamento.setNfe(cabos.getNfe());
			if(cabos.getEntrada())
				orcamento.setSituation("Entrada para Estoque");
			else if(cabos.getSaida() && orcamento.getEquipamento_id() != 0)
				orcamento.setSituation("Manutenção em Equipamento");
			else if(cabos.getSaida() && orcamento.getEquipamento_id() == 0)
				orcamento.setSituation("Manutenção em area ou venda");
			Coletor coletor;
			if(orcamento.getColetor_id() != null && orcamento.getColetor_id() != 0) {
				coletor = coletorRepository.findColetor(orcamento.getColetor_id());
				orcamento.setEmpressa(coletor.getEmpressaName());
				
			}
				
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
