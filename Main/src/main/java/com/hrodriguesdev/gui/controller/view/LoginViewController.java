package com.hrodriguesdev.gui.controller.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginViewController implements Initializable{

	@FXML
	private TextField user, password;
	
	@FXML
	private void enter(KeyEvent event) {
		if(event.getCode().toString() == "ENTER") {
			if( user.getText() == "Robson" ) {

			}			
			
		}
			
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
