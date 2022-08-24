package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_estetico")
public class EstoqueEstetico implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private int MascaraFII;
	private int MascaraFKal;
	private int MascaraFIII;
	private int MascaraCarbo;
	private int MascaraIndic;
	private int EtiqLatFII;
	private int EtiqLatFIII;
	private int EtiqTrasFII;
	private int Punho;
	
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
	public int getMascaraFII() {
		return MascaraFII;
	}
	public void setMascaraFII(int mascaraFII) {
		MascaraFII = mascaraFII;
	}
	public int getMascaraFKal() {
		return MascaraFKal;
	}
	public void setMascaraFKal(int mascaraFKal) {
		MascaraFKal = mascaraFKal;
	}
	public int getMascaraFIII() {
		return MascaraFIII;
	}
	public void setMascaraFIII(int mascaraFIII) {
		MascaraFIII = mascaraFIII;
	}
	public int getMascaraCarbo() {
		return MascaraCarbo;
	}
	public void setMascaraCarbo(int mascaraCarbo) {
		MascaraCarbo = mascaraCarbo;
	}
	public int getMascaraIndic() {
		return MascaraIndic;
	}
	public void setMascaraIndic(int mascaraIndic) {
		MascaraIndic = mascaraIndic;
	}
	public int getEtiqLatFII() {
		return EtiqLatFII;
	}
	public void setEtiqLatFII(int etiqLatFII) {
		EtiqLatFII = etiqLatFII;
	}
	public int getEtiqLatFIII() {
		return EtiqLatFIII;
	}
	public void setEtiqLatFIII(int etiqLatFIII) {
		EtiqLatFIII = etiqLatFIII;
	}
	public int getEtiqTrasFII() {
		return EtiqTrasFII;
	}
	public void setEtiqTrasFII(int etiqTrasFII) {
		EtiqTrasFII = etiqTrasFII;
	}
	public int getPunho() {
		return Punho;
	}
	public void setPunho(int punho) {
		Punho = punho;
	}
	public Long getId() {
		return id;
	}
	
	

}
