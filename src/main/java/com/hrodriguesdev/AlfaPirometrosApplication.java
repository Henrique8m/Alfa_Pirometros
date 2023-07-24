package com.hrodriguesdev;

import java.io.IOException;

import com.hrodriguesdev.gui.controller.LoadViewController;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;
import com.hrodriguesdev.utilitary.tabsLoad.TabsMainView;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class AlfaPirometrosApplication extends Application{
	public static Stage stage;
		
	public static boolean MAIN_TAB_VIEW_SHOW = false;	
	
	public static int REFRESH_TABLE_MAIN_VIEW = 60; //em segundos	
	
	public static String URL_KEY = "reg query HKEY_USERS\\.DEFAULT\\Software\\Yggdrasil\\Alfaestoque";

	public static String URL_RELATORIOS = System.getProperty("user.home")
			.toString() + "\\Desktop\\Relatorios";
	public static String URL_CONEXAO = "C:\\Program Files\\Java\\resources";	
	public static String URL_CONEXAO_PRODUCAO = URL_CONEXAO + "\\dbAlfa.properties";
	public static String URL_CONEXAO_DESENVOLVIMENTO = URL_CONEXAO + "\\dbLocal.properties";	
	public static String URL_CONEXAO_2 = "C:\\Program Files (x86)\\Java\\resources\\dbAlfa.properties";
		
	
	public static String PROPERTIES = "Properties.properties";
	public static String URL_DIRETORIO_YGGDRASIL = "\\AppData\\Local\\YggDrasil";
	public static String URL_AREA_DE_TRABALHO = System.getProperty("user.home").toString() + "\\Desktop"; 
	public static String CERTIFICADO_CAMINHO = AlfaPirometrosApplication.URL_AREA_DE_TRABALHO + "\\Certificados YggDrasil";
	
	//Carregando a view de Load
	@Override
	public void start(Stage arg0) throws ExceptionAlfa, IOException{
		LoadViewController loadView = new LoadViewController();
		Pane pane = (Pane) NewView.loadFXML(FXMLPath.LOAD_VIEW, loadView);
		Scene scene = new Scene(pane);		
		stage = arg0;
		stage = new Stage();
		NewView.STAGE_MAIN_VIEW = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setTitle("Pirometros");		
		stage.getIcons().add(NewView.getIcon());
		AlfaPirometrosApplication.stage.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Task<Void> task = new Task<Void>() {
				    @Override public Void call() {	
				    	TabsMainView.loadMain();
						MAIN_TAB_VIEW_SHOW=true;			
				        return null;
				    }
				};
				new Thread(task).start();
			}
		
		});
		stage.show();
	}
		
	
	public static void main(String[] args) {
		launch(args);
		System.exit(1);
	}

}
