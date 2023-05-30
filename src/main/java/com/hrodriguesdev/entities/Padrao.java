package com.hrodriguesdev.entities;

public class Padrao {
	private String identificacao;
	private String instrumento;
	private String tipo;
	private String certificado;
	private String validade;
	private String rastreabilidade;
	
	public Padrao(String[] fileInformation) {
		if(fileInformation != null ) 
			for(String info: fileInformation) {
				String descricao = info.split("=")[0];
				String informacao = info.split("=")[1];
				switch (descricao.toString()) {
				case "Identificacao":
					this.identificacao = informacao;
					break;
				case "Instrumento":
					this.instrumento = informacao;
					break;
				case "Tipo":
					this.tipo = informacao;
					break;
				case "Validade":
					this.validade = informacao;
					break;
				case "Rastreabilidade":
					this.rastreabilidade = informacao;
					break;
				default:
					break;
				}
			}
	}
	
	
	public String getIdentificacao() {
		return identificacao;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public String getTipo() {
		return tipo;
	}

	public String getValidade() {
		return validade;
	}

	public String getRastreabilidade() {
		return rastreabilidade;
	}


	public String getCertificado() {
		return certificado;
	}
		
}
