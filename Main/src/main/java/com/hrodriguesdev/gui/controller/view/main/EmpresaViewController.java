package com.hrodriguesdev.gui.controller.view.main;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.dao.repository.EmpresaRepository;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.gui.alert.Alerts;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class EmpresaViewController extends MainViewController{
	
	private EmpresaController empresaController = new EmpresaController();
	private EmpresaRepository repository = new EmpresaRepository();
	private Alert alert;

	@FXML
	private ImageView salvarImg, buscarImg;
	
	@FXML
	private TableView<Empresa> tableEmpresa;
	private ObservableList<Empresa> obsEmpresa = FXCollections.observableArrayList(); 
	
	@FXML
	private TableColumn<Empresa, String> cepEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> enderecoEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> estadoEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> cidadeEmpresa;
	
	@FXML
	private TableColumn<Empresa, String> nomeEmpresa;
	
	@FXML
	private TextField cepEmpresaEdit, enderecoEmpresaEdit, estadoEmpresaEdit, cidadeEmpresaEdit, nomeEmpresaEdit;
	
	@FXML
	private ComboBox<String> findEmpresaComboBox;
	
    private static ObservableList<String> obsString = FXCollections.observableArrayList();
    private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;

	@FXML
	private Tab tabEmpresa;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */
		
		tabEmpresa.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				addListener();
				if(comboBoxBusca != "") {
					findEmpresaComboBox.setValue(comboBoxBusca);
					buscarEmpresa(new ActionEvent());
				}
			}else
				removeListener();
		});
		
		startTableEmpresa();		
	}

	/*
	 * configuração inicial da lista das empressas
	 */
	
	private void startTableEmpresa() {
		cepEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa,String>("cep"));
		enderecoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("endereco"));
		estadoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("estado"));
		cidadeEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("cidade"));
		nomeEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("name"));
		tableEmpresa.setItems(obsEmpresa);
		
	}
	
	/*
	 * atualização da lista das empressas
	 */
	
	private void tableEmprUpdate() {

	}


	@FXML
	private void salvarEmpresa(ActionEvent event) {
		System.out.println("Salvar");
	}
	
	
	
    @FXML
    private void deleteEmpresa(KeyEvent event) {
    	if(!tableEmpresa.getSelectionModel().isEmpty() && event.isAltDown() && event.getCode().toString() == "DELETE")
    		showAlert("Deletar Empresa", "Deletar " + tableEmpresa.getSelectionModel().getSelectedItem().getName()+ "?", "", AlertType.CONFIRMATION);    		
    }
    
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
					if(deletarEmpresa(tableEmpresa.getSelectionModel().getSelectedItem() ) )
						Alerts.showAlert("Deletar Empresa", "Deletado com sucesso","", AlertType.INFORMATION);
					else
						Alerts.showAlert("Error", "Por motivos desconhecidos, não foi possível completar sua solicitação", "", AlertType.ERROR);
					tableEmprUpdate();
				}
			}	});
		alert.show();		

	}
	
	private boolean deletarEmpresa(Empresa empresa) {		
		return repository.delete(empresa);
	}
	
	
//	listener do comboBox

	private void addListener() {
		if( dbConection ) {
			obsString = empressaController.findAll();
			filteredList = new FilteredList<>(obsString);  
			inputFilter = new InputFilter<String>( findEmpresaComboBox, filteredList );
			findEmpresaComboBox.getEditor().textProperty().addListener(inputFilter);	
		}

	}	
	
	private void removeListener() {
		findEmpresaComboBox.getEditor().textProperty().removeListener(inputFilter);
		findEmpresaComboBox.setValue("");
	}
	
	@FXML
	private void enterBusca(KeyEvent event) {
//		Busca pelo nome da empresa com enter
			if(event.getTarget() == findEmpresaComboBox) 
				if(event.getCode().toString() == "ENTER") {					
					buscarEmpresa(new ActionEvent());
					removeListener();
					addListener();	
			}
		}

	@FXML
	private void buscarEmpresa(ActionEvent event) {	
		if(!findEmpresaComboBox.getValue().isEmpty()) {
			ObservableList<Empresa> obs = FXCollections.observableArrayList();
			Long id = repository.findEmpresaId(findEmpresaComboBox.getValue());			
			obs.add( repository.findEmpressa( id ));			
			obsEmpresa = obs;
			if(obs.size() >0 )
				comboBoxBusca = findEmpresaComboBox.getValue();
		}else {
    		obsEmpresa = empresaController.findAllEmpresa();
    		tableEmpresa.setItems(obsEmpresa);
    		comboBoxBusca = "";
    	}
    	
    	
    	tableEmpresa.setItems(obsEmpresa);
    	removeListener();
		addListener();
	}
	
}
