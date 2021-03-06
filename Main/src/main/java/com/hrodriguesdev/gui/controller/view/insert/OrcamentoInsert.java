package com.hrodriguesdev.gui.controller.view.insert;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.MainViewController;
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

public class OrcamentoInsert implements Initializable {
	
	private Equipamento equipamento;
//	private Orcamento orcamento;
//	private EstoqueConsumo consumo = new EstoqueConsumo();
//	private EstoqueEletricos eletricos = new EstoqueEletricos();
//	private EstoqueEletronicos eletronicos = new EstoqueEletronicos();
//	private EstoqueEstetico estetico = new EstoqueEstetico();
	private OrcamentoController controller = new OrcamentoController();
	private EquipamentoController equipamentoController = MainViewController.equipamentoController;
	
	private Long orcamentoId;
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
	
	//Add Or??amentos
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
			orcamentoId = controller.add( new Orcamento(nova, 0) );
			if(equipamentoController.updatede(equipamento.getId() , orcamentoId)) {
				equipamentoController.updatede(equipamento.getId(), 2, equipamento);
				try {
					Stage stage = (Stage) cancelar.getScene().getWindow(); 
					stage.close();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}else {
				erro.setText("Erro");
			}
		}
		AlfaPirometrosApplication.viewController.refreshTable();
	
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
		//FilteredList<String> filteredList = new FilteredList<>(AlfaPirometrosApplication.obsPecasEstoque);		
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
		
//	private boolean listManutencao(String value, int intValue) {
//		/*
//		switch (value) {
//			case "BotaoLiga": return "Aguardando Or??amento";
//			case "BoMeFIIFIIIIndicmax":	return "Enviar Or??amento";
//			case "CaixaBat": return "Aguardando Aprova????o";
//			case "FontCarbIndic": return "Aprovado, aquardando Reparo!";
//			case "FontCarbDelta": return "Liberado, aquardando Coleta!";
//			case "PinFemeAliFII": return "N??o Aprovado, aquardando coleta!";
//			
//			case "PinFemeAliFIII": return "Aguardando Or??amento";
//			case "BatFIIFIII":	return "Enviar Or??amento";
//			case "BatDescartavel": return "Aguardando Aprova????o";
//			case "BatInditemp": return "Aprovado, aquardando Reparo!";
//			case "BatLitio": return "Liberado, aquardando Coleta!";
//			case "CarrEcil": return "N??o Aprovado, aquardando coleta!";		
//			case "CarrItalterm": return "Aguardando Or??amento";
//			
//			case "PCIFIII":	return "Enviar Or??amento";
//			case "PCIFKal": return "Aguardando Aprova????o";
//			case "DispFKal": return "Aprovado, aquardando Reparo!";
//			case "FIII": return "Liberado, aquardando Coleta!";
//			case "Indicmax": return "N??o Aprovado, aquardando coleta!";		
//			case "CIFII": return "Aguardando Or??amento";
//			case "CIIndicmax":	return "Enviar Or??amento";
//			case "sirene": return "Aguardando Aprova????o";
//			
//			case "MascaraFII":	return "Enviar Or??amento";
//			case "MascaraFKal": return "Aguardando Aprova????o";
//			case "MascaraFIII": return "Aprovado, aquardando Reparo!";
//			case "MascaraCarbo": return "Liberado, aquardando Coleta!";
//			case "MascaraIndic": return "N??o Aprovado, aquardando coleta!";		
//			case "EtiqLatFII": return "Aguardando Or??amento";
//			case "EtiqLatFIII":	return "Enviar Or??amento";
//			case "EtiqTrasFII": return "Aguardando Aprova????o";		
//			case "Punho":	return "Enviar Or??amento";
//			
//			case "ReceptaculoS": return "Aguardando Aprova????o";
//			case "ReceptaculoSU": return "Aprovado, aquardando Reparo!";
//			case "ReceptaculoEcil": return "Liberado, aquardando Coleta!";
//			case "ReceptaculoK": return "N??o Aprovado, aquardando coleta!";		
//			case "PlugFS": return "Aguardando Or??amento";
//			case "PlugFK":	return "Enviar Or??amento";
//			case "PlugMS": return "Aguardando Aprova????o";
//			case "PlugMK":	return "Enviar Or??amento";
//			case "TomadaS": return "Aguardando Aprova????o";
//			
//		default: return "";
//		}
//		return false;
//		*/
//		return true;
//	}

}
