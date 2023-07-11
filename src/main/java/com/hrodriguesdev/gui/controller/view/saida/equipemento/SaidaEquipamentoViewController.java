package com.hrodriguesdev.gui.controller.view.saida.equipemento;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.SaidaEquipamentoTransacao;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.tabsLoad.TabsMainView;

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
	
	public SaidaEquipamentoViewController(Equipamento equipamento, Orcamento orcamento) {
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}
	
	//@Autowired
	protected OrcamentoController controller = InjecaoDependency.ORCAMENTO_CONTROLLER;
	protected EquipamentoController equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
	protected ColetorController coletorController = InjecaoDependency.COLETOR_CONTROLLER;
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;
	protected Equipamento equipamento;
	protected Orcamento orcamento;
	protected SaidaEquipamentoTransacao transaction = new SaidaEquipamentoTransacao();
	
	@FXML
	protected ImageView cancelarImg;
	@FXML
	protected ImageView salvarImg;
	@FXML
	protected Text erro;
	@FXML
	public TextField data, ultimaCal, modelo, ns, pat, nomeEmpressa;
	@FXML
	protected Button salvar, cancelar;

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

	
	@FXML
	protected void gerarPDF(ActionEvent event) {
			Coletor coletor = getColetor();
			if(coletor == null)
				return;
			GeneratorPDF pdf = new GeneratorPDF();				
			Empresa empressa = empresaController.find( equipamento.getEmpresa() );		
			
			if( equipamentoController.updatedeNsPatModelo(equipamento) ) {
				orcamento.setData_saida(new java.sql.Date(System.currentTimeMillis()));
				pdf.newDocument(coletor, equipamento, empressa, orcamento);
				salvar(event);
				Stage stage = (Stage) salvar.getScene().getWindow();
				stage.close();
				
			}
	}
		
	@FXML
	protected void salvar(ActionEvent event) {
		getColetor();
		
		try {
			updateEquipamentoAndOrcamento();
			if( transaction.saidaEquipamento(equipamento, orcamento)) {
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
		TabsMainView.MAIN_TAB_CONTROLLER.refreshTableMain();
		
	}

	protected void updateEquipamentoAndOrcamento() throws NullPointerException{

		orcamento.setData_saida(new java.sql.Date(System.currentTimeMillis()));
		switch ( equipamento.getStatus() ) {
			case 2:
				equipamento.setStatus( 12 );
				orcamento.setStatus(12);
				equipamento.setLaboratorio(true);
				orcamento.setLaboratorio(true);
				break;
			case 3:
				equipamento.setStatus( 13 );
				orcamento.setStatus(13);
				equipamento.setLaboratorio(true);
				orcamento.setLaboratorio(true);
				break;
			case 9:
				equipamento.setStatus( 12 );
				orcamento.setStatus(12);
				equipamento.setLaboratorio(true);
				orcamento.setLaboratorio(true);
				break;
			
			default:
				equipamento.setLaboratorio(false);
				orcamento.setLaboratorio(false);
				equipamento.setStatus( 7 );
				orcamento.setStatus(7);
				break;
				
		}
		
	}

	protected Coletor getColetor() {
		Coletor coletor = new Coletor();
		if(coleta.getValue()== "" ||  nomeColetor.getText()== "" ) {
			if(!coleta.getValue().isBlank() || !nomeColetor.getText().isBlank()) 
				if(!coleta.getValue().isEmpty() || !nomeColetor.getText().isEmpty())			
					erro.setText("O campo nome da Empressa e nome do coletor, não pode estar vazio");
					return null;
		}
		try {	
			if ( empresaController.isExist(coleta.getValue()) == null ) {
				throw new DbException("Empresa não existe");
			}

			coletor.setOrcamento_id( equipamento.getOrcamento_id() );	
			coletor.setEmpresaName(coleta.getValue());
			coletor.setNomeColetor(nomeColetor.getText());
			coletor.setDataHoraColeta( dataColeta.getText() );
			coletor.setDate(new java.sql.Date(System.currentTimeMillis()));
			coletor.setHoraColeta( Integer.parseInt( Format.formataTimeInt.format(new Date(System.currentTimeMillis() )  ) ) );
			orcamento.setColetor_id( coletorController.add(coletor) );
			
		}catch(DbException e2) {
			erro.setText("Empresa Não Encontrada");
			return null;
		}
		return coletor;
	}	
	
	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
		Stage stage = (Stage) cancelar.getScene().getWindow(); 
		stage.close();
	}
	
	@FXML
	protected void cancelar(ActionEvent event) {
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
		
		nomeEmpressa.setText(equipamento.getEmpresaName());
	    data.setText(Format.formatData.format( equipamento.getDateChegada() ) );
	    if(equipamento.getUltimaCalibDate()!=null) 
	    	ultimaCal.setText(Format.formatData.format( equipamento.getUltimaCalibDate() ) );
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
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
	}

	public void addListener() {
		obsString =  empresaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( coleta, filteredList );
		coleta.getEditor().textProperty().addListener(inputFilter);	
	}
	
	@SuppressWarnings("unused")
	private void removeListener() {
		coleta.getEditor().textProperty().removeListener(inputFilter);
		coleta.setValue("");
	}
	
}

