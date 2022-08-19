package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.InputFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EquipamentoViewControllerDois {

	protected Date date;
	protected OrcamentoController controller = MainViewController.orcamentoController;
	protected EquipamentoController equipamentoController = MainViewController.equipamentoController;
	
	
	@FXML
	protected ImageView cancelarImg, salvarImg, salvarImg1, logoYgg;
	@FXML
	protected Button salvar;
	@FXML
	protected TextField data;
	@FXML
	protected Text erro;
	@FXML
	protected TextField ultimaCalTxt;
	@FXML
	protected TextField modeloTxt;
	@FXML
	protected TextField nsTxt;
	@FXML
	protected TextField patTxt;
	
	
	@FXML
	protected ComboBox<String> nomeEmpressa;
	
	public static ObservableList<String> obsString = FXCollections.observableArrayList();	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@FXML
	private TableView<Equipamento> tableEquipamentos;
	@FXML
	private TableColumn<Equipamento, String> modelo;
	@FXML
	private TableColumn<Equipamento, String> ns;
	@FXML
	private TableColumn<Equipamento, String> pat;
	@FXML
	private TableColumn<Equipamento, Date> ultimaCal;
    private  ObservableList<Equipamento> obsListEquipamentos = FXCollections.observableArrayList();
    
		
	public EquipamentoViewControllerDois() {
		super();
	}

	@FXML
	public void adcionar(ActionEvent event) {
		Equipamento obj = new Equipamento();
		if(nomeEmpressa.getValue()== "" ||  modeloTxt.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e Modelo, não pode ser nulo");
			return;
		}
		try {
			obj.setEmpressa( MainViewController.empressaController.isExist( nomeEmpressa.getValue() ) );
			if ( obj.getEmpressa() == null ) {
				throw new DbException("Empresa não existe");
			}
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
		try {
			obj.setEmpressaName( nomeEmpressa.getValue() );	
			obj.setModelo( modeloTxt.getText() );  
			obj.setStatus( 1 );
			obj.setDataChegada( data.getText() );			
			obj.setNs( nsTxt.getText() );
			obj.setPat( patTxt.getText() );
			if (ultimaCalTxt.getText().length() == 10 ) {
				obj.setUltimaCalibDate( Geral.dateParceString( ultimaCalTxt.getText() ) );
			}
			obj.setDataChegada(null);
			obj.setLaboratorio(true);
			if (data.getText().length() == 10 ) {
				obj.setDateChegada( Geral.dateParceString( data.getText() ) );
			}
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
			return;
		}
		try {
			obj.setId(equipamentoController.add(obj));
			if(obj.getId() != 0l) {
				Stage stage = (Stage) salvar.getScene().getWindow();
				MainViewController.obsListTableFilaEquipamentos.add(obj);
				stage.close();
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		AlfaPirometrosApplication.viewController.refreshTable();
		
	}

	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
		Stage stage = (Stage) salvar.getScene().getWindow(); 
		stage.close();
	}

	@FXML
	public void sair(ActionEvent event) {
		removeListener();
		try {
			Stage stage = (Stage) salvar.getScene().getWindow(); 
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" ) {
				
			 if(event.getTarget().equals(ultimaCalTxt)){
				 ultimaCalTxt.setText(Format.replaceData( ultimaCalTxt.getText() ) );
				 ultimaCalTxt.end();
			 }
	
		}
		
	}

	@FXML
	public void findNs(ActionEvent event) {
		Equipamento obj = equipamentoController.findByNs( nsTxt.getText() );
		if( obj != null	) {
			
			nomeEmpressa.setValue( obj.getEmpressaName() );	
			modeloTxt.setText( obj.getModelo() );  
			patTxt.setText( obj.getPat() );
			ultimaCalTxt.setText( obj.getDataCal() );	
		}		
		
	}

	public void initialize(URL location, ResourceBundle resources) {
		addListener();
	
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		salvarImg1.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
		date = new Date(System.currentTimeMillis());
	    data.setText(Format.formatData.format(date));
	    
	}

	
	public void addListener() {
		obsString =  MainViewController.empressaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( nomeEmpressa, filteredList );
		nomeEmpressa.getEditor().textProperty().addListener(inputFilter);	
		
	}
	
	private void removeListener() {
		nomeEmpressa.getEditor().textProperty().removeListener(inputFilter);
		nomeEmpressa.setValue("");
	}


}