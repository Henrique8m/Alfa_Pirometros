package com.hrodriguesdev.gui.controller.view.main;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.dao.repository.CertificadoRepository;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.InputFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CertificadoPaginaController extends EmpresaViewController{
	
	private CertificadoRepository repositoryCertificado = new CertificadoRepository();
	private Equipamento equipamento;
	private Alert alert;
	
	@FXML
	private ComboBox<String> textEmpresaCertificado;
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	private static ObservableList<String> obsString = FXCollections.observableArrayList();	
	
	@FXML
	private TextField textNsEquipCertificado, textPatEquipCertificado;
	
	@FXML
	private TextField nomeEmpressaClickCertificado, nsClickCertificado, patClickCertificado, modeloClickCertificado;
	
	@FXML
	private TableView<Equipamento> tableEquipamentosCertificados;
	private ObservableList<Equipamento> osbListEquipamento = FXCollections.observableArrayList();
	
	
	@FXML
	private TableColumn<Equipamento, String> empressaCertificado;
	
	@FXML
	private TableColumn<Equipamento, String> nsCertificado;
	
	@FXML
	private TableColumn<Equipamento, String> patCertificado;
	
	@FXML
	private TableColumn<Equipamento, String> modeloCertificado;
	
	@FXML
	private TableView<Certificado> tableCertificado;
	private ObservableList<Certificado> osbListCertificado = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Certificado, Date> dataCalibracao;
	
	@FXML
	private TableColumn<Certificado, Integer> numeroCertificado;
	
	@FXML
	private TextField numeroCertificadoNovo, dataCalibracaoNovo;
	
	@FXML
	private Tab tabCertificado;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */
		
		tabCertificado.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				addListener();
				if(comboBoxBusca != "") {
					if(comboBoxBusca != "") {
						textEmpresaCertificado.setValue(comboBoxBusca);
					buscar();
					}
				}
			}else
				removeListener();
		});
		strartTableCertificado();
	}
	
	private void addListener() {
		obsString =  MainViewController.empressaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( textEmpresaCertificado, filteredList );
		textEmpresaCertificado.getEditor().textProperty().addListener(inputFilter);	
		
	}
	
	private void removeListener() {
		textEmpresaCertificado.getEditor().textProperty().removeListener(inputFilter);
		textEmpresaCertificado.setValue("");
	}
	
	protected void enter(KeyEvent event) {
		if(event.getTarget() == textEmpresaCertificado) {
			buscar();
		}
	}
	
	private void strartTableCertificado() {		
		tableEquipamentosCertificados.setEditable(false);	 
		
		empressaCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empressaName"));		
		modeloCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		nsCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		patCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		
		tableEquipamentosCertificados.setItems(osbListEquipamento);		
		
		tableCertificado.setEditable(false);		
		
		numeroCertificado.setCellValueFactory(new PropertyValueFactory<Certificado, Integer>("numero"));
		dataCalibracao.setCellValueFactory(new PropertyValueFactory<>("date_cal"));
		dataCalibracao.setCellFactory( cell -> {
            return new TableCell<Certificado, Date>() {
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
		tableCertificado.setItems(osbListCertificado);
		
	}
	
	private void tableCertUpdate() {
		List<Certificado> certificado = repositoryCertificado.findAllByEquipamento(equipamento.getId());
		ObservableList<Certificado> obs = FXCollections.observableArrayList();
		obs.addAll(certificado);
		tableCertificado.setItems(obs);
		tableCertificado.refresh();
		
	}
	
	@FXML
	protected void clickCertificado(MouseEvent event) throws SQLException {
		if(tableEquipamentosCertificados.getSelectionModel().getSelectedItem() != null) {
			equipamento = tableEquipamentosCertificados.getSelectionModel().getSelectedItem();
			
			tableCertUpdate();
			
			nomeEmpressaClickCertificado.setText(equipamento.getEmpressaName());
			if( equipamento.getNs()!= null ) nsClickCertificado.setText(equipamento.getNs() );
			if( equipamento.getPat()!= null ) patClickCertificado.setText(equipamento.getPat() );
			if( equipamento.getModelo()!= null ) modeloClickCertificado.setText( equipamento.getModelo() );
			
		}
	}
	
    private void buscar(){		
		Equipamento equipamento = new Equipamento();
		if(textEmpresaCertificado.getValue()!= null)
			if( !textEmpresaCertificado.getValue().isEmpty() ) {
	    		equipamento.setEmpressaName(textEmpresaCertificado.getValue());
	    	}
    	if( !textNsEquipCertificado.getText().isEmpty() ) {
    		equipamento.setNs(textNsEquipCertificado.getText());
    	}    	  
    	if( !textPatEquipCertificado.getText().isEmpty() ) {
    		equipamento.setPat(textPatEquipCertificado.getText());
    	}    	
    	
    	ObservableList<Equipamento> obs = equipamentoController.findAll(equipamento);
    	if(obs.size()>0 ) {
    		osbListEquipamento = obs;    	
    		comboBoxBusca = textEmpresaCertificado.getValue();
    	}
    	else {
    		osbListEquipamento = equipamentoController.findAll(); 		
    		comboBoxBusca = "";
    	}
    	
    	tableEquipamentosCertificados.setItems(osbListEquipamento);
    	
		removeListener();
		addListener();
    }
    
    @FXML
    private void salvarCertificado(ActionEvent event) {
    	if(equipamento!=null)
    		if(!dataCalibracaoNovo.getText().isBlank() && !numeroCertificadoNovo.getText().isBlank() ) {
    			if(dataCalibracaoNovo.getText().length() < 10)
    				return;
    			Long certificado_id = repositoryCertificado.add(
	    				new Certificado(
	    				equipamento.getId(), 
	    				Geral.dateParceString( dataCalibracaoNovo.getText() ), 
	    				Integer.parseInt( numeroCertificadoNovo.getText()) ) 
	    				);
			
    			if(equipamento.getUltimaCalibDate() == null || equipamento.getUltimaCalibDate().before(Geral.dateParceString( dataCalibracaoNovo.getText() )) )
	    			if(certificado_id != null)
	    				repositoryCertificado.updateEquipamento(
	    						certificado_id,
	    						equipamento.getId(),
	    						Geral.dateParceString( dataCalibracaoNovo.getText() ) 
	    						);
		    	
    			tableCertUpdate();
    		}
    }
    
//    Formatar em tempo real a data inserida
    
    @FXML
    private void formatarData(KeyEvent event) {
    	if(!dataCalibracaoNovo.getText().isBlank()) {
    		dataCalibracaoNovo.setText( Format.replaceData(dataCalibracaoNovo.getText()) );
    		dataCalibracaoNovo.end();
    	}
    }
    
	/*
	 * Logicaa para o delete de Certificado, tecla alt tem que estar apertada!!
	 */
    
    @FXML
    private void delete(KeyEvent event) {
    	if(!tableCertificado.getSelectionModel().isEmpty() && event.isAltDown() && event.getCode().toString() == "DELETE")
    		showAlert("Deletar Certificado", "Deletara certificado de numero " + tableCertificado.getSelectionModel().getSelectedItem().getNumero()+ "?", "", AlertType.CONFIRMATION);    		
    }
    
//    Confirmar deletar
    
	private void showAlert(String title, String header, String content, AlertType type) {
		if(alert!=null) 
			if(alert.isShowing())
				alert.close();
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.setOnCloseRequest( new EventHandler<DialogEvent>() {
			public void handle(DialogEvent e) { 
				if(alert.getResult().getButtonData().toString() == "OK_DONE") {
					if(deletarCertificado(tableCertificado.getSelectionModel().getSelectedItem() ) )
						Alerts.showAlert("Deletar Certificado", "Deletado com sucesso","", AlertType.CONFIRMATION);
					tableCertUpdate();
				}
			}	});
		alert.show();		

	}
	
	private boolean deletarCertificado(Certificado certificado) {		
		return repositoryCertificado.delete(certificado);
	}
	
}
