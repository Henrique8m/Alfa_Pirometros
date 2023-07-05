package com.hrodriguesdev.entities.DTO;

import java.sql.Date;

public class OrcamentoDTOEquipamento {
	private String relatorio;
	private Date data_chegada;
	private Date date_saida;
	private String situation;
	private String empresa;
	private String ns;
	private String pat;
	
	public String getRelatorio() {
		return relatorio;
	}
	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}
	public Date getData_chegada() {
		return data_chegada;
	}
	public void setData_chegada(Date data_chegada) {
		this.data_chegada = data_chegada;
	}
	public Date getDate_saida() {
		return date_saida;
	}
	public void setDate_saida(Date date_saida) {
		this.date_saida = date_saida;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getNs() {
		return ns;
	}
	public void setNs(String ns) {
		this.ns = ns;
	}
	public String getPat() {
		return pat;
	}
	public void setPat(String pat) {
		this.pat = pat;
	}
	
}
