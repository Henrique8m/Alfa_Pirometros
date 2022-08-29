package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "tb_consumo")
public class EstoqueConsumo implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private int BotaoLiga;
	private int BoMeFIIFIIIIndicmax;
	private int CaixaBat;
	
	public EstoqueConsumo(Long orcamento_id, Boolean saida, int nfe, int botaoLiga, int boMeFIIFIIIIndicmax,
			int caixaBat) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		BotaoLiga = botaoLiga;
		BoMeFIIFIIIIndicmax = boMeFIIFIIIIndicmax;
		CaixaBat = caixaBat;
	}
	
	public EstoqueConsumo(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			BotaoLiga = rs.getInt("b_liga");
			BoMeFIIFIIIIndicmax = rs.getInt("b_m_forneros");
			CaixaBat = rs.getInt("caixa_bateria");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
		
	public static List<Orcamento> orcamentoListConsumo(EstoqueConsumo consumo) {
		List<Orcamento> list = new ArrayList<>();
		
		if( consumo.getBotaoLiga() > 0) 
			list.add(new Orcamento("BotaoLiga", consumo.getBotaoLiga() ) );
		
		if( consumo.getBoMeFIIFIIIIndicmax() > 0) 
			list.add(new Orcamento("BoMeFIIFIIIIndicmax", consumo.getBoMeFIIFIIIIndicmax() ) );
		
		if( consumo.getCaixaBat() > 0) 
			list.add(new Orcamento("CaixaBat", consumo.getCaixaBat() ) );
		
		return list;
	}
	
	@Override
	public String toString() {
		String list = "";
		
		if( BotaoLiga > 0) 
			list = list + "Botao Liga, quantidade = " + BotaoLiga + "\n";
		
		if( BoMeFIIFIIIIndicmax > 0) 
			list = list + "Botao Memoria, quantidade = " + BoMeFIIFIIIIndicmax + "\n";
		
		if( CaixaBat > 0) 
			list = list + "Caixa para bateria, quantidade = " + CaixaBat + "\n";
		
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
	public int getBotaoLiga() {
		return BotaoLiga;
	}
	public void setBotaoLiga(int botaoLiga) {
		BotaoLiga = botaoLiga;
	}
	public int getBoMeFIIFIIIIndicmax() {
		return BoMeFIIFIIIIndicmax;
	}
	public void setBoMeFIIFIIIIndicmax(int boMeFIIFIIIIndicmax) {
		BoMeFIIFIIIIndicmax = boMeFIIFIIIIndicmax;
	}
	public int getCaixaBat() {
		return CaixaBat;
	}
	public void setCaixaBat(int caixaBat) {
		CaixaBat = caixaBat;
	}
	public Long getId() {
		return id;
	}

	
}
