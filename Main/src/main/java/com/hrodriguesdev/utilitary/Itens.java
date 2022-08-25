package com.hrodriguesdev.utilitary;

import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;

public class Itens {

	private EstoqueEletricos eletricos;
	private EstoqueEletronicos eletronicos;
	private EstoqueConsumo consumo;
	private EstoqueEstetico estetico;
	private EstoqueSinal sinal;
	
	private boolean eletricosCreatede = false;
	private boolean eletronicosCreatede = false;
	private boolean consumoCreatede = false;
	private boolean esteticoCreatede = false;
	private boolean sinalCreatede = false;
	
	public Itens(Long orcamento_id, boolean saida, int nfe) {
		eletricos(orcamento_id, saida, nfe);
		eletronicos(orcamento_id, saida, nfe);
		consumo(orcamento_id, saida, nfe);
		estetico(orcamento_id, saida, nfe);
		sinal(orcamento_id, saida, nfe);
	}
	
	public boolean createdItensOrcamento() {
		return true;
	}
	
	public boolean addItem(String item, int quantidade) {
		if(item != null) {
			
			switch (item) {
			
//			Estoque consumo
			
			case "BotaoLiga":
				consumo.setBotaoLiga(quantidade);	
				consumoCreatede = true;
				return true;
	
			case "BoMeFIIFIIIIndicmax":
				consumo.setBoMeFIIFIIIIndicmax(quantidade);
				consumoCreatede = true;
				return true;
				
			case "CaixaBat":
				consumo.setCaixaBat(quantidade);
				consumoCreatede = true;
				return true;
				
//			eletricos
				
			case "FontCarbIndic":
				eletricos.setFontCarbIndic(quantidade);
				eletricosCreatede = true;
				return true;
				
			case "FontCarbDelta":
				eletricos.setFontCarbDelta(quantidade);		
				eletricosCreatede = true;
				return true;
				
			case "PinFemeAliFII":
				eletricos.setPinFemeAliFII(quantidade);
				eletricosCreatede = true;
				return true;		
				
			case "PinFemeAliFIII":
				eletricos.setPinFemeAliFIII(quantidade);	
				eletricosCreatede = true;
				return true;	
				
			case "BatFIIFIII":
				eletricos.setBatFIIFIII(quantidade);	
				eletricosCreatede = true;
				return true;	
				
			case "BatDescartavel":
				eletricos.setBatDescartavel(quantidade);	
				eletricosCreatede = true;
				return true;					
				
			case "BatInditemp":
				eletricos.setBatInditemp(quantidade);
				eletricosCreatede = true;
				return true;				
				
			case "BatLitio":
				eletricos.setBatLitio(quantidade);
				eletricosCreatede = true;
				return true;	
				
			case "CarrEcil":
				eletricos.setCarrEcil(quantidade);
				eletricosCreatede = true;
				return true;
				
			case "CarrItalterm":
				eletricos.setCarrItalterm(quantidade);
				eletricosCreatede = true;
				return true;
				
//			eletronicos
				
			case "PCIFIII":
				eletronicos.setPCIFIII(quantidade);
				eletronicosCreatede = true;
				return true;	
				
			case "PCIFKal":
				eletronicos.setPCIFKal(quantidade);
				eletronicosCreatede = true;
				return true;
				
			case "DispFKal":
				eletronicos.setDispFKal(quantidade);
				eletronicosCreatede = true;
				return true;	
				
			case "CIFII":
				eletronicos.setCIFII(quantidade);
				eletronicosCreatede = true;
				return true;
				
			case "CIIndicmax":
				eletronicos.setCIIndicmax(quantidade);
				eletronicosCreatede = true;
				return true;	
				
			case "sirene":
				eletronicos.setSirene(quantidade);
				eletronicosCreatede = true;
				return true;	
				
			case "Indicmax":
				eletronicos.setIndicmax(quantidade);
				eletronicosCreatede = true;
				return true;	
				
			case "FIII":
				eletronicos.setFIII(quantidade);
				eletronicosCreatede = true;
				return true;	
				
				
//			Estetico
				
			case "MascaraFII":
				estetico.setMascaraFII(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "MascaraFKal":
				estetico.setMascaraFKal(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "MascaraFIII":
				estetico.setMascaraFIII(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "MascaraCarbo":
				estetico.setMascaraCarbo(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "MascaraIndic":
				estetico.setMascaraIndic(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "EtiqLatFII":
				estetico.setEtiqLatFII(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "EtiqLatFIII":
				estetico.setEtiqLatFIII(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "EtiqTrasFII":
				estetico.setEtiqTrasFII(quantidade);
				esteticoCreatede = true;
				return true;
				
			case "Punho":
				estetico.setPunho(quantidade);
				esteticoCreatede = true;
				return true;
				
//			Sinal
				
			case "ReceptaculoS":
				sinal.setReceptaculoS(quantidade);
				sinalCreatede = true;
				return true;
				
			case "ReceptaculoSU":
				sinal.setReceptaculoSU(quantidade);
				sinalCreatede = true;
				return true;
				
			case "ReceptaculoEcil":
				sinal.setReceptaculoEcil(quantidade);
				sinalCreatede = true;
				return true;		
				
			case "ReceptaculoK":
				sinal.setReceptaculoK(quantidade);
				sinalCreatede = true;
				return true;
				
			case "PlugFS":
				sinal.setPlugFS(quantidade);
				sinalCreatede = true;
				return true;
				
			case "PlugFK":
				sinal.setPlugFK(quantidade);
				sinalCreatede = true;
				return true;
				
			case "PlugMS":
				sinal.setPlugMS(quantidade);
				sinalCreatede = true;
				return true;
				
			case "PlugMK":
				sinal.setPlugMK(quantidade);
				sinalCreatede = true;
				return true;	
				
			case "TomadaS":
				sinal.setTomadaS(quantidade);
				sinalCreatede = true;
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
	
	public boolean saveAll() {
		
	}
	

}

