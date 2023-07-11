package com.hrodriguesdev.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTORelatorio;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.enums.OSStatus;

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

	public boolean update(Orcamento orcamento) {
		return repository.update( orcamento );
	}
	
	public List<Orcamento> findAllLaboratorio(boolean laboratorio) {	
		return repository.findAllLaboratorio(laboratorio);	
		
	}
	
	public ObservableList<Orcamento> findAllIdEquipamento(Long equipamento_id) {	
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		obs.addAll(repository.findAllIdEquipamento(equipamento_id));
		return obs;
		
	}
	
	public List<Orcamento> findAll(){
		return repository.findAll();
	}
	
	public ObservableList<Orcamento> findAll(boolean entrada, boolean saida, boolean mRealizada, boolean mCurso) {
		List<Orcamento> listaFmCurso = new ArrayList<>();		
		List<Orcamento> listaBruta = findAll();	
		
		listaBruta.forEach((listaBru) -> {
			if(!mCurso) {						
				if(!listaBru.getLaboratorio())
					listaFmCurso.add(listaBru);
			}else
				listaFmCurso.add(listaBru);
				
		});
		
		List<Orcamento> listaFCE = listaFmCurso;
		List<EstoqueConsumo> consumoList = itensFind.findAllConsumo();
		List<Orcamento> listaFES = new ArrayList<>();
		
		listaFCE.forEach((listaFEntrada)->{
			if(!entrada) {
					consumoList.forEach((consumo)->{						
						if(consumo.getOrcamento_id().equals(listaFEntrada.getId())) {
							if(!consumo.getEntrada())
								listaFES.add(listaFEntrada);
						}
		
					});
				
			}else listaFES.add(listaFEntrada);
		});
		
		List<Orcamento> listaFESR = new ArrayList<>();
		if(!saida) {
			listaFES.forEach((listaFEntradaSaida)->{
				
					if(listaFEntradaSaida.getEquipamento_id() == 0) {
						consumoList.forEach((consumo)->{						
							if(consumo.getOrcamento_id().equals(listaFEntradaSaida.getId())) {							
								if(!consumo.getSaida())
									listaFESR.add(listaFEntradaSaida);							
							}
			
						});
						
					}else listaFESR.add(listaFEntradaSaida);
				
			});
			
		}else listaFESR.addAll(listaFES);
		
//		listaFES.forEach((objList)-> {
//			consumoList.stream().findFirst().filter((consumo)-> consumo.getOrcamento_id().equals(objList.getId( )) )
//		} );
		
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		
		listaFESR.forEach((listaFEntradaSaidaMrealizada)->{
			if(!mRealizada) {
				if(listaFEntradaSaidaMrealizada.getEquipamento_id() != 0) {
					consumoList.forEach((consumo)->{						
						if(consumo.getOrcamento_id().equals(listaFEntradaSaidaMrealizada.getId())) {							
							if(!consumo.getSaida()){
								obs.add(listaFEntradaSaidaMrealizada);
//								System.out.println("Teste 01 ");
							}
						}		
					});
					
				}else {
					obs.add(listaFEntradaSaidaMrealizada);
//					System.out.println("Teste 02 ");
				}
			}else obs.add(listaFEntradaSaidaMrealizada);
		});
						
		obs.forEach((orcamento)->{
			
			consumoList.forEach((consumo)->{				
				if(consumo.getOrcamento_id().equals(orcamento.getId())) {
					if(consumo.getNfe()!= null)
//						orcamento.setNfe(consumo.getNfe());
					if(consumo.getEntrada())
						orcamento.setSituation("Entrada para Estoque");
					else if(orcamento.getEquipamento_id() != 0) {
						if(orcamento.getStatus() > 13)
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

	public ObservableList<Orcamento> findAllNoCertificado(Long id) {
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
		ObservableList<Orcamento> obs2 = FXCollections.observableArrayList();
		obs.addAll(repository.findAllIdEquipamento(id));
		
		
		for(Orcamento orc: obs) {
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
			if(orcamento.getEquipamento_id()==0)
				if(orcamento.getColetor_id()!=0) {
					 OrcamentoDTORelatorio dto = new OrcamentoDTORelatorio(orcamento);
					 Coletor coletor = coletorController.findById(orcamento.getColetor_id());
					 dto.setAuthor(coletor.getNomeColetor());
					 dto.setEmpresa(coletor.getEmpresaName());
					 listOSIn.stream().filter(osIn -> osIn.getIdOrcamento().equals(orcamento.getId())).findFirst().ifPresentOrElse( 
							 x -> {
								 dto.setFinalidade("Entrada");
							 },() -> {
								dto.setFinalidade("Saida");
							 });;
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
			if(orcamento.getEquipamento_id()!=0) {
				OrcamentoDTOEquipamento dto = new OrcamentoDTOEquipamento(orcamento);
				try {
					Equipamento equipamento = equipamentoController.findById(orcamento.getEquipamento_id());
					for(OSStatus os : OSStatus.values()) {
						if(os.getStatusInt() == orcamento.getStatus())
							dto.setSituation(os.getStatusStr());
					}		
					dto.setEmpresa(equipamento.getEmpresaName());
					dto.setNs(equipamento.getNs());
					dto.setPat(equipamento.getPat());
				}catch(ExceptionAlfa e) {
					e.printStackTrace();
				}				
				list.add(dto);
			}
		});
		return list;
	}


	
}
