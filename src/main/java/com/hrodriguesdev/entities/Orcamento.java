package com.hrodriguesdev.entities;

public class Orcamento {
	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
		
	public String getItem() {
		switch (Item) {
		case "BotaoLiga":
			return "Botão de Liga";

		case "BoMeFIIFIIIIndicmax":
			
			return "Botão de Memoria";
		case "CaixaBat":
			
			return "Caixa da Bateria";
		case "FontCarbIndic":
				
			return "Fonte alimentação";	
		case "FontCarbDelta":
					
			return "Fonte alimentação";		
		case "PinFemeAliFII":
						
			return "Pino Femêa Alimentação";		
		case "PinFemeAliFIII":
							
			return "Pino Femêa Alimentação";	
		case "BatFIIFIII":
						
			return "Bateria";	
		case "BatDescartavel":
									
			return "Bateria 9v";					
		case "BatInditemp":
			
			return "Bateria";				
		case "BatLitio":
			
			return "Bateria Litium";	
		case "CarrEcil":
					
			return "Carregador 6Vcc";	
		case "CarrItalterm":
			
			return "Carregador 18Vcc";	
		case "PCIFIII":
			
			return "PCI Fornero III";	
		case "PCIFKal":
			
			return "PCI Fornero Kal";
		case "DispFKal":
			
			return "Display Fornero Kall";		
		case "CIFII":
					
			return "CI Microcontrolador";
		case "CIIndicmax":
			
			return "CI Microcontrolador";		
		case "sirene":
			
			return "Sirene fim medição";		
		case "MascaraFII":
			
			return "Mascara";
		case "MascaraFKal":
			
			return "Mascara";
		case "MascaraFIII":
			
			return "Mascara";
		case "MascaraCarbo":
			
			return "Mascara";
		case "MascaraIndic":
			
			return "Mascara";
		case "EtiqLatFII":
			
			return "Etiqueta";
		case "EtiqLatFIII":
			
			return "Etiqueta";
		case "EtiqTrasFII":
			
			return "Etiqueta";
		case "ReceptaculoS":
			
			return "Receptaculo S";
		case "ReceptaculoSU":
			
			return "Receptaculo SU";
		case "ReceptaculoEcil":
			
			return "Receptaculo Ecil";			
		case "ReceptaculoK":
			
			return "Receptaculo K";
		case "PlugFS":
			
			return "Plug Fêmea S";
		case "PlugFK":
			
			return "Plug Fêmea K";
		case "PlugMS":
			
			return "Plug Macho S";
		case "PlugMK":
			
			return "Plug Macho K";			
		case "TomadaS":
			
			return "Tomada S";				
		default:
			return Item;
		}
	
	}
	public void setItem(String item) {
		Item = item;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	private Long id;
	private String Item;
	private Integer quantidade;
	
	public Orcamento(String item, Integer quantidade) {
		Item = item;
		this.quantidade = quantidade;
	}
	public Orcamento() {}

}
