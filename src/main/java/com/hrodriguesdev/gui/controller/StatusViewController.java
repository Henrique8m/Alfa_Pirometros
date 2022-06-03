package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.db.DbException;
import com.hrodriguesdev.gui.alert.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StatusViewController implements Initializable{
	private Controller controller;
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
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = MainViewController.controller;
		
	}
	
	private void update(int status) {		
		try {
			controller.updatedeEquipamento( MainViewController.equipamento.getId(), status );
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
			}


}
