package com.hrodriguesdev.gui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LoadViewController implements Initializable {
	
	private Timeline timeline;	
	
	private String nameEntrada = "Status";
	private boolean licenseKey = false;
	
	@FXML
	private ProgressBar progressBar;
	@FXML
	private ImageView logo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		beginTimer();
		logo.setImage(NewView.getLogo());		
		licenseKey = exec(AlfaPirometrosApplication.URL_KEY);
	
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
			if(licenseKey) {
				if (AlfaPirometrosApplication.MAIN_TAB_VIEW_SHOW) {
					timeline.pause();
					openView();
					AlfaPirometrosApplication.stage.close();
					timeline.stop();
				}
			}else {
				timeline.stop();
				Alerts.showAlert("Erro ao validar Licensa",
						"Licensa espirada, favor entrar em contato com desenvolvedor",
						"", 
						Alert.AlertType.ERROR);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
				System.exit(1);
			}
		}));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}
		
	public boolean exec(String comando) {
		List<String> strList = new ArrayList<>();
		try {
			Process SerialNumberProcess = Runtime.getRuntime().exec(comando);
			InputStreamReader ISR = new InputStreamReader(SerialNumberProcess.getInputStream(),
					Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(ISR);

			String nextLine = br.readLine();
			while (nextLine != null) {
				strList.add(nextLine);
				nextLine = br.readLine();
			}

			SerialNumberProcess.waitFor();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String statusValue = strList.stream().filter(str -> str.contains(nameEntrada)).findFirst().get();
		int indexOf = statusValue.indexOf("0x");
		statusValue = statusValue.substring(indexOf);
		
		try {
			int dados = Geral.getDecimal(  statusValue.replace("0x", "")  );
			Date dateKey = Geral.dateParceNotSeparator(String.valueOf(dados)); 						
			@SuppressWarnings("deprecation")
			Date dateNow = new Date(LocalDate.now().getYear()-1900, LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());
			
			if(dateKey.after(dateNow)) {
//				System.out.println(dateKey + "    " + dateNow);
				return true;
			}
			
		}catch(NumberFormatException e) {
			Log.logString("LoadViewController", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
}

