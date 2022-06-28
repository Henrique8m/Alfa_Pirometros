package com.hrodriguesdev.utilitary;

import java.io.IOException;

import com.hrodriguesdev.AlfaPirometrosApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewView {	
	
	public static synchronized <T> Object loadFXML(String fxml, Object controller) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(AlfaPirometrosApplication.class.getResource("gui/resources/" + fxml + ".fxml"));
		fxmlLoader.setController(controller);
		return fxmlLoader.load();
	}
	
	public static void getNewViewModall(String title, Pane pane, Stage stageEvent){
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(new Scene(pane));
		stage.setResizable(false);
		stage.initOwner(stageEvent);
		stage.getIcons().add(new Image(AlfaPirometrosApplication.class.getResource("gui/resources/" + "Yggdrasilicon.jpg").toString()));
		stage.initModality(Modality.WINDOW_MODAL);			
		stage.initStyle(StageStyle.UTILITY);
		stage.setAlwaysOnTop(true);						
		stage.showAndWait();
	}
	
	public static void getNewView(String title, String fxml, Object controller){		
		try {
			Pane Pane = (Pane) loadFXML(fxml, controller);
			Scene scene = new Scene(Pane);
			Stage stage = new Stage();
			stage.setMaximized(false);
			stage.setTitle(title);
		    stage.getIcons().add(new Image(AlfaPirometrosApplication.class.getResource("gui/resources/" + "Yggdrasilicon.jpg").toString()));
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {			
			e.printStackTrace();
			return;
			
		}

	}
	
	public static Stage getNewView(String title, Scene mainScene, Stage stage){
		stage.setMaximized(true);
		stage.setTitle(title);
	    stage.getIcons().add(new Image(AlfaPirometrosApplication.class.getResource("gui/resources/" + "Yggdrasilicon.jpg").toString()));
		stage.setScene(mainScene);
		stage.show();
		return stage;
	}

}
