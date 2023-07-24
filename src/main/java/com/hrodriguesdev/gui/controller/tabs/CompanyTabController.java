package com.hrodriguesdev.gui.controller.tabs;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CompanyTabController implements Initializable{
	
	@FXML
	private ComboBox<String> findEmpresaComboBox;
	
    private ObservableList<String> obsString = FXCollections.observableArrayList();
    private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@FXML
	private ImageView adcionarImg, buscarImg,  clearImg, empresaImg, salvarImg;
	
	@FXML
	private Tab tabEmpresa;	
	
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
	
	//@Autowired
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;

	private Alert alert;
	private Boolean nameUpdate = false;				
	private Empresa empresa;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */		
		tabEmpresa.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				addListener();
				if(MainTabController.comboBoxBusca != "") {
					findEmpresaComboBox.setValue(MainTabController.comboBoxBusca);
					buscarEmpresa(new ActionEvent());
				}
			}else
				removeListener();
		});
		
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() throws InterruptedException {
		    	startTable();
		    	imageInit();
		        return null;
		    }
		};
		new Thread(task).start();	
		
		
	}
	
	/*
	 * Quando precionado a tecla enter dentro do comboBox faz a busca
	 * automaticamente
	 */
	@FXML
	private void enterBusca(KeyEvent event) {
		if(event.getCode().toString() == "ENTER") {					
			buscarEmpresa(new ActionEvent());
			removeListener();
			addListener();	
		}
	}
	
	@FXML
	private void buscarEmpresa(ActionEvent event) {	
		if(findEmpresaComboBox != null)					
			if(!findEmpresaComboBox.getValue().isEmpty()) {
				ObservableList<Empresa> obs = FXCollections.observableArrayList();
				Long id = empresaController.findEmpresaId(findEmpresaComboBox.getValue());			
				obs.add( empresaController.findEmpresa( id ));			
				obsEmpresa = obs;
				if(obs.size() >0 )
					MainTabController.comboBoxBusca = findEmpresaComboBox.getValue();
			}else {
	    		obsEmpresa = empresaController.findAllEmpresa();
	    		tableEmpresa.setItems(obsEmpresa);
	    		MainTabController.comboBoxBusca = "";
	    	}
    	
    	tableEmpresa.setItems(obsEmpresa);
    	removeListener();
		addListener();
	}

    @FXML
    private void deleteEmpresa(KeyEvent event) {
    	if(!tableEmpresa.getSelectionModel().isEmpty() && event.isAltDown() && event.getCode().toString() == "DELETE")
    		showAlert("Deletar Empresa", "Deletar " + tableEmpresa.getSelectionModel().getSelectedItem().getName()+ "?", "", AlertType.CONFIRMATION);    		
    }
    
	/*
	 * Alimenta as linha de edição quando clicamos em empresa na lista de busca
	 * 
	 */
	@FXML
	public void clickEmpresa(MouseEvent event) throws SQLException {	
		if(tableEmpresa != null)
			if(!tableEmpresa.getSelectionModel().isEmpty()) {
				empresa = tableEmpresa.getSelectionModel().getSelectedItem(); 
				if(empresa == null)
					return;
				nomeEmpresaEdit.setText(empresa.getName());
				cidadeEmpresaEdit.setText(empresa.getCidade());
				estadoEmpresaEdit.setText(empresa.getEstado());
				enderecoEmpresaEdit.setText(empresa.getEndereco());
				cepEmpresaEdit.setText(empresa.getCep());
			}
			else empresa = null;
	}
	

	@FXML
	private void salvarEmpresa(ActionEvent event) {
		if(validacaoCampos()) {
			if(empresa != null) {
				empresa = updateCampoEmpresa(empresa);
				if(!empresaController.updateEmpresa(empresa, nameUpdate))	
					Alerts.showAlert("Atualização de empresa", "", "Erro ao atualizar", AlertType.ERROR);
				else Alerts.showAlert("Atualização de empresa", "", "Atualizado com sucesso, a atualização so acontece um campo por vez!", AlertType.INFORMATION);
				limp(new ActionEvent());
				buscarEmpresa(new ActionEvent());
				removeListener();
				addListener();
				nameUpdate = false;
			}
		}
		
	}
	
	private Empresa updateCampoEmpresa(Empresa empresa2) {
		if(!empresa.getName().equals(nomeEmpresaEdit.getText())) {
			empresa.setName(nomeEmpresaEdit.getText());
			nameUpdate = true;
			return empresa;
		}
		if(!empresa.getCidade().equals(cidadeEmpresaEdit.getText())) {
			empresa.setCidade(cidadeEmpresaEdit.getText());
			return empresa;
		}
		if(!empresa.getEstado().equals(estadoEmpresaEdit.getText())) {
			empresa.setEstado(estadoEmpresaEdit.getText());
			return empresa;
		}
		if(!empresa.getEndereco().equals(enderecoEmpresaEdit.getText())) {
			empresa.setEndereco(enderecoEmpresaEdit.getText());
			return empresa;
		}
		if(!empresa.getCep().equals(cepEmpresaEdit.getText())) {
			empresa.setCep(cepEmpresaEdit.getText());
			return empresa;
		}
		return empresa;
	}

	@FXML
	private void adcionarEmpresa(ActionEvent event) {
		if(empresaController.exist(nomeEmpresaEdit.getText())) {
			Alerts.showAlert("Falha", "Empresa já existe", "", AlertType.INFORMATION);
			return;
		}
		if(validacaoCampos()) {			
			Empresa empresa = new Empresa();
			empresa.setName(nomeEmpresaEdit.getText());
			empresa.setCidade(cidadeEmpresaEdit.getText());
			empresa.setEstado(estadoEmpresaEdit.getText());
			empresa.setEndereco(enderecoEmpresaEdit.getText());
			empresa.setCep(cepEmpresaEdit.getText());
			empresaController.addEmpresa(empresa);
			findEmpresaComboBox.setValue(empresa.getName());
			buscarEmpresa(new ActionEvent());
			limp(new ActionEvent());
			
		}
		else Alerts.showAlert("Falha", "Todos os campos deven esta preenchidos", "", AlertType.INFORMATION);
		
	}
	
	@FXML
	private void limp(ActionEvent event){
		nomeEmpresaEdit.setText("");
		cidadeEmpresaEdit.setText("");
		estadoEmpresaEdit.setText("");
		enderecoEmpresaEdit.setText("");
		cepEmpresaEdit.setText("");
		empresa = null;
	}

	@FXML
	private void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" ) {
			if(event.getTarget().equals(estadoEmpresaEdit)){
				 String input = estadoEmpresaEdit.getText().toUpperCase().replaceAll("[^A-Z]+", "");
				 if(input.length() > 2) {
					 StringBuilder stringBuilder = new StringBuilder(input);
					 input = stringBuilder.replace(input.length()-1, input.length(), "").toString();
				 }
				 estadoEmpresaEdit.setText(input);
				 estadoEmpresaEdit.end();	
				 
			}else if(event.getTarget().equals(cepEmpresaEdit)){
				cepEmpresaEdit.setText(Format.replaceCep( cepEmpresaEdit.getText() ) );
				cepEmpresaEdit.end();
				
			 }
		}
	}
	

	/*
	 * atualização da lista das empressas
	 */	
	private void tableEmprUpdate() {
		tableEmpresa.setItems(FXCollections.observableArrayList());
	}
		
	private boolean validacaoCampos() {
		if(!nomeEmpresaEdit.getText().isBlank() &&
				!cidadeEmpresaEdit.getText().isBlank() &&
				!estadoEmpresaEdit.getText().isBlank() &&
				!enderecoEmpresaEdit.getText().isBlank() &&
				!cepEmpresaEdit.getText().isBlank()) 
			return true;
		return false;
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
					limp(new ActionEvent());
				}
			}	});
		alert.show();		

	}
	
	private boolean deletarEmpresa(Empresa empresa) {		
		return empresaController.delete(empresa);
	}

	/*
	 * listener do comboBox, para que sesja alimentada e filtrada em tempo real, de
	 * acordo com a digitação
	 */	
	private void addListener() {
			obsString = empresaController.findAllObs();
			filteredList = new FilteredList<>(obsString);  
			inputFilter = new InputFilter<String>( findEmpresaComboBox, filteredList );
			findEmpresaComboBox.getEditor().textProperty().addListener(inputFilter);	
		
	}	
	
	private void removeListener() {
		findEmpresaComboBox.getEditor().textProperty().removeListener(inputFilter);
		findEmpresaComboBox.setValue("");
	}
	
	private void imageInit() {		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		adcionarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-vassoura.png").toString() );
		clearImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-empresa.png").toString() );
		empresaImg.setImage(image);
		
	}
	
	/*
	 * configuração inicial da lista das empressas
	 */
	private void startTable() {
		cepEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa,String>("cep"));
		enderecoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("endereco"));
		estadoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("estado"));
		cidadeEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("cidade"));
		nomeEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("name"));
		tableEmpresa.setItems(obsEmpresa);
		
	}
	
}