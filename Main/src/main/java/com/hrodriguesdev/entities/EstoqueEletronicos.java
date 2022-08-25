package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public EstoqueEletronicos(Long orcamento_id, Boolean saida, int nfe, int sirene, int pCIFIII, int pCIFKal,
			int dispFKal, int fIII, int indicmax, int cIFII, int cIIndicmax) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		this.sirene = sirene;
		PCIFIII = pCIFIII;
		PCIFKal = pCIFKal;
		DispFKal = dispFKal;
		FIII = fIII;
		Indicmax = indicmax;
		CIFII = cIFII;
		CIIndicmax = cIIndicmax;
	}
	
	public EstoqueEletronicos(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			sirene = rs.getInt("sirene");
			PCIFIII = rs.getInt("pci_fiii");
			PCIFKal = rs.getInt("pci_fkal");	
			DispFKal = rs.getInt("disp_fkal");
			FIII = rs.getInt("fiii");	
			Indicmax = rs.getInt("indicmax");
			CIFII = rs.getInt("ci_fii");	
			CIIndicmax = rs.getInt("ci_indicmax");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
		
	public void setId(Long id) {
		this.id = id;
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
