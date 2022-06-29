package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
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
import com.hrodriguesdev.gui.controller.insert.EquipamentoInsert;
import com.hrodriguesdev.gui.controller.insert.OrcamentoInsert;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

//@Component
public class MainViewController implements Initializable{
		
//	private GeneratorPDF generator = new GeneratorPDF();	
	public static OrcamentoController controller = new OrcamentoController();
	public static ColetorController coletorController = new ColetorController();
	public static EquipamentoController equipamentoController = new EquipamentoController();
	public static EmpressaController empressaController = new EmpressaController();
	private Timeline timeline;
	private Boolean dbConection;
	
	public static Equipamento equipamento;
	public static Equipamento equipamentoEdit;
	public static Orcamento orcamentoEdit;
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
	private TableColumn<Equipamento, String> ultimaCal;
	@FXML
	private TableColumn<Equipamento, String> relatorio;
	@FXML
	private TableView<Equipamento> tableFilaEquipamentos;
    public static ObservableList<Equipamento> obsListTableFilaEquipamentos = FXCollections.observableArrayList();
    
    @FXML
	private TableColumn<Anotations, String> descricao;
	@FXML
	private TableColumn<Anotations, String> referencia;
	@FXML
	private TableView<Anotations> tabelaAnotacoes;
    public static ObservableList<Anotations> obsListTableAnotacoes = FXCollections.observableArrayList();
    
    @FXML
    private void updatedEquipamento(KeyEvent e) throws IOException {
    	if(e.getCode().toString() == "F2" ) {
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			equipamentoEdit = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    			NewView.getNewView("Edit Equipamento", "entradaEquipamento", new EditEquipamentoViewController() );

    		}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			equipamentoEdit = tableFindEquipamentos.getSelectionModel().getSelectedItem();
    			NewView.getNewView("Edit Equipamento", "entradaEquipamento", new EditEquipamentoViewController() );

    		}
    		
