package com.hrodriguesdev.utilitary;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.ItensRepository;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;
import com.hrodriguesdev.entities.Orcamento;

public class Itens {

	private EstoqueEletricos eletricos;
	private EstoqueEletronicos eletronicos;
	private EstoqueConsumo consumo;
	private EstoqueEstetico estetico;
	private EstoqueSinal sinal;
		
	public Itens(Long orcamento_id, boolean saida, int nfe) {
		eletricos(orcamento_id, saida, nfe);
		eletronicos(orcamento_id, saida, nfe);
		consumo(orcamento_id, saida, nfe);
		estetico(orcamento_id, saida, nfe);
		sinal(orcamento_id, saida, nfe);
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
				
			default:
				return false;
			}
		}
		return false;
	
	}
	
	private void eletricos(Long orcamento_id, boolean saida, int nfe) {
		if(eletricos == null)
			eletricos = new EstoqueEletricos(orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void eletronicos(Long orcamento_id, boolean saida, int nfe) {
		if(eletronicos == null)
			eletronicos = new EstoqueEletronicos(orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void consumo(Long orcamento_id, boolean saida, int nfe) {
		if(consumo == null)
			consumo = new EstoqueConsumo(orcamento_id, saida, nfe, 0, 0, 0);
	}

	private void estetico(Long orcamento_id, boolean saida, int nfe) {
		if(estetico == null)
			estetico = new EstoqueEstetico(orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	private void sinal(Long orcamento_id, boolean saida, int nfe) {
		if(sinal == null)
			sinal = new EstoqueSinal(orcamento_id, saida, nfe, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public boolean saveAll(Orcamento orcamento) throws DbException {
		ItensRepository repository = new ItensRepository();
		OrcamentoRepository orcamentoRe = new OrcamentoRepository();		

		orcamento.setEletronicos( repository.saveEletronicos(eletronicos) );
		orcamento.setEletricos( repository.saveEletricos(eletricos) );
		orcamento.setConsumo( repository.saveConsumo(consumo) );	
		orcamento.setEstetico( repository.saveEstetico(estetico) );
		orcamento.setSinal( repository.saveSinal(sinal) );
		return orcamentoRe.setIdItens(orcamento);
	}
	
	public String allItens(Long orcamento_id) {
		ItensRepositoryFind find = new ItensRepositoryFind();
		find.consumoByOrcamentoId(orcamento_id).toString();
		find.eletricosByOrcamentoId(orcamento_id);
		find.eletronicosByOrcamentoId(orcamento_id);
		find.esteticoByOrcamentoId(orcamento_id);
		find.sinalByOrcamentoId(orcamento_id);
		
		
	}
	
}

