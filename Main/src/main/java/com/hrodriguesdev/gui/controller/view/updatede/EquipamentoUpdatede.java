package com.hrodriguesdev.gui.controller.view.updatede;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.controller.view.insert.EquipamentoInsert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EquipamentoUpdatede extends EquipamentoInsert implements Initializable{
	
	private Equipamento equipamento;
	
	@FXML
	private Button cancelar;
	
//	recebe no construtor o equipamento que vamos atualizar
	public EquipamentoUpdatede( Equipamento equipamento ) {
		this.equipamento = equipamento;
	}

	public void cancelar(ActionEvent event) {
		Stage stage = (Stage) cancelar.getScene().getWindow(); 
		stage.close();
	}
	
//	Atualizar o equipamento com o que foi passado de novo, 
	public void atualizar(ActionEvent event) {
		if(nsTxt.getText().isBlank() && patTxt.getText().isBlank() || modeloTxt.getText().isBlank() ) {
			erro.setText("Campos nulo");
			return;
		}
		try {
			equipamento.setModelo( modeloTxt.getText() );  	
			equipamento.setNs( nsTxt.getText() );
			equipamento.setPat( patTxt.getText() );
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
			return;
		}
		try {			
			if( equipamentoController.updatedeNsPatModelo(equipamento)  ) {
				Stage stage = (Stage) cancelar.getScene().getWindow(); 
				stage.close();
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		
	}	
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
	
		modeloTxt.setText(equipamento.getModelo());
		nsTxt.setText(equipamento.getNs());
		patTxt.setText(equipamento.getPat());
		   
	}

}

