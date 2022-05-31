package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OrcamentoViewController implements Initializable {
	
	@FXML
	private Button cancelar;
	
	@FXML
	private ImageView cancelarImg;
		
	@FXML
	public TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal;
	@FXML
	private TextArea obs;
	
	
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Orcamento orcamento = MainViewController.orcamento;
		Equipamento equipamento = MainViewController.equipamento;
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(equipamento.getDataChegada());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		ultimaCal.setText(equipamento.getUltimaCalib());	
		
		obs.setText(orcamento.getItem());
		
	}	

	
	@FXML
	public void cancelar(ActionEvent event) {
		try {
			Stage stage = (Stage) cancelar.getScene().getWindow(); 
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}