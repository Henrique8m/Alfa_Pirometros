package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_sinal")
public class EstoqueSinal implements Serializable {

	private static final long serialVersionUID = 1L;	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private int S;
	private int SU;
	private int Ecil;
	private int K;
	private int PlugFS;
	private int PlugFK;
	private int PlugMS;
	private int PlugMK;
	private int TomadaS;

}
