package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.db.DbException;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SaidaEquipamentoViewController implements Initializable {
	
	//@Autowired
	protected Controller controller = new Controller();
	protected ColetorController coletorController = MainViewController.coletorController;
	protected Equipamento equipamento = MainViewController.equipamentoEdit;
	
	@FXML
	protected ImageView cancelarImg;
	@FXML
	protected ImageView salvarImg;
	@FXML
	protected ImageView addEmpressaImg;
	@FXML
	private Text erro;
	@FXML
	public TextField data, ultimaCal, modelo, ns, pat, nomeEmpressa;
	@FXML
	protected Button salvar, cancelar, addEmpressa;

	//--------------
	
	@FXML
	protected ImageView pdf;
	@FXML
	protected TextField dataColeta, nomeColetor;
	@FXML
	protected ComboBox<String> coleta = new ComboBox<>();	
	public static ObservableList<String> obsString = FXCollections.observableArrayList();
	
	@FXML
	protected void gerarPDF(ActionEvent event) {
		
		if(coleta.getValue() != "" &&  nomeColetor.getText() != "" ) {
			GeneratorPDF pdf = new GeneratorPDF();	
			Coletor coletor = getColetor();
			Empressa empressa = controller.findEmpresa( equipamento.getEmpressa() );			
			if( controller.UpdatedEquipamento(equipamento) ) {
				pdf.newDocument(coletor, equipamento, empressa);
				Stage stage = (Stage) salvar.getScene().getWindow();
				stage.close();
				
			}
			
		}
		salvar(event);
	}
	
	
	@FXML
	protected void salvar(ActionEvent event) {
		getColetor();
		updateEquipamento();
		try {
			
			if( controller.UpdatedEquipamento(equipamento) ) {
				Stage stage = (Stage) salvar.getScene().getWindow();
				stage.close();
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		
		
	}


	protected void updateEquipamento() {
		try {
			equipamento.setDataSaida( dataColeta.getText() );
			switch ( equipamento.getStatus() ) {
			case 2:
				equipamento.setStatus( 12 );
				equipamento.setLaboratorio(true);
				break;
			case 3:
				equipamento.setStatus( 13 );
				equipamento.setLaboratorio(true);
				break;
			default:
				equipamento.setLaboratorio(false);
				equipamento.setStatus( 7 );
				break;
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
		}
	}


	protected Coletor getColetor() {
		Coletor coletor = new Coletor();
		if(coleta.getValue()== "" ||  nomeColetor.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e nome do coletor, não pode estar vazio");
			return null;
		}
		try {	
			if ( controller.findEmpresaId(coleta.getValue()) == null ) {
				throw new DbException("Empresa não existe");
			}

			coletor.setEquipamento_id( equipamento.getId() );	
			coletor.setEmpressaName(coleta.getValue());
			coletor.setNomeColetor(nomeColetor.getText());
			coletor.setDataHoraColeta( dataColeta.getText() );
			equipamento.setColetor_id( coletorController.add(coletor) );
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return null;
		}
		return coletor;
	}	
	
	private void error(String titulo, String mensagem) {
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
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		obsString = controller.getEmpressas();
		FilteredList<String> filteredList = new FilteredList<>(obsString);  
		coleta.getEditor().textProperty().addListener(new InputFilter<String>( coleta, filteredList ) );		
	
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		addEmpressaImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
		
		nomeEmpressa.setText(equipamento.getEmpressaName());
	    data.setText(equipamento.getDataChegada());
		ultimaCal.setText(equipamento.getUltimaCalib());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		dataColeta.setText(Format.formataDateTimeString.format(new Date(System.currentTimeMillis() ) ) );
	    
	    
	}
	
	@FXML
	protected void addEmpressa(ActionEvent e) throws IOException {
		NewView.getNewView("Adcionar Empressa", "newEmpressa", new AddEmpressaViewController() );
		obsString = controller.getEmpressas();
		FilteredList<String> filteredList = new FilteredList<>(obsString);  
		coleta.getEditor().textProperty().addListener(new InputFilter<String>( coleta, filteredList ) );
		
	}

}

