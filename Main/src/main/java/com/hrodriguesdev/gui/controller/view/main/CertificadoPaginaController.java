package com.hrodriguesdev.gui.controller.view.main;

import com.hrodriguesdev.entities.Equipamento;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CertificadoPaginaController extends MainViewController{
	
	@FXML
	private ComboBox<String> textEmpresaCertificado;
	
	@FXML
	private TextField textNsEquipCertificado, textPatEquipCertificado;
	
	@FXML
	private TableView<Equipamento> tableCertificados;

}
