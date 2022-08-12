package com.hrodriguesdev;

import com.hrodriguesdev.gui.controller.LoadViewController;
import com.hrodriguesdev.gui.controller.view.main.PaginaBuscaController;
import com.hrodriguesdev.utilitary.NewView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlfaPirometrosApplication extends Application{
	private static Scene scene;
	public static Stage stage;
	private ImageView starting;
	private Image icon;
	
// 1: return "Aguardando Orçamento";
// 2: return "Enviar Orçamento";
// 3: return "Aguardando Aprovação";
// 4: return "Aprovado, aquardando Reparo!";
// 5: return "Liberado, aquardando Coleta!";
// 6: return "Não Aprovado, aquardando coleta!";
	
	public static boolean springStart = false;
	
	
	
	public static String caminhoPDF = "\\Desktop\\Relatorios";
	
	
	
	public static ObservableList<String> obsPecasEstoque = FXCollections.observableArrayList("Calibracao", "Cabo miolo de lanca",
			"BotaoLiga", "BoMeFIIFIIIIndicmax", "CaixaBat",
			"FontCarbIndic", "FontCarbDelta", "PinFemeAliFII", "PinFemeAliFIII", "BatFIIFIII", "BatDescartavel", "BatInditemp", "BatLitio", "CarrEcil", "CarrItalterm",
			"PCIFIII", "PCIFKal", "DispFKal", "FIII", "Indicmax", "CIFII", "CIIndicmax", "sirene", 
			"MascaraFII", "MascaraFKal", "MascaraFIII", "MascaraCarbo", "MascaraIndic", "EtiqLatFII", "EtiqLatFIII", "EtiqTrasFII", "Punho", 
			"ReceptaculoS", "ReceptaculoSU", "ReceptaculoEcil", "ReceptaculoK", "PlugFS", "PlugFK", "PlugMS", "PlugMK", "TomadaS"
			);
	public static ObservableList<String> obsQuantidade = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10");

	private final String nameIcon = "Yggdrasilicon.jpg";
	private final String nameImageViewStarting = "Yggdrasil.jpg";
	
	public static String caminhoDbProperties = "C:\\Program Files\\Java\\resources\\db.properties";
	public static String caminhoDbProperties2 = "C:\\Program Files (x86)\\Java\\resources\\dbAlfa.properties";
	public static String properties = "Properties.properties";
	public static String strDiretorioYggDrasil = "\\AppData\\Local\\YggDrasil";
	
	public static PaginaBuscaController viewController = new PaginaBuscaController();
	

	//Carregando a view de Load
	@Override
	public void start(Stage arg0) throws Exception {

		loadImage(nameImageViewStarting);
		Pane pane = (Pane) NewView.loadFXML("loadView", new LoadViewController());
		pane.getChildren().add(starting);
		scene = new Scene(pane, 400, 300);		
		stage = arg0;
		stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setTitle("Pirometros");
		stage.getIcons().add(icon);
		stage.show();

	}
		
	
	public static void main(String[] args) {
		 springStart=true;
		 launch(args);
		System.exit(1);
	}
	
	//Carrega as imagens de fundo para a tela de startup
	private void loadImage(String nameImageViewStarting) {
		starting = new ImageView(new Image(
				AlfaPirometrosApplication.class.getResource("gui/resources/" + nameImageViewStarting).toString()));
		icon = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/" + nameIcon).toString());
	}
}
