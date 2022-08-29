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
//		Orcamento orcamento = MainViewController.orcamentoEdit;
//		String bruto = orcamento.getItem();
//		String[] separator =  bruto.split("\n");
//		if( separator.length > 0) {
//			for(int i=0; i < separator.length; i++) {
//				if(i != 0) {
//					obsMateriais.add(new Orcamento(separator[i], 0));
//				}
//			}
//		}
//
//		
//		quantidadeItem.setValue("1");
//		inputFilterNewItem = new InputFilter<String>( newItem,  new FilteredList<>(AlfaPirometrosApplication.obsPecasEstoque) );
//		listener = new InputFilter<String>( quantidadeItem,  new FilteredList<>(AlfaPirometrosApplication.obsQuantidade) );
//		conboBoxInit();
//		imageInit();
//		textFildInserts();
//		tabelaInit();
		
		
	}	
	
}
