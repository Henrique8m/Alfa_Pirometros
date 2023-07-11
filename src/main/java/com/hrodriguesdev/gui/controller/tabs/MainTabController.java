package com.hrodriguesdev.gui.controller.tabs;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.EstoqueRepController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.EquipamentoEntradaViewController;
import com.hrodriguesdev.gui.controller.EstoqueController;
import com.hrodriguesdev.gui.controller.OrcamentoView;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.gui.controller.view.saida.equipemento.OpenSaidaEquipamentoViewController;
import com.hrodriguesdev.gui.controller.view.saida.equipemento.SaidaEquipamentoViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

//@Component
public class MainTabController implements Initializable, Runnable{
	
	@FXML
    public CheckBox sortCal, sortPat, sortNS, sortChegada, sortModelo, sortStatus, sortEmpresa;
	
	@FXML
	private ImageView abrir, cadastrar,cadastrar2, home, inserirColeta, logout, logoYgg, refresh;
	
	@FXML
	private ProgressIndicator ProgressIndicator;

	@FXML
	public TableView<Equipamento> tableFilaEquipamentos;
	public static ObservableList<Equipamento> obsListTableFilaEquipamentos = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Equipamento, String> empressa;
	@FXML
	protected TableColumn<Equipamento, String> status;
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
	protected TextField filtro;	
	private boolean isFilter = true;		
	private Thread threadFiltro = new Thread(this);	
	
	public static String comboBoxBusca = "";	
	
	//@Autowired
	protected EquipamentoController equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
	protected OrcamentoController orcamentoController = InjecaoDependency.ORCAMENTO_CONTROLLER;
	private EstoqueRepController estoqueRepController = new EstoqueRepController();

	private Timeline timeline;
	private boolean dbConection;
	
	
	public void initialize(URL location, ResourceBundle resources) {		
		taskInicial();	
	}

	
	@FXML
    public void refreshAndFilterTable(KeyEvent keyEvent) {
		if(keyEvent.getCode().toString() == "ENTER") {
			clear(new ActionEvent());
			
	    }else if( filtro.getText() != "" ){
	    	isFilter = false;
	    	refreshTableMain();		
	    }else if(filtro.getText() == "") {
	    	isFilter = true;
	    }
	
		if(keyEvent.getCode().toString() == "F5" )
			refreshTableMain();
		    	
    	
    }

	@FXML
    public void clear(ActionEvent e) {
		filtro.setText("");
		isFilter = true;
		refreshTableMain();
    }
    
    @FXML
    public void tableFilaEquipamentoClick(MouseEvent event) throws IOException, SQLException {
		if(event.getClickCount() >= 2) {
			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
				int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
				
//				Status 1, equipamento nao tem orcamento, ir para a pagina para adcionar
				if(status == 1) 
					addOrcamento(new ActionEvent());

				else if(status == 2 || status == 3 || status == 4 || status == 8 || status == 12 || status == 13 || status == 15 || status == 16) 
					openOrcamento(new ActionEvent());
				
				else if( status == 5 || status == 6 || status == 9)
					addColeta(new ActionEvent());
			}				
			
		}
    	
    }
    
    @FXML
    public void addEquipamento(ActionEvent e) throws IOException {
    	NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.ENTRADA_EQUIPAMENTO , new EquipamentoEntradaViewController() ));
    }
    
    @FXML
    public void addOrcamento(ActionEvent e) throws IOException, SQLException {
		if(!tableFilaEquipamentos.getSelectionModel().isEmpty()) {
			int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
			if(status == 1){
				NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.ADD_PRODUCT_OS , new OrcamentoInsert(
						tableFilaEquipamentos.getSelectionModel().getSelectedItem(),
						orcamentoController.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() ) ) ));

			}else openOrcamento(new ActionEvent());

		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
    	
    }  
    
    @FXML
    protected void openOrcamento(ActionEvent e) throws IOException {
    	if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
    		Orcamento orcamento;
    		try {
				orcamento = orcamentoController.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
				dbConection = true;
			} catch (SQLException e1) {
				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
				dbConection = false;
				return;
			}
    		if(orcamento != null) {
	    		if(orcamento.getStatus()>1){
	//    			NewView.getNewView("Entrada Equipamento", "orcamentoView", new OrcamentoViewController() );
	    			NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.OS_VIEW , new OrcamentoView(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento ) ));
	    		}
    		}else
    			Alerts.showAlert("Orcamento" , "Status Orcamento", "Não consta orçamento para este equipamento" , AlertType.INFORMATION);
    		
    	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
	       
    	refreshTableMain();

    }
    
		
	@FXML
    private void addColeta(ActionEvent e) throws IOException, SQLException {
		Orcamento orcamento;
   	 if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {    		
			int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
			orcamento = orcamentoController.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
			if(status == 5 || status == 6 || status == 9) {					
    			if( orcamento.getColetor_id() == null || orcamento.getColetor_id() == 0) {
    				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new SaidaEquipamentoViewController(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento ) );

    			} else{
    				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new OpenSaidaEquipamentoViewController(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento));

    			}
    		}else
    			Alerts.showAlert("Saida de Equipamento" , "Equipamento nao pode ser liberado por:", "" , AlertType.INFORMATION);
    		
    	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
   	 
	}
	
	
	@FXML
    private void estoque(ActionEvent e) throws IOException {
		NewView.addChildrenToMain((Node) NewView.loadFXML("estoque" , new EstoqueController() ));
    }
	
	@FXML
	public void logout(ActionEvent event) {

	}
			
