package com.hrodriguesdev.gui.controller.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.utilitary.Log;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CatalinaRunServer implements Initializable, Runnable {
	
	@FXML
	private TextFlow textExec;
	
	private List<String> output = new ArrayList<>();

	public CatalinaRunServer() {
		Thread th = new Thread(this);
		th.start();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(String line: output) 
			textExec.getChildren().addAll( new Text( line + "\n" ) );
			
	}

	@Override
	public void run() {

		String[] cmds = { System.getenv("CATALINA_HOME").toString() + "/bin/catalina.bat run" };

		try {
			ProcessBuilder builder = new ProcessBuilder("cmd", "/c", String.join("& ", cmds));

			builder.redirectErrorStream(true);

			Process pro = builder.start();

			BufferedReader r = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line;

			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				output.add(line);
				System.out.println(line);
			}
		} catch (Exception e) {
			Log.logString("CatalinaRunServer", e.getMessage());
			System.err.println(e);
		}
	}


	public void shutdown() {
		String[] cmds = { System.getenv("CATALINA_HOME").toString() + "/bin/catalina.bat stop" };

		try {
			ProcessBuilder builder = new ProcessBuilder("cmd", "/c", String.join("& ", cmds));

			builder.redirectErrorStream(true);

			Process pro = builder.start();

			BufferedReader r = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line;

			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				output.add(line);
				System.out.println(line);
			}
		} catch (Exception e) {
			Log.logString("CatalinaRunServer", e.getMessage());
			System.err.println(e);
		}
	}

}
