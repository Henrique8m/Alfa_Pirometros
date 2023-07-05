package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;

import javafx.fxml.Initializable;

public class EstoqueEntradaController extends EstoqueSaidaController implements Initializable{

	public EstoqueEntradaController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		super.saida = false;
	}
	
}
