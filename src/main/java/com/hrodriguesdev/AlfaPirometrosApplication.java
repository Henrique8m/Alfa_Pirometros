package com.hrodriguesdev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlfaPirometrosApplication extends Application {
	private static Scene scene;
	private static Stage stage;
	private ImageView starting;
	private Image icon;

	private final String nameIcon = "Yggdrasil icon.jpg";
	private final String nameImageViewStarting = "Yggdrasil.jpg";

	@Override
	public void start(Stage arg0) throws Exception {

		loadImage(nameImageViewStarting);
		stage = arg0;
		Pane pane = new Pane();
		pane.getChildren().add(starting);
		scene = new Scene(pane, 400, 300);
		stage = new Stage();

		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setTitle("Contagem Caixotes");
		stage.getIcons().add(icon);
		stage.show();

	}

	private void loadImage(String nameImageViewStarting) {
		starting = new ImageView(new Image(
				AlfaPirometrosApplication.class.getResource("gui/resources/" + nameImageViewStarting).toString()));
		icon = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/" + nameIcon).toString());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
