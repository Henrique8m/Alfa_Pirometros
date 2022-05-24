package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_pesagem")
public class Pesagem implements Serializable{

	public Motorista getMotorista() {
		return motorista;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}
	private static final long serialVersionUID = 1L;
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer numeroCaixote;
	private Double peso;
	@SuppressWarnings("unused")
	private String dataHora;
	private String data;
	private String hora;
	private String responsavel;
	private Boolean descarregando;
	
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	
	//@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumn(name = "motorista_id")
	private Motorista motorista;
	
	public Pesagem() {}
	
	
	public Pesagem(Integer numeroCaixote, Double peso, String data, String hora, String responsavel, Boolean descarregando) {
		super();
		this.numeroCaixote = numeroCaixote;
		this.peso = peso;
		this.data = data;
		this.hora = hora;
		this.responsavel = responsavel;
		this.descarregando = descarregando;
	}
	public Integer getNumeroCaixote() {
		return numeroCaixote;
	}
	public void setNumeroCaixote(Integer numeroCaixote) {
		this.numeroCaixote = numeroCaixote;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public String getDataHora() {
		return data + " " + hora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}


	public Boolean getDescarregando() {
		return descarregando;
	}


	public void setDescarregando(Boolean descarregando) {
		this.descarregando = descarregando;
	}

	
	
	
}
