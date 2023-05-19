package com.hrodriguesdev.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ensaios {
	private Long id;


	private Long orcamento_id;
	private String referencia = "-\n-\n-";
	private String primeiro = "-\n-\n-";
	private String segundo = "-\n-\n-";
	private String terceiro = "-\n-\n-";
	
	public Ensaios() {}
	
	public Ensaios(ResultSet rs) {
		try {
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");
			this.referencia = rs.getString("referencia1");
			this.primeiro = rs.getString("valor1");
			this.segundo = rs.getString("valor2");
			this.terceiro = rs.getString("valor3");
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


}
