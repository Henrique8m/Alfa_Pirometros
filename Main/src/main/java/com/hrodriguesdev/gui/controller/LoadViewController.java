package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadViewController implements Initializable {
	
	private Timeline timeline;
	public static Stage stage;	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = new Stage();
		beginTimer();
	}

	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(3), ev -> {
			if (AlfaPirometrosApplication.springStart) {
				//System.out.println("spring Start true ");
				try {
					AnchorPane anchorPane = (AnchorPane) NewView.loadFXML("mainView", AlfaPirometrosApplication.viewController);
					NewView.scene = new Scene(anchorPane);
					NewView.getNewView("Controle de Estoque", NewView.scene, stage);
					NewView.TABPANE = (TabPane) anchorPane.getChildren().get(0);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				AlfaPirometrosApplication.stage.close();
				timeline.stop();
			}
		}));

	timeline.setCycleCount(10);
	timeline.play();

	}
		
}
