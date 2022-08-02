package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.MainViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
		data.setText(equipamento.getDataChegada());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		ultimaCal.setText(equipamento.getUltimaCalib());	
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
	public void cancelar(ActionEvent event) {
		try {
			Stage stage = (Stage) cancelar.getScene().getWindow(); 
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}