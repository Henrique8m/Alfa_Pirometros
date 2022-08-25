package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public EstoqueEstetico(Long orcamento_id, Boolean saida, int nfe, int mascaraFII, int mascaraFKal, int mascaraFIII,
			int mascaraCarbo, int mascaraIndic, int etiqLatFII, int etiqLatFIII, int etiqTrasFII, int punho) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		MascaraFII = mascaraFII;
		MascaraFKal = mascaraFKal;
		MascaraFIII = mascaraFIII;
		MascaraCarbo = mascaraCarbo;
		MascaraIndic = mascaraIndic;
		EtiqLatFII = etiqLatFII;
		EtiqLatFIII = etiqLatFIII;
		EtiqTrasFII = etiqTrasFII;
		Punho = punho;
	}
	
	public EstoqueEstetico(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			MascaraFII = rs.getInt("mascara_fii");
			MascaraFKal = rs.getInt("mascara_fkal");
			MascaraFIII = rs.getInt("mascara_fiii");	
			MascaraCarbo = rs.getInt("mascara_carbo");
			MascaraIndic = rs.getInt("mascara_indic");
			EtiqLatFII = rs.getInt("etiq_lat_fii");
			EtiqLatFIII = rs.getInt("etiq_lat_fiii");
			EtiqTrasFII = rs.getInt("etiq_tras_fii");
			Punho = rs.getInt("punho");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	@Override
	public String toString() {
		String list = "";

		if( MascaraFII > 0) 
			list = list + "Mascara Fornero II, quantidade = " + MascaraFII + "\n";
		
		if( MascaraFKal > 0) 
			list = list + "Mascara Fornero kal, quantidade = " + MascaraFKal + "\n";
		
		if( MascaraFIII > 0) 
			list = list + "Mascara Fornero III, quantidade = " + MascaraFIII + "\n";
		
		if( MascaraCarbo > 0) 
			list = list + "Mascara carbomax II, quantidade = " + MascaraCarbo + "\n";
		
		if( MascaraIndic > 0) 
			list = list + "Mascara Indicmax II, quantidade = " + MascaraIndic + "\n";
		
		if( EtiqLatFII > 0) 
			list = list + "Etiqueta lateral fornero II, quantidade = " + EtiqLatFII + "\n";
		
		if( EtiqLatFIII > 0) 
			list = list + "Etiqueta lateral fornero III, quantidade = " + EtiqLatFIII + "\n";
		
		if( EtiqTrasFII > 0) 
			list = list + "Etiqueta traseira fornero II, quantidade = " + EtiqTrasFII + "\n";
		
		if( Punho > 0) 
			list = list + "Punho, quantidade = " + Punho + "\n";
		
		
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
