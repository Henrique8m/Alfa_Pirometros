package com.hrodriguesdev.utilitary;

import java.io.IOException;

import com.hrodriguesdev.AlfaPirometrosApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewView {	
	
	public static Scene scene;
	public static TabPane TABPANE;
	public static AnchorPane anchorPane;

	
	public static synchronized <T> Object loadFXML(String fxml, Object controller) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(AlfaPirometrosApplication.class.getResource("gui/resources/" + fxml + ".fxml"));
		fxmlLoader.setController(controller);
		return fxmlLoader.load();
	}
	
	public static synchronized <T> Object loadMainView(String fxml, Object controller) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(AlfaPirometrosApplication.class.getResource("gui/resources/" + fxml + ".fxml"));
		fxmlLoader.setController(controller);
		anchorPane = fxmlLoader.load();
		return anchorPane;
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
	
	public static void pag(String title, String fxml, Object controller, Scene scene){		
		try {
			Pane Pane = (Pane) loadFXML(fxml, controller);
			scene = new Scene(Pane);
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

	public static void fecharView() {
		try {
	    	AnchorPane pane = (AnchorPane) NewView.scene.getRoot();
	    	pane.getChildren().clear();	    	
	    	pane.getChildren().add(NewView.TABPANE);

	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public static void addChildrenToMain(Node node) {
    	AnchorPane pane = (AnchorPane) NewView.scene.getRoot();
    	NewView.TABPANE = (TabPane) pane.getChildren().get(0);
	   	pane.getChildren().clear(); 
    	pane.getChildren().add(node);
    	pane.setBottomAnchor(pane.getChildren().get(0), (double) 0);
    	pane.setTopAnchor(pane.getChildren().get(0), (double) 0);
    	pane.setLeftAnchor(pane.getChildren().get(0), (double) 0);
    	pane.setRightAnchor(pane.getChildren().get(0), (double) 0);
//    	pane.getChildren().get(0).layoutBoundsProperty().    	
//    	pane.getChildren().get(0).getLayoutBounds().contains(0, 0, 0, 0);
	}
	
	@SuppressWarnings("static-access")
	public static void addChildrenn(Node node) {
    	AnchorPane pane = (AnchorPane) NewView.scene.getRoot();
	   	pane.getChildren().clear(); 
    	pane.getChildren().add(node);
    	pane.setBottomAnchor(pane.getChildren().get(0), (double) 0);
    	pane.setTopAnchor(pane.getChildren().get(0), (double) 0);
    	pane.setLeftAnchor(pane.getChildren().get(0), (double) 0);
    	pane.setRightAnchor(pane.getChildren().get(0), (double) 0);
//    	pane.getChildren().get(0).layoutBoundsProperty().    	
//    	pane.getChildren().get(0).getLayoutBounds().contains(0, 0, 0, 0);
	}
	

}
