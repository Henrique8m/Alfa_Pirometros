package com.hrodriguesdev;

import com.hrodriguesdev.gui.controller.AddEquipamentoViewController;
import com.hrodriguesdev.gui.controller.LoadViewController;
import com.hrodriguesdev.gui.controller.MainViewController;
import com.hrodriguesdev.utilitary.NewView;

import javafx.application.Application;
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
	
	public static boolean springStart = false;
	


	private final String nameIcon = "Yggdrasilicon.jpg";
	private final String nameImageViewStarting = "Yggdrasil.jpg";
	
	public static String caminhoPDF = "C:\\Users\\Usuario\\Desktop\\Carvao.pdf";
	public static String caminhoDbProperties = "C:\\Program Files\\Java\\resources\\dbAlfa.properties";
	public static String properties = "Properties.properties";
	public static String diretorioStr1 = "\\AppData\\Local\\YggDrasil";
	public static String diretorioStr2 = "\\AppData\\Local\\YggDrasil\\serial";
	
	public static MainViewController viewController = new MainViewController();
	public static AddEquipamentoViewController viewaddChegadaEquipamento = new AddEquipamentoViewController();

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
