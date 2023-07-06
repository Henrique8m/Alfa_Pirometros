package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private int s_borracha;
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
			s_borracha -= rs.getInt("s_borracha");
			s_miolo -= rs.getInt("s_miolo");
			s_extensao -= rs.getInt("s_extensao");	
			k_borracha -= rs.getInt("k_borracha");
			k_miolo -= rs.getInt("k_miolo");
			k_extensao -= rs.getInt("k_extensao");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	public void add(ResultSet rs) {
		try {			

			s_borracha += rs.getInt("s_borracha");
			s_miolo += rs.getInt("s_miolo");
			s_extensao += rs.getInt("s_extensao");	
			k_borracha += rs.getInt("k_borracha");
			k_miolo += rs.getInt("k_miolo");
			k_extensao += rs.getInt("k_extensao");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public EstoqueCabos() {
		s_borracha=0;
		s_miolo=0;
		s_extensao=0;
		k_borracha=0;
		k_miolo=0;
		k_extensao=0;
	}

//	public static List<Orcamento> cabosListSinal(EstoqueCabos sinal) {
//		List<Orcamento> list = new ArrayList<>();
//		try {
//			
//		if(sinal.getS_borracha() > 0) 
//			list.add(new Orcamento("Cabo_S_borracha", sinal.getS_borracha() ) );
//		
//		}catch(NullPointerException e) {}
//
//		try {
//
//				if( sinal.getS_miolo() > 0) 
//			list.add(new Orcamento("Cabo_S_miolo_lanca", sinal.getS_miolo() ) );
//		
//		}catch(NullPointerException e) {}
//		
//		try {
//
//
//		if( sinal.getS_extensao() > 0) 
//			list.add(new Orcamento("Cabo_S_extensao", sinal.getS_extensao() ) );
//			
//		}catch(NullPointerException e) {}
//		try {
//
//					
//		if( sinal.getK_borracha() > 0) 
//			list.add(new Orcamento("Cabo_K_borracha", sinal.getK_borracha())  );
//		
//		}catch(NullPointerException e) {}
//		try {
//			
//		if( sinal.getK_miolo() > 0) 
//			list.add(new Orcamento("Cabo_K_Fibra_Fibra", sinal.getK_miolo() ) );
//		
//			
//		}catch(NullPointerException e) {}
//		
//		try {
//			
//		if( sinal.getK_extensao() > 0) 
//			list.add(new Orcamento("Cabo_K_Fibra_Silicone", sinal.getK_extensao() ) );
//		
//		}catch(NullPointerException e) {}
//		
//
//
//		
//		return list;
//	}

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
	public Integer getNfe() {
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

	public int getS_miolo() {
		return s_miolo;
	}

	public void setS_miolo(Integer s_miolo) {
		this.s_miolo = s_miolo;
	}

	public int getS_extensao() {
		return s_extensao;
	}

	public void setS_extensao(Integer s_extensao) {
		this.s_extensao = s_extensao;
	}

	public int getK_borracha() {
		return k_borracha;
	}

	public void setK_borracha(Integer k_borracha) {
		this.k_borracha = k_borracha;
	}

	public int getK_miolo() {
		return k_miolo;
	}

	public void setK_miolo(Integer k_miolo) {
		this.k_miolo = k_miolo;
	}

	public int getK_extensao() {
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
			list = list + s_borracha + " Cabo S borracha\n";
		
		if( s_miolo > 0) 
			list = list + s_miolo +  " Cabo S fibra fibra\n";
		
		if( s_extensao > 0) 
			list = list + s_extensao + " Cabo S extensao\n";
		
		if( k_borracha > 0) 
			list = list + k_borracha + " Cabo K borracha\n";
		
		if( k_miolo > 0) 
			list = list + k_miolo + " Cabo K fibra fibra\n";
		
		if( k_extensao > 0) 
			list = list + k_extensao + " Cabo K Fibra Silicone\n";
			

		return list;
	}
	
	public String string() {
		String list = "Cabo S borracha = " + s_borracha + "\n";
		
			list = list + "Cabo S fibra fibra, quantidade = " + s_miolo + "cm" + "\n";

			list = list + "Cabo S extensao, quantidade = " + s_extensao + "cm" + "\n";
		
			list = list + "Cabo K borracha, quantidade = " + k_borracha + "cm" + "\n";
		 
			list = list + "Cabo K fibra fibra, quantidade = " + k_miolo + "cm" + "\n";
		
			list = list + "Cabo K fibra silicone, quantidade = " + k_extensao + "cm" + "\n";
			

		return list;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}
	

}
