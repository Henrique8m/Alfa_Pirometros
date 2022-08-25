package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_sinal")
public class EstoqueSinal implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private int ReceptaculoS;
	private int ReceptaculoSU;
	private int ReceptaculoEcil;
	private int ReceptaculoK;
	private int PlugFS;
	private int PlugFK;
	private int PlugMS;
	private int PlugMK;
	private int TomadaS;
	
	
	
	public EstoqueSinal(Long orcamento_id, Boolean saida, int nfe, int receptaculoS, int receptaculoSU,
			int receptaculoEcil, int receptaculoK, int plugFS, int plugFK, int plugMS, int plugMK, int tomadaS) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		ReceptaculoS = receptaculoS;
		ReceptaculoSU = receptaculoSU;
		ReceptaculoEcil = receptaculoEcil;
		ReceptaculoK = receptaculoK;
		PlugFS = plugFS;
		PlugFK = plugFK;
		PlugMS = plugMS;
		PlugMK = plugMK;
		TomadaS = tomadaS;
	}
	
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
	public int getReceptaculoS() {
		return ReceptaculoS;
	}
	public void setReceptaculoS(int receptaculoS) {
		ReceptaculoS = receptaculoS;
	}
	public int getReceptaculoSU() {
		return ReceptaculoSU;
	}
	public void setReceptaculoSU(int receptaculoSU) {
		ReceptaculoSU = receptaculoSU;
	}
	public int getReceptaculoEcil() {
		return ReceptaculoEcil;
	}
	public void setReceptaculoEcil(int receptaculoEcil) {
		ReceptaculoEcil = receptaculoEcil;
	}
	public int getReceptaculoK() {
		return ReceptaculoK;
	}
	public void setReceptaculoK(int receptaculoK) {
		ReceptaculoK = receptaculoK;
	}
	public int getPlugFS() {
		return PlugFS;
	}
	public void setPlugFS(int plugFS) {
		PlugFS = plugFS;
	}
	public int getPlugFK() {
		return PlugFK;
	}
	public void setPlugFK(int plugFK) {
		PlugFK = plugFK;
	}
	public int getPlugMS() {
		return PlugMS;
	}
	public void setPlugMS(int plugMS) {
		PlugMS = plugMS;
	}
	public int getPlugMK() {
		return PlugMK;
	}
	public void setPlugMK(int plugMK) {
		PlugMK = plugMK;
	}
	public int getTomadaS() {
		return TomadaS;
	}
	public void setTomadaS(int tomadaS) {
		TomadaS = tomadaS;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getId() {
		return id;
	}

}
