package com.hrodriguesdev.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Orcamento {
	
	private Long id;
	private String Item;
	private Integer quantidade;
	private Long equipamento_id;
	private Date data_chegada;
	private Date data_saida;
	private Boolean laboratorio;
	private String relatorio;
	private Long coletor_id;
	private int status;
	private Long eletricos;
	private Long consumo;
	private Long eletronicos;
	private Long estetico;
	private Long sinal;
	private Long cabos;

	
	
	public Orcamento(Long equipamento_id, Date data_chegada, Boolean laboratorio) {
		this.setEquipamento_id(equipamento_id);
		this.setData_chegada(data_chegada);
		this.setLaboratorio(laboratorio);
	}
		
	public Orcamento(ResultSet rs) {	
		try {			
			this.id = rs.getLong("id");
			if(rs.getString("Item") != null)
				Item = rs.getString("Item");
			
			quantidade = rs.getInt("quantidade");			
			equipamento_id = rs.getLong("equipamento_id");
			if(rs.getDate("data_saida") != null)
				data_saida = rs.getDate("data_saida");
			
			data_chegada = rs.getDate("data_chegada");
			laboratorio = rs.getBoolean("laboratorio");
			coletor_id = rs.getLong("coletor_id");
			relatorio = rs.getString("relatorio");
			status = rs.getInt("status");
			eletricos = rs.getLong("eletricos");
			consumo = rs.getLong("consumo");
			eletronicos = rs.getLong("eletronicos");
			estetico = rs.getLong("estetico");
			sinal = rs.getLong("sinal");
			setCabos(rs.getLong("cabos"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	
	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getEletricos() {
		return eletricos;
	}

	public void setEletricos(Long eletricos) {
		this.eletricos = eletricos;
	}

	public Long getConsumo() {
		return consumo;
	}

	public void setConsumo(Long consumo) {
		this.consumo = consumo;
	}

	public Long getEletronicos() {
		return eletronicos;
	}

	public void setEletronicos(Long eletronicos) {
		this.eletronicos = eletronicos;
	}

	public Long getEstetico() {
		return estetico;
	}

	public void setEstetico(Long estetico) {
		this.estetico = estetico;
	}

	public Long getSinal() {
		return sinal;
	}

	public void setSinal(Long sinal) {
		this.sinal = sinal;
	}

	public Orcamento(String item, Integer quantidade) {
		Item = item;
		this.quantidade = quantidade;
	}
	
	public Orcamento() {}
	



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
		
	public String getItemRealString() {
		return this.Item;
	}
	
	public String getItem() {
		if(Item != null) {
	
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
		return "";
	
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
	


	public Long getEquipamento_id() {
		return equipamento_id;
	}


	public void setEquipamento_id(Long equipamento_id) {
		this.equipamento_id = equipamento_id;
	}


	public Date getData_chegada() {
		return data_chegada;
	}


	public void setData_chegada(Date data_chegada) {
		this.data_chegada = data_chegada;
	}


	public Date getData_saida() {
		return data_saida;
	}


	public void setData_saida(Date data_saida) {
		this.data_saida = data_saida;
	}


	public Boolean getLaboratorio() {
		return laboratorio;
	}


	public void setLaboratorio(Boolean laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Long getColetor_id() {
		return coletor_id;
	}

	public void setColetor_id(Long coletor_id) {
		this.coletor_id = coletor_id;
	}

	public static List<Orcamento> getList(String itens, EstoqueConsumo consumo2, EstoqueEstetico estetico2,
			EstoqueEletricos eletrico, EstoqueEletronicos eletronico, EstoqueSinal sinal2, EstoqueCabos cabos) {
		List<Orcamento> list = new ArrayList<>();
		list.addAll(EstoqueConsumo.orcamentoListConsumo(consumo2));
		list.addAll(EstoqueEstetico.orcamentoListEstetico(estetico2));
		list.addAll(EstoqueEletricos.orcamentoListEletrico(eletrico));
		list.addAll(EstoqueEletronicos.orcamentoListEletronico(eletronico));
		list.addAll(EstoqueSinal.orcamentoListSinal(sinal2));
		list.addAll(EstoqueCabos.cabosListSinal(cabos));
		if(!itens.isBlank()) {
			String[] Itens = itens.split("\n");
			for(String newIten: Itens) {
				list.add( new Orcamento(newIten, 0));
				}
		}
		
		return list;
	}

	public Long getCabos() {
		return cabos;
	}

	public void setCabos(Long cabos) {
		this.cabos = cabos;
	}

}
