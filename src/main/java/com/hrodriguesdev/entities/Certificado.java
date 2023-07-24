package com.hrodriguesdev.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Certificado {
	private Long id;
	private Long equipamento_id;
	private Date date_cal;
	private int numero;
	private Long ensaio_id = 0l;
	
	public Certificado(Long equipamento_id, Date date_cal, int numero, Long ensaio_id) {
		super();
		this.equipamento_id = equipamento_id;
		this.date_cal = date_cal;
		this.numero = numero;
		this.ensaio_id = ensaio_id;
	}
	
	public Certificado(Long equipamento_id, Date date_cal, int numero) {
		super();
		this.equipamento_id = equipamento_id;
		this.date_cal = date_cal;
		this.numero = numero;
	}
	
	public Certificado(ResultSet rs) {
		try {
			this.id = (rs.getLong("id"));
			this.equipamento_id = rs.getLong("equipamento_id");
			date_cal( rs.getDate("date_cal") );
			this.numero = rs.getInt("numero");
			this.ensaio_id = rs.getLong("ensaio_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Long getEquipamento_id() {
		return equipamento_id;
	}
	public void setEquipamento_id(Long equipamento_id) {
		this.equipamento_id = equipamento_id;
	}
	public Date getDate_cal() {
		return date_cal;
	}
	
	public void setDate_cal(Date date_cal) {
		this.date_cal = date_cal ;
	}
	
	@Deprecated
	public void date_cal(Date date_cal) {
		int date = date_cal.getDate() + 1;
		date_cal.setDate(date);
		this.date_cal = date_cal ;
	}
	
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Long getId() {
		return id;
	}

	public Long getEnsaio_id() {
		return ensaio_id;
	}
	
	@Override
	public String toString() {
		return "Numero - " + numero + " Data - " + date_cal.toString() + "\n";
	}
		
	
}
