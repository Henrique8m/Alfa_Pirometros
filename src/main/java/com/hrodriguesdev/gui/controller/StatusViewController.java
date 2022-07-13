package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.MainViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StatusViewController implements Initializable{
	private EquipamentoController equipamentoController = MainViewController.equipamentoController;
	@FXML
	private Button cancelar;
	
	@FXML
	private void aguardandoOrcamento(ActionEvent e) {
		update(1);
	}
	
	@FXML
	private void enviarOrcamento(ActionEvent e) {
		update(2);
	}
	
	@FXML
	private void aguardandoAprovacao(ActionEvent e) {
		update(3);
	}
	
	@FXML
	private void aguardandoReparo(ActionEvent e) {
		update(4);
	}
	
	@FXML
	private void liberado(ActionEvent e) {
		update(5);
	}
	
	@FXML
	private void naoAprovado(ActionEvent e) {
		update(6);
	}
	
	@FXML
	private void aguardandoReparoSemOrc(ActionEvent e) {
		update(8);
	}
	
	@FXML
	private void liberadoSemOrcamento(ActionEvent e) {
		update(9);
	}
		
	private void update(int status) {		
		try {
			
			equipamentoController.updatede( MainViewController.equipamento.getId(), status, MainViewController.equipamento );
			try {
				Stage stage = (Stage) cancelar.getScene().getWindow(); 
				stage.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			Alerts.showAlert("Status ", "Status Alterado com sucesso", "Equipamento da Empressa " + MainViewController.equipamento.getEmpressaName() , AlertType.INFORMATION);
		} catch (DbException e1) {
			Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR);
		}
		
		AlfaPirometrosApplication.viewController.refreshTable();
	}	

	@Override
	public void initialize(URL location, ResourceBundle resources) {}


}
