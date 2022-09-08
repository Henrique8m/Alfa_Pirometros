package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
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

public class OrcamentoView implements Initializable {
	
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
	private Itens itens;
	;
	
	public OrcamentoView(Equipamento equipamento, Orcamento orcamento) {
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itens = new Itens();
		if( equipamento.getRelatorio() != null && relatorioN.getText() != "" ) 
			relatorioN.setText( equipamento.getRelatorio() );
		
		if(equipamento.getUltimaCalibDate() != null) 
			ultimaCal.setText( Format.formatData.format(equipamento.getUltimaCalibDate()) );
		switchStatus(equipamento.getStatus());		
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(Format.formatData.format(orcamento.getData_chegada()));
		
		
		
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		
		obs.setText( allItens(orcamento.getId(), orcamento) );
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		salvarImg.setImage(image);
	}	
		
	private String allItens(Long orcamento_id, Orcamento orcamento) {
		ItensRepositoryFind find = new ItensRepositoryFind();
		String output = "";
		
		output = output + find.consumoByOrcamentoId(orcamento_id).toString();
		output = output + find.eletricosByOrcamentoId(orcamento_id).toString();
		output = output + find.eletronicosByOrcamentoId(orcamento_id).toString();
		output = output + find.esteticoByOrcamentoId(orcamento_id).toString();
		output = output + find.sinalByOrcamentoId(orcamento_id).toString();
		try{
			output = output + find.cabosByOrcamentoId(orcamento_id).toString();
		}catch (NullPointerException e) {
		}
		
		output = output + orcamento.toString();
		return output;
	}

	
	private void switchStatus(int status) {
		switch (status) {
		case 3:
			aprovado.setVisible(true);
			break;
		case 13:
			aprovado.setVisible(true);
			naoAprovado.setVisible(true);
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
			orcamento.setRelatorio(relatorioN.getText() );
		}
		try {
			MainViewController.orcamentoController.updatedeStatusRelatorio( orcamento.getId(), orcamento.getStatus(), orcamento );
			Alerts.showAlert("Status ", "Status Alterado com sucesso", "Equipamento da Empressa " + equipamento.getEmpressaName() , AlertType.INFORMATION);
		} catch (DbException e1) {
			Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR);
		}
		AlfaPirometrosApplication.viewController.refreshTable();
		NewView.fecharView();
	}
	
	protected void update(int status) {		
		orcamento.setStatus(status);
	}
		
	@FXML
	protected void liberado(ActionEvent e) {
		update(5);
		itens.setSaida(orcamento.getId());
		liberado.setVisible(false);
		aprovadoSemOrca.setVisible(false);
	}
	
	@FXML
	private void aguardandoAprovacao(ActionEvent e) {
		update(3);
		orcamentoEnviado.setVisible(false);
		aprovadoSemOrca.setVisible(false);
	}
	
	@FXML
	private void aguardandoReparo(ActionEvent e) {
		update(4);
		aprovado.setVisible(false);
		naoAprovado.setVisible(false);
	}
	
	@FXML
	private void naoAprovado(ActionEvent e) {
		update(6);
		naoAprovado.setVisible(false);
		aprovado.setVisible(false);
		}
	
	@FXML
	private void liberadoSemOrcamento(ActionEvent e) {
		update(9);
		itens.setSaida(orcamento.getId());
		liberado.setVisible(false);
	}
			
	@FXML
	public void cancelar(ActionEvent event) {
		NewView.fecharView();
	}


	
}
