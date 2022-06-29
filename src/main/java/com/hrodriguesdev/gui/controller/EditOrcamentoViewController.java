package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.utilitary.InputFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditOrcamentoViewController implements Initializable {
	
	private Equipamento equipamento;
//	private Orcamento orcamento;
//	private EstoqueConsumo consumo = new EstoqueConsumo();
//	private EstoqueEletricos eletricos = new EstoqueEletricos();
//	private EstoqueEletronicos eletronicos = new EstoqueEletronicos();
//	private EstoqueEstetico estetico = new EstoqueEstetico();
	private OrcamentoController controller = new OrcamentoController();
	
//	private Long orcamentoId;
	private String list = "Lista de Materiais Usados; \n";
	private String nova = "";
	
	//Button
	@FXML
	private Button salvar, cancelar;
	
	//Image Button
	@FXML
	private ImageView cancelarImg, salvarImg;
	
	@FXML
	private Text erro, erro2;
	
	//Info Employee 
	@FXML
	public TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal;

	
	//Table
	@FXML
	private TableView<Orcamento> tableOrcamento = new TableView<>();
	private ObservableList<Orcamento> obsMateriais = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Orcamento, String> item;
	@FXML
	private TableColumn<Orcamento, Integer> quantidade;
	
	//Add Orçamentos
	@FXML
	private TextField obs;
	@FXML
	private ComboBox<String> newItem = new ComboBox<>();
	@FXML
	private ComboBox<String> quantidadeItem = new ComboBox<>();
	
	//config combobox
	private InputFilter<String> inputFilterNewItem;
	private InputFilter<String> listener;
	
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Orcamento orcamento = MainViewController.orcamentoEdit;
		String bruto = orcamento.getItem();
		String[] separator =  bruto.split("\n");
		if( separator.length > 0) {
			for(int i=0; i < separator.length; i++) {
				if(i != 0) {
					obsMateriais.add(new Orcamento(separator[i], 0));
				}
			}
		}

		
		quantidadeItem.setValue("1");
		inputFilterNewItem = new InputFilter<String>( newItem,  new FilteredList<>(AlfaPirometrosApplication.obsPecasEstoque) );
		listener = new InputFilter<String>( quantidadeItem,  new FilteredList<>(AlfaPirometrosApplication.obsQuantidade) );
		conboBoxInit();
		imageInit();
		textFildInserts();
		tabelaInit();
		
		
	}	
	
	@FXML
	private void addComEnter(KeyEvent event) {
		if(event.getCode().toString()=="ENTER") {
			if(event.getTarget() == newItem) {
				obsMateriais.add(new Orcamento(newItem.getValue(), Integer.parseInt( quantidadeItem.getValue() ) ) );
				tableOrcamento.refresh();
				
				quantidadeItem.getEditor().textProperty().removeListener( listener );	
				newItem.getEditor().textProperty().removeListener( inputFilterNewItem );
				
				newItem.setValue("");
				quantidadeItem.setValue("1");
				conboBoxInit();	
			}else if(event.getTarget() == obs) {
				obsMateriais.add( new Orcamento(obs.getText(), 0 ) );
				obs.setText("");
			}

		}
	}
	
	@FXML
	public void addItem(ActionEvent event) {
		if(!newItem.getValue().isEmpty() && !newItem.getValue().isBlank() && quantidadeItem.getValue()!= null) {
			obsMateriais.add(new Orcamento(newItem.getValue(), Integer.parseInt( quantidadeItem.getValue() ) ) );
			tableOrcamento.refresh();
			
			quantidadeItem.getEditor().textProperty().removeListener( listener );	
			newItem.getEditor().textProperty().removeListener( inputFilterNewItem );
			
			newItem.setValue("");
			quantidadeItem.setValue("1");
			conboBoxInit();
			
			
			
		}else if( !obs.getText().isEmpty() ) {
			obsMateriais.add( new Orcamento(obs.getText(), 0 ) );
			obs.setText("");
			
		}else {
			erro2.setText("Error");
		}
		
	}	
	
	@FXML
	public void removeItem(ActionEvent event) {
		Integer index = tableOrcamento.getSelectionModel().getSelectedIndex();
		if(index != null) {
			obsMateriais.remove(tableOrcamento.getSelectionModel().getSelectedItem() );
			tableOrcamento.setItems(obsMateriais);
			tableOrcamento.refresh();
			System.out.println("Teste");
		}		
	}	


	@FXML
	public void salvar(ActionEvent event) {

		if(obsMateriais.size()>0) {
			obsMateriais.forEach((orcamento)-> {	
				String itemStr = orcamento.getItem();
				this.nova = this.list + itemStr  + "\n";
				this.list = nova;
			});
			Orcamento orcamento = new Orcamento(nova, 0);
			orcamento.setId( MainViewController.orcamentoEdit.getId() );
			if( controller.updatede( orcamento ) ) {
				try {
					Stage stage = (Stage) cancelar.getScene().getWindow(); 
					stage.close();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}else {
				erro.setText("Erro");
			}
		}else {
			erro.setText("Erro");
		}
		
	
	}	
	
	@FXML
	public void cancelar(ActionEvent event) {
		try {
			Stage stage = (Stage) cancelar.getScene().getWindow(); 
			stage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	public void format(KeyEvent event) {
		
}
	
	private void tabelaInit() {
		item.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("Item") );
		quantidade.setCellValueFactory(new PropertyValueFactory<Orcamento, Integer>("quantidade"));
		tableOrcamento.setItems(obsMateriais);
		
	}

	private void conboBoxInit() {
		quantidadeItem.setEditable(true);
		newItem.setEditable(true);			
		quantidadeItem.getEditor().textProperty().addListener(listener );		
		newItem.getEditor().textProperty().addListener( inputFilterNewItem );
	}
	
	private void imageInit() {
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
	}
	
	private void textFildInserts() {
		equipamento = MainViewController.equipamento;
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(equipamento.getDataChegada());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		ultimaCal.setText(equipamento.getUltimaCalib());	
	}

}
