package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.EstoqueRepController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.EstoqueController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

//@Component
public class MainViewController implements Initializable{
	
	protected String comboBoxBusca = "";	
	
		
//	private GeneratorPDF generator = new GeneratorPDF();	
	public static OrcamentoController orcamentoController = new OrcamentoController();
	public static ColetorController coletorController = new ColetorController();
	public static EquipamentoController equipamentoController = new EquipamentoController();
	public static EmpresaController empressaController = new EmpresaController();
	
	private EstoqueRepController estoqueRepController = new EstoqueRepController();
	
//	public static OrcamentoViewControllerDois OrcamentoViewControllerDois = new OrcamentoViewControllerDois();
	private Timeline timeline;
	protected static Boolean dbConection;
	
//	public static Equipamento equipamento;
//	public static Equipamento equipamentoEdit;
//	public static Equipamento equipamentoColeta;
//	public static Orcamento orcamentoEdit;
//	public static Orcamento orcamento;
//	public static Orcamento orcamentoColeta;
	public static Boolean FILTER = true;
	
	@FXML
	private ProgressIndicator ProgressIndicator;
	
	@FXML
	private ImageView logoYgg, cadastrar, refresh, abrir, cadastrar2, inserirColeta, home, buscar, buscar1, pdf, logout;
	//Tabela fila de motoristas

	@FXML
	protected TableColumn<Equipamento, String> empressa;
	@FXML
	protected TableColumn<Equipamento, String> status;
//	@FXML
//	private TableColumn<Equipamento, String> dataChegada;
	@FXML
	private TableColumn<Equipamento, Date> dateChegada;
	@FXML
	private TableColumn<Equipamento, String> modelo;
	@FXML
	private TableColumn<Equipamento, String> ns;
	@FXML
	private TableColumn<Equipamento, String> pat;
	@FXML
	private TableColumn<Equipamento, Date> ultimaCal;
	@FXML
	private TableColumn<Equipamento, String> relatorio;
	@FXML
	public TableView<Equipamento> tableFilaEquipamentos;
    public static ObservableList<Equipamento> obsListTableFilaEquipamentos = FXCollections.observableArrayList();
    
    public CheckBox sortCal, sortPat, sortNS, sortChegada, sortModelo, sortStatus, sortEmpresa;
    
	@FXML
	protected TextField filtro;
	
	@FXML
	protected Tab empresa;
    
	@FXML
    private void estoque(ActionEvent e) throws IOException {
		NewView.addChildrenToMain((Node) NewView.loadFXML("estoque" , new EstoqueController() ));
    }
    
    //------------------------------------- Página de Busca --------------------------------------------------------
	
	

    

    
	@FXML
	public void format(KeyEvent event) {
	}
	
	
//	@FXML
//    private void gerarPDF(ActionEvent e) throws IOException {
//	   	 if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
//	  		equipamentoEdit = tableFindEquipamentos.getSelectionModel().getSelectedItem();   	
//	  		
//	  		if(equipamentoEdit.getStatus() != 1 ) {
//	  			if( equipamentoEdit.getColetor_id() != null || equipamentoEdit.getColetor_id() != 0) {
//	  				NewView.getNewView("Saida de equipamento",  "saidaEquipamento", new OpenSaidaEquipamentoViewController() );
//		     			try {
//		    				obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
//				    		oldObs = obsListTableFilaEquipamentos;
//				    		dbConection = true;
//				    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
//						} catch (DbException e1) {
//						
//							e1.printStackTrace();
//						}
//	  			}
//	  		}
//	  		
//	  	}
//	 }
    
	@FXML
	private TableColumn<Equipamento, String> empressaFind;
	@FXML
	private TableColumn<Equipamento, String> nsFind;
	@FXML
	private TableColumn<Equipamento, String> patFind;
//	@FXML
//	private TableColumn<Equipamento, String> dataChegadaFind;
	@FXML
	private TableColumn<Equipamento, Date> dateChegadaFind;
	@FXML
	private TableColumn<Equipamento, Date> dataSaidaFind;
	@FXML
	protected TableView<Equipamento> tableFindEquipamentos;
    public static ObservableList<Equipamento> obsListTableFindEquipamentos= FXCollections.observableArrayList();
	
	
//----------------------------------------------------------------------------------------------------------------------


	
	public void initialize(URL location, ResourceBundle resources) {		
		taskInicial();	
		NewView.STAGE_MAIN_VIEW.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				    	tabsOpen();


			}
		
		});
	}	 	
	
