package com.hrodriguesdev;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.hrodriguesdev.gui.controller.LoadViewController;
import com.hrodriguesdev.utilitary.NewView;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlfaPirometrosApplication extends Application implements Runnable{
	private static Scene scene;
	public static Stage stage;
	private ImageView starting;
	private Image icon;
	
	private Thread thread;
	public static boolean springStart = false;

	private final String nameIcon = "Yggdrasil icon.jpg";
	private final String nameImageViewStarting = "Yggdrasil.jpg";

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
		thread = new Thread(this);
		thread.start();

	}
	
	//Comando para iniciar o spring
	@Override
	public void run() {
		try {
			initSpring();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private final SpringContext spring = new SpringContext();
	private ConfigurableApplicationContext applicationContext;
	
    public void initSpring() throws IOException {   
        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
        
        	ac.registerBean(Application.class, () -> AlfaPirometrosApplication.this);
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);

        };
        this.applicationContext = new SpringApplicationBuilder().sources(Springinit.class)
                .initializers(initializer).run(getParameters().getRaw().toArray(new String[0]));
     spring.setApplicationContext(applicationContext); 
     springStart=true;
    }
	
	public static void main(String[] args) {
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
