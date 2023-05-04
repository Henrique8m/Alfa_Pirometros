package com.hrodriguesdev.entities;

import java.io.Serializable;

public class Anotations implements Serializable {	


	private static final long serialVersionUID = 1L;
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String descricao;
	private String referencia;
	
	public Anotations() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	};
	

}
