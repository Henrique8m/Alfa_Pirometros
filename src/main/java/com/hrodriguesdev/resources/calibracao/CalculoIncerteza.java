package com.hrodriguesdev.resources.calibracao;

public class CalculoIncerteza {
	
	private double media=0;
	private double erroSis=0;
	private double desvioPadrao=0;
	private double erroAle=0;
	private double incertezaMed=0;
	private double incertezaPad=0;
	private double incertezaFinal=0;
	
	
	public double getMedia() {
		return media;
	}


	public double getErroSis() {
		return erroSis;
	}


	public double getDesvioPadrao() {
		return desvioPadrao;
	}


	public double getErroAle() {
		return erroAle;
	}


	public double getIncertezaMed() {
		return incertezaMed;
	}


	public double getIncertezaPad() {
		return incertezaPad;
	}


	public double getIncertezaFinal() {
		return incertezaFinal;
	}


	public CalculoIncerteza(double[] valor, double objetivo) {
		

		double i = 0;
		double raiz= 0;
		
		for(double x :valor) {
			media = media + x;
			i++;
		}
		media = media/i;
		
		for(double x :valor) {
			raiz += Math.pow(x-media, 2);
		}				
		
		Double raizz = Math.sqrt(raiz/(i-1));
		
		desvioPadrao =  raizz.intValue();
		
		erroAle = (int) (1.65 * desvioPadrao);		
		
		
		
		erroSis = objetivo - media;
		
		incertezaMed = erroSis + erroAle;
		
		incertezaPad = 0.1;
		
		incertezaFinal =  Math.sqrt(Math.pow(incertezaMed, 2)+ Math.pow(incertezaPad, 2));
		
	}
		
}
