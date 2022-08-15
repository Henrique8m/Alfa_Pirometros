package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Coletor implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String empressaName;
	private Long equipamento_id;
	private String nomeColetor;
	private String dataHoraColeta;
	private Date date;
		
	public Coletor(){}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpressaName() {
		return empressaName;
	}
	public void setEmpressaName(String empressaName) {
		this.empressaName = empressaName;
	}
	public String getNomeColetor() {
		return nomeColetor;
	}
	public void setNomeColetor(String nomeColetor) {
		this.nomeColetor = nomeColetor;
	}

	public Long getEquipamento_id() {
		return equipamento_id;
	}

	public void setEquipamento_id(Long empressa_id) {
		this.equipamento_id = empressa_id;
	}


	public String getDataHoraColeta() {
		return dataHoraColeta;
	}


	public void setDataHoraColeta(String dataHoraColeta) {
		this.dataHoraColeta = dataHoraColeta;
	}


	public static Coletor parceColetor(ResultSet rs) {
		Coletor coletor = new Coletor();
		if(rs != null) {
			try {
				coletor.setId( rs.getLong(1) );	
				coletor.setEmpressaName( rs.getString(2) );
				coletor.setNomeColetor( rs.getString(3) );
				coletor.setDataHoraColeta( rs.getString(4) );
				coletor.setEquipamento_id( rs.getLong(5) );	
				coletor.setDate(rs.getDate("date"));
			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} 
		}
		return coletor;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


}
