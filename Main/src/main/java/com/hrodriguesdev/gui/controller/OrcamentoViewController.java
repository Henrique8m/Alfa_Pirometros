package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrcamentoViewController implements Initializable {
	
	@FXML
	private Button cancelar, orcamentoEnviado, aprovado, aprovadoSemOrca, liberado, naoAprovado, liberadoSemOrcamento;
	
	@FXML
	private ImageView cancelarImg, salvarImg;
		
	@FXML
	public TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal, relatorioN;
	@FXML
	private TextArea obs;
	
	private Equipamento equipamento;
	private Orcamento orcamento;
	private Itens itens = new Itens();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orcamento = MainViewController.orcamento;
		equipamento = MainViewController.equipamento;
		if( equipamento.getRelatorio() != null && relatorioN.getText() != "" ) {
			relatorioN.setText( equipamento.getRelatorio() );
		}
		switchStatus(equipamento.getStatus());		
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(Format.formatData.format(orcamento.getData_chegada()));
		
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		
		obs.setText( itens.allItens(orcamento.getId()) );
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
	private void salvar(ActionEvent event) {
		if(relatorioN.getText() != null && relatorioN.getText() != "" ) {
			MainViewController.orcamento.setRelatorio(relatorioN.getText() );
		}
		try {
			MainViewController.orcamentoController.updatedeStatusRelatorio( MainViewController.equipamento.getId(), MainViewController.orcamento.getStatus(), MainViewController.orcamento );
			Alerts.showAlert("Status ", "Status Alterado com sucesso", "Equipamento da Empressa " + MainViewController.equipamento.getEmpressaName() , AlertType.INFORMATION);
		} catch (DbException e1) {
			Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR);
		}
		AlfaPirometrosApplication.viewController.refreshTable();
		NewView.fecharView();
	}
	
	protected void update(int status) {		
		MainViewController.orcamento.setStatus(status);
	}
		
	@FXML
	protected void liberado(ActionEvent e) {
		update(5);
		liberado.setVisible(false);
	}
	
	@FXML
	private void aguardandoAprovacao(ActionEvent e) {
		update(3);
		orcamentoEnviado.setVisible(false);
	}
	
	@FXML
	private void aguardandoReparo(ActionEvent e) {
		update(4);
	}
	
	@FXML
	private void naoAprovado(ActionEvent e) {
		update(6);
	}
	
	@FXML
	private void liberadoSemOrcamento(ActionEvent e) {
		update(9);
		liberado.setVisible(false);
	}
			
	@FXML
	public void cancelar(ActionEvent event) {
		NewView.fecharView();
	}
	
}