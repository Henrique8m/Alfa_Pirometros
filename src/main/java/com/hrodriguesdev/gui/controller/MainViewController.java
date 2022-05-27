package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.Controller;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.utilitary.NewView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

//@Component
public class MainViewController implements Initializable{
		
//	private GeneratorPDF generator = new GeneratorPDF();	
	private Controller controller = new Controller();
	private Timeline timeline;
	
	
	/*
	private Boolean descarregando = false ;	
	private Double totalPeso = 0d;
	
	private List<Pesagem> listPDF = new ArrayList<>();
	private Motorista motoristaPDF;
	
	*/
	
	
	@FXML
	private ImageView logoYgg, cadastrar, refresh, abrir, cadastrar2, inserirColeta, home, buscar, buscar1, pdf;
	//Tabela fila de motoristas

	@FXML
	private TableColumn<Equipamento, String> empressa;
	@FXML
	private TableColumn<Equipamento, Integer> status;
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
    private void addEquipamento(ActionEvent e) throws IOException {
    	NewView.getNewViewModal("Entrada Equipamento", (Pane) NewView.loadFXML("entradaEquipamento", AlfaPirometrosApplication.viewaddChegadaEquipamento), LoadViewController.getStage());
    	
    }
    
    @FXML
    private void addOrcamento(ActionEvent e) throws IOException {
    	NewView.getNewViewModal("Entrada Equipamento", (Pane) NewView.loadFXML("orcamento", AlfaPirometrosApplication.viewaddChegadaEquipamento), LoadViewController.getStage());
    	
    }
    
    @FXML
    private void addColeta(ActionEvent e) throws IOException {
    
    
    }
    
    @FXML
    private void updateStatus(ActionEvent e) throws IOException {
    	
    	/*    	  
    	 * 
    	 *   if(descarregando) {
    		descarregando=false;
        	name.setText("");    		
        	placa2.setText("");
    		obsListTableMotorista.add(motorista);	    
    		motorista=null;
	    	tableMotorista.refresh();	
    	    	if(!descarregando) {    		
	    	if(!tableMotorista.getSelectionModel().isEmpty()) {
	    		descarregando=true;
	    		name.setText(tableMotorista.getSelectionModel().getSelectedItem().getName()); 
	    		placa2.setText(tableMotorista.getSelectionModel().getSelectedItem().getPlaca()); 
	    		motorista = new Motorista();
	    		motorista = tableMotorista.getSelectionModel().getSelectedItem();	    		
	    		obsListTableMotorista.remove(tableMotorista.getSelectionModel().getSelectedIndex());	    		
	    		tableMotorista.refresh();
	    		
	    	}else
	    		System.out.println("Nada Selecionado");
	    		    	
    	}else
    		System.out.println("Temos registro corrente");
    		
    		
    		*/
    }    
    @FXML
    private void openOrcamento(ActionEvent e) throws IOException {
  
	        	
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
		
		/*
		if(motoristaPDF != null) {
			if(!generator.newDocument(motoristaPDF, listPDF) ) {
				Alerts.showAlert("Documento ", "Não foi possiver criar o documento ",
						"", AlertType.ERROR);
			}else {
				String caminho = "E:\\Javas\\Java";
				Alerts.showAlert("Documento ", "Relatorio criado com sucesso!! ",
						"Diretorio = " + caminho , AlertType.INFORMATION);
			}
		}    	
		*/
    	
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
	
//	private List<String> avaliablePorts;
//	private String defautPort = "COM4";
//	private Date date;

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
	    obsListTableFilaEquipamentos = controller.getByLaboratorio(true);
	    tableFilaEquipamentos.setEditable(false);	 
	    dataChegada.setSortType(TableColumn.SortType.DESCENDING);
	    
	    empressa.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));
	    status.setCellValueFactory(new PropertyValueFactory<Equipamento, Integer>("status"));
		dataChegada.setCellValueFactory( new PropertyValueFactory<Equipamento, String>("dataChegada"));
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
		
		
		/*
		//Table pesagem corrente
		obsListTableCarvao =FXCollections.observableArrayList();
		//Alimentar as informações de Peso corrente, e numero de Caixotes
		for(Pesagem p: obsListTableCarvao) {
			totalPeso += p.getPeso();
		}
		caixotes.setText(Integer.toString( obsListTableCarvao.size() ) );
		pesoDescarregando.setText( Double.toString(totalPeso) );
		caixotes.setText( Integer.toString(obsListTableCarvao.size()) );
		tableFilaEquipamentos.setEditable(false);
	 	numeroCaixote.setSortType(TableColumn.SortType.DESCENDING);
		numeroCaixote.setCellValueFactory(new PropertyValueFactory<Pesagem, Integer>("numeroCaixote"));
		peso.setCellValueFactory(new PropertyValueFactory<Pesagem, Double>("peso"));
		dataHora.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("dataHora"));
		responsavel.setCellValueFactory(new PropertyValueFactory<Pesagem, String>("responsavel"));
		tableFilaEquipamentos.setItems(obsListTableCarvao); 		
		
		//Table find Motorista
		tableMotoristaFind.setEditable(false);		 
		nomeMotorista1.setSortType(TableColumn.SortType.DESCENDING);
		nomeMotorista1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("name"));
		placa1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("placa"));
		dataChegada1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("data"));
		horaChegada1.setCellValueFactory(new PropertyValueFactory<Motorista, String>("hora"));
		tableMotoristaFind.setItems(obsListTableViewMotoristaFind);		
		
		//Table de pesagem relacionado ao Motorista
		tablePesoCarvao1.setEditable(false);		 
		numeroCaixote1.setSortType(TableColumn.SortType.DESCENDING);
		numeroCaixote1.setCellValueFactory(new PropertyValueFactory<Pesagem, Integer>("numeroCaixote"));
		peso1.setCellValueFactory(new PropertyValueFactory<Pesagem, Double>("peso"));
		dataHora1.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("dataHora"));
		responsavel1.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("responsavel"));		
		tablePesoCarvao1.setItems(obsListTableCarvao1);
		*/
		
		
		
		
		
	}
	private void beginTimer() {
		
		timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(60), ev -> {
			
			obsListTableFilaEquipamentos = controller.getByLaboratorio(true);
			
			
		}));

	timeline.setCycleCount(10);
	timeline.play();

	}
	

}