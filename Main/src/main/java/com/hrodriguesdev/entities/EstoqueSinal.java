package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private Boolean entrada;
	
	public EstoqueSinal(Boolean entrada, Long orcamento_id, Boolean saida, int nfe, int receptaculoS, int receptaculoSU,
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
		this.setEntrada(entrada);
	}
	
	public EstoqueSinal(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			ReceptaculoS = rs.getInt("receptaculo_s");
			ReceptaculoSU = rs.getInt("receptaculo_su");
			ReceptaculoEcil = rs.getInt("receptaculo_ecil");	
			ReceptaculoK = rs.getInt("receptaculo_k");
			PlugFS = rs.getInt("plug_fs");
			PlugFK = 0;
			PlugMS = rs.getInt("plug_ms");
			PlugMK = rs.getInt("plug_mk");
			TomadaS = rs.getInt("tomada_s");
			this.setEntrada(rs.getBoolean("entrada"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}

	public void remove(ResultSet rs) {
		try {			

			ReceptaculoS =- rs.getInt("receptaculo_s");
			ReceptaculoSU =- rs.getInt("receptaculo_su");
			ReceptaculoEcil =- rs.getInt("receptaculo_ecil");	
			ReceptaculoK =- rs.getInt("receptaculo_k");
			PlugFS =- rs.getInt("plug_fs");
			PlugFK = 0;
			PlugMS =- rs.getInt("plug_ms");
			PlugMK =- rs.getInt("plug_mk");
			TomadaS =- rs.getInt("tomada_s");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	public void add(ResultSet rs) {
		try {			

			ReceptaculoS =+ rs.getInt("receptaculo_s");
			ReceptaculoSU =+ rs.getInt("receptaculo_su");
			ReceptaculoEcil =+ rs.getInt("receptaculo_ecil");	
			ReceptaculoK =+ rs.getInt("receptaculo_k");
			PlugFS =+ rs.getInt("plug_fs");
			PlugFK =+ 0;
			PlugMS =+ rs.getInt("plug_ms");
			PlugMK =+ rs.getInt("plug_mk");
			TomadaS =+ rs.getInt("tomada_s");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public EstoqueSinal() {
		// TODO Auto-generated constructor stub
	}

	public static List<Orcamento> orcamentoListSinal(EstoqueSinal sinal) {
		List<Orcamento> list = new ArrayList<>();
		
		try {

			if( sinal.getReceptaculoS() > 0) 
				list.add(new Orcamento("ReceptaculoS", sinal.getReceptaculoS() ) );
		
		}catch(NullPointerException e) {}

		try {

			
			if( sinal.getReceptaculoSU() > 0) 
				list.add(new Orcamento("ReceptaculoSU", sinal.getReceptaculoSU() ) );
			
			

		}catch(NullPointerException e) {}
		
		try {
	if( sinal.getReceptaculoEcil() > 0) 
			list.add(new Orcamento("ReceptaculoEcil", sinal.getReceptaculoEcil() ) );
			
		}catch(NullPointerException e) {}
	
		try {

					if( sinal.getReceptaculoK() > 0) 
			list.add(new Orcamento("ReceptaculoK", sinal.getReceptaculoK())  );
		}catch(NullPointerException e) {}
		try {

				if( sinal.getPlugFS() > 0) 
			list.add(new Orcamento("PlugFS", sinal.getPlugFS() ) );
		
		}catch(NullPointerException e) {}

		try {
	
		if( sinal.getPlugFK() > 0) 
			list.add(new Orcamento("PlugFK", sinal.getPlugFK() ) );
			
		}catch(NullPointerException e) {}

		try {
		if( sinal.getPlugMS() > 0) 
			list.add(new Orcamento("PlugMS", sinal.getPlugMS() ) );
			
		}catch(NullPointerException e) {}
		try {

					
		if( sinal.getPlugMK() > 0) 
			list.add(new Orcamento("PlugMK", sinal.getPlugMK() ) );
		}catch(NullPointerException e) {}

		try {

					if( sinal.getTomadaS() > 0) 
			list.add(new Orcamento("TomadaS", sinal.getTomadaS() ) );
		}catch(NullPointerException e) {}

		
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

	@Override
	public String toString() {
		String list = "";
		
		if( ReceptaculoS > 0) 
			list = list + "Receptaculo S, quantidade = " + ReceptaculoS + "\n";
		
		if( ReceptaculoSU > 0) 
			list = list + "Receptaculo SU, quantidade = " + ReceptaculoSU + "\n";
		
		if( ReceptaculoEcil > 0) 
			list = list + "Receptaculo Ecil, quantidade = " + ReceptaculoEcil + "\n";
		
		if( ReceptaculoK > 0) 
			list = list + "Receptaculo K, quantidade = " + ReceptaculoK + "\n";
		
		if( PlugFS > 0) 
			list = list + "Plug Fêmea S, quantidade = " + PlugFS + "\n";
		
		if( PlugFK > 0) 
			list = list + "Plug Fêmea K, quantidade = " + PlugFK + "\n";
		
		if( PlugMS > 0) 
			list = list + "Plug Macho S, quantidade = " + PlugMS + "\n";
		
		if( PlugMK > 0) 
			list = list + "Plug Macho K, quantidade = " + PlugMK + "\n";
		
		if( TomadaS > 0) 
			list = list + "Tomada S, quantidade = " + TomadaS + "\n";

		return list;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}
	

}
