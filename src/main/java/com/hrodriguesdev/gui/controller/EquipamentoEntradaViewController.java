package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


//Chamado atraves do EquipamentoMainView
//Chamado atraves do EquipamentoInsert
public class EquipamentoEntradaViewController implements Initializable{
	private String empresa;
	@FXML
	protected ImageView cancelarImg, salvarImg,  logoYgg;
	@FXML
	protected Button salvar, cancelar, buscar;
	@FXML
	protected TextField data;
	@FXML
	protected Text erro;
	@FXML
	protected ComboBox<String> nomeEmpressa;
	
	public static ObservableList<String> obsString = FXCollections.observableArrayList();	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@FXML
	private TableView<Equipamento> tableEquipamentos;
	@FXML
	private TableColumn<Equipamento, String> modelo;
	@FXML
	private TableColumn<Equipamento, String> ns;
	@FXML
	private TableColumn<Equipamento, String> pat;
	@FXML
	private TableColumn<Equipamento, Date> ultimaCal;
    private  ObservableList<Equipamento> obsListEquipamentos = FXCollections.observableArrayList();
    
	//@Autowired
	protected OrcamentoController orcamentoController = InjecaoDependency.ORCAMENTO_CONTROLLER;
	protected EquipamentoController equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
	protected ColetorController coletorController = InjecaoDependency.COLETOR_CONTROLLER;
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;
    
    public EquipamentoEntradaViewController() {}
    
    public EquipamentoEntradaViewController(String nomeEmpresa) {
    	this.empresa = nomeEmpresa;
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListener();
		if(empresa != null) {
			nomeEmpressa.setValue(empresa);
			buscar(new ActionEvent());
		}
		
		try {
			startTable();
		} catch (DbException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
	    data.setText(Format.formatData.format(new Date(System.currentTimeMillis())));
	}
	
	@FXML
	private void keyEventCombobox(KeyEvent event){
		if(event.getTarget() == nomeEmpressa) {
			
			if(event.getCode().toString() == "ESCAPE") {
				removeListener();
				addListener();
				
			}
			if(event.getCode().toString() == "ENTER") {
				buscar(new ActionEvent());
				removeListener();
				addListener();
				
			}
			
		}				
		
	}
	
	@FXML
	private void buscar(ActionEvent event) {
		if(nomeEmpressa.getValue()== "") {
			error( "Campo nulo " ,"O campo nome da Empressa não pode ser nulo");
			erro.setVisible(true);
			erro.setText("Empressa nulo");
			return;
		}
		try {
			Long empressa_id = empresaController.isExist( nomeEmpressa.getValue()  );
			if ( empressa_id == null ) {
				erro.setVisible(true);
				erro.setText("Empressa não existe");
				throw new DbException("Empresa não existe");
			}else {
				obsListEquipamentos = equipamentoController.findByIdEmpresa( empressa_id, true);
				tableEquipamentos.setItems(obsListEquipamentos);
				tableEquipamentos.refresh();
			}
			
		}catch(DbException | SQLException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
	}
	
	@FXML
	public void salvar(ActionEvent event) {	
		try {			
			if( tableEquipamentos.getSelectionModel().getSelectedItem().getId() != null && tableEquipamentos.getSelectionModel().getSelectedItem().getLaboratorio() != true) {
				Orcamento orcamento = new Orcamento(
						tableEquipamentos.getSelectionModel().getSelectedItem().getId(), 
						Geral.dateParceString(data.getText() ) , 
						true					
						);
				Long orcamento_id = orcamentoController.add(orcamento);
				if(orcamento_id != null)
					equipamentoController.updatede(tableEquipamentos.getSelectionModel().getSelectedItem().getId(), true, orcamento_id);
				
				InjecaoDependency.MAIN_TAB_CONTROLLER.refreshTableMain();
				NewView.fecharView();		
			}	
		}catch(NullPointerException e) {
			error("Selection", "Nada selecionado");		
		}
	}	
	
	@FXML
	public void cancelar(ActionEvent event) {
		NewView.fecharView();
	}
	
				
	@FXML
	private void equipamentoClick(MouseEvent event) {
		if(event.getClickCount() >= 2) 
			salvar(new ActionEvent());
		
	}
	
	protected void error(String titulo, String mensagem) {
		Alerts.showAlert(titulo, "", mensagem, AlertType.ERROR);
		NewView.fecharView();
	}
	
	private void startTable() throws DbException, SQLException {
		
		tableEquipamentos.setEditable(false); 
		modelo.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		ultimaCal.setCellValueFactory(new PropertyValueFactory<>("ultimaCalibDate"));
		ultimaCal.setCellFactory( cell -> {
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
		
		tableEquipamentos.setItems(obsListEquipamentos);
		
	}
	
	public void addListener() {
		obsString =  empresaController.findAllObs();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( nomeEmpressa, filteredList );
		nomeEmpressa.getEditor().textProperty().addListener(inputFilter);	
		
	}
	
	private void removeListener() {
		nomeEmpressa.getEditor().textProperty().removeListener(inputFilter);
		nomeEmpressa.setValue("");
	}
}
