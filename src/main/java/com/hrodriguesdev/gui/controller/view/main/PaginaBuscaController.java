package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.updatede.ColetorUpdatede;
import com.hrodriguesdev.gui.controller.view.updatede.EquipamentoUpdatede;
import com.hrodriguesdev.gui.controller.view.updatede.NumeroRelatorioUpdate;
import com.hrodriguesdev.gui.controller.view.updatede.OrcamentoUpdatedeDois;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PaginaBuscaController extends EquipamentoMainView implements Initializable{
  
	private Equipamento equipamento;
	private Orcamento orcamento;
	private Background backgroundPadrao;
	private Long empresa = null;
	
	@FXML
	private Button Adcionar, EditarEquip, cancelar, salvar;
	
	@FXML
	private HBox buscaHbox1, buscaHbox2;
	
	@FXML
	private TextField nomeEmpressaClick, nsClick, patClick,
			modeloClick, dataChegadaClick, relatorioClick, 
			ultimaCalClick, dataSaidaClick, empressaColetaClick,
			dataColetaClick, nomeColetorClick, osClick, equipamentoClick, fabricanteClick;
	@FXML
	private TextArea itensOrcamentoClick;
	
    @FXML
    private TextField textNsEquip, textPatEquip;

    @FXML
    private ComboBox<String> textEmpresa;
    
    private static ObservableList<String> obsString = FXCollections.observableArrayList();
    private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
		

	@FXML
	private TableView<Orcamento> tableOrcamentos;
	private static ObservableList<Orcamento> obsOrcamento = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Orcamento, String>  relatorioTable;
	
	@FXML
	private TableColumn<Orcamento, Date>  chegadaTable;
	
	@FXML
	private TableColumn<Orcamento, Date>  saidaTable;
	
	@FXML
	private Tab tabBuscar;
	
	@FXML
	private ImageView salvarImg2, cancelarImg;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */
		
		tabBuscar.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				addListener();
				if(comboBoxBusca != "") {
					textEmpresa.setValue(comboBoxBusca);
					try {
						buscar(new ActionEvent());
					} catch (IOException e) {
						// 
						e.printStackTrace();
					}
				}
			}else
				removeListener();
		});
		
		startTableOrcamentos();
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg2.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
	
		backgroundPadrao = buscaHbox1.getBackground();
	}

	
	@FXML
	public void click(MouseEvent event) throws SQLException {
//		super.click(event);
//		if(event.getTarget() == tableFindEquipamentos)
		if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
			equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
			obsOrcamento = FXCollections.observableArrayList();
			obsOrcamento = orcamentoController.findAllIdEquipamento(equipamento.getId());
			tableOrcamentos.setItems(obsOrcamento);
			
			tableOrcamentos.refresh();
			nomeEmpressaClick.setText(equipamento.getEmpressaName());
			
			if( equipamento.getNs()!= null ) nsClick.setText(equipamento.getNs() );
			else nsClick.setText("" );
			
			if( equipamento.getPat()!= null ) patClick.setText(equipamento.getPat() );
			else  patClick.setText("");
			
			if( equipamento.getModelo()!= null ) modeloClick.setText( equipamento.getModelo() );
			else modeloClick.setText("");
			
			if( equipamento.getUltimaCalibDate() != null ) {
				Date date = new Date( equipamento.getUltimaCalibDate().getTime() );
				ultimaCalClick.setText( Format.formatData.format(date) );
			}
			else ultimaCalClick.setText( "" );
			
			if(equipamento.getFabricante() != null)
				fabricanteClick.setText(equipamento.getFabricante());
			else fabricanteClick.setText("");
			
			if(equipamento.getInstrumento() != null) 
				equipamentoClick.setText(equipamento.getInstrumento());
			else 
				equipamentoClick.setText("");
			
			
			empressaColetaClick.setText("" );
			dataColetaClick.setText( "");
			nomeColetorClick.setText( "");
			dataChegadaClick.setText( "" ); 
			dataSaidaClick.setText( "" );
			itensOrcamentoClick.setText("");
			relatorioClick.setText("");
			setEditable(false);
			EditarEquip.setVisible(true);
		}else {
			setEditable(false);
		}
		
		
	}	
	
	@FXML
    protected void deletEquipamento(KeyEvent keyEvent) throws IOException {
//		super.updatedEquipamento(keyEvent);
		 if(keyEvent.getCode().toString() == "DELETE" ) {    		
	    	if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
	    		{
	    			Equipamento equipament = tableFindEquipamentos.getSelectionModel().getSelectedItem();
	    			if( !controller.existOrcamento(equipament.getId()) ) {
	    				if (!equipamentoController.delete( equipament.getId() ) ) {
	    					Alerts.showAlert("Falha ao deletar", "", "Erro desconhecido", AlertType.ERROR);
	    				}
	    					
	    			}else 
	    				Alerts.showAlert("Falha ao deletar", "", "Existe relatorio para este equipamento", AlertType.ERROR);
	    		}
	    		
	    	}    
	}
	
	@FXML
	public void clickOrcamento(MouseEvent event) throws SQLException {
		if(equipamento != null)
			if(tableOrcamentos.getSelectionModel().getSelectedItem() != null) {
				orcamento = orcamentoController.getOrcamento( tableOrcamentos.getSelectionModel().getSelectedItem().getId() ); 
				if(orcamento == null)
					return;
				
				if(event.getClickCount()>1) {
					NewView.getNewView("Numero Relatorio", "numeroRelatorio", new NumeroRelatorioUpdate(orcamento));
				}else {		
					Coletor coletor = new Coletor();
					
					
					if( orcamento.getColetor_id() != null && orcamento.getColetor_id() != 0 ) {						
						coletor = MainViewController.coletorController.findById( orcamento.getColetor_id() );
						empressaColetaClick.setText(coletor.getEmpressaName() );
						dataColetaClick.setText( Format.formatData.format(coletor.getDate()));
						nomeColetorClick.setText( coletor.getNomeColetor() );
					}else if(orcamento.getStatus() == 20)
						nomeColetorClick.setText("Manutenção na área");
					else {
						empressaColetaClick.setText("" );
						dataColetaClick.setText( "");
						nomeColetorClick.setText( "");
					}
		
					
					if( orcamento.getData_chegada()!= null ) {
						Date date = new Date( orcamento.getData_chegada().getTime() );						
						dataChegadaClick.setText( Format.formatData.format(date) ); 
						
					}else {
						dataChegadaClick.setText( "" ); 
					}
					if( orcamento.getData_saida() != null ) {
						Date date = new Date( orcamento.getData_saida().getTime() );
						dataSaidaClick.setText( Format.formatData.format(date) );
										
					}else
						dataSaidaClick.setText( "" );
					if( orcamento.getRelatorio() != null ) relatorioClick.setText(orcamento.getRelatorio() );	
					else  relatorioClick.setText("");	
					
					
					itensOrcamentoClick.setText(allItens(orcamento.getId(), orcamento));
					
				}
			}
			else orcamento = null;
		setEditable(false);
	}
	
	
	@FXML
	protected void enter(KeyEvent event) {
		super.enter(event);
//		Busca pelo nome da empresa com enter
		if(event.getTarget() == textEmpresa)
			try {
				buscar(new ActionEvent());
			} catch (IOException e) {
				e.printStackTrace();
			}
//		Editar orçamento e tambem saida
		else if(event.getTarget() == textNsEquip) {
			if(event.getCode().equals(KeyCode.ENTER))
				System.out.println(textNsEquip.getText());			
		}
		else if(event.getTarget() == textPatEquip) {
			if(event.getCode().equals(KeyCode.ENTER))
				System.out.println(textPatEquip.getText());
		}
		else if(event.getTarget() == tableOrcamentos) {
			if(event.getCode().toString() == "F2") {
				if(orcamento!=null){
					NewView.getNewView("Editar Orcamento", "orcamento", new OrcamentoUpdatedeDois(equipamento, orcamento));
				}
			}
			else if(event.getCode().toString() == "F3") {
				if(orcamento.getColetor_id()!= null && orcamento.getColetor_id() != 0)
					NewView.getNewView("Editar coletor", "saidaEquipamento", new ColetorUpdatede(equipamento, orcamento));
			}
		}
	}
		

	
	@FXML
    private void buscar(ActionEvent e) throws IOException {		
		Equipamento equipamento = new Equipamento();
		if(textEmpresa.getValue()!= null)
			if( !textEmpresa.getValue().isEmpty() ) {
	    		equipamento.setEmpressaName(textEmpresa.getValue());
	    	}
    	if( !textNsEquip.getText().isEmpty() ) {
    		equipamento.setNs(textNsEquip.getText());
    	}    	  
    	if( !textPatEquip.getText().isEmpty() ) {
    		equipamento.setPat(textPatEquip.getText());
    	}    	
    	
    	ObservableList<Equipamento> obs = equipamentoController.findAll(equipamento);   	
    	
    	if(obs.size()>0 ) {
    		obsListTableFindEquipamentos = obs;
    		comboBoxBusca = textEmpresa.getValue();
;
    	}else { 		
    		obsListTableFindEquipamentos = equipamentoController.findAll(); 
    		comboBoxBusca = "";
    	}
    	tableFindEquipamentos.setItems(obsListTableFindEquipamentos);
		removeListener();
		addListener();
		setEditable(false);
    }
	
	@FXML
	private void adcionarEquipamento(ActionEvent event) {		
		if(findEmpresa()) {
			setEditable(true);
			
		}

	}
	
	@FXML
	private void editar(ActionEvent event) {
		setEditable(true);
		if( equipamento.getNs()!= null ) nsClick.setText(equipamento.getNs() );
		else nsClick.setText("" );
		
		if( equipamento.getPat()!= null ) patClick.setText(equipamento.getPat() );
		else  patClick.setText("");
		
		if( equipamento.getModelo()!= null ) modeloClick.setText( equipamento.getModelo() );
		else modeloClick.setText("");
		
		if( equipamento.getUltimaCalibDate() != null ) {
			Date date = new Date( equipamento.getUltimaCalibDate().getTime() );
			ultimaCalClick.setText( Format.formatData.format(date) );
		}
		else ultimaCalClick.setText( "" );
		
		if(equipamento.getFabricante() != null)
			fabricanteClick.setText(equipamento.getFabricante());
		else fabricanteClick.setText("");
		
		if(equipamento.getInstrumento() != null) 
			equipamentoClick.setText(equipamento.getInstrumento());
		else 
			equipamentoClick.setText("");
		
		cancelar.setVisible(true);
		salvar.setVisible(true);
		Adcionar.setVisible(false);
				
	}
	
	@FXML
	private void onKeyModelo(KeyEvent e) {
		if(modeloClick.getText().equalsIgnoreCase("Fornero II") ) {
			fabricanteClick.setText("Italterm");
			equipamentoClick.setText("Pirometro Portatil");
		}
	
	}
	
	@FXML
	private void cancelarEdit(ActionEvent e) {
		setEditable(false);		
	}
	
	@FXML
	private void salvarEdit(ActionEvent e) {
		equipamento.setModelo( modeloClick.getText() );  	
		equipamento.setNs( nsClick.getText() );
		equipamento.setPat( patClick.getText() );
		equipamento.setFabricante(fabricanteClick.getText());
		equipamento.setInstrumento(equipamentoClick.getText());
		if(!new EquipamentoUpdatede(equipamento).atualizar())
			error("Atualizar", "Falha ao atualizar o equipamento");
		setEditable(false);
	}
	
