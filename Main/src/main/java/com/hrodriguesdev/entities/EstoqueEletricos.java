package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_eletricos")
public class EstoqueEletricos implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private int FontCarbIndic;
	private int FontCarbDelta;
	private int PinFemeAliFII;
	private int PinFemeAliFIII;
	private int BatFIIFIII;
	private int BatDescartavel;
	private int BatInditemp;
	private int BatLitio;
	private int CarrEcil;
	private int CarrItalterm;
	
	public EstoqueEletricos(Long orcamento_id, Boolean saida, int nfe, int fontCarbIndic, int fontCarbDelta,
			int pinFemeAliFII, int pinFemeAliFIII, int batFIIFIII, int batDescartavel, int batInditemp, int batLitio,
			int carrEcil, int carrItalterm) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		FontCarbIndic = fontCarbIndic;
		FontCarbDelta = fontCarbDelta;
		PinFemeAliFII = pinFemeAliFII;
		PinFemeAliFIII = pinFemeAliFIII;
		BatFIIFIII = batFIIFIII;
		BatDescartavel = batDescartavel;
		BatInditemp = batInditemp;
		BatLitio = batLitio;
		CarrEcil = carrEcil;
		CarrItalterm = carrItalterm;
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
	public int getFontCarbIndic() {
		return FontCarbIndic;
	}
	public void setFontCarbIndic(int fontCarbIndic) {
		FontCarbIndic = fontCarbIndic;
	}
	public int getFontCarbDelta() {
		return FontCarbDelta;
	}
	public void setFontCarbDelta(int fontCarbDelta) {
		FontCarbDelta = fontCarbDelta;
	}
	public int getPinFemeAliFII() {
		return PinFemeAliFII;
	}
	public void setPinFemeAliFII(int pinFemeAliFII) {
		PinFemeAliFII = pinFemeAliFII;
	}
	public int getPinFemeAliFIII() {
		return PinFemeAliFIII;
	}
	public void setPinFemeAliFIII(int pinFemeAliFIII) {
		PinFemeAliFIII = pinFemeAliFIII;
	}
	public int getBatFIIFIII() {
		return BatFIIFIII;
	}
	public void setBatFIIFIII(int batFIIFIII) {
		BatFIIFIII = batFIIFIII;
	}
	public int getBatDescartavel() {
		return BatDescartavel;
	}
	public void setBatDescartavel(int batDescartavel) {
		BatDescartavel = batDescartavel;
	}
	public int getBatInditemp() {
		return BatInditemp;
	}
	public void setBatInditemp(int batInditemp) {
		BatInditemp = batInditemp;
	}
	public int getBatLitio() {
		return BatLitio;
	}
	public void setBatLitio(int batLitio) {
		BatLitio = batLitio;
	}
	public int getCarrEcil() {
		return CarrEcil;
	}
	public void setCarrEcil(int carrEcil) {
		CarrEcil = carrEcil;
	}
	public int getCarrItalterm() {
		return CarrItalterm;
	}
	public void setCarrItalterm(int carrItalterm) {
		CarrItalterm = carrItalterm;
	}
	public Long getId() {
		return id;
	}

	
}
