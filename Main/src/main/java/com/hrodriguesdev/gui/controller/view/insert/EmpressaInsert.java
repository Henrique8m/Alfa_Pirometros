package com.hrodriguesdev.gui.controller.view.insert;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EmpressaController;
import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.SaidaEquipamentoViewController;
import com.hrodriguesdev.gui.controller.view.MainViewController;
import com.hrodriguesdev.utilitary.Format;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmpressaInsert implements Initializable {
	private EmpressaController empressaController = MainViewController.empressaController;
	private SaidaEquipamentoViewController saidaView;
	
	@FXML
	private ImageView cancelarImg, salvarImg;
	@FXML
	private Text erro;
	@FXML
	public TextField nomeEmpressa, cidade, estado, endereco, cep;
	@FXML
	private Button salvar, cancelar;
	
	public EmpressaInsert(SaidaEquipamentoViewController saidaView) {
		this.saidaView = saidaView;
	}
	
	public EmpressaInsert() {}
	
	@FXML
	public void salvar(ActionEvent event) {
		Empressa empressa = new Empressa();
		try {
			if(nomeEmpressa.getText() != null && nomeEmpressa.getText() != "")
				empressa.setName(nomeEmpressa.getText());	
			else {
				erro.setText("ERRO");
				return;
			}
				empressa.setCidade( cidade.getText() );
				empressa.setEstado( estado.getText());
				empressa.setEndereço( endereco.getText() );
				empressa.setCep( cep.getText() );
			
		}catch(NullPointerException e) {
			e.getMessage();
			e.getCause();
			error( "Null Pointer " ,"Null Pointer Exeption");
			return;
		}
		try {
			empressa.setId(empressaController.add(empressa));
			if(empressa.getId() == 0l) {
				erro.setText("ERRO");
				return;
			}else {
				Stage stage = (Stage) cancelar.getScene().getWindow(); 
				listner();
				stage.close();
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		
		
	}	
	
	private void listner() {
		if(saidaView!= null) {
			saidaView.addListener();
			saidaView = null;
		}					
		else MainViewController.equipamentoInsert.addListener();
	}
	
	@FXML
	public void cancelar(ActionEvent event) {
		try {
			Stage stage = (Stage) cancelar.getScene().getWindow(); 
			listner();
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" ) {
			if(event.getTarget().equals(estado)){
				 String input = estado.getText().toUpperCase().replaceAll("[^A-Z]+", "");
				 if(input.length() > 2) {
					 StringBuilder stringBuilder = new StringBuilder(input);
					 input = stringBuilder.replace(input.length()-1, input.length(), "").toString();
				 }
				 estado.setText(input);
				 estado.end();	
				 
			}else if(event.getTarget().equals(cep)){
				cep.setText(Format.replaceCep( cep.getText() ) );
				cep.end();
				
			 }
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
	    
	}	
	
	private void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
		Stage stage = (Stage) cancelar.getScene().getWindow(); 
		stage.close();
	}

}
