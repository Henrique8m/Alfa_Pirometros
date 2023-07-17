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
import com.hrodriguesdev.relatorio.SaidaEquipamentoPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.Log;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SaidaEquipamentoViewController implements Initializable {
	
	@FXML
	public TextField nomeEmpressa, data, ultimaCal, modelo, ns, pat;
	
	@FXML
	protected ComboBox<String> empresaColeta = new ComboBox<>();	
	private ObservableList<String> obsString = FXCollections.observableArrayList();			
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@FXML
	protected TextField dataColeta, nomeColetor;
		
	@FXML
	protected ImageView cancelarImg, salvarImg, pdf;

	@FXML
	protected Text erro;


	//@Autowired
	protected OrcamentoController orcamentoController = InjecaoDependency.ORCAMENTO_CONTROLLER;
	protected EquipamentoController equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
	protected ColetorController coletorController = InjecaoDependency.COLETOR_CONTROLLER;
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;
	
	
	protected SaidaEquipamentoPDF saidaPdf = new SaidaEquipamentoPDF();
	protected SaidaEquipamentoTransacao transaction = new SaidaEquipamentoTransacao();
	
	protected Equipamento equipamento;
	protected Orcamento orcamento;
	protected Coletor coletor;
	protected Empresa empresa;
	

	public SaidaEquipamentoViewController(Equipamento equipamento, Orcamento orcamento) {
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}	

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		addListener();
		imageInit();
		textFildInserts();
	    
	}

	@FXML
	protected void gerarPDF(ActionEvent event) {
		if(salvarSaidaDb()) {
		
			if(saidaPdf.newDocument(coletor, equipamento, empresa, orcamento)) {
				fecharView();
			}
		}
	}
		
	@FXML
	protected void salvar(ActionEvent event) {
		if(salvarSaidaDb()) {
			fecharView();
		}
		
	}

	private boolean salvarSaidaDb() {
		coletor = getColetor();
		if(coletor.getId() == null && coletor.getId() == 0) {
			return false;
		}
		try {
			updateEquipamentoAndOrcamento();
			if( transaction.saidaEquipamento(equipamento, orcamento)) {
				return true;
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return false;				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Log.logString("SaidaEquipamentoViewController", e.getMessage());
			erro.setText("ERRO");
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			Log.logString("SaidaEquipamentoViewController", e.getMessage());
			error( "Null Pointer " ,"Null Pointer Exeption");	
			
		}		
		InjecaoDependency.MAIN_TAB_CONTROLLER.refreshTableMain();
		return true;
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
		if(empresaColeta.getValue()== "" ||  nomeColetor.getText()== "" ) {
			if(!empresaColeta.getValue().isBlank() || !nomeColetor.getText().isBlank()) 
				if(!empresaColeta.getValue().isEmpty() || !nomeColetor.getText().isEmpty())			
					erro.setText("O campo nome da Empressa e nome do coletor, não pode estar vazio");
					return null;
		}
		try {	
			Long empresaId = empresaController.isExist(empresaColeta.getValue());
			if ( empresaId == null) {
				throw new DbException("Empresa não existe");
			} else {
				empresa = empresaController.findEmpresa(empresaId);
			}

			coletor.setOrcamento_id( equipamento.getOrcamento_id() );	
			coletor.setEmpresaName(empresaColeta.getValue());
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
	
	protected void fecharView() {
		Stage stage = (Stage) cancelarImg.getScene().getWindow();
		stage.close();
	}
	
	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
	}
	
	@FXML
	protected void cancelar(ActionEvent event) {
		fecharView();
	}
		
	protected void textFildInserts() {
		nomeEmpressa.setText(equipamento.getEmpresaName());
	    data.setText(Format.formatData.format( equipamento.getDateChegada() ) );
	    if(equipamento.getUltimaCalibDate()!=null) 
	    	ultimaCal.setText(Format.formatData.format( equipamento.getUltimaCalibDate() ) );
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		dataColeta.setText(Format.formataDateTimeString.format(new Date(System.currentTimeMillis() ) ) );
	}
	
	public void addListener() {
		obsString =  empresaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( empresaColeta, filteredList );
		empresaColeta.getEditor().textProperty().addListener(inputFilter);	
		
	}
	
	protected void imageInit() {
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
	}
	
}

