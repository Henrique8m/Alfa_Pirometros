package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_eletronicos")
public class EstoqueEletronicos implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private int sirene;
	private int PCIFIII;
	private int PCIFKal;
	private int DispFKal;
	private int FIII;
	private int Indicmax;
	private int CIFII;
	private int CIIndicmax;

}
