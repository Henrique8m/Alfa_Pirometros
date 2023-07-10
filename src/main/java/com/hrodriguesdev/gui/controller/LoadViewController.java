package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadViewController implements Initializable {
	
	private Timeline timeline;	
	
	@FXML
	private ProgressBar progressBar;
	@FXML
	private ImageView logo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		beginTimer();
		logo.setImage(NewView.getLogo());		
	
	}

	public void openView() {
		NewView.getNewView("Controle de Estoque", NewView.SCENE_MAIN_VIEW, NewView.STAGE_MAIN_VIEW);
		
	}
	
	private void beginTimer() {
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() {
		        return null;
		    }
		};	
		progressBar.progressProperty().bind(task.progressProperty());
		
		new Thread(task).start();		
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
			if (AlfaPirometrosApplication.springStart) {
				timeline.pause();
				openView();
				AlfaPirometrosApplication.stage.close();
				timeline.stop();
			}
		}));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}
		
}

