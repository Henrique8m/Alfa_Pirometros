package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_equipamento")
public class Equipamento implements Serializable {	

	public EstoqueEletronicos getEstoqueEletronicos() {
		return estoqueEletronicos;
	}

	public void setEstoqueEletronicos(EstoqueEletronicos estoqueEletronicos) {
		this.estoqueEletronicos = estoqueEletronicos;
	}

	public EstoqueEletricos getEstoqueempressa() {
		return estoqueempressa;
	}

	public void setEstoqueempressa(EstoqueEletricos estoqueempressa) {
		this.estoqueempressa = estoqueempressa;
	}

	public EstoqueEstetico getEstoqueEstetico() {
		return estoqueEstetico;
	}

	public void setEstoqueEstetico(EstoqueEstetico estoqueEstetico) {
		this.estoqueEstetico = estoqueEstetico;
	}

	public EstoqueSinal getEstoqueSinal() {
		return estoqueSinal;
	}

	public void setEstoqueSinal(EstoqueSinal estoqueSinal) {
		this.estoqueSinal = estoqueSinal;
	}

	public String getEmpressaName() {
		return empressaName;
	}

	public void setEmpressaName(String empressaName) {
		this.empressaName = empressaName;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(String dataChegada) {
		this.dataChegada = dataChegada;
	}

	public String getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
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

	public String getUltimaCalib() {
		return ultimaCalib;
	}

	public void setUltimaCalib(String ultimaCalib) {
		this.ultimaCalib = ultimaCalib;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public EstoqueEletronicos getEstoque() {
		return estoqueEletronicos;
	}

	public void setEstoque(EstoqueEletronicos estoque) {
		this.estoqueEletronicos = estoque;
	}

	public Empressa getEmpressa() {
		return empressa;
	}

	public void setEmpressa(Empressa empressa) {
		this.empressa = empressa;
	}

	public Long getId() {
		return id;
	}

	private static final long serialVersionUID = 1L;
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String empressaName;
	private String modelo;

	private int status;
	private String dataChegada;
	private String dataSaida;	
	private String ns;
	private String pat;
	private String ultimaCalib;
	private String certificado;
	private double valor;
	private boolean laboratorio;
	

	//@OneToOne(mappedBy = "motorista", fetch = FetchType.EAGER)
	private EstoqueEletronicos estoqueEletronicos;
	private EstoqueEletricos estoqueempressa;
	private EstoqueEstetico estoqueEstetico;
	private EstoqueSinal estoqueSinal;
	private Empressa empressa;
	
	public Equipamento() {}
	
	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(boolean laboratorio) {
		this.laboratorio = laboratorio;
	}
	
	
}
