package com.hrodriguesdev.utilitary;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.ItensRepository;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.ItensRepositoryUpdatede;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.EstoqueCabos;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;
import com.hrodriguesdev.entities.Orcamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("unused")
public class Itens {

	private EstoqueEletricos eletricos;
	private EstoqueEletronicos eletronicos;
	private EstoqueConsumo consumo;
	private EstoqueEstetico estetico;
	private EstoqueSinal sinal;
	private EstoqueCabos cabos;
	private ItensRepositoryFind repository;
		
	public Itens() {
		repository = new ItensRepositoryFind();
	}
	
	public Itens(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		eletricos(orcamento_id, saida, nfe, entrada);
		eletronicos(orcamento_id, saida, nfe, entrada);
		consumo(orcamento_id, saida, nfe, entrada);
		estetico(orcamento_id, saida, nfe, entrada);
		sinal(orcamento_id, saida, nfe, entrada);
		cabos(orcamento_id, saida, nfe, entrada);
	}

	public boolean addItem(String item, int quantidade) {
		if(item != null) {
			
			switch (item) {
			
//			Estoque consumo
			
			case "BotaoLiga":
				consumo.setBotaoLiga(quantidade);	
				return true;
	
			case "BoMeFIIFIIIIndicmax":
				consumo.setBoMeFIIFIIIIndicmax(quantidade);
				return true;
				
			case "CaixaBat":
				consumo.setCaixaBat(quantidade);
				return true;
				
//			eletricos
				
			case "FontCarbIndic":
				eletricos.setFontCarbIndic(quantidade);
		
				return true;
				
			case "FontCarbDelta":
				eletricos.setFontCarbDelta(quantidade);		

				return true;
				
			case "PinFemeAliFII":
				eletricos.setPinFemeAliFII(quantidade);
			
				return true;		
				
			case "PinFemeAliFIII":
				eletricos.setPinFemeAliFIII(quantidade);	
			
				return true;	
				
			case "BatFIIFIII":
				eletricos.setBatFIIFIII(quantidade);	
			
				return true;	
				
			case "BatDescartavel":
				eletricos.setBatDescartavel(quantidade);	
			
				return true;					
				
			case "BatInditemp":
				eletricos.setBatInditemp(quantidade);
		
				return true;				
				
			case "BatLitio":
				eletricos.setBatLitio(quantidade);
		
				return true;	
				
			case "CarrEcil":
				eletricos.setCarrEcil(quantidade);
		
				return true;
				
			case "CarrItalterm":
				eletricos.setCarrItalterm(quantidade);
			
				return true;
				
//			eletronicos
				
			case "PCIFIII":
				eletronicos.setPCIFIII(quantidade);
			
				return true;	
				
			case "PCIFKal":
				eletronicos.setPCIFKal(quantidade);
		
				return true;
				
			case "DispFKal":
				eletronicos.setDispFKal(quantidade);
		
				return true;	
				
			case "CIFII":
				eletronicos.setCIFII(quantidade);
		
				return true;
				
			case "CIIndicmax":
				eletronicos.setCIIndicmax(quantidade);
		
				return true;	
				
			case "sirene":
				eletronicos.setSirene(quantidade);

				return true;	
				
			case "Indicmax":
				eletronicos.setIndicmax(quantidade);

				return true;	
				
			case "FIII":
				eletronicos.setFIII(quantidade);

				return true;	
				
				
//			Estetico
				
			case "MascaraFII":
				estetico.setMascaraFII(quantidade);
	
				return true;
				
			case "MascaraFKal":
				estetico.setMascaraFKal(quantidade);

				return true;
				
			case "MascaraFIII":
				estetico.setMascaraFIII(quantidade);

				return true;
				
			case "MascaraCarbo":
				estetico.setMascaraCarbo(quantidade);
	
				return true;
				
			case "MascaraIndic":
				estetico.setMascaraIndic(quantidade);

				return true;
				
			case "EtiqLatFII":
				estetico.setEtiqLatFII(quantidade);

				return true;
				
			case "EtiqLatFIII":
				estetico.setEtiqLatFIII(quantidade);

				return true;
				
			case "EtiqTrasFII":
				estetico.setEtiqTrasFII(quantidade);
		
				return true;
				
			case "Punho":
				estetico.setPunho(quantidade);
			
				return true;
				
//			Sinal
				
			case "ReceptaculoS":
				sinal.setReceptaculoS(quantidade);
	
				return true;
				
			case "ReceptaculoSU":
				sinal.setReceptaculoSU(quantidade);
			
				return true;
				
			case "ReceptaculoEcil":
				sinal.setReceptaculoEcil(quantidade);
		
				return true;		
				
			case "ReceptaculoK":
				sinal.setReceptaculoK(quantidade);
		
				return true;
				
			case "PlugFS":
				sinal.setPlugFS(quantidade);
		
				return true;
				
			case "PlugFK":
				sinal.setPlugFK(quantidade);
	
				return true;
				
			case "PlugMS":
				sinal.setPlugMS(quantidade);
	
				return true;
				
			case "PlugMK":
				sinal.setPlugMK(quantidade);

				return true;	
				
			case "TomadaS":
				sinal.setTomadaS(quantidade);

				return true;	
//				"", "", "", "", "", ""
			case "Cabo_S_borracha":
				cabos.setS_borracha(quantidade);
				
				return true;
			case "Cabo_S_miolo_lanca":
				cabos.setS_miolo(quantidade);
				
				return true;
			case "Cabo_S_extensao":
				cabos.setS_extensao(quantidade);
				
				return true;
			case "Cabo_K_borracha":
				cabos.setK_borracha(quantidade);
				
				return true;
			case "Cabo_K_Fibra_Fibra":
				cabos.setK_miolo(quantidade);
				
				return true;
			case "Cabo_K_Fibra_Silicone":
				cabos.setK_extensao(quantidade);
				
				return true;
				
			default:
				return false;
			}
		}
		return false;
	
	}
	
