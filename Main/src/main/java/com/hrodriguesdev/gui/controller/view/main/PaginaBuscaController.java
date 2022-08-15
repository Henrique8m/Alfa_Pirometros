package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PaginaBuscaController extends EquipamentoMainView implements Initializable{

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
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		addListener();
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	public void click(MouseEvent event) {
		if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
			Equipamento equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
			Coletor coletor = new Coletor();
			Orcamento orcamento = new Orcamento();
			if( equipamento.getColetor_id() != null && equipamento.getColetor_id() != 0 ) {
				coletor = MainViewController.coletorController.findById( equipamento.getColetor_id() );
			}
			if ( equipamento.getOrcamento_id() != null && equipamento.getOrcamento_id() != 0 ) {
				try {
					orcamento = OrcamentoMainView.controller.findById( equipamento.getOrcamento_id() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			nomeEmpressaClick.setText(equipamento.getEmpressaName());
			if( equipamento.getNs()!= null ) nsClick.setText(equipamento.getNs() );
			if( equipamento.getPat()!= null ) patClick.setText(equipamento.getPat() );
			if( equipamento.getModelo()!= null ) modeloClick.setText( equipamento.getModelo() );
			if( equipamento.getDateChegada()!= null ) {
				Date date = new Date( equipamento.getDateChegada().getTime() );
				int day = date.getDate() + 1;
				date.setDate(day);
				dataChegadaClick.setText( Format.formatData.format(date) ); 
			}
			if( equipamento.getRelatorio() != null ) relatorioClick.setText(equipamento.getRelatorio() );
			if( equipamento.getUltimaCalibDate() != null ) {
				Date date = new Date( equipamento.getUltimaCalibDate().getTime() );
				int day = date.getDate() + 1;
				date.setDate(day);
				ultimaCalClick.setText( Format.formatData.format(date) );
			}
			if( equipamento.getDateSaida() != null ) {
				Date date = new Date( equipamento.getDateSaida().getTime() );
				int day = date.getDate() + 1;
				date.setDate(day);
				dataSaidaClick.setText( Format.formatData.format(date) );
			}
			if( equipamento.getColetor_id() != null && equipamento.getColetor_id() != 0) {
				empressaColetaClick.setText(coletor.getEmpressaName() );
				Date date = new Date(coletor.getDate().getTime() );
				int day = date.getDate() + 1;
				date.setDate(day);				
				dataColetaClick.setText( Format.formatData.format(date));
				nomeColetorClick.setText( coletor.getNomeColetor() );
			}
			if( orcamento.getItem() != null ) itensOrcamentoClick.setText(orcamento.getItem());
			
		}
	}	
	
	@FXML
	private void enter(KeyEvent event) {
		if(event.getTarget() == textEmpresa)
			try {
				buscar(new ActionEvent());
				removeListener();
				addListener();
			} catch (IOException e) {
				e.printStackTrace();
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
