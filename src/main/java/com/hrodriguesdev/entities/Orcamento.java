package com.hrodriguesdev.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Orcamento {
	
	private Long id;
	private String Item;
	private Long equipamento_id;
	private Date data_chegada;
	private Date data_saida;
	private Boolean laboratorio;
	private String relatorio;
	private Long coletor_id;
	private int status;
	private Long eletricos;
	private Long consumo;
	private Long eletronicos;
	private Long estetico;
	private Long sinal;
	private Long cabos;
	private String products;
	
//	private int nfe;
	private String situation;
	private String empressa;
	
//	
	private int nfe;
	private String author;
	private Long empresaId;
	
	
	public Orcamento(Long equipamento_id, Date data_chegada, Boolean laboratorio) {
		this.setEquipamento_id(equipamento_id);
		this.setData_chegada(data_chegada);
		this.setLaboratorio(laboratorio);
	}
		
	public Orcamento(ResultSet rs) {	
		try {			
			this.id = rs.getLong("id");
			if(rs.getString("Item") != null)
				Item = rs.getString("Item");
						
			equipamento_id = rs.getLong("equipamento_id");
			if(rs.getDate("data_saida") != null)
				data_saida( rs.getDate("data_saida"));
			
			data_chegada( rs.getDate("data_chegada"));
			laboratorio = rs.getBoolean("laboratorio");
			coletor_id = rs.getLong("coletor_id");
			relatorio = rs.getString("relatorio");
			status = rs.getInt("status");
			eletricos = rs.getLong("eletricos");
			consumo = rs.getLong("consumo");
			eletronicos = rs.getLong("eletronicos");
			estetico = rs.getLong("estetico");
			sinal = rs.getLong("sinal");
			setCabos(rs.getLong("cabos"));
			
			
			this.setNfe(rs.getInt("nfe"));
			this.setAuthor(rs.getString("author"));
			this.setEmpresaId(rs.getLong("empresa_id"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
			
	public Orcamento() {}
		
	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getEletricos() {
		return eletricos;
	}

	public void setEletricos(Long eletricos) {
		this.eletricos = eletricos;
	}

	public Long getConsumo() {
		return consumo;
	}

	public void setConsumo(Long consumo) {
		this.consumo = consumo;
	}

	public Long getEletronicos() {
		return eletronicos;
	}

	public void setEletronicos(Long eletronicos) {
		this.eletronicos = eletronicos;
	}

	public Long getEstetico() {
		return estetico;
	}

	public void setEstetico(Long estetico) {
		this.estetico = estetico;
	}

	public Long getSinal() {
		return sinal;
	}

	public void setSinal(Long sinal) {
		this.sinal = sinal;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
		
	public String getItemRealString() {
		return this.Item;
	}
	
	public String getItem() {
		return Item;
	}
	
	public void setItem(String item) {
		Item = item;
	}
	
	public Long getEquipamento_id() {
		return equipamento_id;
	}

	public void setEquipamento_id(Long equipamento_id) {
		this.equipamento_id = equipamento_id;
	}

	public Date getData_chegada() {
		return data_chegada;
	}

	public void setData_chegada(Date data_chegada) {
		this.data_chegada = data_chegada ;

	}

	@SuppressWarnings("deprecation")
	public void data_chegada(Date data_chegada) {
		int date = data_chegada.getDate() + 1;
		data_chegada.setDate(date);
		this.data_chegada = data_chegada ;

	}

	public Date getData_saida() {
		return data_saida;
	}

	public void setData_saida(Date data_saida) {
		this.data_saida = data_saida ;
	}
	
	@SuppressWarnings("deprecation")
	public void data_saida(Date data_saida) {
		int date = data_saida.getDate() + 1;
		data_saida.setDate(date);
		this.data_saida = data_saida ;
	}


	public Boolean getLaboratorio() {
		return laboratorio;
	}


	public void setLaboratorio(Boolean laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Long getColetor_id() {
		return coletor_id;
	}

	public void setColetor_id(Long coletor_id) {
		this.coletor_id = coletor_id;
	}

//	public static List<Orcamento> getList(String itens, EstoqueConsumo consumo2, EstoqueEstetico estetico2,
//			EstoqueEletricos eletrico, EstoqueEletronicos eletronico, EstoqueSinal sinal2, EstoqueCabos cabos) {
//		List<Orcamento> list = new ArrayList<>();
//		try {
//			list.addAll(EstoqueConsumo.orcamentoListConsumo(consumo2));
//		}catch(NullPointerException e){
//			e.printStackTrace();
//		}
//		try {
//			list.addAll(EstoqueEstetico.orcamentoListEstetico(estetico2));
//		}catch(NullPointerException e){
//			e.printStackTrace();
//		}
//		try {
//			list.addAll(EstoqueEletricos.orcamentoListEletrico(eletrico));
//		}catch(NullPointerException e){
//			e.printStackTrace();
//		}
//		try {
//			list.addAll(EstoqueEletronicos.orcamentoListEletronico(eletronico));
//		}catch(NullPointerException e){
//			e.printStackTrace();
//		}
//		try {
//				list.addAll(EstoqueSinal.orcamentoListSinal(sinal2));
//		}catch(NullPointerException e){
//			e.printStackTrace();
//		}
//		try {
//			list.addAll(EstoqueCabos.cabosListSinal(cabos));
//		}catch(NullPointerException e){
//			e.printStackTrace();
//		}
//		
//		if(!itens.isBlank()) {
//			String[] Itens = itens.split("\n");
//			for(@SuppressWarnings("unused") String newIten: Itens) {
//				list.add( new Orcamento());
//				}
//		}
//		
//		return list;
//	}

	public Long getCabos() {
		return cabos;
	}

	public void setCabos(Long cabos) {
		this.cabos = cabos;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getEmpressa() {
		return empressa;
	}

	public void setEmpressa(String empressa) {
		this.empressa = empressa;
	}

	@Override
	public String toString() {
		if(Item!=null) 
			if(Item!= "")
				return Item;
		
		return "";
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public int getNfe() {
		return nfe;
	}

	public void setNfe(int nfe) {
		this.nfe = nfe;
	}

	
	
}