    		    try {
					obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
				} catch (DbException | SQLException e1) {
					
					e1.printStackTrace();
				}
    			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
    			oldObs = obsListTableFilaEquipamentos;
    			tableFilaEquipamentos.refresh();   		
    		
    		
    	}else if(e.getCode().toString() == "F3" ) {
    		
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			try {
    				equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
					orcamentoEdit = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
					NewView.getNewView("Edit Orcamento", "orcamento", new EditOrcamentoViewController() );
				
    			} catch (SQLException e1) {
					e1.printStackTrace();
				}
    			

    		}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			try {
    				equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
					orcamentoEdit = controller.findById( tableFindEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
					NewView.getNewView("Edit Orcamento", "orcamento", new EditOrcamentoViewController() );
				
    			} catch (SQLException e1) {
					e1.printStackTrace();
				}

    		}
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
			oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.refresh();   		
		
    		
    	}else if(e.getCode().toString() == "F12" ) {
    		
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
				equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
				equipamento.setLaboratorio(true);
				equipamentoController.updatede(equipamento.getId(), equipamento.getStatus(), equipamento);			

    		}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
				equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
				equipamento.setLaboratorio(true);
				equipamentoController.updatede(equipamento.getId(), equipamento.getStatus(), equipamento);	

    		}
    		try {
				obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
			} catch (DbException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
			oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.refresh();   		
		
    		
    	}
    	else if(e.getCode().toString() == "DELETE" ) {    		
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			Equipamento equipament = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    			if(equipament.getOrcamento_id() == null || equipament.getOrcamento_id() == 0l) {
    				if (equipamentoController.delete( equipament.getId() ) ) {
    	    		    try {
    						obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
    					} catch (DbException | SQLException e1) {
    						
    						e1.printStackTrace();
    					}
    	    			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
    	    			oldObs = obsListTableFilaEquipamentos;
    	    			tableFilaEquipamentos.refresh();  
    				}else {
    					System.out.println("Nao deletado");
    				}
    					
    			}else 
    				System.out.println("orcamento id " + equipament.getOrcamento_id() );
    		}
    		
    	}else {
    		System.out.println(e.getCode().toString() );
    	}
    
    	
    }
   
    
    @FXML
    private void addEquipamento(ActionEvent e) throws IOException {
    	NewView.getNewView("Entrada Equipamento", "entradaEquipamento", new EquipamentoInsert() );
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
		oldObs = obsListTableFilaEquipamentos;
		tableFilaEquipamentos.refresh();    	
    }
    
    @FXML
    private void updateStatus(ActionEvent e) throws IOException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			NewView.getNewView("Alterar Status","status", new StatusViewController() );
			try {
				obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
				dbConection = true;
			} catch (DbException | SQLException e1) {
				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
				dbConection = false;
			}
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
			oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.refresh();
		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
	   	
    }

	@FXML
    private void addOrcamento(ActionEvent e) throws IOException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {			
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id()  == null || tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() == 0) 
			{
				NewView.getNewView("Entrada Equipamento", "orcamento", new OrcamentoInsert() );
				try {
					obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
					dbConection = true;
				} catch (DbException | SQLException e2) {
					showAlerts("DB exception ", "Erro na comunicação com banco de dados", e2.getMessage(), AlertType.ERROR );
					dbConection = false;
				}
				tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
				oldObs = obsListTableFilaEquipamentos;
				tableFilaEquipamentos.refresh();
			}
			else
			{
				showAlerts("Orcamento ", "", "Ja consta orcamento para o equipamento selecionado ", AlertType.INFORMATION );
			}

		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
    	
    }

    
    @FXML
    private void openOrcamento(ActionEvent e) throws IOException {
    	if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
    		equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    		try {
				orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
				dbConection = true;
			} catch (SQLException e1) {
				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
				dbConection = false;
				return;
			}
    		if(orcamento != null) {
    			NewView.getNewView("Entrada Equipamento", "orcamentoView", new OrcamentoViewController() );
    		}else
    			Alerts.showAlert("Orcamento" , "Status Orcamento", "Não consta orçamento para este equipamento" , AlertType.INFORMATION);
    		
    	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
	        
		try {
			obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
    		oldObs = obsListTableFilaEquipamentos;
    		dbConection = true;
    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
		} catch (DbException | SQLException e1) {
		
			e1.printStackTrace();
		}
    }
          
 
     @FXML
    private void addColeta(ActionEvent e) throws IOException {
    	 if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
     		equipamentoEdit = tableFilaEquipamentos.getSelectionModel().getSelectedItem();     		
     		
     		if(equipamentoEdit.getStatus() != 1 ) {
     			if( equipamentoEdit.getColetor_id() == null || equipamentoEdit.getColetor_id() == 0) {
     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new SaidaEquipamentoViewController() );
	     			try {
						obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
			    		oldObs = obsListTableFilaEquipamentos;
			    		dbConection = true;
			    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
					} catch (DbException | SQLException e1) {
					
						e1.printStackTrace();
					}
     			} else{
     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new OpenSaidaEquipamentoViewController());
	     			try {
						obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
			    		oldObs = obsListTableFilaEquipamentos;
			    		dbConection = true;
			    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
					} catch (DbException | SQLException e1) {
					
						e1.printStackTrace();
					}
     			}
     		}else
     			Alerts.showAlert("Saida de Equipamento" , "Equipamento nao pode ser liberado por:", equipamentoEdit.getStatusStr() , AlertType.INFORMATION);
     		
     	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
    }
	
	
	
	//------------------------------------- Página de Busca --------------------------------------------------------
	
	
    @FXML
    private TextField textNsEquip, textPatEquip;
    @FXML
    private ComboBox<String> textEmpresa;
    
    private static ObservableList<String> obsString = FXCollections.observableArrayList();
    private FilteredList<String> filteredList;
    
	@FXML
    private void buscar(ActionEvent e) throws IOException {
		
		Equipamento equipamento = new Equipamento();
		if(textEmpresa.getValue()!= null)
			if( !textEmpresa.getValue().isEmpty() ) {
	    		equipamento.setEmpressaName(textEmpresa.getValue());
	    	}
    	if( !textNsEquip.getText().isEmpty() ) {
    		equipamento.setNs(textNsEquip.getText());
    	}    	  
    	if( !textPatEquip.getText().isEmpty() ) {
    		equipamento.setPat(textPatEquip.getText());
    	}    	
    	
    	ObservableList<Equipamento> obs = equipamentoController.findAll(equipamento);
    	if(obs.size()>0 ) {
    		obsListTableFindEquipamentos = obs;    	
;
    	}else { 		
    		obsListTableFindEquipamentos = equipamentoController.findAll(); 		
    	}
    	tableFindEquipamentos.setItems(obsListTableFindEquipamentos);
    }
    
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
	@FXML
	private TableColumn<Equipamento, String> dataChegadaFind;
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
		if( dbConection ) {
			obsString = empressaController.findAll();
			
		}
		strartTable();
		filteredList = new FilteredList<>(obsString); 		
		textEmpresa.getEditor().textProperty().addListener(new InputFilter<String>( textEmpresa, filteredList ) );	
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
		
		if( dbConection ) {
			try {
	    		obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
	    		oldObs = obsListTableFilaEquipamentos;
	
		    }catch(DbException | SQLException e) {
		    	dbConection = false;
		    } 
		}

		
	    tableFilaEquipamentos.setEditable(false);	 
	    dataChegada.setSortType(TableColumn.SortType.DESCENDING);
	    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    status.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("statusStr"));
		dataChegada.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		relatorio.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("relatorio"));
		ultimaCal.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ultimaCalib"));
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
		
		
		tableFindEquipamentos.setEditable(false);	 
	    dataChegada.setSortType(TableColumn.SortType.DESCENDING);
	    
	    empressaFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    nsFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
	    patFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
	    dataChegadaFind.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));	    
	    dataSaidaFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("dataSaida"));	
		
	    tableFindEquipamentos.setItems(obsListTableFindEquipamentos);	
		
	}
	
	private ObservableList<Equipamento> oldObs = FXCollections.observableArrayList();
	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(60), ev -> {
			obsString = empressaController.findAll();
			filteredList = new FilteredList<>(obsString); 
			if(dbConection) {
				try {
					obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
					if(obsListTableFilaEquipamentos.size() > oldObs.size() ) {
						showAlerts("Updatede Lista", "", "Equipamento da empressa " + obsListTableFilaEquipamentos.get(obsListTableFilaEquipamentos.size()-1).getEmpressaName() + " foi adcionado a lista de equipamentos ", AlertType.INFORMATION);
						oldObs = obsListTableFilaEquipamentos;
						return;
					}
					if(obsListTableFilaEquipamentos.size() < oldObs.size() ) {
						oldObs = obsListTableFilaEquipamentos;
						return;
					}
					for(int i =0; i< oldObs.size(); i++) {
						if( obsListTableFilaEquipamentos.get(i).getStatus() != oldObs.get(i).getStatus() ) {
							showAlerts("Updatede Lista", "", "Equipamento da empressa " + obsListTableFilaEquipamentos.get(obsListTableFilaEquipamentos.size()-1).getEmpressaName() + " teve o status alterado ", AlertType.ERROR);
						}
					}
					oldObs = obsListTableFilaEquipamentos;
				} catch (DbException | SQLException e) {
					showAlerts("begin Timer ", "", e.getMessage(), AlertType.INFORMATION );
				}			
			}else {
		    	showAlerts("DB exception ", "","Erro na comunicação com banco de dados, reiniciar App", AlertType.ERROR );
			}
		}));

	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();

	}
	
	
	private void showAlerts(String title, String mensage, String error, AlertType alertType) {
		Alerts.showAlert( title , mensage, error , alertType);
	}
    	

}