package com.hrodriguesdev.gui.controller.view.insert;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.EquipamentoEntradaViewController;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.gui.controller.view.updatede.EquipamentoUpdatede;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class EquipamentoInsert implements Initializable{

	protected Date date;
	protected OrcamentoController controller = MainViewController.orcamentoController;
	protected EquipamentoController equipamentoController = MainViewController.equipamentoController;
	
	
	@FXML
	protected ImageView cancelarImg, salvarImg, logoYgg;
	@FXML
	private Button voltar;
	@FXML
	protected Text erro;
	@FXML
	protected TextField modeloTxt;
	@FXML
	protected TextField nsTxt;
	@FXML
	protected TextField patTxt;
	
	
	@FXML
	private ComboBox<String> nomeEmpressa;
	
	private static ObservableList<String> obsString = FXCollections.observableArrayList();	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@FXML
	private TableView<Equipamento> tableEquipamentos;
	@FXML
	private TableColumn<Equipamento, String> modelo;
	@FXML
	private TableColumn<Equipamento, String> ns;
	@FXML
	private TableColumn<Equipamento, String> pat;
	@FXML
	private TableColumn<Equipamento, Date> ultimaCal;
    private  ObservableList<Equipamento> obsListEquipamentos = FXCollections.observableArrayList();
    
		
	public EquipamentoInsert() {}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListener();
		try {
			startTable();
		} catch (DbException | SQLException e) {
			e.printStackTrace();
		}
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
	}
	
	@FXML
	private void keyEventCombobox(KeyEvent event){
		if(event.getTarget() == nomeEmpressa) {
			
			if(event.getCode().toString() == "ESCAPE") {
				removeListener();
				addListener();
				
			}
			if(event.getCode().toString() == "ENTER") {
				buscar();
				removeListener();
				addListener();
				
			}
			
		}				
		
	}
	
	private void buscar() {
		if(nomeEmpressa.getValue()== "") {
			error( "Campo nulo " ,"O campo nome da Empressa não pode ser nulo");
			erro.setVisible(true);
			erro.setText("Empressa nulo");
			return;
		}
		try {
			Long empressa_id = MainViewController.empressaController.isExist( nomeEmpressa.getValue() );
			if ( empressa_id == null ) {
				erro.setVisible(true);
				erro.setText("Empressa não existe");
				throw new DbException("Empresa não existe");
			}else {
				obsListEquipamentos = MainViewController.equipamentoController.findByIdEmpressa( empressa_id , false);
				tableEquipamentos.setItems(obsListEquipamentos);
				tableEquipamentos.refresh();
			}
			
		}catch(DbException | SQLException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
	}
	
	@FXML
	public void adcionar(ActionEvent event) {
		Long id;
		String empressaName;
		String modelo;
		String ns;
		String pat;
		Long empressa;
		
		
		if(nomeEmpressa.getValue()== "" ||  modeloTxt.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e Modelo, não pode ser nulo");
			return;
		}
		try {
			empressa = ( MainViewController.empressaController.isExist( nomeEmpressa.getValue() ) );
			if ( empressa == null ) {
				throw new DbException("Empresa não existe");
			}
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
		try {
			empressaName = nomeEmpressa.getValue();	
			modelo = modeloTxt.getText();  		
			ns = nsTxt.getText();
			pat = patTxt.getText();
			
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
			return;
		}
		try {
			id = equipamentoController.add(new Equipamento(empressaName, modelo, ns, pat, empressa));
			if(id != 0l) {
				NewView.fecharView();
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		try {
			NewView.addChildrenn((Node) NewView.loadFXML("entradaEquipamentoDois" , new EquipamentoEntradaViewController( nomeEmpressa.getValue() ) ));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
		erro.setText(mensagem);
	}

	@FXML
	public void sair(ActionEvent event) {
		removeListener();
		NewView.fecharView();
	}

	@FXML
	public void format(KeyEvent event) {

	}
	
	public void addListener() {
		obsString =  MainViewController.empressaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( nomeEmpressa, filteredList );
		nomeEmpressa.getEditor().textProperty().addListener(inputFilter);	
		
	}
	
	private void removeListener() {
		nomeEmpressa.getEditor().textProperty().removeListener(inputFilter);
	}
	
	private void startTable() throws DbException, SQLException {
		
		tableEquipamentos.setEditable(false); 
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
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
		
		tableEquipamentos.setItems(obsListEquipamentos);
		
	}
	
	@FXML
	private void keyTable(KeyEvent keyEvent) {
		if(keyEvent.getCode().toString() == "DELETE" ) {    		
    		if(tableEquipamentos.getSelectionModel().getSelectedItem() != null){
    			Equipamento equipament = tableEquipamentos.getSelectionModel().getSelectedItem();
    			
    			if( !controller.existOrcamento(equipament.getId()) ) {
    				if (equipamentoController.delete( equipament.getId() ) ) {
    					buscar();
    				}else {
    					error("Fail delete", "Erro desconhecido");
    				}
    					
    			}else 
    				error("Fail delete", "Existe orçamento para este equipamento");
    		}
    		
    	} 
		else if(keyEvent.getCode().toString() == "F2" ) {    		
    		if(tableEquipamentos.getSelectionModel().getSelectedItem() != null){
        		{
        			NewView.getNewView("Edit Equipamento", "updatedeEquipamento", new EquipamentoUpdatede(tableEquipamentos.getSelectionModel().getSelectedItem()) );

        		}
	
    		}
    		
    	} 
		
	}
	

}