package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_consumo")
public class EstoqueConsumo implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private int BotaoLiga;
	private int BoMeFIIFIIIIndicmax;
	private int CaixaBat;
	
	public Long getOrcamento_id() {
		return orcamento_id;
	}
	public void setOrcamento_id(Long orcamento_id) {
		this.orcamento_id = orcamento_id;
	}
	public Boolean getSaida() {
		return saida;
	}
	public void setSaida(Boolean saida) {
		this.saida = saida;
	}
	public int getNfe() {
		return nfe;
	}
	public void setNfe(int nfe) {
		this.nfe = nfe;
	}
	public int getBotaoLiga() {
		return BotaoLiga;
	}
	public void setBotaoLiga(int botaoLiga) {
		BotaoLiga = botaoLiga;
	}
	public int getBoMeFIIFIIIIndicmax() {
		return BoMeFIIFIIIIndicmax;
	}
	public void setBoMeFIIFIIIIndicmax(int boMeFIIFIIIIndicmax) {
		BoMeFIIFIIIIndicmax = boMeFIIFIIIIndicmax;
	}
	public int getCaixaBat() {
		return CaixaBat;
	}
	public void setCaixaBat(int caixaBat) {
		CaixaBat = caixaBat;
	}
	public Long getId() {
		return id;
	}

	
	
}
