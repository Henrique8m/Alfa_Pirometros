package com.hrodriguesdev.gui.controller.view.updatede;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.EquipamentoViewControllerDois;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class EquipamentoUpdatede extends EquipamentoViewControllerDois implements Initializable{
	
	private Equipamento equipamento = MainViewController.equipamentoEdit;
	private Orcamento orcamento = MainViewController.orcamento;

	
	@Override
	public void adcionar(ActionEvent event) {
		if(nomeEmpressa.getValue()== "" ||  modeloTxt.getText()== "" ) {
			error( "Campo nulo " ,"O campo nome da Empressa e Modelo, não pode ser nulo");
			return;
		}
		try {
			equipamento.setEmpressa( MainViewController.empressaController.isExist( nomeEmpressa.getValue() ) );
			if ( equipamento.getEmpressa() == null ) {
				throw new DbException("Empresa não existe");
			}
			
		}catch(DbException e2) {
			error( "Find Empresa" ,"Empresa Não Encontrada");
			return;
		}
		try {
			equipamento.setEmpressaName( nomeEmpressa.getValue() );	
			equipamento.setModelo( modeloTxt.getText() );  
			equipamento.setStatus( 1 );

//			orcamento.setData_chegada( Geral.dateParce( data.getText() ) );			
			equipamento.setNs( nsTxt.getText() );
			equipamento.setPat( patTxt.getText() );

//			equipamento.setUltimaCalib( ultimaCalTxt.getText() );	
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			error( "Null Pointer " ,"Null Pointer Exeption");			
			return;
		}
		try {
			
			if( equipamentoController.updated(equipamento)  ) {
				NewView.fecharView();
				
			}else {
				error( "SQL Exeption " ,"Error ao Salvar, id não teve retorno");		
				return;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			erro.setText("ERRO");
			
		}
		
		AlfaPirometrosApplication.viewController.refreshTable();
		
	}	
		
	@Override
	public void format(KeyEvent event) {
		if(event.getCode().toString() != "BACK_SPACE" ) {
				
			 if(event.getTarget().equals(ultimaCal)){
				 ultimaCal.setText(Format.replaceData( ultimaCal.getText() ) );
				 ultimaCal.end();
			 }
			 if(event.getTarget().equals(data)){
				 data.setText(Format.replaceData( data.getText() ) );
				 data.end();
			 }
	
		}
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<String> obsString = FXCollections.observableArrayList();
		obsString = MainViewController.empressaController.findAll();
		FilteredList<String> filteredList = new FilteredList<>(obsString);  
		nomeEmpressa.getEditor().textProperty().addListener(new InputFilter<String>( nomeEmpressa, filteredList ) );		
	
		Image image =  new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		addEmpressaImg.setImage(image);
	
		nomeEmpressa.setValue(equipamento.getEmpressaName());
	    data.setText( Format.formatData.format( orcamento.getData_chegada() ) );
		ultimaCal.setText(equipamento.getUltimaCalib());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		data.setEditable(true);
		    
	}


}

