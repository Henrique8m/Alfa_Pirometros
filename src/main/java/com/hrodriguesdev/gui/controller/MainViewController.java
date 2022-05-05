package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import com.hrodriguesdev.AlfaPirometrosApplication;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;

public class MainViewController implements Initializable{
	private Timeline timeline;
	private int i =0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Iniciou o controllador com sucesso!");
		beginTimer();
	}

	
	private void beginTimer() { 
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(6), ev -> { 
			i++;
			System.out.println("Test timeline cycle "+ i);
			if(AlfaPirometrosApplication.springStart) {
				System.out.println("spring Start true ");
				timeline.stop();
			}
		}));
		 
		timeline.setCycleCount(10); 
		timeline.play(); 
		 
		}
	 
	@SuppressWarnings("unused")
	private void cancelTimer() {
		timeline.stop();
	}
	
}
