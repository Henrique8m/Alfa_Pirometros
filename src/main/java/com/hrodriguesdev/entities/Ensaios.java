package com.hrodriguesdev.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ensaios {
	private Long id;


	private Long orcamento_id;
	private Long equipamento_id;
	private String referencia = "-\n-\n-";
	private String primeiro = "-\n-\n-";
	private String segundo = "-\n-\n-";
	private String terceiro = "-\n-\n-";
	
	public Ensaios(Long equipamento_id, Long orcamento_id) {
		this.equipamento_id = equipamento_id;
		this.orcamento_id = orcamento_id;
	}
	
	public Ensaios(ResultSet rs) {
		try {
			this.id = rs.getLong("id");
			this.equipamento_id = rs.getLong("equipamento_id");
			this.orcamento_id = rs.getLong("orcamento_id");
			this.referencia = rs.getString("referencia1");
			this.primeiro = rs.getString("valor1");
			this.segundo = rs.getString("valor2");
			this.terceiro = rs.getString("valor3");
			/*
			 * EnsaioViewController.logger.error("Ensaio id = "+ id + "\n " +
			 * "Equipamento_id = " + equipamento_id + "\n " +"Orcamento id = " +
			 * orcamento_id + "\n " +"Referencia: " + referencia + "\n " +"Valor 1: " +
			 * primeiro + "\n " +"Valor 2:" + segundo + "\n " +"Valor 3: " + terceiro);
			 */
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getPrimeiro() {
		return primeiro;
	}
	public void setPrimeiro(String primeiro) {
		this.primeiro = primeiro;
	}
	public String getSegundo() {
		return segundo;
	}
	public void setSegundo(String segundo) {
		this.segundo = segundo;
	}
	public String getTerceiro() {
		return terceiro;
	}
	public void setTerceiro(String terceiro) {
		this.terceiro = terceiro;
	}	
	
	public Long getOrcamento_id() {
		return orcamento_id;
	}

	public void setOrcamento_id(Long orcamento_id) {
		this.orcamento_id = orcamento_id;
	}

	public Long getEquipamento_id() {
		return equipamento_id;
	}

	public void setEquipamento_id(Long equipamento_id) {
		this.equipamento_id = equipamento_id;
	}


}
