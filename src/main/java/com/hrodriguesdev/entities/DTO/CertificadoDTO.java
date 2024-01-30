package com.hrodriguesdev.entities.DTO;

import java.sql.Date;

public class CertificadoDTO {
	private int numero;
	private Date dateCal;
	private String empresa;
	private String modelo;
	private String ns;
	private String pat;
	private String cidade;
	private String endereco;
	
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
	
	public CertificadoDTO(
			int numero,
			Date dateCal, 
			String modelo,
			String ns,
			String pat, 
			String empresa,
			String cidade,
			String endereco) {
		super();
		this.numero = numero;
		this.dateCal = dateCal;
		this.modelo = modelo;
		this.ns = ns;
		this.pat = pat;
		this.empresa = empresa;
		this.cidade = cidade;
		this.endereco = endereco;
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
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((ns == null) ? 0 : ns.hashCode());
		result = prime * result + ((pat == null) ? 0 : pat.hashCode());
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
		CertificadoDTO other = (CertificadoDTO) obj;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (ns == null) {
			if (other.ns != null)
				return false;
		} else if (!ns.equals(other.ns))
			return false;
		if (pat == null) {
			if (other.pat != null)
				return false;
		} else if (!pat.equals(other.pat))
			return false;
		return true;
	}
	
	

}
