package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Entity
//@Table(name = "tb_empressa")
public class Empresa implements Serializable {	

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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
	private String endereco;
	private String cep;
	private int nivelConfianca;
	
	public Empresa() {}

	public Empresa(ResultSet rs) {
		try {
			id = (rs.getLong(1));
			name = (rs.getString(2));
			cidade = (rs.getString(3));
			estado = (rs.getString(4));
			endereco = (rs.getString(5));
			cep = (rs.getString(6));  
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
