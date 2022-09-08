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
	private Integer BotaoLiga;
	private Integer BoMeFIIFIIIIndicmax;
	private Integer CaixaBat;
	private Boolean entrada;
	
	public EstoqueConsumo(Boolean entrada, Long orcamento_id, Boolean saida, int nfe, int botaoLiga, int boMeFIIFIIIIndicmax,
			int caixaBat) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		BotaoLiga = botaoLiga;
		BoMeFIIFIIIIndicmax = boMeFIIFIIIIndicmax;
		CaixaBat = caixaBat;
		this.setEntrada(entrada);
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
			this.setEntrada(rs.getBoolean("entrada"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
		
	public EstoqueConsumo() {
		
		BotaoLiga = 0;
		BoMeFIIFIIIIndicmax = 0;
		CaixaBat = 0;

	}

	public static List<Orcamento> orcamentoListConsumo(EstoqueConsumo consumo) {
		List<Orcamento> list = new ArrayList<>();
		try {
		if( consumo.getBotaoLiga() > 0) 
			list.add(new Orcamento("BotaoLiga", consumo.getBotaoLiga() ) );
			
		}catch(NullPointerException e) {}
		try {
		if( consumo.getBoMeFIIFIIIIndicmax() > 0) 
			list.add(new Orcamento("BoMeFIIFIIIIndicmax", consumo.getBoMeFIIFIIIIndicmax() ) );
			
		}catch(NullPointerException e) {}
		try {
		if( consumo.getCaixaBat() > 0) 
			list.add(new Orcamento("CaixaBat", consumo.getCaixaBat() ) );
			
		}catch(NullPointerException e) {}

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
	
	public String string() {
		String list = "Botao liga, quantidade = " + BotaoLiga + "\n";
		
			list = list + "Botao memoria, quantidade = " + BoMeFIIFIIIIndicmax + "\n";

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
	public Integer getNfe() {
		return nfe;
	}
	public void setNfe(int nfe) {
		this.nfe = nfe;
	}
	public Integer getBotaoLiga() {
		return BotaoLiga;
	}
	public void setBotaoLiga(Integer botaoLiga) {
		BotaoLiga = botaoLiga;
	}
	public Integer getBoMeFIIFIIIIndicmax() {
		return BoMeFIIFIIIIndicmax;
	}
	public void setBoMeFIIFIIIIndicmax(Integer boMeFIIFIIIIndicmax) {
		BoMeFIIFIIIIndicmax = boMeFIIFIIIIndicmax;
	}
	public Integer getCaixaBat() {
		return CaixaBat;
	}
	public void setCaixaBat(Integer caixaBat) {
		CaixaBat = caixaBat;
	}
	public Long getId() {
		return id;
	}

	public void remove(ResultSet rs) {
		try {			
			BotaoLiga -= rs.getInt("b_liga");
			BoMeFIIFIIIIndicmax -= rs.getInt("b_m_forneros");
			CaixaBat -= rs.getInt("caixa_bateria");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}

	public void add(ResultSet rs) {
		try {			
			BotaoLiga += rs.getInt("b_liga");
			BoMeFIIFIIIIndicmax += rs.getInt("b_m_forneros");
			CaixaBat += rs.getInt("caixa_bateria");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}
	
}
