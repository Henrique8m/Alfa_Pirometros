package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_eletronicos")
public class EstoqueEletronicos implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private int sirene;
	private int PCIFIII;
	private int PCIFKal;
	private int DispFKal;
	private int FIII;
	private int Indicmax;
	private int CIFII;
	private int CIIndicmax;
	
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
	public int getSirene() {
		return sirene;
	}
	public void setSirene(int sirene) {
		this.sirene = sirene;
	}
	public int getPCIFIII() {
		return PCIFIII;
	}
	public void setPCIFIII(int pCIFIII) {
		PCIFIII = pCIFIII;
	}
	public int getPCIFKal() {
		return PCIFKal;
	}
	public void setPCIFKal(int pCIFKal) {
		PCIFKal = pCIFKal;
	}
	public int getDispFKal() {
		return DispFKal;
	}
	public void setDispFKal(int dispFKal) {
		DispFKal = dispFKal;
	}
	public int getFIII() {
		return FIII;
	}
	public void setFIII(int fIII) {
		FIII = fIII;
	}
	public int getIndicmax() {
		return Indicmax;
	}
	public void setIndicmax(int indicmax) {
		Indicmax = indicmax;
	}
	public int getCIFII() {
		return CIFII;
	}
	public void setCIFII(int cIFII) {
		CIFII = cIFII;
	}
	public int getCIIndicmax() {
		return CIIndicmax;
	}
	public void setCIIndicmax(int cIIndicmax) {
		CIIndicmax = cIIndicmax;
	}
	public Long getId() {
		return id;
	}
	
	

}
