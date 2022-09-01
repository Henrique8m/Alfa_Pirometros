package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "tb_sinal")
public class EstoqueCabos implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;

	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private Integer s_borracha;
	private Integer s_miolo;
	private Integer s_extensao;
	private Integer k_borracha;
	private Integer k_miolo;
	private Integer k_extensao;
	private Boolean entrada;
	
	
	
	public EstoqueCabos(Boolean entrada, Long orcamento_id, Boolean saida, int nfe, Integer s_borracha, Integer s_miolo,
			Integer s_extensao, Integer k_borracha, Integer k_miolo, Integer k_extensao) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		this.s_borracha = s_borracha;
		this.s_miolo = s_miolo;
		this.s_extensao = s_extensao;
		this.k_borracha = k_borracha;
		this.k_miolo = k_miolo;
		this.k_extensao = k_extensao;
		this.setEntrada(entrada);
	}

	public EstoqueCabos(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			s_borracha = rs.getInt("s_borracha");
			s_miolo = rs.getInt("s_miolo");
			s_extensao = rs.getInt("s_extensao");	
			k_borracha = rs.getInt("k_borracha");
			k_miolo = rs.getInt("k_miolo");
			k_extensao = rs.getInt("k_extensao");
			this.setEntrada(rs.getBoolean("entrada"));

		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}

	public void remove(ResultSet rs) {
		try {			
			s_borracha =- rs.getInt("s_borracha");
			s_miolo =- rs.getInt("s_miolo");
			s_extensao =- rs.getInt("s_extensao");	
			k_borracha =- rs.getInt("k_borracha");
			k_miolo =- rs.getInt("k_miolo");
			k_extensao =- rs.getInt("k_extensao");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	public void add(ResultSet rs) {
		try {			

			s_borracha =+ rs.getInt("s_borracha");
			s_miolo =+ rs.getInt("s_miolo");
			s_extensao =+ rs.getInt("s_extensao");	
			k_borracha =+ rs.getInt("k_borracha");
			k_miolo =+ rs.getInt("k_miolo");
			k_extensao =+ rs.getInt("k_extensao");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public EstoqueCabos() {
		// TODO Auto-generated constructor stub
	}

	public static List<Orcamento> cabosListSinal(EstoqueCabos sinal) {
		List<Orcamento> list = new ArrayList<>();
		
		if( sinal.getS_borracha() > 0) 
			list.add(new Orcamento("Cabo_S_borracha", sinal.getS_borracha() ) );
		
		if( sinal.getS_miolo() > 0) 
			list.add(new Orcamento("Cabo_S_miolo_lanca", sinal.getS_miolo() ) );
		
		if( sinal.getS_extensao() > 0) 
			list.add(new Orcamento("Cabo_S_extensao", sinal.getS_extensao() ) );
		
		if( sinal.getK_borracha() > 0) 
			list.add(new Orcamento("Cabo_K_borracha", sinal.getK_borracha())  );
		
		if( sinal.getK_miolo() > 0) 
			list.add(new Orcamento("Cabo_K_Fibra_Fibra", sinal.getK_miolo() ) );
		
		if( sinal.getK_extensao() > 0) 
			list.add(new Orcamento("Cabo_K_Fibra_Silicone", sinal.getK_extensao() ) );
		
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
	
	public Integer getS_borracha() {
		return s_borracha;
	}

	public void setS_borracha(Integer s_borracha) {
		this.s_borracha = s_borracha;
	}

	public Integer getS_miolo() {
		return s_miolo;
	}

	public void setS_miolo(Integer s_miolo) {
		this.s_miolo = s_miolo;
	}

	public Integer getS_extensao() {
		return s_extensao;
	}

	public void setS_extensao(Integer s_extensao) {
		this.s_extensao = s_extensao;
	}

	public Integer getK_borracha() {
		return k_borracha;
	}

	public void setK_borracha(Integer k_borracha) {
		this.k_borracha = k_borracha;
	}

	public Integer getK_miolo() {
		return k_miolo;
	}

	public void setK_miolo(Integer k_miolo) {
		this.k_miolo = k_miolo;
	}

	public Integer getK_extensao() {
		return k_extensao;
	}

	public void setK_extensao(Integer k_extensao) {
		this.k_extensao = k_extensao;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		String list = "";
		
		if( s_borracha > 0) 
			list = list + "Cabo_S_borracha = " + s_borracha + "\n";
		
		if( s_miolo > 0) 
			list = list + "Cabo_S_fibra_fibra, quantidade = " + s_miolo + "\n";
		
		if( s_extensao > 0) 
			list = list + "Cabo_S_extensao, quantidade = " + s_extensao + "\n";
		
		if( k_borracha > 0) 
			list = list + "Cabo_K_borracha, quantidade = " + k_borracha + "\n";
		
		if( k_miolo > 0) 
			list = list + "Cabo_K_fibra_fibra, quantidade = " + k_miolo + "\n";
		
		if( k_extensao > 0) 
			list = list + "Cabo_K_Fibra_Silicone, quantidade = " + k_extensao + "\n";
			

		return list;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}
	

}