	private void eletricos(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		if(eletricos == null)
			eletricos = new EstoqueEletricos(entrada, orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void eletronicos(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		if(eletronicos == null)
			eletronicos = new EstoqueEletronicos(entrada, orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void consumo(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		if(consumo == null)
			consumo = new EstoqueConsumo(entrada, orcamento_id, saida, nfe, 0, 0, 0);
	}

	private void estetico(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		if(estetico == null)
			estetico = new EstoqueEstetico(entrada, orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void sinal(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		if(sinal == null)
			sinal = new EstoqueSinal(entrada, orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void cabos(Long orcamento_id, boolean saida, int nfe, boolean entrada) {
		if(cabos == null)
			cabos = new EstoqueCabos(entrada, orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0);
	}
	
	public boolean saveAll(Orcamento orcamento) throws DbException {
		ItensRepository repository = new ItensRepository();
		OrcamentoRepository orcamentoRe = new OrcamentoRepository();		

		orcamento.setEletronicos( repository.saveEletronicos(eletronicos) );
		orcamento.setEletricos( repository.saveEletricos(eletricos) );
		orcamento.setConsumo( repository.saveConsumo(consumo) );	
		orcamento.setEstetico( repository.saveEstetico(estetico) );
		orcamento.setSinal( repository.saveSinal(sinal) );
		orcamento.setCabos( repository.saveCabos(cabos) );
		if(orcamento.getColetor_id() == null)
			orcamento.setColetor_id(0l);
		return orcamentoRe.setIdItens(orcamento);
	}

	public ObservableList<Orcamento> getAllItens(Long id, String itens) {
		ItensRepositoryFind repository = new ItensRepositoryFind();
		EstoqueConsumo consumo = repository.consumoByOrcamentoId(id);
		EstoqueEstetico estetico = repository.esteticoByOrcamentoId(id);
		EstoqueEletricos eletrico = repository.eletricosByOrcamentoId(id);
		EstoqueEletronicos eletronico = repository.eletronicosByOrcamentoId(id);
		EstoqueSinal sinal = repository.sinalByOrcamentoId(id);
		EstoqueCabos cabos = repository.cabosByOrcamentoId(id);
		
//		List<Orcamento> list = Orcamento.getList(itens ,consumo, estetico, eletrico, eletronico, sinal, cabos);
		ObservableList<Orcamento> obs = FXCollections.observableArrayList();
//		obs.addAll(list);
		
		return obs;
	}
	
	public boolean setSaida(Long orcamento_id) {
		
		ItensRepositoryFind repository = new ItensRepositoryFind();
		
		EstoqueConsumo consumo = repository.consumoByOrcamentoId(orcamento_id);
		EstoqueEstetico estetico = repository.esteticoByOrcamentoId(orcamento_id);
		EstoqueEletricos eletrico = repository.eletricosByOrcamentoId(orcamento_id);
		EstoqueEletronicos eletronico = repository.eletronicosByOrcamentoId(orcamento_id);
		EstoqueSinal sinal = repository.sinalByOrcamentoId(orcamento_id);
		EstoqueCabos cabos = repository.cabosByOrcamentoId(orcamento_id);
		
		ItensRepositoryUpdatede repositoryUpdate = new ItensRepositoryUpdatede();
		consumo.setSaida(true);
		estetico.setSaida(true);
		eletrico.setSaida(true);
		eletronico.setSaida(true);
		sinal.setSaida(true);
		cabos.setSaida(true);
		
		repositoryUpdate.updatedeConsumo(consumo);
		repositoryUpdate.updatedeEletricos(eletrico);
		repositoryUpdate.updatedeEletronicos(eletronico);
		repositoryUpdate.updatedeEstetico(estetico);
		repositoryUpdate.updatedeSinal(sinal);
		repositoryUpdate.updatedeCabos(cabos);
		
		return true;
	}
	
	public boolean setBalanço() {
		return true;
	}

	public EstoqueConsumo getEstoqueConsumo() {		
		return repository.consumoEstoque();
	}
	
	public EstoqueEletricos getEstoqueEletrico() {		
		return repository.eletricosEstoque();
	}
	
	public EstoqueEletronicos getEstoqueEletronicos() {		
		return repository.eletronicosEstoque();
	}
	
	public EstoqueEstetico getEstoqueEstetico() {		
		return repository.esteticoEstoque();
	}
	
	public EstoqueSinal getEstoqueSinal() {		
		return repository.sinalEstoque();
	}
	
	public EstoqueCabos getEstoqueCabos() {		
		return repository.cabosEstoque();
	}
	
}