//	direciona todo processo de inicialização para o segundo plano
	public void taskInicial() {
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() throws InterruptedException {	
//		    	Primeira coneção com banco de dados
				try {
					
					Thread.sleep(5000);
					DB.getConnection();
					dbConection = true;
					strartTable();
					updateTable();			
					sortTable();
					imageInit();
					
				}catch (DbException e) {
					Log.logString("MainViewController", e.getMessage());
					e.printStackTrace();
					dbConection = false;
				}finally {
					DB.closeConnection();
				}		    	
		    	
    			try {
    				updateProductsCombobox();
    			}catch (DbException e) {
    				e.printStackTrace();
    			}
    			beginTimer();

    			
    			ProgressIndicator.setVisible(false);
		        return null;
		    }
		};
	
		ProgressIndicator.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}
	
	private void tabsOpen() {
		try {
			TabPane tabPane = (TabPane) NewView.loadFXML("tab", new Teste());	
			
			AnchorPane pane = (AnchorPane) NewView.SCENE_MAIN_VIEW.getRoot();			
	    	NewView.TABPANE = (TabPane) pane.getChildren().get(0);	    	
			NewView.TABPANE.getTabs().add(tabPane.getTabs().get(0));
//			NewView.TABPANE.getTabs().get(0)
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	Atualiza a tabela de tempos em tempos
	private void beginTimer() {		
		timeline = new Timeline(new KeyFrame(Duration.seconds(AlfaPirometrosApplication.UPDATETIME), ev -> {
			if(dbConection) {
				if(FILTER) {					
					updateTable();
					updateProductsCombobox();
					
				}
			}else {
		    	showAlerts("DB exception ", "","Erro na comunicação com banco de dados, reiniciar App", AlertType.ERROR );
			}
		}));

	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();

	}
	
	
	private void updateProductsCombobox() {
		estoqueRepController.refreshObsProducts();		
	}

	public void updateTable() {
		try {			
			ObservableList<Equipamento> obsList = FXCollections.observableArrayList();
			obsList = orcamentoController.findAllLaboratorio(true);
			listShort(obsList);
	    	obsListTableFilaEquipamentos = obsList;							
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
			dbConection = true;  
			tableFilaEquipamentos.refresh();
		} catch (DbException e) {
			e.printStackTrace();
			Log.logString("MainViewController", e.getMessage());
		}
	}
	
	protected void showAlerts(String title, String mensage, String error, AlertType alertType) {
		Alerts.showAlert( title , mensage, error , alertType);
	}
	
	public ObservableList<Equipamento> listShort(ObservableList<Equipamento> list){
		if(sortCal.isSelected()) {
			try {
				list.sort( (a, b) -> a.getUltimaCalibDate().compareTo(b.getUltimaCalibDate()) );
			}catch(NullPointerException e) {
				
			}			
		}else if(sortPat.isSelected()){
			list.sort( (a, b) -> a.getPat().compareTo(b.getPat()));
		}else if(sortNS.isSelected()) {
			list.sort( (a, b) -> a.getNs().compareTo(b.getNs()));
		}else if(sortChegada.isSelected()) {
			list.sort( (a, b) -> a.getDateChegada().compareTo(b.getDateChegada()));
		}else if(sortModelo.isSelected()) {
			list.sort( (a, b) -> a.getModelo().compareTo(b.getModelo()));
		}else if(sortStatus.isSelected()) {
			list.sort( (a, b) -> a.getStatusStr().compareTo(b.getStatusStr()));
		}else{
			list.sort( (a, b) -> a.getEmpressaName().compareTo(b.getEmpressaName()));
		}    
		return list;
	}
	
//	Para manter somento um selecionado
    public void sortTable() {
		sortCal.selectedProperty().addListener((value)->{
			if(sortCal.isSelected()){
				sortPat.setSelected(false);
				sortNS.setSelected(false);
				sortChegada.setSelected(false);
				sortModelo.setSelected(false);
				sortStatus.setSelected(false);
				sortEmpresa.setSelected(false);
				updateTable();
			};
		});
		sortPat.selectedProperty().addListener((value)->{
			if(sortPat.isSelected()){
				sortCal.setSelected(false);
				sortNS.setSelected(false);
				sortChegada.setSelected(false);
				sortModelo.setSelected(false);
				sortStatus.setSelected(false);
				sortEmpresa.setSelected(false);
				updateTable();
			};
		});
		sortNS.selectedProperty().addListener((value)->{
			if(sortNS.isSelected()){
				sortPat.setSelected(false);
				sortCal.setSelected(false);
				sortChegada.setSelected(false);
				sortModelo.setSelected(false);
				sortStatus.setSelected(false);
				sortEmpresa.setSelected(false);
				updateTable();
			};
		});
		sortChegada.selectedProperty().addListener((value)->{
			if(sortChegada.isSelected()){
				sortPat.setSelected(false);
				sortNS.setSelected(false);
				sortCal.setSelected(false);
				sortModelo.setSelected(false);
				sortStatus.setSelected(false);
				sortEmpresa.setSelected(false);
				updateTable();
			};
		});
		sortModelo.selectedProperty().addListener((value)->{
			if(sortModelo.isSelected()){
				sortPat.setSelected(false);
				sortNS.setSelected(false);
				sortChegada.setSelected(false);
				sortCal.setSelected(false);
				sortStatus.setSelected(false);
				sortEmpresa.setSelected(false);
				updateTable();
			};
		});
		
		sortStatus.selectedProperty().addListener((value)->{
			if(sortStatus.isSelected()){
				sortPat.setSelected(false);
				sortNS.setSelected(false);
				sortChegada.setSelected(false);
				sortModelo.setSelected(false);
				sortCal.setSelected(false);
				sortEmpresa.setSelected(false);
				updateTable();
			};
		});
		sortEmpresa.selectedProperty().addListener((value)->{
			if(sortEmpresa.isSelected()){
				sortPat.setSelected(false);
				sortNS.setSelected(false);
				sortChegada.setSelected(false);
				sortModelo.setSelected(false);
				sortStatus.setSelected(false);
				sortCal.setSelected(false);
				updateTable();
			};
		});
		
    }
    
    private void imageInit() {		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/Yggdrasilicon.jpg").toString() );
		logoYgg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-refresh.png").toString() );
		refresh.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		cadastrar.setImage(image);
		cadastrar2.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-abrir-arquivo.png").toString() );
		abrir.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-insert.png").toString() );
		inserirColeta.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-bandeira-de-chegada.png").toString() );
		home.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscar.setImage(image);
		buscar1.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
		
    }
    
//  Configuração inicial das tabelas    
	private void strartTable() {	
		
	    tableFilaEquipamentos.setEditable(false);		    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    status.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("statusStr"));	    	    
		dateChegada.setCellValueFactory( new PropertyValueFactory<>("dateChegada"));		
		dateChegada.setCellFactory( cell -> {
            return new TableCell<Equipamento, Date>() {
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
		
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		relatorio.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("relatorio"));
		ultimaCal.setCellValueFactory(new PropertyValueFactory<>("ultimaCalibDate"));
		ultimaCal.setCellFactory( cell -> {
            return new TableCell<Equipamento, Date>() {
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
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);		
		
		tableFindEquipamentos.setEditable(false);	 	    
	    empressaFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    nsFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
	    patFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
	    dateChegadaFind.setCellValueFactory( new PropertyValueFactory<>("dateChegada"));
	    dateChegadaFind.setCellFactory( cell -> {
            return new TableCell<Equipamento, Date>() {
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
	    
	    dataSaidaFind.setCellValueFactory(new PropertyValueFactory<>("dateSaida"));	
	    dataSaidaFind.setCellFactory( cell -> {
            return new TableCell<Equipamento, Date>() {
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
		
	    tableFindEquipamentos.setItems(obsListTableFindEquipamentos);	
		
	}
	
}