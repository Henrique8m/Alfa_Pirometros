package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SaidaEquipamentoViewController implements Initializable {
	
	//@Autowired
	private Controller controller = new Controller();
	private Equipamento equipamento = MainViewController.equipamentoEdit;
	
	@FXML
	private ImageView cancelarImg, salvarImg, addEmpressaImg;
	@FXML
	private Text erro;
	@FXML
	public TextField data, ultimaCal, modelo, ns, pat, nomeEmpressa;
	@FXML
	private Button salvar, cancelar;

	//--------------
	
	@FXML
	private ImageView pdf;
	@FXML
	public TextField dataColeta, nomeColetor;
	@FXML
	private ComboBox<String> coleta = new ComboBox<>();	
	public static ObservableList<String> obsString = FXCollections.observableArrayList();
	
	private Coletor col;
	private Equipamento equip;
	
	@FXML
	public void gerarPDF(ActionEvent event) {
		salvar(event);
		if(coleta.getValue() != "" &&  nomeColetor.getText() != "" ) {
			GeneratorPDF pdf = new GeneratorPDF();
			Empressa empressa = controller.findEmpresa( equip.getEmpressa() );
			pdf.newDocument(col, equip, empressa);
		}
	}
	
	
	@FXML
	public void salvar(ActionEvent event) {
		if(coleta.getValue()== "" ||  nomeColetor.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e nome do coletor, não pode estar vazio");
			return;
		}
		try {	
			if ( controller.findEmpresaId(coleta.getValue()) == null ) {
				throw new DbException("Empresa não existe");
			}
			Coletor coletor = new Coletor();
			coletor.setEquipamento_id( equipamento.getId() );	
			coletor.setEmpressaName(coleta.getValue());
			coletor.setNomeColetor(nomeColetor.getText());
			coletor.setDataHoraColeta( dataColeta.getText() );
			col = coletor;
			equipamento.setColetor_id( controller.addColetor(coletor) );
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
		try {
			equipamento.setDataSaida( dataColeta.getText() );
			equipamento.setStatus( 7 );
			equipamento.setLaboratorio(false);	
			equip = equipamento;
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
			return;
		}
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
	private void addEmpressa(ActionEvent e) throws IOException {
		NewView.getNewViewModal("Adcionar Empressa", (Pane) NewView.loadFXML("newEmpressa", new AddEmpressaViewController()), LoadViewController.getStage());
		obsString = controller.getEmpressas();
		FilteredList<String> filteredList = new FilteredList<>(obsString);  
		coleta.getEditor().textProperty().addListener(new InputFilter<String>( coleta, filteredList ) );
		
	}

}

