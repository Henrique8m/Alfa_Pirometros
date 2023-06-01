package com.hrodriguesdev.entities;

/*
 * A descricao esta conforme o certificado que usamos de padrao
 * onde a fem - é o sinal aplicado na entrado do equipamento
 * e unidade sera o sinal indicado no visor do aparelho
 * a medida é a escala ao qual o sinal é indicado no visor
*/
public class DescricaoInstrumento {
	private String modelo = "Modelo";
	private String instrumento = "Instrumento";
	private String fabricante = "Fabricante";
	private String resolucao = "Resolucao";
	private String fem = "FEM";
	private String unidade = "Unidade";
	private String medida = "TEMPERATURA";
	
	/*
	 * O nome do sensor, sera o nome da tabela qual esta informacao de forca eletro
	 * motris e unidade;
	 */	
	private String sensor = "TermoparS";
	
	public DescricaoInstrumento(String[] fileModelo) {
		if(fileModelo != null ) 
			for(String info: fileModelo) {
				String[] split = info.split("=");
				if(split.length>1) {
					String descricao = split[0];
					String informacao = split[1];
					switch (descricao.toString()) {
					case "Modelo":
						this.modelo = informacao;
						break;
					case "Instrumento":
						this.instrumento = informacao;
						break;
					case "Fabricante":
						this.fabricante = informacao;
						break;
					case "Resolucao":
						this.resolucao = informacao;
						break;
					case "FEM":
						this.fem = informacao;
						break;
					case "Unidade":
						this.unidade = informacao;
						break;
					case "Medida":
						this.medida = informacao;
						break;
					case "Sensor":
						this.sensor = informacao;
					default:
						break;
					}
				}
			}
	}
	
	public String getModelo() {
		return modelo;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public String getFabricante() {
		return fabricante;
	}
	public String getResolucao() {
		return resolucao;
	}
	public String getFem() {
		return fem;
	}
	public String getUnidade() {
		return unidade;
	}

	public String getMedida() {
		return medida;
	}

	public String getSensor() {
		return sensor;
	}
	

}
