package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RelatoriosController implements Initializable{
	private OrcamentoController orcamentoController = new OrcamentoController();
	private EquipamentoController equipamentoController = new EquipamentoController();
	private Itens itens = new Itens();

	@FXML
	protected ComboBox<String> empressaCombobox;
	
	@FXML
	protected DatePicker inicioDatePiker, finalDatePiker;
	
	@FXML
	private VBox empressaVbox;
	
	@FXML
	private HBox coletorHbox, equipamentoHbox;
	
	@FXML
	private TextField ns, pat, empressaName,
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
					Orcamento orcamento = orcamentoController.getOrcamento( tableOrcamentos.getSelectionModel().getSelectedItem().getId() ); 
					if(orcamento == null)
						return;
					
					if(orcamento.getStatus() == 20 && orcamento.getEquipamento_id() == 0 )  {
						 equipamentoHbox.setVisible(false);
						 
						 empressaVbox.setVisible(false);
						 
						 coletorHbox.setVisible(true);
						 coletor(orcamento);
						 
					}else if(orcamento.getStatus() == 20 && orcamento.getEquipamento_id() != 0 )  {
						equipamentoHbox.setVisible(true);						 
						 empressaVbox.setVisible(true);
						 equipamento(orcamento);
						 
						 coletorHbox.setVisible(false);
						 
					}else if(orcamento.getStatus() == 7) {
						 equipamentoHbox.setVisible(true);						 
						 empressaVbox.setVisible(true);
						 equipamento(orcamento);
						 
						 coletorHbox.setVisible(true);
						 coletor(orcamento);
						 
					}else {
						equipamentoHbox.setVisible(true);						
						 empressaVbox.setVisible(true);
						 equipamento(orcamento);
						 
						 coletorHbox.setVisible(false);
					}		

						obsMateriais = itens.getAllItens(orcamento.getId(), orcamento.getItem() );
						tableOrcamento.setItems(obsMateriais);
						tableOrcamento.refresh();

			}
	}
	
	private void coletor(Orcamento orcamento) {
		if( orcamento.getColetor_id() != null && orcamento.getColetor_id() != 0 ) {						
			Coletor coletor = MainViewController.coletorController.findById( orcamento.getColetor_id() );
			dataColeta.setText( Format.formatData.format(coletor.getDate()));
			nomeColetor.setText( coletor.getNomeColetor() );
			nomeEmpressa.setText(coletor.getEmpressaName());
		}else {
			dataColeta.setText( "" );
			nomeColetor.setText( "" );
			nomeEmpressa.setText( "" );
		}
		
	}
	
	private void equipamento(Orcamento orcamento) {
		if( orcamento.getEquipamento_id() != 0 ) {
			Equipamento equipamento =  equipamentoController.findById(orcamento.getEquipamento_id());
			if(equipamento != null) {
				ns.setText(equipamento.getNs());
				pat.setText(equipamento.getPat());
				modelo.setText(equipamento.getModelo());
				empressaName.setText(equipamento.getEmpressaName());
				if(equipamento.getUltimaCalibDate() != null) 
					ultimaCal.setText( Format.formatData.format(equipamento.getUltimaCalibDate() ) );
				else
					ultimaCal.setText("");
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
		
		 coletorHbox.setVisible(false);
		 equipamentoHbox.setVisible(false);
		 empressaVbox.setVisible(false);
		
		
	}
	
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		NewView.addChildren((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}
	
	@FXML
	private void buscar(ActionEvent event) throws IOException {
		obsOrcamento = orcamentoController.findAll();
		
		if(inicioDatePiker.getValue() != null ) {			
			java.sql.Date gettedDatePickerDateStart = java.sql.Date.valueOf(inicioDatePiker.getValue());						
			obsOrcamento = obsOrcamento.filtered(x -> x.getData_chegada().after(gettedDatePickerDateStart));
			
		}
		if(finalDatePiker.getValue() != null) {
			java.sql.Date gettedDatePickerDateFinal = java.sql.Date.valueOf(finalDatePiker.getValue());	
			obsOrcamento = obsOrcamento.filtered(x -> x.getData_chegada().before(gettedDatePickerDateFinal));
		}

		tableOrcamentos.setItems(obsOrcamento);
		tableOrcamentos.refresh();
	}
	
}
