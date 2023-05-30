package com.hrodriguesdev.entities;

/*
 * Classse responsavel de armazenar todos os valores pertinente a resultados
 * da calibracao
*/
public class CalibracaoEnsaio {
	/*
	 * Valor 1 e Valor 2, sÃ£o os valores referencia que sÃ£o aplicados 
	 * Fem1 e Fem2, Forca eletro motriz, grandesa aplicada no equipamento 
	 * em1 e em2, erro da medicao na grandesa aplicada no equipamento 
	 * emIndicada1 e emIndicada2, erro da medicao na grandesa indicada no equipamento 
	 * desvio1 e desvio2,a referencia menos emIndicado sai o desvio na grandeza do equipamento
	 * ism1 e ism2, inserteza do sistema de medicao, calculado com a formula
	 */	
	private String valor1;
	private String valor2;
	private String fem1;
	private String fem2;
	private String em1;
	private String em2;
	private String emIndicada1;
	private String emIndicada2;
	private String desvio1;
	private String desvio2;
	private String ism1;
	private String ism2;
	private String fatorK = "1,65";
	
	public CalibracaoEnsaio() {
		
	}
		
	public String getValor1() {
		return valor1;
	}
	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}
	public String getValor2() {
		return valor2;
	}
	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}
	public String getFem1() {
		return fem1;
	}
	public void setFem1(String fem1) {
		this.fem1 = fem1;
	}
	public String getFem2() {
		return fem2;
	}
	public void setFem2(String fem2) {
		this.fem2 = fem2;
	}
	public String getEm1() {
		return em1;
	}
	public void setEm1(String em1) {
		this.em1 = em1;
	}
	public String getEm2() {
		return em2;
	}
	public void setEm2(String em2) {
		this.em2 = em2;
	}
	public String getEmIndicada1() {
		return emIndicada1;
	}
	public void setEmIndicada1(String emIndicada1) {
		this.emIndicada1 = emIndicada1;
	}
	public String getEmIndicada2() {
		return emIndicada2;
	}
	public void setEmIndicada2(String emIndicada2) {
		this.emIndicada2 = emIndicada2;
	}
	public String getDesvio1() {
		return desvio1;
	}
	public void setDesvio1(String desvio1) {
		this.desvio1 = desvio1;
	}
	public String getDesvio2() {
		return desvio2;
	}
	public void setDesvio2(String desvio2) {
		this.desvio2 = desvio2;
	}
	public String getIsm1() {
		return ism1;
	}
	public void setIsm1(String ism1) {
		this.ism1 = ism1;
	}
	public String getIsm2() {
		return ism2;
	}
	public void setIsm2(String ism2) {
		this.ism2 = ism2;
	}

	public String getFatorK() {
		return fatorK;
	}

	public void setFatorK(String fatorK) {
		this.fatorK = fatorK;
	}
	
}
