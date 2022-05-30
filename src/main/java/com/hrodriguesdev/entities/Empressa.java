package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_empressa")
public class Empressa implements Serializable {	

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public int getNivelConfianca() {
		return nivelConfianca;
	}

	public void setNivelConfianca(int nivelConfianca) {
		this.nivelConfianca = nivelConfianca;
	}

	private static final long serialVersionUID = 1L;
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String cidade;
	private String estado;
	private String endereço;
	private String cep;
	private int nivelConfianca;
	
	public Empressa() {}
	
	
	

}
