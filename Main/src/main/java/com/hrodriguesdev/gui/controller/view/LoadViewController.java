package com.hrodriguesdev.gui.controller.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadViewController implements Initializable {
	
	private Timeline timeline;
	private static Stage stage;	
	private static Scene scene;	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = new Stage();
		beginTimer();
	}

	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(5), ev -> {
			if (AlfaPirometrosApplication.springStart) {
				//System.out.println("spring Start true ");
				try {
					AnchorPane anchorPane = (AnchorPane) NewView.loadFXML("mainView", AlfaPirometrosApplication.viewController);
					scene = new Scene(anchorPane);
					NewView.getNewView("Controle de Estoque", scene, stage);
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
	
	public static Scene getScene() {
		return scene;
	}
	
}