//	////////////////////////////////////////////////////////
	
	
	private void editCancel() {
		cancelar.setVisible(false);
		salvar.setVisible(false);
		EditarEquip.setVisible(false);
	}
	
	
	private void setEditable(boolean bool) {
		if(bool) {
			editableHbox(bool);
			clearText();	
			BackgroundFill fill = new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY);
			Background backRed = new Background(fill);
			buscaHbox1.setBackground(backRed);
			buscaHbox2.setBackground(backRed);
		}else {
			buscaHbox1.setBackground(backgroundPadrao);
			buscaHbox2.setBackground(backgroundPadrao);
			editableHbox(bool);
		}
			

	}
	
	private void editableHbox(boolean bool) {
		fabricanteClick.setEditable(bool);
		fabricanteClick.setFocusTraversable(bool);
		equipamentoClick.setEditable(bool);
		equipamentoClick.setFocusTraversable(bool);
		modeloClick.setEditable(bool);
		modeloClick.setFocusTraversable(bool);
		patClick.setEditable(bool);
		patClick.setFocusTraversable(bool);
		nsClick.setEditable(bool);
		nsClick.setFocusTraversable(bool);
		textEmpresa.setEditable(!bool);
		Adcionar.setVisible(bool);
		editCancel();
		
	}
	
	private void clearText() {
		fabricanteClick.setText("");
		equipamentoClick.setText("");
		modeloClick.setText("");
		patClick.setText("");
		nsClick.setText("");
	}
	
	public void addListener() {
		if( dbConection ) {
			obsString = empressaController.findAll();
			filteredList = new FilteredList<>(obsString);  
			inputFilter = new InputFilter<String>( textEmpresa, filteredList );
			textEmpresa.getEditor().textProperty().addListener(inputFilter);	
		}

	}	
	
	private void removeListener() {
		textEmpresa.getEditor().textProperty().removeListener(inputFilter);
		textEmpresa.setValue("");
	}
	

	private void startTableOrcamentos() {
		tableOrcamentos.setEditable(false);	 		
		relatorioTable.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("relatorio"));		
		chegadaTable.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		chegadaTable.setCellFactory( cell -> {
            return new TableCell<Orcamento, Date>() {
                @Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                      
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );		
		
		saidaTable.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
		saidaTable.setCellFactory( cell -> {
            return new TableCell<Orcamento, Date>() {
                @Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                      
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );		
		
		tableOrcamentos.setItems(obsOrcamento);
		
	}


	private String allItens(Long orcamento_id, Orcamento orcamento) {
		ItensRepositoryFind find = new ItensRepositoryFind();
		String output = "";
		try{
			output = output + find.consumoByOrcamentoId(orcamento_id).toString();
			output = output + find.eletricosByOrcamentoId(orcamento_id).toString();
			output = output + find.eletronicosByOrcamentoId(orcamento_id).toString();
			output = output + find.esteticoByOrcamentoId(orcamento_id).toString();
			output = output + find.sinalByOrcamentoId(orcamento_id).toString();
		
			output = output + find.cabosByOrcamentoId(orcamento_id).toString();
		}catch (NullPointerException e) {
		}
		
		output = output + orcamento.toString();
		return output;
	}
		
	private boolean findEmpresa() {
		if(textEmpresa.getValue()== "") {
			error( "Campo nulo " ,"O campo nome da Empressa não pode ser nulo");
			return false;
		}
		try {
			empresa = MainViewController.empressaController.isExist( textEmpresa.getValue() );
			if ( empresa == null ) {
				error( "Empresa" ,"Empresa não existe");
				throw new DbException("Empresa não existe");
			}else{
				nomeEmpressaClick.setText(textEmpresa.getValue());
				return true;
			}
				
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return false;
		}
	}
	
	@FXML
	public void adcionar(ActionEvent event) {
		if(empresa == null)
			if(empresa == 0)
				return;
		Long id;
		String empressaName;
		String modelo;
		String ns;
		String pat;
		String instrumento;
		String fabricante;
		
		
		if(modeloClick.getText()== "" || equipamentoClick.getText() == "" || 
				fabricanteClick.getText() == "" ) {
			error("Campo vazio ", "Existe compo vazio");
			return;
		}
		try {
			empressaName = textEmpresa.getValue();	
			modelo = modeloClick.getText();  		
			ns = nsClick.getText();
			pat = patClick.getText();
			instrumento = equipamentoClick.getText();
			fabricante = fabricanteClick.getText();
			
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
			return;
		}
		try {
			id = equipamentoController.add(new Equipamento(empressaName, modelo, ns, pat, empresa, fabricante, instrumento));
			if(id != 0l) {
				try {
					buscar(new ActionEvent());
				} catch (IOException e) {
					e.printStackTrace();
				}		
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();;
			
		}
		
	}
	
	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
	}
	
}
