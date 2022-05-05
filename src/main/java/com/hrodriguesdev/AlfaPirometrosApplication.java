package com.hrodriguesdev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlfaPirometrosApplication extends Application{
	private static Scene scene;
	private static Stage stage;	
	
	@Override
	public void start(Stage arg0) throws Exception {

		stage = arg0;
		Pane pane = new Pane();
		scene = new Scene(pane, 400, 300);
		stage = new Stage();		
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setTitle("Contagem Caixotes");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
