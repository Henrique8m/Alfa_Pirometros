package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class RelatoriosController implements Initializable{
	private OrcamentoController orcamentoController = new OrcamentoController();
	private Itens itens = new Itens();

	
	@FXML
	private VBox nsVbox, patVbox, modeloVbox, calVbox, coletaVbox, coletorVbox, empressaVbox;
	
	@FXML
	private TextField ns, pat,
			modelo, ultimaCal, dataColeta, nomeColetor, nomeEmpressa;
	
	@FXML
	private TableView<Orcamento> tableOrcamentos;
	private ObservableList<Orcamento> obsOrcamento = FXCollections.observableArrayList();
		
	@FXML
	private TableColumn<Orcamento, String>  relatorioTable;
	
	@FXML
	private TableColumn<Orcamento, Date>  chegadaTable;
	
	@FXML
	private TableColumn<Orcamento, Date>  saidaTable;
	
	@FXML
	private TableColumn<Orcamento, Integer>  nfe;
	
	@FXML
	private TableColumn<Orcamento, String>  situation;
	
	@FXML
	protected TableView<Orcamento> tableOrcamento = new TableView<>();
	protected ObservableList<Orcamento> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Orcamento, String> item;
	@FXML
	protected TableColumn<Orcamento, Integer> quantidade;
		
	@FXML
	private ImageView cancelarImg, buscarImg, logoYgg;
	
	@FXML
	public void clickOrcamento(MouseEvent event) throws SQLException {
			if(tableOrcamentos.getSelectionModel().getSelectedItem() != null) {
					Coletor coletor = new Coletor();
					Orcamento orcamento = orcamentoController.getOrcamento( tableOrcamentos.getSelectionModel().getSelectedItem().getId() ); 
					
					if(orcamento.getStatus() == 20) {
						 nsVbox.setVisible(false); 
						 patVbox.setVisible(false);
						 modeloVbox.setVisible(false);
						 calVbox.setVisible(false);
						 empressaVbox.setVisible(true);
						 coletaVbox.setVisible(true);
						 coletorVbox.setVisible(true);
					}else if(orcamento.getStatus() == 7) {
						 nsVbox.setVisible(true); 
						 patVbox.setVisible(true);
						 modeloVbox.setVisible(true);
						 calVbox.setVisible(true);
						 coletaVbox.setVisible(true);
						 coletorVbox.setVisible(true);
						 empressaVbox.setVisible(true);
					}else {
						 nsVbox.setVisible(true); 
						 patVbox.setVisible(true);
						 modeloVbox.setVisible(true);
						 calVbox.setVisible(true);
					}
											
					
					
					if( orcamento.getColetor_id() != null && orcamento.getColetor_id() != 0 ) {						
						coletor = MainViewController.coletorController.findById( orcamento.getColetor_id() );
						dataColeta.setText( Format.formatData.format(coletor.getDate()));
						nomeColetor.setText( coletor.getNomeColetor() );
						nomeEmpressa.setText(coletor.getEmpressaName());
					}else {
						dataColeta.setText( "" );
						nomeColetor.setText( "" );
					}
					if(orcamento.getId()!=0 && orcamento.getId()!=null) {
						obsMateriais = itens.getAllItens(orcamento.getId(), orcamento.getItem() );
						tableOrcamento.setItems(obsMateriais);
						tableOrcamento.refresh();
						
					}
					else {
						obsMateriais = FXCollections.observableArrayList();
						tableOrcamento.setItems(obsMateriais);
						tableOrcamento.refresh();
					}
			}
	}
	
	private void startTableOrcamentos() {
		tableOrcamentos.setEditable(false);	 
		
		relatorioTable.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("relatorio"));	
		nfe.setCellValueFactory(new PropertyValueFactory<Orcamento, Integer>("nfe"));
		situation.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("situation"));
		
		chegadaTable.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		chegadaTable.setCellFactory( cell -> {
            return new TableCell<Orcamento, Date>() {
                @Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                      
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );		
		
		saidaTable.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
		saidaTable.setCellFactory( cell -> {
            return new TableCell<Orcamento, Date>() {
                @Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                      
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );		
		
		item.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("Item") );
		quantidade.setCellValueFactory(new PropertyValueFactory<Orcamento, Integer>("quantidade"));
		tableOrcamento.setItems(obsMateriais);
		
		tableOrcamentos.setItems(obsOrcamento);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startTableOrcamentos();

		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/Yggdrasilicon.jpg").toString() );
		logoYgg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscarImg.setImage(image);
		
		
	}
	
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		NewView.addChildren((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}
	
	@FXML
	private void buscar(ActionEvent event) throws IOException {
		obsOrcamento = orcamentoController.findAll();
		tableOrcamentos.setItems(obsOrcamento);
		tableOrcamentos.refresh();
	}
	
}
