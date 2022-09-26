package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.updatede.ColetorUpdatede;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PaginaBuscaController extends EquipamentoMainView implements Initializable{
  
	private Equipamento equipamento;
	private Orcamento orcamento;
	
	@FXML
	private TextField nomeEmpressaClick, nsClick, patClick,
			modeloClick, dataChegadaClick, relatorioClick, 
			ultimaCalClick, dataSaidaClick, empressaColetaClick,
			dataColetaClick, nomeColetorClick;
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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		addListener();
		startTableOrcamentos();
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
			
			empressaColetaClick.setText("" );
			dataColetaClick.setText( "");
			nomeColetorClick.setText( "");
			dataChegadaClick.setText( "" ); 
			dataSaidaClick.setText( "" );
			itensOrcamentoClick.setText("");
			relatorioClick.setText("");
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
	
	
	@FXML
	protected void enter(KeyEvent event) {
		super.enter(event);
//		Busca pelo nome da empresa com enter
		if(event.getTarget() == textEmpresa)
			try {
				buscar(new ActionEvent());
				removeListener();
				addListener();
			} catch (IOException e) {
				e.printStackTrace();
			}
//		Editar orçamento e tambem saida
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
;
    	}else { 		
    		obsListTableFindEquipamentos = equipamentoController.findAll(); 		
    	}
    	tableFindEquipamentos.setItems(obsListTableFindEquipamentos);
		removeListener();
		addListener();
    }
	
}
