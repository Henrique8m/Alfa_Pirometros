package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "tb_motorista")
public class Motorista implements Serializable {	

	private static final long serialVersionUID = 1L;
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String phone;
	private String placa;
	private String cidade;
	private String estado;
	private Long cnh;
	private String data;
	private String hora;
	private Boolean fila;
	


	public void setId(Long id) {
		this.id = id;
	}

	//@OneToMany(mappedBy = "motorista", fetch = FetchType.EAGER)
	private List<Pesagem> pesagem = new ArrayList<>();
	
	public Motorista() {}

	public Motorista(String name, String phone, String placa, String cidade, String estado, Long cnh, String data, String hora,Boolean fila) {
		super();
		this.name = name;
		this.phone = phone;
		this.placa = placa;
		this.setCidade(cidade);
		this.estado = estado;
		this.cnh = cnh;
		this.data = data;
		this.hora = hora;
		this.fila = fila;
	}
	
	public List<Pesagem> getPesagem() {
		return pesagem;
	}

	public void setPesagem(Pesagem pesagem) {
		this.pesagem.add(pesagem);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getCnh() {
		return cnh;
	}

	public void setCnh(Long cnh) {
		this.cnh = cnh;
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

	public Long getId() {
		return id;
	}

	public Boolean getFila() {
		return fila;
	}

	public void setFila(Boolean fila) {
		this.fila = fila;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
}
