package com.hrodriguesdev.gui.controller.view.insert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.EnsaioViewController;
import com.hrodriguesdev.gui.controller.RegisterProductsController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

//Chamadas da class
//gui EstoqueEntrada
//gui EstoqueSaida
//gui main OrcamentoMainView
//gui update OrcamentoUpdateDois

public class OrcamentoInsert extends RegisterProductsController implements Initializable {

	public OrcamentoInsert(Equipamento equipamento, Orcamento orcamento) {
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}

	protected Equipamento equipamento;
	protected Orcamento orcamento;
	protected EnsaiosController controllerEnsaios = new EnsaiosController();

	protected Long orcamentoId;
	protected String list = "";
	protected String nova = "";

	@FXML
	protected VBox observacaoVbox;

	@FXML
	protected HBox hboxOrcamento1, hboxOrcamento2, hbox1, hbox2;

	// Button
	@FXML
	protected Button salvar, cancelar, ensaioButton;

	// Image Button
	@FXML
	protected ImageView cancelarImg, salvarImg, ensaioImg;

	@FXML
	protected Text erro;

	// Info Employee
	@FXML
	protected TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal, nfeText, responsavel;

	// Table
	@FXML
	protected TableView<Product> productSelectedTable = new TableView<>();
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> productsSelected, descriptionSelected, unitMeasurementSelected;
	@FXML
	protected TableColumn<Product, Double> amountSelected;

	// Add Orçamentos
	@FXML
	protected TextField quantidadeItem, obsSelected, filterProductsTextField;

	@FXML
	protected ComboBox<String> empressaComboBox;

	// config combobox
	protected InputFilter<String> inputFilterNewItem;
	protected InputFilter<String> listener;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quantidadeItem.setText("1");
		imageInit();
		textFildInserts();
		startTable();
		data.setEditable(true);
	}

//	Acaoes dos botoes da view

//	Acaoes dos referente aos orcamentos

	@FXML
	protected void addItem(ActionEvent event) {
		if (!productTable.getSelectionModel().isEmpty()) {
			try {
				double qtde = Double.valueOf(quantidadeItem.getText());
				if (qtde > 0) {
					Product prod = productTable.getSelectionModel().getSelectedItem();

					obsMateriais.stream().filter(produto -> produto.equals(prod)).findFirst().ifPresentOrElse((a) -> {
						a.setQtde(a.getQtde() + qtde);
					}, () -> {
						prod.setQtde(qtde);
						obsMateriais.add(prod);
					});
					productSelectedTable.setItems(obsMateriais);
					productSelectedTable.refresh();

				} else
					erro.setText("Quantidade tem que ser maior que 0");
			} catch (NumberFormatException e) {
				Log.logString("OrcamentoInsert", e.getMessage());
				e.printStackTrace();
			}

		}
		descelectAll();
	}

	@FXML
	protected void removeItem(ActionEvent event) {
		if (!productSelectedTable.getSelectionModel().isEmpty()) {
			obsMateriais.remove(productSelectedTable.getSelectionModel().getSelectedIndex() );
			productSelectedTable.setItems(obsMateriais);
			productSelectedTable.refresh();
		} else
			obsSelected.setText("");
	}

//	botao para fecha a tela
	@FXML
	protected void cancelar(ActionEvent event) throws IOException {
		NewView.fecharView();
	}

//	Botao pra inserir o ensaio do equipamento em questao
	@FXML
	private void ensaio() {
		NewView.getNewView("Ensaios", "ensaioInserts", new EnsaioViewController(equipamento, orcamento));
	}

