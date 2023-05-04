package com.hrodriguesdev.gui.controller.view.updatede;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.utilitary.Itens;

import javafx.fxml.Initializable;

public class OrcamentoUpdatedeDois extends OrcamentoInsert implements Initializable {
	
	private Orcamento orcamento;
	private Itens itens = new Itens();
	
	public OrcamentoUpdatedeDois(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
		this.orcamento = orcamento;
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		obsMateriais.addAll(itens.getAllItens(orcamento.getId(), orcamento.getItem() ) );	
	}	
	
}
