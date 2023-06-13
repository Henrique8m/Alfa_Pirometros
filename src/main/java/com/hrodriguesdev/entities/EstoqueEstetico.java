package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private Integer MascaraFII;
	private Integer MascaraFKal;
	private Integer MascaraFIII;
	private Integer MascaraCarbo;
	private Integer MascaraIndic;
	private Integer EtiqLatFII;
	private Integer EtiqLatFIII;
	private Integer EtiqTrasFII;
	private Integer Punho;
	private Boolean entrada;
	
	public EstoqueEstetico(Boolean entrada, Long orcamento_id, Boolean saida, int nfe, int mascaraFII, int mascaraFKal, int mascaraFIII,
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
		this.setEntrada(entrada);
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
			this.setEntrada(rs.getBoolean("entrada"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public void remove(ResultSet rs) {
		try {			

			MascaraFII -= rs.getInt("mascara_fii");
			MascaraFKal -= rs.getInt("mascara_fkal");
			MascaraFIII -= rs.getInt("mascara_fiii");	
			MascaraCarbo -= rs.getInt("mascara_carbo");
			MascaraIndic -= rs.getInt("mascara_indic");
			EtiqLatFII -= rs.getInt("etiq_lat_fii");
			EtiqLatFIII -= rs.getInt("etiq_lat_fiii");
			EtiqTrasFII -= rs.getInt("etiq_tras_fii");
			Punho -= rs.getInt("punho");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public void add(ResultSet rs) {
		try {			

			MascaraFII += rs.getInt("mascara_fii");
			MascaraFKal += rs.getInt("mascara_fkal");
			MascaraFIII += rs.getInt("mascara_fiii");	
			MascaraCarbo += rs.getInt("mascara_carbo");
			MascaraIndic += rs.getInt("mascara_indic");
			EtiqLatFII += rs.getInt("etiq_lat_fii");
			EtiqLatFIII += rs.getInt("etiq_lat_fiii");
			EtiqTrasFII += rs.getInt("etiq_tras_fii");
			Punho += rs.getInt("punho");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public EstoqueEstetico() {
		MascaraFII=0;
		MascaraFKal=0;
		MascaraFIII=0;
		MascaraCarbo=0;
		MascaraIndic=0;
		EtiqLatFII=0;
		EtiqLatFIII=0;
		EtiqTrasFII=0;
		Punho=0;
	}

	public static List<Orcamento> orcamentoListEstetico(EstoqueEstetico estetico) {
		List<Orcamento> list = new ArrayList<>();
		try {

					if( estetico.getMascaraFII() > 0) 
			list.add(new Orcamento("MascaraFII", estetico.getMascaraFII() ) );
		}catch(NullPointerException e) {}

		try {
		if( estetico.getMascaraFKal() > 0) 
			list.add(new Orcamento("MascaraFKal", estetico.getMascaraFKal() ) );
			
		}catch(NullPointerException e) {}
		try {
		if( estetico.getMascaraFIII() > 0) 
			list.add(new Orcamento("MascaraFIII", estetico.getMascaraFIII() ) );
			
		}catch(NullPointerException e) {}
		try {
		if( estetico.getMascaraCarbo() > 0) 
			list.add(new Orcamento("MascaraCarbo", estetico.getMascaraCarbo() ) );
			
		}catch(NullPointerException e) {}
		try {
		if( estetico.getMascaraIndic() > 0) 
			list.add(new Orcamento("MascaraIndic", estetico.getMascaraIndic() ) );
			
		}catch(NullPointerException e) {}
		try {
		if( estetico.getEtiqLatFII() > 0) 
			list.add(new Orcamento("EtiqLatFII", estetico.getEtiqLatFII() ) );
			
		}catch(NullPointerException e) {}
		try {
	if( estetico.getEtiqLatFIII() > 0) 
			list.add(new Orcamento("EtiqLatFIII", estetico.getEtiqLatFIII() ) );
			
		}catch(NullPointerException e) {}
		
		try {

		if( estetico.getEtiqTrasFII() > 0) 
			list.add(new Orcamento("EtiqTrasFII", estetico.getEtiqTrasFII() ) );
			
		}catch(NullPointerException e) {}
		try {
		
		if( estetico.getPunho() > 0) 
			list.add(new Orcamento("Punho", estetico.getPunho() ) );
			
		}catch(NullPointerException e) {}

		
	
		

		
		return list;
	}
	
	@Override
	public String toString() {
		String list = "";

		if( MascaraFII > 0) 
			list = list + MascaraFII + " Mascara Fornero II\n";
		
		if( MascaraFKal > 0) 
			list = list + MascaraFKal + " Mascara Fornero kal\n";
		
		if( MascaraFIII > 0) 
			list = list + MascaraFIII + " Mascara Fornero III\n";
		
		if( MascaraCarbo > 0) 
			list = list + MascaraCarbo + " Mascara carbomax II\n";
		
		if( MascaraIndic > 0) 
			list = list + MascaraIndic + " Mascara Indicmax II\n";
		
		if( EtiqLatFII > 0) 
			list = list + EtiqLatFII + " Etiqueta lateral fornero II\n";
		
		if( EtiqLatFIII > 0) 
			list = list + EtiqLatFIII + " Etiqueta lateral fornero III\n";
		
		if( EtiqTrasFII > 0) 
			list = list + EtiqTrasFII + " Etiqueta traseira fornero II\n";
		
		if( Punho > 0) 
			list = list + Punho + "Punho\n";
		
		
		return list;
	}
	
	public String string() {
		String list = "Mascara Fornero II, quantidade = " + MascaraFII + "\n";
		
			list = list + "Mascara Fornero kal, quantidade = " + MascaraFKal + "\n";
		
			list = list + "Mascara Fornero III, quantidade = " + MascaraFIII + "\n";
		
			list = list + "Mascara carbomax II, quantidade = " + MascaraCarbo + "\n";
		
			list = list + "Mascara Indicmax II, quantidade = " + MascaraIndic + "\n";
		
			list = list + "Etiqueta lateral fornero II, quantidade = " + EtiqLatFII + "\n";
		
			list = list + "Etiqueta lateral fornero III, quantidade = " + EtiqLatFIII + "\n";
		
			list = list + "Etiqueta traseira fornero II, quantidade = " + EtiqTrasFII + "\n";
		
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

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}

	
	

}