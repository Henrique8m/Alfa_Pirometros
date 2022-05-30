package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.entities.Equipamento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddOrcamentoViewController implements Initializable {
	
	private Controller controller = new Controller();
	private Equipamento equipamento;
	
	@FXML
	private ImageView cancelarImg, salvarImg;
	@FXML
	private Text erro;
	@FXML
	public TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal;
	@FXML
	private Button salvar, cancelar;
	@FXML
	private TableView<String> tableOrcamento = new TableView<>();
	@FXML
	private TableColumn<String, String> item;
	@FXML
	private TableColumn<String, String> quantidade;
	
	@FXML
	private TextArea obs;
	@FXML
	private ComboBox<String> newItem = new ComboBox<>();
	@FXML
	private ComboBox<Integer> quantidadeItem = new ComboBox<>();
	
	
	@FXML
	public void salvar(ActionEvent event) {
		
		
	}	
	
	@FXML
	public void cancelar(ActionEvent event) {
		try {
			Stage stage = (Stage) cancelar.getScene().getWindow(); 
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	public void format(KeyEvent event) {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		equipamento = MainViewController.addOrcamento;
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(equipamento.getDataChegada());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		ultimaCal.setText(equipamento.getUltimaCalib());
		
		

		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
	    
	}	

}
