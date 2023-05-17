package com.hrodriguesdev.gui.controller.view.main;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

//@Component
public class MainViewController implements Initializable{
	
	protected String comboBoxBusca = "";	
	
		
//	private GeneratorPDF generator = new GeneratorPDF();	
	public static OrcamentoController orcamentoController = new OrcamentoController();
	public static ColetorController coletorController = new ColetorController();
	public static EquipamentoController equipamentoController = new EquipamentoController();
	public static EmpresaController empressaController = new EmpresaController();
	
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
		try {
			DB.getConnection();
			dbConection = true;
		}catch (DbException e) {
			e.printStackTrace();
			dbConection = false;
		}finally {
			DB.closeConnection();
		}

		strartTable();
		
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
		sortTable();
		
		beginTimer();
//		ItensRepositoryFind repo = new ItensRepositoryFind();
//		repo.UpdateSinal();
		
		
		
	}	 	
	
	
	public void strartTable() {	
		
		if( dbConection ) {
			try {
				obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
	    		oldObs = obsListTableFilaEquipamentos;
	
		    }catch(DbException e) {
		    	dbConection = false;
		    } 
		}
				
	    tableFilaEquipamentos.setEditable(false);	 
	    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    status.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("statusStr"));	    
//		dataChegada.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));	
	    
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
	    
//	    dataChegadaFind.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));	
	    
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
	
	protected ObservableList<Equipamento> oldObs = FXCollections.observableArrayList();
	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(AlfaPirometrosApplication.UPDATETIME), ev -> {
			if(dbConection) {
				if(FILTER) {
					
					updateTable();
					
				}
			}else {
		    	showAlerts("DB exception ", "","Erro na comunicação com banco de dados, reiniciar App", AlertType.ERROR );
			}
		}));

	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();

	}
	
	
	public void updateTable() {
		try {
			
			ObservableList<Equipamento> obsList = FXCollections.observableArrayList();
			obsList = orcamentoController.findAllLaboratorio(true);
			listShort(obsList);
	    	obsListTableFilaEquipamentos = obsList;
						
		oldObs = obsListTableFilaEquipamentos;
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
		dbConection = true;  
		tableFilaEquipamentos.refresh();
		
//					obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
//					if(obsListTableFilaEquipamentos.size() > oldObs.size() ) {						
//						List<Equipamento> list1 = obsListTableFilaEquipamentos.stream().filter(item1 -> {
//							return oldObs.stream().filter(item2 -> item2.equals(item1) ).findAny().isPresent();
//						}).collect( Collectors.toList() );
//						obsListTableFilaEquipamentos.addAll(list1);
//						
////					        List<Integer> interseccao = lista1.stream().filter(item1 -> {
////					            return lista2.stream().filter(item2 -> new Integer(item2).equals(item1)).findAny().isPresent();
////					        }).collect(Collectors.toList());
//
////						showAlerts("Updatede Lista", "", "Equipamento da empressa " + obsListTableFilaEquipamentos.get(obsListTableFilaEquipamentos.size()-1).getEmpressaName() + " foi adcionado equipamento na lista ", AlertType.INFORMATION);
//						oldObs = obsListTableFilaEquipamentos;
//						return;
//					}
//					if(obsListTableFilaEquipamentos.size() < oldObs.size() ) {
//						oldObs = obsListTableFilaEquipamentos;
//						return;
//					}
//					for(int i =0; i< oldObs.size(); i++) {
//						if( obsListTableFilaEquipamentos.get(i).getStatus() != oldObs.get(i).getStatus() ) {
////							showAlerts("Updatede Lista", "", "Equipamento da empressa " + obsListTableFilaEquipamentos.get(obsListTableFilaEquipamentos.size()-1).getEmpressaName() + " teve o status alterado ", AlertType.ERROR);
//						}
//					}
//					oldObs = obsListTableFilaEquipamentos;
		} catch (DbException e) {
//					showAlerts("begin Timer ", "", e.getMessage(), AlertType.INFORMATION );
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
	
}