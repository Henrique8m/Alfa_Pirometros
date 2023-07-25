package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.controller.UnidadeMedidaController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class RegisterProductsController implements Initializable {

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
	protected MenuItem addMenuItem, saveMenuItem, editMenuItem, cancelarMenuItem;
	@FXML
	protected Text errorTxt;

	protected InputFilter<String> inputFilter;
	ObservableList<UnidadeMedida> obsUnidade;

	ProductsController controllerProd = new ProductsController();
	UnidadeMedidaController controllerUnid = new UnidadeMedidaController();
	Product product = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		unitMeasurementBox.setValue("");
		startTable();
		obsUnidade = controllerUnid.findAllUnidadeMedidasObs();
		comboboxInit();
	}

//	Menu itens 1

	@FXML
	private void addAction(ActionEvent e) {
		cancelarAction(new ActionEvent());
		visibleAddAction(true);
		clearTextField();
	}

	@FXML
	private void saveAction(ActionEvent e) {
		errorTxt.setText("");
		double valuePaid = 0;
		double valueSale = 0;
		String productFieldTxt = productField.getText();
		String descriptionFieldText = descriptionField.getText();
		try {
			valuePaid = Double.valueOf(valuePaidField.getText().replaceAll("[^0-9-.]+", ""));
			valueSale = Double.valueOf(valueSaleField.getText().replaceAll("[^0-9-.]+", ""));

		} catch (NumberFormatException e1) {
			errorTxt.setText("Valores fora do padrao em, valor pago ou valor venda ou quantidade");
			return;
		}
		long idUnit = 0;
		try {
			if (!unitMeasurementBox.getValue().isEmpty())
				idUnit = obsUnidade.stream().filter(a -> a.getUnidade().equals(unitMeasurementBox.getValue()))
						.findFirst().orElseThrow(() -> new ExceptionAlfa("Unidade nao encontrada")).getId();
		} catch (ExceptionAlfa | NullPointerException e2) {
			errorTxt.setText(e2.getMessage());
			return;
		}
		if (idUnit == 0) {
			errorTxt.setText("Erro na escolha da unidade de medida!");
			return;
		}
		if (productFieldTxt.isBlank()) {
			errorTxt.setText("Nome do produto nao pode estar em branco!");
			return;
		}
		if (descriptionFieldText.isBlank()) {
			errorTxt.setText("Descricao do produto nao pode estar em branco!");
			return;
		}

		boolean created = false;

		if (product != null) {
			product = new Product(product.getId(), productFieldTxt, descriptionFieldText, valuePaid, valueSale, idUnit);
			created = controllerProd.update(product);
			if (created) {
				refresh();
				Alerts.showAlert("Updated Produto", "Produto atualizado", "", AlertType.INFORMATION);
			}
		} else {
			Product product = new Product(productFieldTxt, descriptionFieldText, valuePaid, valueSale, idUnit);
			created = controllerProd.createdNew(product);
			if (created) {
				Alerts.showAlert("Criado Produto", "Produto " + product.getName() + ", criado com sucesso!", "",
						AlertType.INFORMATION);
			}
		}
		cancelarAction(new ActionEvent());
	}

	@FXML
	private void editAction(ActionEvent e) {
		if (!productTable.getSelectionModel().isEmpty()) {
			product = productTable.getSelectionModel().getSelectedItem();
			visibleAddAction(true);
			product = productTable.getSelectionModel().getSelectedItem();
			productField.setText(product.getName());
			descriptionField.setText(product.getDescricao());
			valuePaidField.setText(product.getValor_pago().toString());
			valueSaleField.setText(product.getValor_venda().toString());
			unitMeasurementBox.setValue(product.getUnidadeMedida());
		}
	}

	@FXML
	private void cancelarAction(ActionEvent e) {
		visibleAddAction(false);
		clearTextField();
		obsUnidade = controllerUnid.findAllUnidadeMedidasObs();
		refresh();
		errorTxt.setText("");
		product = null;

	}

//	Menu itens 2

	@FXML
	private void addUnidade(ActionEvent e) {
		NewView.getNewView("Unidade Medida", "registerUnidade", new RegisterUnidadeController());

	}

//	Table action

	@FXML
	private void keyReleasedTable(KeyEvent eventKey) {
		String keyCode = eventKey.getCode().toString();
		if (keyCode.compareTo("F2") == 0) {
			editAction(new ActionEvent());
		} else if (keyCode.compareTo("DELETE") == 0) {
			if (!productTable.getSelectionModel().isEmpty()) {
				product = productTable.getSelectionModel().getSelectedItem();
				boolean delete = false;
				try {
//					delete = controller.delete(product.getId());					
					refresh();
				} catch (ExceptionAlfa e) {
					Alerts.showAlert("Produto", "Erro ao deletar", e.getMessage(), AlertType.ERROR);
				}
				if (delete)
					Alerts.showAlert("Produto", "Produto - " + product.getName() + " Deletada com sucesso", "",
							AlertType.INFORMATION);
			}
		} else if (keyCode.compareTo("ESC") == 0)
			cancelarAction(new ActionEvent());

	}

	@FXML
	private void filtredDouble(KeyEvent eventKey) {
		if (eventKey.getTarget().equals(valuePaidField)) {
			valuePaidField.setText(Format.replaceValue(valuePaidField.getText()));
			valuePaidField.end();
		} else if (eventKey.getTarget().equals(valueSaleField)) {
			valueSaleField.setText(Format.replaceValue(valueSaleField.getText()));
			valueSaleField.end();
		}
	}

//	Metodos

	protected void refresh() {
		try {
			obs = controllerProd.findAllObs();
			obs.sort((a,b) -> a.getName().compareTo(b.getName()));
			productTable.setItems(obs);
			productTable.refresh();
		} catch (DbException e) {
			Log.logString("RegisterUnidadeMedidasController", e.getMessage());
		}
	}

	private void visibleAddAction(boolean visible) {
		editProductsBox.setVisible(visible);
		saveMenuItem.setVisible(visible);
		cancelarMenuItem.setVisible(visible);
		editMenuItem.setVisible(!visible);
		addMenuItem.setVisible(!visible);
	}

	private void clearTextField() {
		productField.setText("");
		descriptionField.setText("");
		valuePaidField.setText("0.00");
		valueSaleField.setText("0.00");
	}

	private void comboboxInit() {
		ObservableList<String> obsString = FXCollections.observableArrayList();
		for (UnidadeMedida unid : obsUnidade) {
			obsString.add(unid.getUnidade());
		}

		inputFilter = new InputFilter<String>(unitMeasurementBox, new FilteredList<>(obsString));
		unitMeasurementBox.setEditable(true);
		unitMeasurementBox.getEditor().textProperty().addListener(inputFilter);
	}

	public void startTable() {
		productTable.setEditable(false);
		products.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		description.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurement.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountPaid.setCellValueFactory(new PropertyValueFactory<Product, Double>("valor_pago"));
		saleValue.setCellValueFactory(new PropertyValueFactory<Product, Double>("valor_venda"));
		amount.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));
		productTable.setItems(obs);
		refresh();
	}
}
