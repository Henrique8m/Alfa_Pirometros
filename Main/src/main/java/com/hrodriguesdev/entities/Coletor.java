package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Coletor implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String empressaName;
	private Long orcamento_id;
	private String nomeColetor;
	private String dataHoraColeta;
	private Date date;
	private int horaColeta;
		
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

	public Long getOrcamento_id() {
		return orcamento_id;
	}

	public void setOrcamento_id(Long empressa_id) {
		this.orcamento_id = empressa_id;
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
				coletor.setEmpressaName( rs.getString("empressaName") );
				coletor.setNomeColetor( rs.getString("nomeColetor") );
				
				if(rs.getString( "dataHoraColeta" )!= null)
					coletor.setDataHoraColeta( rs.getString( "dataHoraColeta" ) );
				
				coletor.setOrcamento_id( rs.getLong("orcamento_id") );	
				
				Date date = rs.getDate("dateColeta");
				if(date != null)coletor.date(date);

				coletor.setHoraColeta(rs.getInt("horaColeta") );
			
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
	
	@SuppressWarnings("deprecation")
	public void date(Date date) {
		int datee = date.getDate() + 1;
		date.setDate(datee);
		this.date = date;
	}


	public int getHoraColeta() {
		return horaColeta;
	}


	public void setHoraColeta(int horaColeta) {
		this.horaColeta = horaColeta;
	}


}
