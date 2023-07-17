package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.relatorio.GeneratorPDFEstoque;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EstoqueController extends RegisterProductsController implements Initializable{
	GeneratorPDFEstoque pdf = new GeneratorPDFEstoque();
		
	@FXML
	private ImageView saidaMaterialImg, logoYgg, entradaMaterialImg, cancelarImg, pdfImg, relatorioImg, registerImg;	
	@FXML
	protected Button cancelar;
	
	
	@FXML
	protected void cancelar(ActionEvent event) {
		NewView.fecharView();
	}
	
	@FXML
	protected void salvarSaida(ActionEvent event) {
		NewView.fecharView();
	} 
	
	@FXML
	protected void saidaMaterial(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML(FXMLPath.ADD_PRODUCT_OS , new EstoqueSaidaController(new Equipamento(), new Orcamento() ) ));
	}
	
	@FXML
	protected void entradaMaterial(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML(FXMLPath.ADD_PRODUCT_OS , new EstoqueEntradaController(new Equipamento(), new Orcamento() ) ));
	}
	
	@FXML
	protected void relatorios(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML(FXMLPath.RELATORIO , new RelatoriosController( ) ));
	}
	
	@FXML
	protected void relatoriosPdf(ActionEvent event) throws IOException {
		String local = 	AlfaPirometrosApplication.URL_RELATORIOS;
		ProductsController products = new ProductsController();
		List<String> listProducts = new ArrayList<>();
		products.findAll().forEach(product ->{
			listProducts.add(product.getName());
		});
		if(pdf.newDocument(listProducts, local))
			Alerts.showAlert("Relatorio", "Relatorio gerado com sucesso em , "+ local, "", AlertType.INFORMATION);
	}
			

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/Yggdrasilicon.jpg").toString() );
		logoYgg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		entradaMaterialImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-remover.png").toString() );
		saidaMaterialImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-relatorio.png").toString() );
		relatorioImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdfImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-insert.png").toString() );
		registerImg.setImage(image);
		
		startTable();
		
	}
	
	@FXML
	private void registerProducts(ActionEvent e) {
		NewView.getNewView("Produtos", "registerProducts", new RegisterProductsController());
	}
	
}
