package com.hrodriguesdev.gui.controller.tabs;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.DTO.EquipamentoComboBox;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.updatede.ColetorUpdateViewController;
import com.hrodriguesdev.gui.controller.view.updatede.EquipamentoUpdatede;
import com.hrodriguesdev.gui.controller.view.updatede.NumeroRelatorioUpdate;
import com.hrodriguesdev.gui.controller.view.updatede.OrcamentoUpdate;
import com.hrodriguesdev.relatorio.RelatorioGeneratorPDF;
import com.hrodriguesdev.resources.file.ReadFiles;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
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
import javafx.scene.control.Tooltip;
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

public class FindTabController implements Initializable{
	
	@FXML
	private Button Adcionar, EditarEquip, cancelar, salvar;
	
    @FXML
    private ComboBox<String> textEmpresa;    
    private static ObservableList<String> obsString = FXCollections.observableArrayList();
    private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@FXML
	private ComboBox<String> modeloClick;
    private static ObservableList<String> obsStringModelo = FXCollections.observableArrayList();
    private FilteredList<String> filteredListModelo;
	private InputFilter<String> inputFilterModelo;
	
	@FXML
	private HBox buscaHbox1, buscaHbox2;
	
	@FXML
	private ImageView buscar, buscar1, cancelarImg, pdf, salvarImg, addEquipamento;
	
	@FXML
	private Tab tabBuscar;	
	
	@FXML
	protected TableView<Equipamento> tableFindEquipamentos;
    public static ObservableList<Equipamento> obsListTableFindEquipamentos= FXCollections.observableArrayList();
		
	@FXML
	private TableColumn<Equipamento, String> empressaFind, modeloFind;
	@FXML
	private TableColumn<Equipamento, String> nsFind;
	@FXML
	private TableColumn<Equipamento, String> patFind;
	@FXML
	private TableColumn<Equipamento, Date> dateChegadaFind;
	@FXML
	private TableColumn<Equipamento, Date> dataSaidaFind;

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
	protected TableView<Product> productSelectedTable = new TableView<>();
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	
	@FXML
	protected TableColumn<Product, String> productsSelected, descriptionSelected, unitMeasurementSelected;	
	@FXML
	protected TableColumn<Product, Double> amountSelected;

	@FXML
	private TextField nomeEmpressaClick, nsClick, patClick,
			dataChegadaClick, relatorioClick, 
			ultimaCalClick, dataSaidaClick, empressaColetaClick,
			dataColetaClick, nomeColetorClick, osClick, equipamentoClick, fabricanteClick,
			textNsEquip, textPatEquip;
	

	
	@FXML
	private TextArea itensOrcamentoClick;
	
	//@Autowired
	protected EquipamentoController equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
	protected OrcamentoController orcamentoController = InjecaoDependency.ORCAMENTO_CONTROLLER;
	protected ColetorController coletorController = InjecaoDependency.COLETOR_CONTROLLER;
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;
	
	private Equipamento equipamento;
	private Orcamento orcamento;
	private Background backgroundPadrao;
	private Long empresa = null;	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */
			
