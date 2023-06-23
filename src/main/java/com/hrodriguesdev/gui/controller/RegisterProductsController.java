package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.EstoqueRepController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.utilitary.Log;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class RegisterProductsController implements Initializable{
	
	@FXML
	protected TableView<Product> productTable;
	protected ObservableList<Product> obs = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> products, description, unitMeasurement;
	@FXML
	protected TableColumn<Product, Double> amountPaid, saleValue, amount;
	@FXML
	protected TextField productField, descriptionField, valuePaidField, valueSaleField, amountField;
	@FXML
	protected ComboBox<String> unitMeasurementBox;
	@FXML
	protected HBox editProductsBox;
	@FXML
	protected MenuItem addMenuItem, saveMenuItem, editMenuItem;	
	
	
	EstoqueRepController controller = new EstoqueRepController();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
	}
	
	@FXML
	private void addAction(ActionEvent e) {
		editProductsBox.setVisible(true);
		saveMenuItem.setVisible(true);
	}
	
	@FXML
	private void saveAction(ActionEvent e) {
		
	}
	
	public void strartTable() {			
		try {
			obs = controller.findAllProductsobs();
	    }catch(DbException e) {
	    	Log.logString("RegisterProductsController", e.getMessage());
	    } 
				
		productTable.setEditable(false); 
			
		products.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));		
		description.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurement.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountPaid.setCellValueFactory(new PropertyValueFactory<Product, Double>("valor_pago"));
		saleValue.setCellValueFactory(new PropertyValueFactory<Product, Double>("valor_venda"));
		amount.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));	
	    
		productTable.setItems(obs);		
		
		
	}

}
