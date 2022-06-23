package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
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

public class EditEquipamentoViewController implements Initializable {

	//@Autowired
	private Controller controller = new Controller();
	private Equipamento equipamento = MainViewController.equipamentoEdit;
	
	@FXML
	private ImageView cancelarImg, salvarImg, addEmpressaImg;
	@FXML
	private Text erro;
	@FXML
	public TextField data, ultimaCal, modelo, ns, pat;
	@FXML
	private Button salvar, cancelar;
	@FXML
	private ComboBox<String> nomeEmpressa = new ComboBox<>();
	
	public static ObservableList<String> obsString = FXCollections.observableArrayList();
	
	
	@FXML
	public void salvar(ActionEvent event) {
		if(nomeEmpressa.getValue()== "" ||  modelo.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e Modelo, não pode ser nulo");
			return;
		}
		try {
			equipamento.setEmpressa( controller.findEmpresaId( nomeEmpressa.getValue() ) );
			if ( equipamento.getEmpressa() == null ) {
				throw new DbException("Empresa não existe");
			}
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
		try {
			equipamento.setEmpressaName( nomeEmpressa.getValue() );	
			equipamento.setModelo( modelo.getText() );  
			equipamento.setStatus( 1 );
			equipamento.setDataChegada( data.getText() );			
			equipamento.setNs( ns.getText() );
			equipamento.setPat( pat.getText() );
			equipamento.setUltimaCalib( ultimaCal.getText() );	
			if(equipamento.getColetor_id() == null || equipamento.getColetor_id() == 0l)
				equipamento.setLaboratorio(true);
			equipamento.setColetor_id(0l);
			
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
	
	
	@FXML
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" ) {
				
			 if(event.getTarget().equals(ultimaCal)){
				 ultimaCal.setText(Format.replaceData( ultimaCal.getText() ) );
				 ultimaCal.end();
			 }
			 if(event.getTarget().equals(data)){
				 data.setText(Format.replaceData( data.getText() ) );
				 data.end();
			 }
	
		}
		
	}
	
	@FXML
	public void findNs(ActionEvent event) {
		Equipamento obj = controller.findEquipamentoNs( ns.getText() );
		if( obj != null	) {
			
			nomeEmpressa.setValue( obj.getEmpressaName() );	
			modelo.setText( obj.getModelo() );  
			pat.setText( obj.getPat() );
			ultimaCal.setText( obj.getDataCal() );	
		}		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		obsString = controller.getEmpressas();
		FilteredList<String> filteredList = new FilteredList<>(obsString);  
		nomeEmpressa.getEditor().textProperty().addListener(new InputFilter<String>( nomeEmpressa, filteredList ) );		
	
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		addEmpressaImg.setImage(image);
	
		nomeEmpressa.setValue(equipamento.getEmpressaName());
	    data.setText(equipamento.getDataChegada());
		ultimaCal.setText(equipamento.getUltimaCalib());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		data.setEditable(true);
		    
	}
	
	@FXML
	private void addEmpressa(ActionEvent e) throws IOException {
		NewView.getNewViewModal("Adcionar Empressa", (Pane) NewView.loadFXML("newEmpressa", new AddEmpressaViewController()), LoadViewController.getStage());
		
	}

}

