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
	protected VBox chegada, modeloVbox, nsVbox, patVbox, calVbox, empressaVBox, nfeVbox, responsavelVbox, empressaVBox1;
	@FXML
	protected HBox observacaoVbox;
	
	//Button
	@FXML
	protected Button salvar, cancelar;
	
	//Image Button
	@FXML
	protected ImageView cancelarImg, salvarImg, ensaioImg;
	
	@FXML
	protected Text erro, infoText;
	
	//Info Employee 
	@FXML
	protected TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal, nfeText, responsavel;
		
	//Table
	@FXML
	protected TableView<Product> productSelectedTable = new TableView<>();
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> productsSelected, descriptionSelected, unitMeasurementSelected;
	@FXML
	protected TableColumn<Product, Double> amountSelected;
	
	//Add Orçamentos
	@FXML
	protected TextField obs, quantidadeItem, obsSelected, filterProductsTextField;
	
	@FXML 
	protected ComboBox<String> empressaComboBox;
	
	//config combobox
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
		if(!obs.getText().isBlank()) {
			orcamento.setItem(obs.getText());
			if(!obsSelected.getText().isBlank())
				obsSelected.setText(obsSelected.getText() 
						+ ", " 
						+ obs.getText());
			else 
				obsSelected.setText(obs.getText());
			
		}else if(!productTable.getSelectionModel().isEmpty()){
			try {
				double qtde = Double.valueOf(quantidadeItem.getText());
				if(qtde >0) {
					Product prod = productTable.getSelectionModel().getSelectedItem();
					
					obsMateriais.stream().filter(produto -> produto.equals(prod)).findFirst().ifPresentOrElse(
							(a) -> {
								a.setQtde(a.getQtde() + qtde);
								} , 
							() -> {
								prod.setQtde(qtde);
								obsMateriais.add(prod);
							});
					productSelectedTable.refresh();

				}else
					erro.setText("Quantidade tem que ser maior que 0");
			}catch(NumberFormatException e) {
				Log.logString("OrcamentoInsert", e.getMessage());
				e.printStackTrace();
			}
						
		}
		descelectAll();	
	}	
	
	
	@FXML
	protected void removeItem(ActionEvent event) {
		if(!productSelectedTable.getSelectionModel().isEmpty()) {
			obsMateriais.remove(productSelectedTable.getSelectionModel().getSelectedItem() );
			productSelectedTable.setItems(obsMateriais);
			productSelectedTable.refresh();
		}else
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
		NewView.getNewView("Ensaios","ensaioInserts" , new EnsaioViewController(equipamento, orcamento));
	}

//	botao para salvar os dados alterado 
	@FXML
	protected void salvar(ActionEvent event) throws IOException {
		orcamentoId = orcamento.getId();
		
		List<ProductsOs> listProductsOs = new ArrayList<>();
		
		if(!obsSelected.getText().isBlank())
			orcamento.setItem(obsSelected.getText());
		if(obsMateriais.size()>0) {
			obsMateriais.forEach((product) -> {
				listProductsOs.add(new ProductsOs(orcamentoId, product.getId(), product.getQtde()));				
			});
		}else if(obsSelected.getText().isBlank())
			return ;
		if(orcamento.getStatus() == 1)
			orcamento.setStatus(2);
		if(data.getText().length() == 10)
			orcamento.setData_chegada(Geral.dateParceString(data.getText()));
		
		OSController controller = new OSController();
		OrcamentoController controllerOrcamento = new OrcamentoController();
		if(controller.createNewOS(listProductsOs) )
			if(controllerOrcamento.updatede(orcamento)) 
					NewView.fecharView();			
			else {
				Log.logString("OrcamentoInsert", "Erro ao Atualizar o orcamento");
				System.out.println("Erro ao Atualizar o orcamento\nOrcamentoInsert\n");
			}
		else {
			Log.logString("OrcamentoInsert", "Erro Criar a lista da os");
			Alerts.showAlert("Error", "Contatar Adm", "", AlertType.ERROR);
			System.out.println("Erro Criar a lista da os\nOrcamentoInsert\n");
		}
						
//		Itens itens = new Itens(orcamentoId, false , 0, false);		
//		if(obsMateriais.size()>0) {
//			obsMateriais.forEach((orcamento)-> {	
//
//				if( !itens.addItem(orcamento.getItemRealString(), orcamento.getQuantidade() ) )	{
////					String itemStr = orcamento.getItem();
//					this.nova = this.list + itemStr  + "\n";
//					this.list = nova;
//				}				
//			});
//			
//			if(!this.list.isEmpty())
//				orcamento.setItem(list);	

//			if(itens.saveAll( orcamento) ) {
//				try {
//					NewView.fecharView();
//				
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//			}else {
//				erro.setText("Erro");
//			}
//		}		
		AlfaPirometrosApplication.viewController.refreshTable();
	
	}	
		
//	Acoes de tecla
	
//	TextField de obervacao, a area de add orcamento
	@FXML
	protected void addComEnter(KeyEvent event) {
		if(event.getCode().toString()=="ENTER") {
			addItem(new ActionEvent());

		}
	}
	
	@FXML
	protected void esc(KeyEvent event) {
		if(event.getCode().toString()=="ESC") {
			descelectAll();
		}
	}
	
//	filtro para inserir quantidade de produto no orcamento 
	@FXML
	private void filtredDouble(KeyEvent eventKey) {
		if(eventKey.getTarget().equals(quantidadeItem)) {
			quantidadeItem.setText(quantidadeItem.getText().replaceAll("[^0-9-.]+", ""));
			quantidadeItem.end();
		}
	}
	
	
	@FXML
	protected void format(KeyEvent event) {
		if(event.getTarget() == nfeText) {
			nfeText.setText( nfeText.getText().replaceAll("[^0-9]+", "") );
			nfeText.end();
		}
		else if(event.getTarget() == data) {
			data.setText(Format.replaceData(data.getText()));
			data.end();
		}
	}	
		
//	metodos auxiliares
	
//	tira a selecao de todas as tabelas, limpa a quantidade, e limpa o campo de observacao
	protected void descelectAll() {
		obs.setText("");
		productTable.getSelectionModel().clearSelection();
		productSelectedTable.getSelectionModel().clearSelection();
		quantidadeItem.setText("1");
		erro.setText("");
		
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
	
	
//	Inicia todas as imagens contidas na view
	protected void imageInit() {
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-ensaio.png").toString() );
		try {
			ensaioImg.setImage(image);
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
//	insere as informações dos equipamento assim que a view abre
	protected void textFildInserts() {
				data.setText( Format.formatData.format(orcamento.getData_chegada()) );nomeEmpressa.setText(equipamento.getEmpressaName());

		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		if(equipamento.getUltimaCalibDate() != null) 
			ultimaCal.setText( Format.formatData.format(equipamento.getUltimaCalibDate()) );
//		ultimaCal.setText(equipamento.getUltimaCalib());	
	}
	
		
// Table Products	
	
	
	
}
