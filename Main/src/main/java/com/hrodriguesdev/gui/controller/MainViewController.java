package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpressaController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Anotations;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.extend.OpenSaidaEquipamentoViewController;
import com.hrodriguesdev.gui.controller.view.insert.EquipamentoInsert;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

//@Component
public class MainViewController implements Initializable{
		
//	private GeneratorPDF generator = new GeneratorPDF();	
	public static OrcamentoController orcamentoController = new OrcamentoController();
	public static ColetorController coletorController = new ColetorController();
	public static EquipamentoController equipamentoController = new EquipamentoController();
	public static EmpressaController empressaController = new EmpressaController();
	public static EquipamentoInsert equipamentoInsert = new EquipamentoInsert();
	private Timeline timeline;
	protected static Boolean dbConection;
	
	public static Equipamento equipamento;
	public static Equipamento equipamentoEdit;
	public static Orcamento orcamentoEdit;
	public static Orcamento orcamento;
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
	private TableColumn<Equipamento, String> ultimaCal;
	@FXML
	private TableColumn<Equipamento, String> relatorio;
	@FXML
	public TableView<Equipamento> tableFilaEquipamentos;
    public static ObservableList<Equipamento> obsListTableFilaEquipamentos = FXCollections.observableArrayList();
    
    @FXML
	private TableColumn<Anotations, String> descricao;
	@FXML
	private TableColumn<Anotations, String> referencia;
	@FXML
	private TableView<Anotations> tabelaAnotacoes;
    public static ObservableList<Anotations> obsListTableAnotacoes = FXCollections.observableArrayList();
    

    //------------------------------------- Página de Busca --------------------------------------------------------
	
	

    

    
	@FXML
	public void format(KeyEvent event) {
	}
	
	
	@FXML
    private void gerarPDF(ActionEvent e) throws IOException {
	   	 if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
	  		equipamentoEdit = tableFindEquipamentos.getSelectionModel().getSelectedItem();   	
	  		
	  		if(equipamentoEdit.getStatus() != 1 ) {
	  			if( equipamentoEdit.getColetor_id() != null || equipamentoEdit.getColetor_id() != 0) {
	  				NewView.getNewView("Saida de equipamento",  "saidaEquipamento", new OpenSaidaEquipamentoViewController() );
		     			try {
							obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
				    		oldObs = obsListTableFilaEquipamentos;
				    		dbConection = true;
				    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
						} catch (DbException | SQLException e1) {
						
							e1.printStackTrace();
						}
	  			}
	  		}
	  		
	  	}
	 }
    
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
	private TableColumn<Equipamento, String> dataSaidaFind;
	@FXML
	protected TableView<Equipamento> tableFindEquipamentos;
    public static ObservableList<Equipamento> obsListTableFindEquipamentos= FXCollections.observableArrayList();
	
	
//----------------------------------------------------------------------------------------------------------------------


	@Override
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
		
		beginTimer();
//		ColetorRepository repo = new ColetorRepository();
//		repo.updatedeAllDate();
	}	 	
	
	
	public void strartTable() {	
		
		if( dbConection ) {
			try {
	    		obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
	    		oldObs = obsListTableFilaEquipamentos;
	
		    }catch(DbException | SQLException e) {
		    	dbConection = false;
		    } 
		}
				
	    tableFilaEquipamentos.setEditable(false);	 
	    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    status.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("statusStr"));	    
//		dataChegada.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));		
		dateChegada.setCellValueFactory( new PropertyValueFactory<Equipamento, Date>("dateChegada"));
		
		
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		relatorio.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("relatorio"));
		ultimaCal.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ultimaCalib"));
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);		
		
		tableFindEquipamentos.setEditable(false);	 	    
	    empressaFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    nsFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
	    patFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
	    
//	    dataChegadaFind.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));	
	    
	    dateChegadaFind.setCellValueFactory( new PropertyValueFactory<Equipamento, Date>("dateChegada"));	
	    
	    dataSaidaFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("dataSaida"));	
		
	    tableFindEquipamentos.setItems(obsListTableFindEquipamentos);	
		
	}
	
	protected ObservableList<Equipamento> oldObs = FXCollections.observableArrayList();
	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(AlfaPirometrosApplication.UPDATETIME), ev -> {
			if(dbConection) {
				if(FILTER) {
					try {
					obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
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
					} catch (DbException | SQLException e) {
	//					showAlerts("begin Timer ", "", e.getMessage(), AlertType.INFORMATION );
					}			
				}
			}else {
		    	showAlerts("DB exception ", "","Erro na comunicação com banco de dados, reiniciar App", AlertType.ERROR );
			}
		}));

	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();

	}
	
	
	protected void showAlerts(String title, String mensage, String error, AlertType alertType) {
		Alerts.showAlert( title , mensage, error , alertType);
	}

}