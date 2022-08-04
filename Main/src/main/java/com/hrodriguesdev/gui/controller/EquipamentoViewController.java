package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.insert.EmpressaInsert;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EquipamentoViewController {

	protected Date date;
	protected OrcamentoController controller = MainViewController.orcamentoController;
	protected EquipamentoController equipamentoController = MainViewController.equipamentoController;

	@FXML
	protected ImageView cancelarImg;
	@FXML
	protected ImageView salvarImg;
	@FXML
	protected ImageView addEmpressaImg;
	@FXML
	protected Text erro;
	@FXML
	protected TextField data;
	@FXML
	protected TextField ultimaCal;
	@FXML
	protected TextField modelo;
	@FXML
	protected TextField ns;
	@FXML
	protected TextField pat;
	@FXML
	protected Button salvar;
	@FXML
	private Button cancelar;
	@FXML
	protected ComboBox<String> nomeEmpressa;
	
	public static ObservableList<String> obsString = FXCollections.observableArrayList();	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	public EquipamentoViewController() {
		super();
	}

	@FXML
	public void salvar(ActionEvent event) {
		Equipamento obj = new Equipamento();
		if(nomeEmpressa.getValue()== "" ||  modelo.getText()== "" ) {
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
			obj.setModelo( modelo.getText() );  
			obj.setStatus( 1 );
			obj.setDataChegada( data.getText() );			
			obj.setNs( ns.getText() );
			obj.setPat( pat.getText() );
			obj.setUltimaCalib( ultimaCal.getText() );	
			obj.setLaboratorio(true);
			
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
		Stage stage = (Stage) cancelar.getScene().getWindow(); 
		stage.close();
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

	@FXML
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" ) {
				
			 if(event.getTarget().equals(ultimaCal)){
				 ultimaCal.setText(Format.replaceData( ultimaCal.getText() ) );
				 ultimaCal.end();
			 }
	
		}
		
	}

	@FXML
	public void findNs(ActionEvent event) {
		Equipamento obj = equipamentoController.findByNs( ns.getText() );
		if( obj != null	) {
			
			nomeEmpressa.setValue( obj.getEmpressaName() );	
			modelo.setText( obj.getModelo() );  
			pat.setText( obj.getPat() );
			ultimaCal.setText( obj.getDataCal() );	
		}		
		
	}

	public void initialize(URL location, ResourceBundle resources) {
		addListener();
	
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		addEmpressaImg.setImage(image);
		
		date = new Date(System.currentTimeMillis());
	    data.setText(Format.formatData.format(date));
	    
	}
	
	@FXML
	protected void addEmpressa(ActionEvent e) throws IOException {
		removeListener();
		NewView.getNewView("Adcionar Empressa", "newEmpressa", new EmpressaInsert() );
		
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