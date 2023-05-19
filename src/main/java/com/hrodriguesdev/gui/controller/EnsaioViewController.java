package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EnsaioViewController implements Initializable{
	
	private Equipamento equipamento;
	private Orcamento orcamento; 
	private Ensaios ensaio;
	
	@FXML
	private TextField refeVal1, refeVal2, refeVal3,
						aplicado1, aplicado2, aplicado3,
						sinalCalibr11, sinalCalibr12, sinalCalibr13,
						sinalCalibr21, sinalCalibr22, sinalCalibr23;
	
	@FXML
	private ImageView salvarImg, cancelarImg;
	
	public EnsaioViewController(Equipamento equipamento, Orcamento orcamento) {
		this.orcamento = orcamento;
		this.equipamento = equipamento;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void salvar( ActionEvent e) {
		ensaio = new Ensaios();
		ensaio.setReferencia(
				refeVal1.getText() + "\n"
				+ refeVal2.getText() + "\n"
				+ refeVal3.getText());
		ensaio.setPrimeiro(aplicado1.getText() + "\n"
				+ aplicado2.getText()  + "\n"
				+ aplicado3.getText());
		
		ensaio.setSegundo(sinalCalibr11.getText() + "\n"
				+ sinalCalibr12.getText()  + "\n"
				+ sinalCalibr13.getText());
		
		ensaio.setTerceiro(sinalCalibr21.getText()  + "\n"
				+ sinalCalibr22.getText() + "\n"
				+ sinalCalibr23.getText());
		
		ensaio.setOrcamento_id(orcamento.getId());
		System.out.println(ensaio.getReferencia() + "\n"
				+ ensaio.getPrimeiro() + "\n"
						+ ensaio.getSegundo() + "\n"
								+ ensaio.getTerceiro());
	
		Stage stage = (Stage) refeVal1.getScene().getWindow();
		stage.close();
	}
	
	
	@FXML
	private void cancelar(ActionEvent e) {
		Stage stage = (Stage) refeVal1.getScene().getWindow();
		stage.close();
	}
	

}
