package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.utilitary.Format;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddMotoristaViewController implements Initializable {
	
	private Date date;
	
	//@Autowired
	private Controller controller = new Controller();
	
	@FXML
	private ImageView cancelarImg, salvarImg;
	@FXML
	private Text erro;
	@FXML
	public TextField nome, data, cnh, placa, hora, estado, cidade, telefone;
	@FXML
	private Button salvar, cancelar;
	
	@FXML
	public void salvar(ActionEvent event) {
		Motorista motorista = new Motorista();
		try {
			motorista.setName(nome.getText());
			motorista.setData(data.getText());
			motorista.setPlaca(placa.getText());
			motorista.setHora(hora.getText());
			motorista.setEstado(estado.getText());
			motorista.setPhone(telefone.getText());
			motorista.setCidade(cidade.getText());	
			motorista.setFila(true);
			if(cnh.getText()!= "") {
				Long cnhInput = Long.valueOf(cnh.getText());
				motorista.setCnh(cnhInput);
			}
			
		}catch(Exception e) {
			e.getMessage();
			e.getCause();
			erro.setText("ERRO");
		}
		try {
			motorista.setId(controller.addMotorista(motorista));
			if(motorista.getId() != 0l) {
				Stage stage = (Stage) salvar.getScene().getWindow(); 
				stage.close();
				
			}else {
				erro.setText("ERRO");
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		MainViewController.obsListTableMotorista.add(motorista);
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
				
			 if(event.getTarget().equals(placa)){
				placa.setText(Format.replacePlaca(placa.getText()));
				placa.end();
				
			 }else if(event.getTarget().equals(estado)){
				 String input = estado.getText().toUpperCase().replaceAll("[^A-Z]+", "");
				 if(input.length() > 2) {
					 StringBuilder stringBuilder = new StringBuilder(input);
					 input = stringBuilder.replace(input.length()-1, input.length(), "").toString();
				 }
				 estado.setText(input);
				 estado.end();	
				 
			}else if(event.getTarget().equals(nome)){
				String input = nome.getText().toUpperCase();
				input = input.replaceAll("[^A-Z-' ']+", "");			
				nome.setText(input);
				nome.end();
				
			 }else if(event.getTarget().equals(cidade)){
				String input = cidade.getText().toUpperCase();
				input = input.replaceAll("[^A-Z-' ']+", "");			
				cidade.setText(input);
				cidade.end();
				
			 }else if(event.getTarget().equals(cnh)){
					String input = cnh.getText().replaceAll("[^0-9]+", "");
					if(input.length() > 11) {
						 StringBuilder stringBuilder = new StringBuilder(input);
						 input = stringBuilder.replace(input.length()-1, input.length(), "").toString();
					}
					cnh.setText(input);
					cnh.end();
					
			}else if(event.getTarget().equals(telefone)){
				String input = telefone.getText().replaceAll("[^0-9-'('-')'-'-']+", "");
				StringBuilder stringBuilder = new StringBuilder(input);
				if(input.length()>1 && input.charAt(0)!='(') {
					input = stringBuilder.insert(0, "(").toString();
					
				}else if(input.length()>3 && input.charAt(3)!=')') {
					input = stringBuilder.insert(3, ")").toString();
				
				}else if(input.length()>9 && input.charAt(9)!='-') {
					input = stringBuilder.insert(9, "-").toString();
				}
				if(input.length() > 14) {
					 input = stringBuilder.replace(input.length()-1, input.length(), "").toString();
				}
				telefone.setText(input);
				telefone.end();
				
			}
		}		 
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
		date = new Date(System.currentTimeMillis());
	    hora.setText(Format.formataTimeString.format(date));
	    data.setText(Format.formatData.format(date));
	    
	}
}
