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
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.DTO.OrcamentoDTORelatorio;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RelatoriosController implements Initializable{
	
	private OrcamentoController orcamentoController = new OrcamentoController();
	private EquipamentoController equipamentoController = new EquipamentoController();

	@FXML
	protected DatePicker inicioDatePiker, finalDatePiker;
	
	@FXML
	protected ComboBox<String> empressaCombobox;
		
	@FXML
	private CheckBox manutencaoEmCurco, manutencaoRealizada, saidaMaterial, entradaMaterial;
	
	@FXML
	private ImageView cancelarImg, buscarImg;	
	
	
//	Tabela saida de material
	
	@FXML
	private TableView<OrcamentoDTORelatorio> MaterialOutTable;
	
	private ObservableList<OrcamentoDTORelatorio> obsOrcamento = FXCollections.observableArrayList();
	@FXML
	private TableColumn<OrcamentoDTORelatorio, Date>  MaterialOutData;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, Integer>  MaterialOutNfe;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, String>  MaterialOutEmpresa;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, String>  MaterialOutAuthor;

//	Tabela Manutencao em equipamentos
	
	@FXML
	private TableView<Orcamento> MaintenanceTable;
	
	private ObservableList<Orcamento> obsMaintenance = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Orcamento, String>  MaintenanceRelatorio;
	@FXML
	private TableColumn<Orcamento, Date>  MaintenanceDateIn;
	@FXML
	private TableColumn<Orcamento, Date>  MaintenanceDateOut;
	@FXML
	private TableColumn<Orcamento, String>  MaintenanceSituation;
	@FXML
	private TableColumn<Orcamento, String>  MaintenanceEmploye;	
	@FXML
	private TableColumn<Orcamento, String>  MaintenanceNS;	
	@FXML
	private TableColumn<Orcamento, String>  MaintenancePat;
	
//	tabela materiais usados
	
	protected TableView<Product> productSelectedTable = new TableView<>();
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> productsSelected, descriptionSelected, unitMeasurementSelected;
	@FXML
	protected TableColumn<Product, Double> amountSelected;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startTable();
		imageInit();

				
	}

//	Button
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}
	
//	Button
	@FXML
	private void buscar(ActionEvent event) throws IOException {
		Boolean entrada = entradaMaterial.selectedProperty().getValue();
		Boolean saida = saidaMaterial.selectedProperty().getValue();
		Boolean mRealizada = manutencaoRealizada.selectedProperty().getValue();
		Boolean mCurso = manutencaoEmCurco.selectedProperty().getValue();
		
		
		obsOrcamento = orcamentoController.findAll(entrada, saida, mRealizada, mCurso);
		
		if(inicioDatePiker.getValue() != null ) {			
			java.sql.Date gettedDatePickerDateStart = java.sql.Date.valueOf(inicioDatePiker.getValue());						
			obsOrcamento = obsOrcamento.filtered(x -> x.getData_chegada().after(gettedDatePickerDateStart));
			
		}
		if(finalDatePiker.getValue() != null) {
			java.sql.Date gettedDatePickerDateFinal = java.sql.Date.valueOf(finalDatePiker.getValue());	
			obsOrcamento = obsOrcamento.filtered(x -> x.getData_chegada().before(gettedDatePickerDateFinal));
		}

		MaterialOutTable.setItems(obsOrcamento);
		MaterialOutTable.refresh();
	}
	
//	Acao de click na tabela de saida de material e na tabela de manutencao em equipamentos
	@FXML
	public void clickOrcamento(MouseEvent event) throws SQLException {
			if(MaterialOutTable.getSelectionModel().getSelectedItem() != null) {
					Orcamento orcamento = orcamentoController.getOrcamento( MaterialOutTable.getSelectionModel().getSelectedItem().getId() ); 
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

//						obsProducts = itens.getAllItens(orcamento.getId(), orcamento.getItem() );
						productSelectedTable.setItems(obsMateriais);
						productSelectedTable.refresh();

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

	private void startTable() {
		
		MaterialOutTable.setEditable(false);		
		MaterialOutData.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		MaterialOutData.setCellFactory( cell -> {
			return new TableCell<OrcamentoDTORelatorio, Date>() {
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
		
		MaterialOutNfe.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, Integer>("nfe"));
		
		MaterialOutEmpresa.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, String>("empresa"));	
		MaterialOutAuthor.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, String>("author"));
		MaterialOutTable.setItems(obsOrcamento);	
		
		
		
		MaintenanceTable.setEditable(false);		
		MaintenanceRelatorio.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("relatorio"));			
		MaintenanceDateIn.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		MaintenanceDateIn.setCellFactory( cell -> {
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
		
		MaintenanceDateOut.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
		MaintenanceDateOut.setCellFactory( cell -> {
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
		
		MaintenanceSituation.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("situation"));
		MaintenanceEmploye.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("empresa"));
		MaintenanceNS.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("ns"));
		MaintenancePat.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("pat"));
		MaintenanceTable.setItems(obsMaintenance);
		
		
		
		
		productsSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));		
		descriptionSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurementSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountSelected.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));		
		productSelectedTable.setItems(obsMateriais);
		
	}

	
	
	private void imageInit() {
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscarImg.setImage(image);
				
	}
	
}
