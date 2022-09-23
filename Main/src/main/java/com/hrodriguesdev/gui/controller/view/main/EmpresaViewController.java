package com.hrodriguesdev.gui.controller.view.main;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Empresa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class EmpresaViewController extends MainViewController{

	@FXML
	private ImageView salvarImg, buscarImg;
	
	@FXML
	private TableView<Empresa> tableEmpresa;
	private ObservableList<Empresa> obsEmpresa = FXCollections.observableArrayList(); 
	
	@FXML
	private TableColumn<Empresa, String> cepEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> enderecoEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> estadoEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> cidadeEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> nomeEmpresa;
	
	@FXML
	private TextField cepEmpresaEdit, enderecoEmpresaEdit, estadoEmpresaEdit, cidadeEmpresaEdit, nomeEmpresaEdit;
	
	@FXML
	private ComboBox<String> findEmpresaComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		startTableEmpresa();
		
		
	}


	private void startTableEmpresa() {
		cepEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa,String>("cep"));
		enderecoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("endereco"));
		estadoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("estado"));
//		cidadeEmpresa
//		nomeEmpresa
		
	}


	@FXML
	private void salvarEmpresa(ActionEvent event) {
		System.out.println("Salvar");
	}
	
	@FXML
	private void buscarEmpresa(ActionEvent event) {
		System.out.println("Salvar");
	}

}
