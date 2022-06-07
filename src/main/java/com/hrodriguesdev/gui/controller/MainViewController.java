package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

//@Component
public class MainViewController implements Initializable{
		
//	private GeneratorPDF generator = new GeneratorPDF();	
	public static Controller controller = new Controller();
	private Timeline timeline;
	
	public static Equipamento equipamento;
	public static Orcamento orcamento;
	
	@FXML
	private ImageView logoYgg, cadastrar, refresh, abrir, cadastrar2, inserirColeta, home, buscar, buscar1, pdf;
	//Tabela fila de motoristas

	@FXML
	private TableColumn<Equipamento, String> empressa;
	@FXML
	private TableColumn<Equipamento, String> status;
	@FXML
	private TableColumn<Equipamento, String> dataChegada;
	@FXML
	private TableColumn<Equipamento, String> modelo;
	@FXML
	private TableColumn<Equipamento, String> ns;
	@FXML
	private TableColumn<Equipamento, String> pat;
	@FXML
	private TableView<Equipamento> tableFilaEquipamentos;
    public static ObservableList<Equipamento> obsListTableFilaEquipamentos = FXCollections.observableArrayList();
    
    @FXML
	private TableColumn<String> descricao;
	@FXML
	private TableColumn< String> referencia;
	@FXML
	private TableView<String> tabelaAnotacoes;
    public static ObservableList<String> obsListTableAnotacoes = FXCollections.observableArrayList();
    
    
    
    
    
    @FXML
    private void addEquipamento(ActionEvent e) throws IOException {
    	NewView.getNewViewModal("Entrada Equipamento",  (Pane) NewView.loadFXML("entradaEquipamento", new AddEquipamentoViewController() ) , LoadViewController.getStage());
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
		tableFilaEquipamentos.refresh();    	
    }
    
    @FXML
    private void updateStatus(ActionEvent e) throws IOException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			NewView.getNewViewModal("Alterar Status", (Pane) NewView.loadFXML("status", new StatusViewController() ), LoadViewController.getStage());
			try {
				obsListTableFilaEquipamentos = controller.getByLaboratorio(true);
			} catch (DbException | SQLException e1) {
				Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR);
			}
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
			tableFilaEquipamentos.refresh();
		}
		else {
			showAlerts();
		}
	   	
    }


    
    
    @FXML
    private void addOrcamento(ActionEvent e) throws IOException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {			
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			NewView.getNewViewModal("Entrada Equipamento", (Pane) NewView.loadFXML("orcamento", new AddOrcamentoViewController() ), LoadViewController.getStage());
			try {
				obsListTableFilaEquipamentos = controller.getByLaboratorio(true);
			} catch (DbException | SQLException e2) {
				Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e2.getMessage(), AlertType.ERROR);
			}
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
			tableFilaEquipamentos.refresh();
		}
		else {
			showAlerts();
		}
    	
    }
    
    
    @FXML
    private void openOrcamento(ActionEvent e) throws IOException {
    	if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
    		equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    		orcamento = controller.getOrcamento( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
    		if(orcamento != null) {
    			NewView.getNewViewModal("Entrada Equipamento", (Pane) NewView.loadFXML("orcamentoView", new OrcamentoViewController() ), LoadViewController.getStage());
    		}else
    			Alerts.showAlert("Orcamento" , "Status Orcamento", "Não consta orçamento para este equipamento" , AlertType.INFORMATION);
    		
    	}else showAlerts();
	        	
    }
          
 
     @FXML
    private void addColeta(ActionEvent e) throws IOException {
    	//System.out.println();
    	showAlerts();
    }
	
	
	
	//------------------------------------- Página de Busca --------------------------------------------------------
	
	
    @FXML
    private TextField textEmpresa, textNsEquip, textPatEquip;

    
	@FXML
    private void buscar(ActionEvent e) throws IOException {
    	System.out.println("Buscar");
    }
    
	@FXML
	public void format(KeyEvent event) {
		
	}
	
	@FXML
	public void click(MouseEvent event) {

		System.out.println("Click");		
	}	
	
	@FXML
    private void gerarPDF(ActionEvent e) throws IOException {
		
    	
    }
    
	@FXML
	private TableColumn<Equipamento, String> empressaFind;
	@FXML
	private TableColumn<Equipamento, String> nsFind;
	@FXML
	private TableColumn<Equipamento, String> patFind;
	@FXML
	private TableColumn<Equipamento, String> dataChegadaFind;
	@FXML
	private TableColumn<Equipamento, String> dataSaidaFind;
	@FXML
	private TableView<Equipamento> tableFindEquipamentos;
    public static ObservableList<Equipamento> obsListTableFindEquipamentos= FXCollections.observableArrayList();
	
	
//----------------------------------------------------------------------------------------------------------------------


	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		
		
}	 	
	
	
	public void strartTable() {	
		
		//Table fila de Motorista descarregando
	    try {
	    	obsListTableFilaEquipamentos = controller.getByLaboratorio(true);
	    	oldObs = obsListTableFilaEquipamentos;
	    }catch(DbException e) {
	    	Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e.getMessage(), AlertType.ERROR);
	    } catch (SQLException e) {
	    	Alerts.showAlert("SQL exception ", "Erro na comunicação com banco de dados", e.getMessage(), AlertType.ERROR);
		}
		
	    tableFilaEquipamentos.setEditable(false);	 
	    dataChegada.setSortType(TableColumn.SortType.DESCENDING);
	    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    status.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("statusStr"));
		dataChegada.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
		
		
		
		
	}
	
	private ObservableList<Equipamento> oldObs = FXCollections.observableArrayList();
	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(60), ev -> {
			
			try {
				obsListTableFilaEquipamentos = controller.getByLaboratorio(true);
				if(obsListTableFilaEquipamentos.size() > oldObs.size() ) {
					Alerts.showAlert("Updatede Lista", "", "Equipamento da empressa " + obsListTableFilaEquipamentos.get(obsListTableFilaEquipamentos.size()-1).getEmpressaName() + " foi adcionado a lista de equipamentos ", AlertType.INFORMATION);
				
				}
				if(obsListTableFilaEquipamentos.size() < oldObs.size() ) {
					oldObs = obsListTableFilaEquipamentos;
				}
				for(int i =0; i< oldObs.size(); i++) {
					if( obsListTableFilaEquipamentos.get(i).getStatus() != oldObs.get(i).getStatus() ) {
						Alerts.showAlert("Updatede Lista", "", "Equipamento da empressa " + obsListTableFilaEquipamentos.get(obsListTableFilaEquipamentos.size()-1).getEmpressaName() + " teve o status alterado ", AlertType.ERROR);
					}
				}
				oldObs = obsListTableFilaEquipamentos;
			} catch (DbException | SQLException e) {
				Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e.getMessage(), AlertType.ERROR);
			}
			
			
		}));

	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();

	}
	
	
	private void showAlerts() {
		Alerts.showAlert("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION);
	}  
	

}