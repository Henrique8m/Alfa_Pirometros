package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private Integer FontCarbIndic;
	private Integer FontCarbDelta;
	private Integer PinFemeAliFII;
	private Integer PinFemeAliFIII;
	private Integer BatFIIFIII;
	private Integer BatDescartavel;
	private Integer BatInditemp;
	private Integer BatLitio;
	private Integer CarrEcil;
	private Integer CarrItalterm;
	private Boolean entrada;
	
	public EstoqueEletricos(Boolean entrada, Long orcamento_id, Boolean saida, int nfe, int fontCarbIndic, int fontCarbDelta,
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
		this.entrada = entrada;
	}
	
	public EstoqueEletricos(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			FontCarbIndic = rs.getInt("font_carb_indic");
			FontCarbDelta = rs.getInt("font_carb_delta");
			PinFemeAliFII = rs.getInt("pin_femea_ali_fii");	
			PinFemeAliFIII = rs.getInt("pin_femea_ali_fiii");
			BatFIIFIII = rs.getInt("bat_fii_fiii");	
			BatDescartavel = rs.getInt("bat_descartavel");
			BatInditemp = rs.getInt("bat_inditemp");	
			BatLitio = rs.getInt("bat_litio");	
			CarrEcil = rs.getInt("carr_ecil");	
			CarrItalterm = rs.getInt("carr_italterm");	
			this.entrada = rs.getBoolean("entrada");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public void remove(ResultSet rs) {
		try {			

			FontCarbIndic =- rs.getInt("font_carb_indic");
			FontCarbDelta =- rs.getInt("font_carb_delta");
			PinFemeAliFII =- rs.getInt("pin_femea_ali_fii");	
			PinFemeAliFIII =- rs.getInt("pin_femea_ali_fiii");
			BatFIIFIII =- rs.getInt("bat_fii_fiii");	
			BatDescartavel =- rs.getInt("bat_descartavel");
			BatInditemp =- rs.getInt("bat_inditemp");	
			BatLitio =- rs.getInt("bat_litio");	
			CarrEcil =- rs.getInt("carr_ecil");	
			CarrItalterm =- rs.getInt("carr_italterm");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void add(ResultSet rs) {
		try {			

			FontCarbIndic =+ rs.getInt("font_carb_indic");
			FontCarbDelta =+ rs.getInt("font_carb_delta");
			PinFemeAliFII =+ rs.getInt("pin_femea_ali_fii");	
			PinFemeAliFIII =+ rs.getInt("pin_femea_ali_fiii");
			BatFIIFIII =+ rs.getInt("bat_fii_fiii");	
			BatDescartavel =+ rs.getInt("bat_descartavel");
			BatInditemp =+ rs.getInt("bat_inditemp");	
			BatLitio =+ rs.getInt("bat_litio");	
			CarrEcil =+ rs.getInt("carr_ecil");	
			CarrItalterm =+ rs.getInt("carr_italterm");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public EstoqueEletricos() {
		// TODO Auto-generated constructor stub
	}

	public static List<Orcamento> orcamentoListEletrico(EstoqueEletricos eletricos) {
		List<Orcamento> list = new ArrayList<>();
		
		if( eletricos.getFontCarbIndic() > 0) 
			list.add(new Orcamento("FontCarbIndic", eletricos.getFontCarbIndic() ) );
		
		if( eletricos.getFontCarbDelta() > 0) 
			list.add(new Orcamento("FontCarbDelta", eletricos.getFontCarbDelta() ) );
		
		if( eletricos.getPinFemeAliFII() > 0) 
			list.add(new Orcamento("PinFemeAliFII", eletricos.getPinFemeAliFII() ) );
		
		if( eletricos.getPinFemeAliFIII() > 0) 
			list.add(new Orcamento("PinFemeAliFIII", eletricos.getPinFemeAliFIII() ) );
		
		if( eletricos.getBatFIIFIII() > 0) 
			list.add(new Orcamento("BatFIIFIII", eletricos.getBatFIIFIII() ) );
		
		if( eletricos.getBatDescartavel() > 0) 
			list.add(new Orcamento("BatDescartavel", eletricos.getBatDescartavel() ) );
		
		if( eletricos.getBatInditemp() > 0) 
			list.add(new Orcamento("BatInditemp", eletricos.getBatInditemp() ) );
		
		if( eletricos.getBatLitio() > 0) 
			list.add(new Orcamento("BatLitio", eletricos.getBatLitio() ) );
		
		if( eletricos.getCarrEcil() > 0) 
			list.add(new Orcamento("CarrEcil", eletricos.getCarrEcil() ) );
		
		if( eletricos.getCarrItalterm() > 0) 
			list.add(new Orcamento("CarrItalterm", eletricos.getCarrItalterm() ) );
		
		return list;
	}
	
	@Override
	public String toString() {
		String list = "";

		if( FontCarbIndic > 0) 
			list = list + "Fonte italterm universal, quantidade = " + FontCarbIndic + "\n";
		
		if( FontCarbDelta > 0) 
			list = list + "Font carbomax delta, quantidade = " + FontCarbDelta + "\n";
		
		if( PinFemeAliFII > 0) 
			list = list + "Pino fêmea alimentacao Fornero II, quantidade = " + PinFemeAliFII + "\n";
		
		if( PinFemeAliFIII > 0) 
			list = list + "Pino fêmea alimentacao Fornero III, quantidade = " + PinFemeAliFIII + "\n";
		
		if( BatFIIFIII > 0) 
			list = list + "Bateria de fornero, quantidade = " + BatFIIFIII + "\n";
		
		if( BatDescartavel > 0) 
			list = list + "Bateria 9V, quantidade = " + BatDescartavel + "\n";
		
		if( BatInditemp > 0) 
			list = list + "Bateria inditemp II, quantidade = " + BatInditemp + "\n";
		
		if( BatLitio > 0) 
			list = list + "Bateria inditemp II Plus - litio, quantidade = " + BatLitio + "\n";
		
		if( CarrEcil > 0) 
			list = list + "Carregador ecil, quantidade = " + CarrEcil + "\n";
		
		if( CarrItalterm > 0) 
			list = list + "Carregador Italterm, quantidade = " + CarrItalterm + "\n";
		
		return list;
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

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}



	
}
