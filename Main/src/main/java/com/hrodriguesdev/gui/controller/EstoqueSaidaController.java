package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class EstoqueSaidaController  extends OrcamentoInsert implements Initializable{
	
	@FXML
	private VBox chegada, modeloVbox, nsVbox, patVbox, calVbox;

	public EstoqueSaidaController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
