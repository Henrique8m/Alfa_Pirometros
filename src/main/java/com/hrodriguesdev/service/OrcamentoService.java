package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTORelatorio;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.enums.OSStatus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrcamentoService {
	private OrcamentoRepository repository = new OrcamentoRepository();

	public Long addOrcamento(Orcamento orcamento) {

		return repository.add(orcamento);
	}

	public Orcamento getOrcamento(Long id) throws SQLException {
		return repository.getOrcamento(id);
	}

	public boolean update(Orcamento orcamento) {
		return repository.updatede(orcamento);
	}

	public boolean insertColetorAndNfe(Orcamento orcamento) {
		return repository.insertColetorAndNfe(orcamento);
	}

	public List<Orcamento> findAllLaboratorio(boolean laboratorio) {
		return repository.findAllLaboratorio(laboratorio);

	}

	public ObservableList<Orcamento> findAllIdEquipamento(Long equipamento_id) {
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		obs.addAll(repository.findAllIdEquipamento(equipamento_id));
		return obs;

	}

	public List<Orcamento> findAll() {
		return repository.findAll();
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

	public ObservableList<Orcamento> findAllNoCertificado(Long id) {
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		ObservableList<Orcamento> obs2 = FXCollections.observableArrayList();
		obs.addAll(repository.findAllIdEquipamento(id));

		for (Orcamento orc : obs) {
//			Ensaios ensaios = ensaioController.findByOrcamentoId(orc.getId());
//			if(ensaios==null)
			obs2.add(orc);
		}

		return obs2;
	}

	public List<OrcamentoDTORelatorio> findAllDTORelatorio() {
		OSController osController = new OSController();
		List<ProductsOs> listOSIn = osController.findAllIn();
		ColetorController coletorController = new ColetorController();
		List<OrcamentoDTORelatorio> list = new ArrayList<>();
		List<Orcamento> listOrcamento = findAll();
		listOrcamento.forEach(orcamento -> {
			if (orcamento.getEquipamento_id() == 0)
				if (orcamento.getColetor_id() != 0) {
					OrcamentoDTORelatorio dto = new OrcamentoDTORelatorio(orcamento);
					Coletor coletor = coletorController.findById(orcamento.getColetor_id());
					dto.setAuthor(coletor.getNomeColetor());
					dto.setEmpresa(coletor.getEmpresaName());
					listOSIn.stream().filter(osIn -> osIn.getIdOrcamento().equals(orcamento.getId())).findFirst()
							.ifPresentOrElse(x -> {
								dto.setFinalidade("Entrada");
							}, () -> {
								dto.setFinalidade("Saida");
							});
					;
					list.add(dto);
				}
		});
		return list;
	}

	public List<OrcamentoDTOEquipamento> findAllDTOEquipamento() {
		EquipamentoController equipamentoController = new EquipamentoController();
		List<OrcamentoDTOEquipamento> list = new ArrayList<>();
		List<Orcamento> listOrcamento = findAll();
		listOrcamento.forEach(orcamento -> {
			if (orcamento.getEquipamento_id() != 0) {
				OrcamentoDTOEquipamento dto = new OrcamentoDTOEquipamento(orcamento);
				try {
					Equipamento equipamento = equipamentoController.findById(orcamento.getEquipamento_id());
					for (OSStatus os : OSStatus.values()) {
						if (os.getStatusInt() == orcamento.getStatus())
							dto.setSituation(os.getStatusStr());
					}
					dto.setEmpresa(equipamento.getEmpresaName());
					dto.setNs(equipamento.getNs());
					dto.setPat(equipamento.getPat());
				} catch (ExceptionAlfa e) {
					e.printStackTrace();
				}
				list.add(dto);
			}
		});
		return list;
	}

}
