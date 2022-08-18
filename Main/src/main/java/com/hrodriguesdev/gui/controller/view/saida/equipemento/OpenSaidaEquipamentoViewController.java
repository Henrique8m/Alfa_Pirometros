package com.hrodriguesdev.gui.controller.view.saida.equipemento;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class OpenSaidaEquipamentoViewController extends SaidaEquipamentoViewController{

	@Override
	protected void gerarPDF(ActionEvent event) {
		GeneratorPDF pdf = new GeneratorPDF();	
		Coletor coletor = getColetor();
		Empressa empressa = empressaController.find( equipamento.getEmpressa() );			
		
		pdf.newDocument(coletor, equipamento, empressa);
		Stage stage = (Stage) salvar.getScene().getWindow();
		stage.close();
				
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		super.imageInit();
		
		nomeEmpressa.setText(equipamento.getEmpressaName());
	    data.setText(equipamento.getDataChegada());
		ultimaCal.setText(equipamento.getUltimaCalib());
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		dataColeta.setText(Format.formataDateTimeString.format(new Date(System.currentTimeMillis() ) ) );	    	    
		
		dataColeta.setText( MainViewController.equipamentoEdit.getDataSaida() );
		Coletor coletor = coletorController.findById( MainViewController.equipamentoEdit.getColetor_id() );
		nomeColetor.setText( coletor.getNomeColetor() );
		coleta.setValue(  coletor.getEmpressaName() );
		
		nomeColetor.setEditable(false);
		coleta.setEditable(false);
		salvar.setVisible(false);
		addEmpressa.setVisible(false);
		
		if(equipamento.getStatus() == 5) {
			equipamento.setStatus(7);
			equipamento.setLaboratorio(false);
			equipamentoController.updated(equipamento);
			
		}
		
	}	

}