		tabBuscar.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				addListener();
				if(MainTabController.comboBoxBusca != "") {
					textEmpresa.setValue(MainTabController.comboBoxBusca);
					
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
		
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() throws InterruptedException {	
		    	startTable();
		    	imageInit();
		    	backgroundPadrao = buscaHbox1.getBackground();
		        return null;
		    }
		};
		new Thread(task).start();	
		textEmpresa.setTooltip(new Tooltip("Campo para inserir o nome da empresa"));
	}
		
	@FXML
	protected void enter(KeyEvent event) {
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
		
	}
	
	
	@FXML
    private void buscar(ActionEvent e) throws IOException {		
		Equipamento equipamento = new Equipamento();
		if(textEmpresa.getValue()!= null)
			if( !textEmpresa.getValue().isEmpty() ) {
	    		equipamento.setEmpresaName(textEmpresa.getValue());
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
    		MainTabController.comboBoxBusca = textEmpresa.getValue();

    	}else { 		
    		obsListTableFindEquipamentos = equipamentoController.findAllObs(); 
    		MainTabController.comboBoxBusca = "";
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
	private void editOrcamento(KeyEvent event){
		if(event.getTarget() == tableOrcamentos) {
			if(event.getCode().toString() == "F2") {
				if(orcamento!=null){
					NewView.getNewView("Editar Orcamento", FXMLPath.ADD_PRODUCT_OS, new OrcamentoUpdate(equipamento, orcamento,obsMateriais,itensOrcamentoClick.getText()));
				}
			}
			else if(event.getCode().toString() == "F3") {
				if(orcamento.getColetor_id()!= null && orcamento.getColetor_id() != 0)
					NewView.getNewView("Editar coletor", FXMLPath.SAIDA_EQUIPAMENTO, new ColetorUpdateViewController(equipamento, orcamento));
			}
		}
	}
	
	@FXML
	private void editar(ActionEvent event) {
		setEditable(true);
		if( equipamento.getNs()!= null ) nsClick.setText(equipamento.getNs() );
		else nsClick.setText("" );
		
		if( equipamento.getPat()!= null ) patClick.setText(equipamento.getPat() );
		else  patClick.setText("");
		
		if( equipamento.getModelo()!= null ) modeloClick.setValue( equipamento.getModelo() );
		else modeloClick.setValue("");
		
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
	public void clickEquipamentos(MouseEvent event) throws SQLException {
		if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
			clearProduct();
			equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
			obsOrcamento = FXCollections.observableArrayList();
			obsOrcamento = orcamentoController.findAllIdEquipamento(equipamento.getId());
			tableOrcamentos.setItems(obsOrcamento);
			
			tableOrcamentos.refresh();
			nomeEmpressaClick.setText(equipamento.getEmpresaName());
			
			if( equipamento.getNs()!= null ) nsClick.setText(equipamento.getNs() );
			else nsClick.setText("" );
			
			if( equipamento.getPat()!= null ) patClick.setText(equipamento.getPat() );
			else  patClick.setText("");
			
			if( equipamento.getModelo()!= null ) modeloClick.setValue( equipamento.getModelo() );
			else modeloClick.setValue("");
			
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
		 if(keyEvent.getCode().toString() == "DELETE" ) {    		
	    	if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
	    		{
	    			Equipamento equipament = tableFindEquipamentos.getSelectionModel().getSelectedItem();
	    			ObservableList<Orcamento> list = orcamentoController.findAllIdEquipamento(equipament.getId());
	    			if(list.size()==1) { 
	    				if(!orcamentoController.delete(list.get(0).getId()) )
	    					Alerts.showAlert("Falha ao deletar", "", "Erro ao deletar o orcamento do equipamento", AlertType.ERROR);
	    			}else if(list.size()>2) {
	    				Alerts.showAlert("Falha ao deletar", "", "Muitos orcamentos para este equipamento", AlertType.ERROR);
	    				return;
	    			}
	    			if( !orcamentoController.existOrcamento(equipament.getId()) ) {
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
				
				if(event!=null && event.getClickCount()>1) {
					NewView.getNewView("Numero Relatorio", "numeroRelatorio", new NumeroRelatorioUpdate(orcamento));
				}else {		
					Coletor coletor = new Coletor();					
					
					if( orcamento.getColetor_id() != null && orcamento.getColetor_id() != 0 ) {						
						coletor = coletorController.findById( orcamento.getColetor_id() );
						empressaColetaClick.setText(coletor.getEmpresaName() );
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
										
					itensOrcamentoClick.setText(orcamento.getItem());
					
					findOS(orcamento.getId());
					
				}
			}
			else orcamento = null;
		setEditable(false);
	}
		
	@FXML
	private void cancelarEdit(ActionEvent e) {
		setEditable(false);		
	}
	
	@FXML
	private void salvarEdit(ActionEvent e) {
		equipamento.setModelo( modeloClick.getValue() );  	
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
		if(bool)
			addListenerModelo();
		else 
			removeListenerModelo();
		modeloClick.setEditable(bool);
		modeloClick.setFocusTraversable(bool);
		modeloClick.setDisable(!bool);
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
		modeloClick.setValue("");
		patClick.setText("");
		nsClick.setText("");
	}
		
	private boolean findEmpresa() {
		if(textEmpresa.getValue()== "") {
			error( "Campo nulo " ,"O campo nome da Empressa não pode ser nulo");
			return false;
		}
		try {
			empresa = empresaController.isExist( textEmpresa.getValue() );
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
		Long id = null;
		String empressaName;
		String modelo;
		String ns;
		String pat;
		String instrumento;
		String fabricante;
		
		
		if(modeloClick.getValue()== "" || equipamentoClick.getText() == "" || 
				fabricanteClick.getText() == "" ) {
			error("Campo vazio ", "Existe compo vazio");
			return;
		}
		try {
			empressaName = textEmpresa.getValue();	
			modelo = modeloClick.getValue();  		
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
	
	@FXML
	private void GerarRelatorio(ActionEvent event) {
		EnsaiosController ensaioController = new EnsaiosController();
		RelatorioGeneratorPDF pdf = new RelatorioGeneratorPDF();
		if(orcamento != null)
			if(orcamento.getId() != null) {
				Ensaios ensaios = ensaioController.findByOrcamentoId(orcamento.getId());
				
				try{
					if(ensaios.getPrimeiro()==null || ensaios.getPrimeiro().isBlank()) 
						throw new NullPointerException();		
				}catch(NullPointerException e) {
					Alerts.showAlert("Falta ensaios", "Favor inserir o primeiro ensaio", "", AlertType.ERROR);
					return;
				}
				
				pdf.printRelatorioPdf(equipamento, ensaios, orcamento,itensOrcamentoClick.getText() , obsMateriais);					
			}
		
	
	}
	
	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
	}
	
	private void findOS(Long id) {
		ProductsController controller = new ProductsController();
		obsMateriais = controller.findAllOsByOrcamentoId(id);
		productSelectedTable.setItems(obsMateriais);
	}
	
	private void clearProduct() {
		obsMateriais = FXCollections.observableArrayList();
		productSelectedTable.setItems(obsMateriais);
	}
	
	private void addListener() {
		obsString = empresaController.findAllObs();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( textEmpresa, filteredList );	
		textEmpresa.getEditor().textProperty().addListener(inputFilter);	
		textEmpresa.hide();
					
	}	
	
	private void addListenerModelo() {
		ReadFiles readFiles = new ReadFiles();		
	
		obsStringModelo = readFiles.findAllModelos();
		filteredListModelo = new FilteredList<>(obsStringModelo);  
		inputFilterModelo = new InputFilter<String>( modeloClick, filteredListModelo );	
		modeloClick.getEditor().textProperty().addListener(inputFilterModelo);	
		modeloClick.hide();
		
		modeloClick.valueProperty().addListener((newValue)->{
			if(modeloClick.isEditable()) {
				EquipamentoComboBox combo = readFiles.readEquipamento(modeloClick.getValue());
				fabricanteClick.setText(combo.getFabricante());
				equipamentoClick.setText(combo.getEquipamento());				
			}
			
		});
	}
	
	private void removeListenerModelo() {
		try {
			modeloClick.getEditor().textProperty().removeListener(inputFilterModelo);
			modeloClick.setValue("");			
		}
		catch(NullPointerException e) {}
	}
	
	
	private void removeListener() {
		textEmpresa.getEditor().textProperty().removeListener(inputFilter);
		textEmpresa.setValue("");
	}
	
    private void imageInit() {			
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString() );
		buscar.setImage(image);
		buscar1.setImage(image);
		image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdf.setImage(image);
		image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		addEquipamento.setImage(image);
		
    }
	
	private void startTable() {
		tableFindEquipamentos.setEditable(false);	 	    
	    empressaFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empresaName"));
	    modeloFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
	    nsFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
	    patFind.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
	    dateChegadaFind.setCellValueFactory( new PropertyValueFactory<>("dateChegada"));
	    dateChegadaFind.setCellFactory( cell -> {
            return new TableCell<Equipamento, Date>() {
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
	    
	    dataSaidaFind.setCellValueFactory(new PropertyValueFactory<>("dateSaida"));	
	    dataSaidaFind.setCellFactory( cell -> {
            return new TableCell<Equipamento, Date>() {
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
	    tableFindEquipamentos.setItems(obsListTableFindEquipamentos);	
	    
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
		
//		Tabela de produtos selecionados
		productsSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));		
		descriptionSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurementSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountSelected.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));		
		productSelectedTable.setItems(obsMateriais);
		
	}
	
}
