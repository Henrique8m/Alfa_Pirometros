package com.hrodriguesdev.gui.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {
	static Alert alert;
	static boolean resposta = false;
	
	public static void showAlert(String title, String header, String content, AlertType type) {
		if(alert!=null) 
			if(alert.isShowing())
				alert.close();
		alert = new Alert(type);
		//alert.setDialogPane(null)
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();		

	}
	
	public static boolean showAlertConfirmation(String title, String header, String content) {
		if(alert!=null) 
			if(alert.isShowing())
				alert.close();
		alert = new Alert(AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getButtonTypes().setAll(btnSim, btnNao);
		
	
		
		alert.showAndWait().ifPresent(b -> {
            if (b == btnSim) {
            	setResposta(true);
            } else if (b == btnNao) {
            	setResposta(false);
            }
        });		
		
		return resposta;
	}
	
	private static void setResposta(boolean resposta) {
		Alerts.resposta = resposta;
	}
	
}
