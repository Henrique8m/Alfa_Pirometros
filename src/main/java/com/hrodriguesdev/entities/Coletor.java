package com.hrodriguesdev.entities;

import java.io.Serializable;

public class Coletor implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String empressaName;
	private Long equipamento_id;
	private String nomeColetor;
	private String dataHoraColeta;
		
	public Coletor(){}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpressaName() {
		return empressaName;
	}
	public void setEmpressaName(String empressaName) {
		this.empressaName = empressaName;
	}
	public String getNomeColetor() {
		return nomeColetor;
	}
	public void setNomeColetor(String nomeColetor) {
		this.nomeColetor = nomeColetor;
	}

	public Long getEquipamento_id() {
		return equipamento_id;
	}

	public void setEquipamento_id(Long empressa_id) {
		this.equipamento_id = empressa_id;
	}


	public String getDataHoraColeta() {
		return dataHoraColeta;
	}


	public void setDataHoraColeta(String dataHoraColeta) {
		this.dataHoraColeta = dataHoraColeta;
	}


}
