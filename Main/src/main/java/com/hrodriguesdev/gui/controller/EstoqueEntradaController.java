package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;

public class EstoqueEntradaController extends OrcamentoInsert implements Initializable{

	public EstoqueEntradaController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	private Orcamento orcamento;
	private OrcamentoController orcamentoController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quantidadeItem.setValue("1");
		chegada.setVisible(false);
		modeloVbox.setVisible(false);
		nsVbox.setVisible(false);
		patVbox.setVisible(false);
		calVbox.setVisible(false);
		empressaVBox.setVisible(false);
		observacaoVbox.setVisible(false);
		nfeVbox.setVisible(true);
		nfeText.setEditable(true);
		infoText.setVisible(true);
		conboBoxInit();
		tabelaInit();
		imageInit();
		
	}
	
	@Override
	protected void salvar(ActionEvent event) throws IOException {
		if( !nfeText.getText().isBlank() && nfeText.getText() != "0") {		
			
			
			orcamento = createOrcamento();
			orcamentoController = new OrcamentoController();
			
			Long orcamento_id = orcamentoController.add(orcamento);
			orcamento.setId(orcamento_id);
			
			if(orcamento_id == null) {
				erro.setText("Erro ao salvar orcamento");
				return;
			}
			int nfe = 0;
			try {
				nfe = Integer.parseInt( nfeText.getText() );
			}catch(NumberFormatException e) {
				Alerts.showAlert("NFE", "Numero da nota errado! Certifique de colocar somente numeros", "", AlertType.ERROR);
				return;
			}

			boolean entrada = true;
			orcamento.setNfe(nfe);
						
			Itens itens = new Itens(orcamento_id, false , nfe , entrada);
			
			if(obsMateriais.size()>0) {
				obsMateriais.forEach((orcamento)-> {	
	
				if( !itens.addItem(orcamento.getItemRealString(), orcamento.getQuantidade() ) )	{
						String itemStr = orcamento.getItem();
						this.nova = this.list + itemStr  + "\n";
						this.list = nova;
					}				
				});
				if(!this.list.isEmpty())
					orcamento.setItem(list);		
				orcamento.setStatus(20);
				if(itens.saveAll( orcamento) ) {
					try {
						cancelar( new ActionEvent() );
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}else {
					erro.setText("Erro");
				}
			}
			
			NewView.addChildrenn((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
		}else 
			erro.setText("numero da nfe nao pode estar em branco");
		
	}
	
	@FXML
	protected void cancelar(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}
	
	protected Orcamento createOrcamento() {
		Orcamento obj = new Orcamento();
		obj.setEquipamento_id(0l);
		obj.setData_chegada(new Date(System.currentTimeMillis() ) );
		obj.setLaboratorio(false);
		return obj;
	}
	
	

}
