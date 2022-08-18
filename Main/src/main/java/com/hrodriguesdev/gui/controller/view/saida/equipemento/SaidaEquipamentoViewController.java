package com.hrodriguesdev.gui.controller.view.saida.equipemento;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpressaController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.insert.EmpressaInsert;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SaidaEquipamentoViewController implements Initializable {
	
	//@Autowired
	protected OrcamentoController controller = MainViewController.orcamentoController;
	protected EquipamentoController equipamentoController = MainViewController.equipamentoController;
	protected ColetorController coletorController = MainViewController.coletorController;
	protected EmpressaController empressaController = MainViewController.empressaController;
	protected Equipamento equipamento = MainViewController.equipamentoEdit;
	
	@FXML
	protected ImageView cancelarImg;
	@FXML
	protected ImageView salvarImg;
	@FXML
	protected ImageView addEmpressaImg;
	@FXML
	private Text erro;
	@FXML
	public TextField data, ultimaCal, modelo, ns, pat, nomeEmpressa;
	@FXML
	protected Button salvar, cancelar, addEmpressa;

	//--------------
	
	@FXML
	protected ImageView pdf;
	@FXML
	protected TextField dataColeta, nomeColetor;
	@FXML
	protected ComboBox<String> coleta = new ComboBox<>();	
	private ObservableList<String> obsString = FXCollections.observableArrayList();	
	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
		
	public void addListener() {
		obsString =  MainViewController.empressaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( coleta, filteredList );
		coleta.getEditor().textProperty().addListener(inputFilter);	
	}
	
	private void removeListener() {
		coleta.getEditor().textProperty().removeListener(inputFilter);
		coleta.setValue("");
	}
	
	@FXML
	protected void addEmpressa(ActionEvent e) throws IOException {
		removeListener();
		NewView.getNewView("Adcionar Empressa", "newEmpressa", new EmpressaInsert(this) );
		
	}
	
	@FXML
	protected void gerarPDF(ActionEvent event) {
		
		if(coleta.getValue() != "" &&  nomeColetor.getText() != "" ) {
			GeneratorPDF pdf = new GeneratorPDF();	
			Coletor coletor = getColetor();
			Empressa empressa = empressaController.find( equipamento.getEmpressa() );			
			if( equipamentoController.updated(equipamento) ) {
				pdf.newDocument(coletor, equipamento, empressa);
				Stage stage = (Stage) salvar.getScene().getWindow();
				stage.close();
				
			}
			
		}
		salvar(event);
	}
	
	
	@FXML
	protected void salvar(ActionEvent event) {
		getColetor();
		
		try {
			updateEquipamento();
			if( equipamentoController.updated(equipamento) ) {
				Stage stage = (Stage) salvar.getScene().getWindow();
				stage.close();
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");	
			
		}		
		AlfaPirometrosApplication.viewController.refreshTable();;
		
	}

	protected void updateEquipamento() throws NullPointerException{

		equipamento.setDataSaida( dataColeta.getText() );
		equipamento.setDateSaida(new java.sql.Date(System.currentTimeMillis()));
		switch ( equipamento.getStatus() ) {
			case 2:
				equipamento.setStatus( 12 );
				equipamento.setLaboratorio(true);
				break;
			case 3:
				equipamento.setStatus( 13 );
				equipamento.setLaboratorio(true);
				break;
			default:
				equipamento.setLaboratorio(false);
				equipamento.setStatus( 7 );
				break;
				
		}
		
	}

	protected Coletor getColetor() {
		Coletor coletor = new Coletor();
		if(coleta.getValue()== "" ||  nomeColetor.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e nome do coletor, não pode estar vazio");
			return null;
		}
		try {	
			if ( empressaController.isExist(coleta.getValue()) == null ) {
				throw new DbException("Empresa não existe");
			}

			coletor.setEquipamento_id( equipamento.getId() );	
			coletor.setEmpressaName(coleta.getValue());
			coletor.setNomeColetor(nomeColetor.getText());
			coletor.setDataHoraColeta( dataColeta.getText() );
			coletor.setDate(new java.sql.Date(System.currentTimeMillis()));
			coletor.setHoraColeta( Integer.parseInt( Format.formataTimeInt.format(new Date(System.currentTimeMillis() )  ) ) );
			equipamento.setColetor_id( coletorController.add(coletor) );
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return null;
		}
		return coletor;
	}	
	
	private void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
		Stage stage = (Stage) cancelar.getScene().getWindow(); 
		stage.close();
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
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		addListener();
		imageInit();
		
		nomeEmpressa.setText(equipamento.getEmpressaName());
	    data.setText(equipamento.getDataChegada());
		ultimaCal.setText(equipamento.getUltimaCalib());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		dataColeta.setText(Format.formataDateTimeString.format(new Date(System.currentTimeMillis() ) ) );
	    
	}
	
	protected void imageInit() {
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		addEmpressaImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
	}

}
