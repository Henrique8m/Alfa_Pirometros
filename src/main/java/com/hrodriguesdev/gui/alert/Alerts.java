package com.hrodriguesdev.gui.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		//alert.setDialogPane(null);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
}
