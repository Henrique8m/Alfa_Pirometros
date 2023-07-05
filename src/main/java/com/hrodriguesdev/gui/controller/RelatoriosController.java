package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTORelatorio;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
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
	
	@FXML
	private ProgressIndicator ProgressIndicator;
	
	
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
	@FXML
	private TableColumn<OrcamentoDTORelatorio, String>  MaterialOutFinalidade;
	

//	Tabela Manutencao em equipamentos
	
	@FXML
	private TableView<OrcamentoDTOEquipamento> MaintenanceTable;
	
	private ObservableList<OrcamentoDTOEquipamento> obsMaintenance = FXCollections.observableArrayList();
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String>  MaintenanceRelatorio;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, Date>  MaintenanceDateIn;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, Date>  MaintenanceDateOut;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String>  MaintenanceSituation;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String>  MaintenanceEmploye;	
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String>  MaintenanceNS;	
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String>  MaintenancePat;
	
//	tabela materiais usados
	
	@FXML
	protected TableView<Product> productSelectedTable;
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> productsSelected;
	@FXML
	protected TableColumn<Product, String> descriptionSelected;
	@FXML
	protected TableColumn<Product, String> unitMeasurementSelected;
	@FXML
	protected TableColumn<Product, Double> amountSelected;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startTable();
		imageInit();		
		finalDatePiker.setValue(LocalDate.now());
		inicioDatePiker.setValue(LocalDate.of(2020, 1, 1));
	}

//	Button
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}
	
//	Button
	@FXML
	private void buscar(ActionEvent event) throws IOException {
		ProgressIndicator.setVisible(true);
		obsOrcamento = FXCollections.observableArrayList();
		obsMaintenance = FXCollections.observableArrayList();
		
		Boolean entrada = entradaMaterial.selectedProperty().getValue();
		Boolean saida = saidaMaterial.selectedProperty().getValue();
		Boolean mRealizada = manutencaoRealizada.selectedProperty().getValue();
		Boolean mCurso = manutencaoEmCurco.selectedProperty().getValue();

		Task<Void> task = new Task<Void>() {
		    @Override public Void call() {	    	
		    	if(entrada)
		    		obsOrcamento.addAll(orcamentoController.findAllDTORelatorio().stream().filter(
		    			x -> x.getFinalidade().equals("Entrada")).collect(Collectors.toList()));
		    	if(saida)
		    		obsOrcamento.addAll(orcamentoController.findAllDTORelatorio().stream().filter(
			    			x -> x.getFinalidade().equals("Saida")).collect(Collectors.toList()));
		    	
		    	if(mRealizada)
		    		obsMaintenance.addAll(orcamentoController.findAllDTOEquipamento().stream().filter(
		    				x -> !x.isLaboratorio()).collect(Collectors.toList()));
		    	if(mCurso)
		    		obsMaintenance.addAll(orcamentoController.findAllDTOEquipamento().stream().filter(
		    				x -> x.isLaboratorio()).collect(Collectors.toList()));
		    	
		    	
		    	if(inicioDatePiker.getValue() != null ) {			
		    		
		    		Date gettedDatePickerDateStart = Date.valueOf(inicioDatePiker.getValue());						
		    		obsOrcamento = obsOrcamento.filtered(x -> {
		    			
//		    			int date = x.getData_chegada().getDate() -1 ;
//		    			Date data = x.getData_chegada();
//		    			data.setDate(date);
//		    			if(data.after(gettedDatePickerDateStart))
//		    					return true;
//		    			return false;
		    			
		    			if(x.getData_chegada().after(gettedDatePickerDateStart) ||  x.getData_chegada().equals(gettedDatePickerDateStart))
		    					return true;
		    			return false;
		    		});
		    		obsMaintenance = obsMaintenance.filtered(x -> x.getData_chegada().after(gettedDatePickerDateStart));
		    		
		    	}
		    	if(finalDatePiker.getValue() != null) {
		    		Date gettedDatePickerDateFinal = Date.valueOf(finalDatePiker.getValue());	
		    		obsOrcamento = obsOrcamento.filtered(x ->{
		    			if(x.getData_chegada().before(gettedDatePickerDateFinal) ||  x.getData_chegada().equals(gettedDatePickerDateFinal))
	    					return true;
		    			return false;
		    		});
		    		obsMaintenance = obsMaintenance.filtered(x -> x.getData_chegada().before(gettedDatePickerDateFinal));
		    	}
	
				MaterialOutTable.setItems(obsOrcamento);;
				MaintenanceTable.setItems(obsMaintenance);	
	//			updateProgress(100, 100);
				ProgressIndicator.setVisible(false);
		        return null;
		    }
		};
	
		ProgressIndicator.progressProperty().bind(task.progressProperty());
		new Thread(task).start();		
		
	}
	
//	Acao de click na tabela de saida de material e na tabela de manutencao em equipamentos
	@FXML
	public void clickOrcamento(MouseEvent event) throws SQLException {
			

			if(!MaterialOutTable.getSelectionModel().isEmpty()) {
				ProductsController controller = new ProductsController();
				obsMateriais = controller.findAllByOrcamentoId( MaterialOutTable.getSelectionModel().getSelectedItem().getId());
				productSelectedTable.setItems(obsMateriais);
				productSelectedTable.refresh();
				System.out.println(obsMateriais.get(0).getName());
//
			}else {
//				ProductsController controller = new ProductsController();
//				obsMateriais = controller.findAllOsByOrcamentoId( MaterialOutTable.getSelectionModel().getSelectedItem().getId());
//				productSelectedTable.setItems(obsMateriais);
//				productSelectedTable.refresh();
//				System.out.println("Teste");
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
		MaterialOutFinalidade.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, String>("finalidade"));
		MaterialOutTable.setItems(obsOrcamento);		
		
		MaintenanceTable.setEditable(false);		
		MaintenanceRelatorio.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("relatorio"));			
		MaintenanceDateIn.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		MaintenanceDateIn.setCellFactory( cell -> {
			return new TableCell<OrcamentoDTOEquipamento, Date>() {
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
            return new TableCell<OrcamentoDTOEquipamento, Date>() {
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
		
		MaintenanceSituation.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("situation"));
		MaintenanceEmploye.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("empresa"));
		MaintenanceNS.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("ns"));
		MaintenancePat.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("pat"));
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
