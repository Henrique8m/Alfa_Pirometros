package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.NewView;

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
import javafx.scene.text.Text;

//@Component
public class MainViewController implements Initializable{
		
	private Motorista motorista;
	private GeneratorPDF generator = new GeneratorPDF();	

	private Boolean descarregando = false ;	
	private Double totalPeso = 0d;
	
	private List<Pesagem> listPDF = new ArrayList<>();
	private Motorista motoristaPDF;
	@FXML
	private ImageView logoYgg, logo, save, seta, seta1, adcionar, home, buscar, buscar1, pdf;
	//Tabela fila de motoristas

	@FXML
	private TableColumn<Motorista, String> nomeMotorista;
	@FXML
	private TableColumn<Motorista, String> placa;
	@FXML
	private TableColumn<Motorista, String> dataChegada;
	@FXML
	private TableColumn<Motorista, String> horaChegada;
	@FXML
	private TableView<Motorista> tableMotorista;
    public static ObservableList<Motorista> obsListTableMotorista = FXCollections.observableArrayList();
    
	@FXML
	private Text pesoDescarregando, caixotes;
	@FXML
	public Text comunicacao;
	
    //Tabela pesagem de carvão
    @FXML
	private TextField name, placa2;
	@FXML
	private TableColumn<Pesagem, Integer> numeroCaixote;
	@FXML
	private TableColumn<Pesagem, Double> peso;
	@FXML
	private TableColumn<Pesagem, String> dataHora;
	@FXML
	private TableColumn<Pesagem, String> responsavel;
    @FXML
	private TableView<Pesagem> tablePesoCarvao;
    public static ObservableList<Pesagem> obsListTableCarvao = FXCollections.observableArrayList();
    
    @FXML
    private void addNewMotorista(ActionEvent e) throws IOException {
    	NewView.getNewViewModal("Cadastro de Motorista", (Pane) NewView.loadFXML("addMotoristaView", AlfaPirometrosApplication.viewaddMotorista), LoadViewController.getStage());
    	
    }
    
    @FXML
    private void iniciarPesagem(ActionEvent e) throws IOException {
		

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
    	
    }
    
    @FXML
    private void desfazer(ActionEvent e) throws IOException {
    	if(descarregando) {
    		descarregando=false;
        	name.setText("");    		
        	placa2.setText("");
    		obsListTableMotorista.add(motorista);	    
    		motorista=null;
	    	tableMotorista.refresh();	  
	    	
    	}else
    		System.out.println("Nada Descarregando");
    	
    }    
    
    @FXML
    private void salvarPesagem(ActionEvent e) throws IOException {
    	
    }    
 
	
	
	
	//------------------------------------- Página de Busca --------------------------------------------------------
	
	@FXML
	private TableColumn<Motorista, String> nomeMotorista1;
	@FXML
	private TableColumn<Motorista, String> placa1;
	@FXML
	private TableColumn<Motorista, String> dataChegada1;
	@FXML
	private TableColumn<Motorista, String> horaChegada1;
	@FXML
	private TableView<Motorista> tableMotoristaFind;
    public static ObservableList<Motorista> obsListTableViewMotoristaFind = FXCollections.observableArrayList();;
	
    
	@FXML
	private TableColumn<Pesagem, Integer> numeroCaixote1;
	@FXML
	private TableColumn<Pesagem, Double> peso1;
	@FXML
	private TableColumn<Pesagem, String> dataHora1;
	@FXML
	private TableColumn<Pesagem, String> responsavel1;
    @FXML
	private TableView<Pesagem> tablePesoCarvao1;
    public static ObservableList<Pesagem> obsListTableCarvao1 = FXCollections.observableArrayList();
    
    
    @FXML
    private TextField name1, data1, placa3;

    
	@FXML
    private void buscar(ActionEvent e) throws IOException {
    	
    }
    
	@FXML
	public void format(KeyEvent event) {
		
	}
	
	@FXML
	public void click(MouseEvent event) {
		
	}	
	
	@FXML
    private void gerarPDF(ActionEvent e) throws IOException {
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
    	
    }
	
	
//----------------------------------------------------------------------------------------------------------------------
	
//	private List<String> avaliablePorts;
//	private String defautPort = "COM4";
	private Date date;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		strartTable();

		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/Yggdrasilicon.jpg").toString() );
		logoYgg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/metalNobre.jpg").toString() );
		logo.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		save.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-fechar-painel.png").toString() );
		seta.setImage(image);
		seta1.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		adcionar.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-bandeira-de-chegada.png").toString() );
		home.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscar.setImage(image);
		buscar1.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
		 
		 
}	 	
	
	
	public void strartTable() {	
		//Table fila de Motorista descarregando
		obsListTableMotorista = FXCollections.observableArrayList();	
		tableMotorista.setEditable(false);	 
		nomeMotorista.setSortType(TableColumn.SortType.DESCENDING);
		nomeMotorista.setCellValueFactory(new PropertyValueFactory<Motorista, String>("name"));
		placa.setCellValueFactory(new PropertyValueFactory<Motorista, String>("placa"));
		dataChegada.setCellValueFactory( new PropertyValueFactory<Motorista, String>("data"));
		horaChegada.setCellValueFactory(new PropertyValueFactory<Motorista, String>("hora"));
		tableMotorista.setItems(obsListTableMotorista);
		
		//Table pesagem corrente
		obsListTableCarvao =FXCollections.observableArrayList();
		//Alimentar as informações de Peso corrente, e numero de Caixotes
		for(Pesagem p: obsListTableCarvao) {
			totalPeso += p.getPeso();
		}
		caixotes.setText(Integer.toString( obsListTableCarvao.size() ) );
		pesoDescarregando.setText( Double.toString(totalPeso) );
		caixotes.setText( Integer.toString(obsListTableCarvao.size()) );
		tablePesoCarvao.setEditable(false);
	 	numeroCaixote.setSortType(TableColumn.SortType.DESCENDING);
		numeroCaixote.setCellValueFactory(new PropertyValueFactory<Pesagem, Integer>("numeroCaixote"));
		peso.setCellValueFactory(new PropertyValueFactory<Pesagem, Double>("peso"));
		dataHora.setCellValueFactory( new PropertyValueFactory<Pesagem, String>("dataHora"));
		responsavel.setCellValueFactory(new PropertyValueFactory<Pesagem, String>("responsavel"));
		tablePesoCarvao.setItems(obsListTableCarvao); 		
		
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
		
	}

	public void addPesagem(Double valueStabilized) { 
		 Format.formatData.format(date);
		 Format.formataTimeString.format(date);
	}
}