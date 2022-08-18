package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OrcamentoViewController extends StatusViewController implements Initializable {
	
	@FXML
	private Button cancelar, orcamentoEnviado, aprovado, aprovadoSemOrca, liberado, naoAprovado, liberadoSemOrcamento;
	
	@FXML
	private ImageView cancelarImg, salvarImg;
		
	@FXML
	public TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal, relatorioN;
	@FXML
	private TextArea obs;
	
	private Equipamento equipamento;
	
	@FXML
	private void salvar(ActionEvent event) {
		if(relatorioN.getText() != null && relatorioN.getText() != "" ) {
			equipamento.setRelatorio( relatorioN.getText() );
			MainViewController.equipamentoController.updatede(equipamento.getId(), equipamento.getStatus() , equipamento);
		}
		try {
			Stage stage = (Stage) cancelar.getScene().getWindow(); 
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		AlfaPirometrosApplication.viewController.refreshTable();;

	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Orcamento orcamento = MainViewController.orcamento;
		equipamento = MainViewController.equipamento;
		if( equipamento.getRelatorio() != null && relatorioN.getText() != "" ) {
			relatorioN.setText( equipamento.getRelatorio() );
		}
		switchStatus(equipamento.getStatus());		
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(Format.formatData.format(equipamento.getDateChegada()));
		
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		
		Date date = equipamento.getUltimaCalibDate();
		if(date != null) ultimaCal.setText(Format.formatData.format(date));	
		
		obs.setText(orcamento.getItem());
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		salvarImg.setImage(image);
	}	

	
	private void switchStatus(int status) {
		switch (status) {
		case 3:
			aprovado.setVisible(true);
			break;
		case 13:
			aprovado.setVisible(true);
			break;
		case 4:
			liberado.setVisible(true);
			break;
		case 8:
			liberadoSemOrcamento.setVisible(true);
			break;
		default:
			orcamentoEnviado.setVisible(true);
			aprovadoSemOrca.setVisible(true);
			break;
		}
		
	}
	
	@FXML
	@Override
	protected void liberado(ActionEvent e) {
		update(5);
	}

	@Override
	protected void update(int status) {		
		try {
			
			equipamentoController.updatede( MainViewController.equipamento.getId(), status, MainViewController.equipamento );
			Alerts.showAlert("Status ", "Status Alterado com sucesso", "Equipamento da Empressa " + MainViewController.equipamento.getEmpressaName() , AlertType.INFORMATION);
		} catch (DbException e1) {
			Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR);
		}
		
		AlfaPirometrosApplication.viewController.refreshTable();
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