package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.controller.UnidadeMedidaController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Log;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class RegisterUnidadeController implements Initializable{
	
	@FXML
	protected TableView<UnidadeMedida> unidadeMedidaTable;
	protected ObservableList<UnidadeMedida> obs = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<UnidadeMedida, String> unidadeMedida;
	@FXML
	protected TextField unidadeMedidaField;
	@FXML
	protected MenuItem addMenuItem, saveMenuItem, editMenuItem, cancelarMenuItem;		
	@FXML
	protected HBox editUnidadeMedidasBox;
	
	UnidadeMedidaController controller = new UnidadeMedidaController();
	UnidadeMedida unidade = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		strartTable();
		refresh();
	}
	
//	Menu itens 1
	
	@FXML
	private void addAction(ActionEvent e) {
		 visibleAddAction(true);		
		 unidadeMedidaField.setText("");
		 
	}
	
	@FXML
	private void saveAction(ActionEvent e) {		
		String unidadeTxt = unidadeMedidaField.getText();
		if(!unidadeTxt.isBlank()) {
			boolean created = false;
			if(unidade != null) {
				unidade.setUnidade(unidadeTxt);
				created = controller.updateUnidade(unidade);
			}else
				created = controller.createNewUnit(new UnidadeMedida(unidadeTxt));
			if(created) {
				refresh();
				visibleAddAction(false);
				Alerts.showAlert("Criado Unidade Medida", "Unidade  - " + unidadeTxt + " ok", "", AlertType.INFORMATION);
			}
		}
	}
	
	public void strartTable() {				
		unidadeMedidaTable.setEditable(false); 			
		unidadeMedida.setCellValueFactory(new PropertyValueFactory<UnidadeMedida, String>("unidade"));				
		refresh();
				
	}
	
	private void refresh() {
		try {
			obs = controller.findAllUnidadeMedidasObs();
			unidadeMedidaTable.setItems(obs);
			unidadeMedidaTable.refresh();
	    }catch(DbException e) {
	    	Log.logString("RegisterUnidadeMedidasController", e.getMessage());
	    } 
	}
	
	
	@FXML
	private void editAction(ActionEvent e) {
		if(!unidadeMedidaTable.getSelectionModel().isEmpty()) {
			visibleAddAction(true);
			unidade = unidadeMedidaTable.getSelectionModel().getSelectedItem();
			unidadeMedidaField.setText(unidade.getUnidade());			
		}
	}
	
	@FXML
	private void cancelarAction(ActionEvent e) {
		visibleAddAction(false);
		unidadeMedidaField.setText("");
		
	}

	@FXML
	private void keyReleased(KeyEvent eventKey) {
		String keyCode = eventKey.getCode().toString();
		if(keyCode.compareTo("F2")==0) {
			editAction(new ActionEvent());
		}else if(keyCode.compareTo("DELETE")==0) {
			if(!unidadeMedidaTable.getSelectionModel().isEmpty()) {
				unidade = unidadeMedidaTable.getSelectionModel().getSelectedItem();
				boolean delete = false;
				try {
					delete = controller.delete(unidade.getId());					
					refresh();
				}catch (ExceptionAlfa e) {
					Alerts.showAlert("Unidade Medida", "Erro ao deletar", e.getMessage(), AlertType.ERROR);
				}
				if(delete)
					Alerts.showAlert("Unidade Medida", "Unidade  - " + unidade.getUnidade() + " Deletada com sucesso", "", AlertType.INFORMATION);
			}
		}else if(keyCode.compareTo("ESC")==0) 
			cancelarAction(new ActionEvent());
		
	}
	
	private void visibleAddAction(boolean visible) {
		editUnidadeMedidasBox.setVisible(visible);
		saveMenuItem.setVisible(visible);
		cancelarMenuItem.setVisible(visible);
		editMenuItem.setVisible(!visible);
		addMenuItem.setVisible(!visible);
	}
	
	
}
