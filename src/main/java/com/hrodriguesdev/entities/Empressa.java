package com.hrodriguesdev.entities;

import java.io.Serializable;

//@Entity
//@Table(name = "tb_empressa")
public class Empressa implements Serializable {	

	private static final long serialVersionUID = 1L;
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String cidade;
	private String estado;
	private String endereço;
	private int nivelConfianca;
	
	public Empressa() {}

}
