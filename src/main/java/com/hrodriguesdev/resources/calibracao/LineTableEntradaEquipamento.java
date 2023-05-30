package com.hrodriguesdev.resources.calibracao;

public class LineTableEntradaEquipamento {
	private double indicado;
	private double aplicado;

	public double getIndicado() {
		return indicado;
	}
	
	public void setIndicado(double indicado) {
		this.indicado = indicado;
	}
	
	public double getAplicado() {
		return aplicado;
	}
	
	public void setAplicado(double aplicado) {
		this.aplicado = aplicado;
	}

	@Override
	public String toString() {
		return indicado + "�C\n" + aplicado;
	}
}
