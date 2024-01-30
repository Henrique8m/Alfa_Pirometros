package com.hrodriguesdev.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
	private String products;
	
//	private int nfe;
	private String situation;
	private String empressa;
	
//	
	private int nfe;
	private String author;
	private Long empresaId;
	
	private Double quantidade;
	
	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	private Timestamp timestamp;
	
	
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
				this.data_saida = rs.getDate("data_saida");
			
			this.data_chegada =  rs.getDate("data_chegada");
			this.laboratorio = rs.getBoolean("laboratorio");
			this.coletor_id = rs.getLong("coletor_id");
			this.relatorio = rs.getString("relatorio");
			this.status = rs.getInt("status");
			
			
			this.nfe = rs.getInt("nfe");
			try {
				this.author = rs.getString("author");
			}catch (NullPointerException e ) {
				System.out.println("Null pointer - Orcamento");
			}
			this.empresaId = rs.getLong("empresa_id");
			
			this.timestamp = rs.getTimestamp("data_chegada_sql");
			
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

	public Date getData_saida() {
		return data_saida;
	}

	public void setData_saida(Date data_saida) {
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamento other = (Orcamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
