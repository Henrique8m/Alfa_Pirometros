package com.hrodriguesdev.entities.DTO;

import java.sql.Date;

import com.hrodriguesdev.entities.Orcamento;

public class OrcamentoDTOEquipamento {
	private Long id;
	private String relatorio;
	private Date data_chegada;
	private Date data_saida;
	private String situation;
	private String empresa;
	private String ns;
	private String pat;
	private boolean laboratorio;
	
	private String orcamentoN;	
	private Date ultimaCalibDate;
	private String modelo;
	
	public OrcamentoDTOEquipamento(Orcamento orcamento) {
		this.relatorio = orcamento.getRelatorio();
		this.data_chegada = orcamento.getData_chegada();
		this.data_saida = orcamento.getData_saida();
		this.laboratorio = orcamento.getLaboratorio();
		this.id = orcamento.getId();
	}
	public Long getId() {
		return id;
	}
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
	public Date getData_saida() {
		return data_saida;
	}
	public void setData_saida(Date date_saida) {
		this.data_saida = date_saida;
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
	public boolean isLaboratorio() {
		return laboratorio;
	}
	public String getOrcamentoN() {
		return orcamentoN;
	}
	public void setOrcamentoN(String orcamentoN) {
		this.orcamentoN = orcamentoN;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Date getUltimaCalibDate() {
		return ultimaCalibDate;
	}
	public void setUltimaCalibDate(Date ultimaCalibDate) {
		this.ultimaCalibDate = ultimaCalibDate;
	}
	
}
