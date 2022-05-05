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
import javafx.scene.layout.AnchorPane;

public class LoadViewController implements Initializable {
	private Timeline timeline;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		beginTimer();
	}

	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(6), ev -> {
			if (AlfaPirometrosApplication.springStart) {
				//System.out.println("spring Start true ");
				try {
					AnchorPane anchorPane = (AnchorPane) NewView.loadFXML("mainView", new MainViewController());
					NewView.getNewView("Alfa Pirometros", new Scene(anchorPane));
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
