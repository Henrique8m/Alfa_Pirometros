package com.hrodriguesdev;

import java.io.IOException;

import com.hrodriguesdev.gui.controller.LoadViewController;
import com.hrodriguesdev.gui.controller.view.main.PaginaBuscaController;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class AlfaPirometrosApplication extends Application{
	private static Scene scene;
	public static Stage stage;
		
	public static boolean springStart = false;	
	
	public static int UPDATETIME = 60; //em segundos	
	
	public static String caminhoPDF = "\\Desktop\\Relatorios";
	public static String CAMINHO_ICONS = AlfaPirometrosApplication.class.getResource(
			"gui/resources/").toString();
	
//	
//	public static ObservableList<String> OBS_PRODUCTS = FXCollections.observableArrayList(
//			
//			"BotaoLiga", "BoMeFIIFIIIIndicmax", "CaixaBat",
//			"FontCarbIndic", "FontCarbDelta", "PinFemeAliFII", "PinFemeAliFIII", 
//			"BatFIIFIII", "BatDescartavel", "BatInditemp", "BatLitio", "CarrEcil", "CarrItalterm",
//			
//			"PCIFIII", "PCIFKal", "DispFKal", "FIII", "Indicmax", "CIFII", "CIIndicmax", "sirene", 
//			"MascaraFII", "MascaraFKal", "MascaraFIII", "MascaraCarbo", "MascaraIndic", "EtiqLatFII", "EtiqLatFIII", "EtiqTrasFII", "Punho", 
//			
//			"ReceptaculoS", "ReceptaculoSU", "ReceptaculoEcil", "ReceptaculoK", "PlugFS", "PlugFK", "PlugMS", "PlugMK", "TomadaS",
//			
//			"Cabo_S_borracha", "Cabo_S_miolo_lanca", "Cabo_S_extensao", "Cabo_K_borracha", "Cabo_K_Fibra_Fibra", "Cabo_K_Fibra_Silicone", 
//			
//			"Calibracao"
//			);
//	
	
	public static ObservableList<String> OBS_PRODUCTS = FXCollections.observableArrayList();
			
	public static ObservableList<String> obsQuantidade = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10");


	
	public static String URL_CONEXAO = "C:\\Program Files\\Java\\resources";
	
	public static String URL_CONEXAO_PRODUCAO = URL_CONEXAO + "\\Alfa.properties";
	public static String URL_CONEXAO_DESENVOLVIMENTO = URL_CONEXAO + "\\dbLocal.properties";
	
	public static String URL_CONEXAO_2 = "C:\\Program Files (x86)\\Java\\resources\\Alfa.properties";
		
	
	public static String properties = "Properties.properties";
	public static String strDiretorioYggDrasil = "\\AppData\\Local\\YggDrasil";
	public static String URL_AREA_DE_TRABALHO = System.getProperty("user.home").toString() + "\\Desktop"; 
	public static String CERTIFICADO_CAMINHO = AlfaPirometrosApplication.URL_AREA_DE_TRABALHO + "\\Certificados YggDrasil";
	
	public static PaginaBuscaController viewController;
	
		

	//Carregando a view de Load
	@Override
	public void start(Stage arg0) throws ExceptionAlfa, IOException{
		LoadViewController loadView = new LoadViewController();
		Pane pane = (Pane) NewView.loadFXML(FXMLPath.LOAD_VIEW, loadView);
		scene = new Scene(pane);		
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
						try {
							viewController =  new PaginaBuscaController();
							AnchorPane anchorPane = (AnchorPane) NewView.loadFXML("mainView", viewController);
							NewView.SCENE_MAIN_VIEW = new Scene(anchorPane);
						} catch (IOException e) {
							e.printStackTrace();
							System.exit(1);
						}
						springStart=true;		
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
