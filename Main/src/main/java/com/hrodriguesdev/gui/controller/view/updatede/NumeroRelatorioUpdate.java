package com.hrodriguesdev.gui.controller.view.updatede;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.service.OrcamentoService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NumeroRelatorioUpdate implements Initializable{

	private Orcamento orcamento;
	private OrcamentoService service = new OrcamentoService();
	
	@FXML
	private TextField relatorioNumero;

	public NumeroRelatorioUpdate(Orcamento orcamento) {
		this.orcamento = orcamento;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(orcamento.getRelatorio()!= null)
			relatorioNumero.setText(orcamento.getRelatorio());
	}
	
	@FXML
	private void salvar(ActionEvent event) {
		if(!relatorioNumero.getText().isBlank()) {
			orcamento.setRelatorio(relatorioNumero.getText());
			service.updatedeStatusRelatorio(orcamento.getId(), orcamento.getStatus(), orcamento);
			Stage stage = (Stage) relatorioNumero.getScene().getWindow();
			stage.close();
		}
	}
	
}