//	direciona todo processo de inicialização para o segundo plano
	public void taskInicial() {
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() throws InterruptedException {	
//		    	Primeira coneção com banco de dados
				try {					
					Thread.sleep(3000);
					strartTable();
					refreshTableMain();			
					sortTable();
					imageInit();
					updateProductsCombobox();
					beginTimer();
					
				}catch (DbException e) {
					Log.logString("MainViewController", e.getMessage());
					e.printStackTrace();
					dbConection = false;
				}
    			    			
    			ProgressIndicator.setVisible(false);
		        return null;
		    }
		};
	
		ProgressIndicator.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}
	
	private void updateProductsCombobox() {
		estoqueRepController.refreshObsProducts();		
	}
	
//	Atualiza a tabela de tempos em tempos
	private void beginTimer() {		
		timeline = new Timeline(new KeyFrame(Duration.seconds(AlfaPirometrosApplication.REFRESH_TABLE_MAIN_VIEW), ev -> {
			if(dbConection) {
				if(isFilter) {					
					refreshTableMain();
					updateProductsCombobox();
					
				}
			}else {
		    	showAlerts("DB exception ", "","Erro na comunicação com banco de dados, reiniciar App", AlertType.ERROR );
			}
		}));

	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();

	}	
	
	@SuppressWarnings("deprecation")
	public void refreshTableMain() {    	
		if(threadFiltro.getState().toString() == "NEW")
			threadFiltro.start();
		else 
			threadFiltro.resume();					
		
    }

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while(true) {
			ObservableList<Equipamento> obsList = FXCollections.observableArrayList();
	    	try{
	    		if(!filtro.getText().isEmpty() && !filtro.getText().isBlank() )   			
	    			obsList = equipamentoController.findByName( filtro.getText() );
	    		else
	    			obsList = orcamentoController.findAllLaboratorio(true);
	    		
	    		listShort(obsList); 		
	    		
	    		obsListTableFilaEquipamentos = obsList;
				tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
	    		dbConection = true;  
	    		tableFilaEquipamentos.refresh();
	    		
			} catch (DbException | SQLException e1) {
				dbConection = false;
				e1.printStackTrace();
			}
	    	threadFiltro.suspend();
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
			list.sort( (a, b) -> a.getEmpresaName().compareTo(b.getEmpresaName()));
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
				refreshTableMain();
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
				refreshTableMain();
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
				refreshTableMain();
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
				refreshTableMain();
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
				refreshTableMain();
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
				refreshTableMain();
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
				refreshTableMain();
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
		
    }
        
//  Configuração inicial das tabelas    
	private void strartTable() {	
		
	    tableFilaEquipamentos.setEditable(false);		    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empresaName"));
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
		
	}
	
}