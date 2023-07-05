package com.hrodriguesdev.entities.DTO;

import java.sql.Date;

import com.hrodriguesdev.entities.Orcamento;

public class OrcamentoDTORelatorio {
	public Long getId() {
		return id;
	}
	private Long id;
	private Date data_chegada;
	private Integer nfe;
	private String empresa;
	private String author;
	private String finalidade;
	
	public OrcamentoDTORelatorio(Orcamento orcamento) {
		this.data_chegada = orcamento.getData_chegada();
		this.nfe = orcamento.getNfe();
		this.id = orcamento.getId();
	}
	
	public void setData_chegada(Date data_chegada) {
		this.data_chegada = data_chegada;
	}
	public void setNfe(Integer nfe) {
		this.nfe = nfe;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getData_chegada() {
		return data_chegada;
	}
	public Integer getNfe() {
		return nfe;
	}
	public String getEmpresa() {
		return empresa;
	}
	public String getAuthor() {
		return author;
	}
	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

}
