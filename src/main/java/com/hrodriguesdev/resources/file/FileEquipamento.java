package com.hrodriguesdev.resources.file;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.EnsaioViewController;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;

/*
 * chamada na main view certificadoPaginaController
 * Para inserir arquivo de equipamento
*/
public class FileEquipamento extends EnsaioViewController{

	public FileEquipamento(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString());
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString());
		cancelarImg.setImage(image);
		ModeloFile.setVisible(true);
	}

	@Override
	protected void salvarReferencia(ActionEvent e) {
		if(!ModeloFile.getText().isBlank()&&!ModeloFile.getText().isEmpty()) {
			equipamento.setModelo(ModeloFile.getText());
			super.salvarReferencia(e);
		}
	}

	@Override
	protected void infoEquip(ActionEvent e) {
		if(!ModeloFile.getText().isBlank()&&!ModeloFile.getText().isEmpty()) {
			String modelo = equipamento.getModelo();
			equipamento.setModelo(ModeloFile.getText());
			super.infoEquip(e);
			equipamento.setModelo(modelo);
		}		
	}
	
	@Override
	protected void ensaioGet() {}
	
	@Override
	protected void salvar(ActionEvent e) {}

	@Override
	protected void readRefPadrao() {}

}