//	botao para salvar os dados alterado 
	@FXML
	protected void salvar(ActionEvent event) throws IOException {
		orcamentoId = orcamento.getId();

		List<ProductsOs> listProductsOs = new ArrayList<>();

					orcamento.setItem(obsSelected.getText());
		if (obsMateriais.size() > 0) {
			obsMateriais.forEach((product) -> {
				listProductsOs.add(new ProductsOs(orcamentoId, product.getId(), product.getQtde()));
			});
		}
		if (orcamento.getStatus() == 1)
			orcamento.setStatus(2);
		if (data.getText().length() == 10)
			orcamento.setData_chegada(Geral.dateParceString(data.getText()));

		OSController osController = new OSController();
		
		if(listProductsOs.size() > 0) {
			if (osController.createNewOS(listProductsOs))
				updateOrcamento();
			else {
			Log.logString("OrcamentoInsert", "Erro Criar a lista da os");
			Alerts.showAlert("Error", "Contatar Adm", "", AlertType.ERROR);
			System.out.println("Erro Criar a lista da os\nOrcamentoInsert\n");
			}
		}else 
			updateOrcamento();
			
		InjecaoDependency.MAIN_TAB_CONTROLLER.refreshTableMain();

	}

	private void updateOrcamento() {
		OrcamentoController controllerOrcamento = new OrcamentoController();
		if (controllerOrcamento.update(orcamento))
			NewView.fecharView();
		else {
			Log.logString("OrcamentoInsert", "Erro ao Atualizar o orcamento");
			System.out.println("Erro ao Atualizar o orcamento\nOrcamentoInsert\n");
		}
	}
	
	
//	Acoes de tecla

//	TextField de obervacao, a area de add orcamento
	@FXML
	protected void addComEnter(KeyEvent event) {
		if (event.getCode().toString() == "ENTER") {
			addItem(new ActionEvent());
			if (event.getTarget().equals(super.productTable)) {
				filterProductsTextField.requestFocus();
			}
			if (event.getTarget().equals(quantidadeItem)) {
				filterProductsTextField.requestFocus();
			}
		} else if (event.getCode().toString() == KeyCode.ESCAPE.toString()) {
			descelectAll();
		}
	}

	@FXML
	protected void filterList(KeyEvent event) {
		if (!filterProductsTextField.getText().isBlank()) {
			productTable.setItems(super.obs.filtered(product -> product.getName().toUpperCase()
					.contains(filterProductsTextField.getText().toUpperCase())));
			productTable.refresh();

		}
	}

	@FXML
	protected void esc(KeyEvent event) {
		if (event.getCode().toString() == "ESC") {
			descelectAll();
		}
	}

//	filtro para inserir quantidade de produto no orcamento 
	@FXML
	private void filtredDouble(KeyEvent eventKey) {
		if (eventKey.getTarget().equals(quantidadeItem)) {
			quantidadeItem.setText(quantidadeItem.getText().replaceAll("[^0-9-.]+", ""));
			quantidadeItem.end();
		}
	}

	@FXML
	protected void format(KeyEvent event) {
		if (event.getTarget() == nfeText) {
			nfeText.setText(nfeText.getText().replaceAll("[^0-9]+", ""));
			nfeText.end();
		} else if (event.getTarget() == data) {
			data.setText(Format.replaceData(data.getText()));
			data.end();
		}
	}

//	metodos auxiliares

//	tira a selecao de todas as tabelas, limpa a quantidade, e limpa o campo de observacao
	protected void descelectAll() {
		productTable.getSelectionModel().clearSelection();
		productSelectedTable.getSelectionModel().clearSelection();
		quantidadeItem.setText("1");
		erro.setText("");
		filterProductsTextField.setText("");
		super.refresh();

	}

//	insere as informações dos equipamento assim que a view abre
	protected void textFildInserts() {
		data.setText(Format.formatData.format(orcamento.getData_chegada()));
		nomeEmpressa.setText(equipamento.getEmpresaName());

		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		if (equipamento.getUltimaCalibDate() != null)
			ultimaCal.setText(Format.formatData.format(equipamento.getUltimaCalibDate()));
//		ultimaCal.setText(equipamento.getUltimaCalib());	
	}

//	Inicia todas as imagens contidas na view
	protected void imageInit() {
		Image image = new Image(
				AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString());
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString());
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-ensaio.png").toString());
		try {
			ensaioImg.setImage(image);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

//	Inicia as tabelas conforme os dados que vao ser inseridos na mesma
	@Override
	public void startTable() {
//		Tabela de produtos selecionados
		productsSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		descriptionSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurementSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountSelected.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));
		productSelectedTable.setItems(obsMateriais);

//		tabela com todos os produtos
		productTable.setEditable(false);
		products.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		description.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurement.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));

		refresh();
	}

}
