package com.hrodriguesdev.entities.DTO;

import java.sql.Date;

public class CertificadoDTO {
	private int numero;
	private Date dateCal;
	private String empresa;
	private String modelo;
	private String ns;
	private String pat;
	
	public CertificadoDTO(
			int numero,
			Date dateCal, 
			String modelo,
			String ns,
			String pat, String empresa) {
		super();
		this.numero = numero;
		this.dateCal = dateCal;
		this.modelo = modelo;
		this.ns = ns;
		this.pat = pat;
		this.empresa = empresa;
	}
	public String getEmpresa() {
		return empresa;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public Date getDateCal() {
		return dateCal;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public String getNs() {
		return ns;
	}
	
	public String getPat() {
		return pat;
	}

}
