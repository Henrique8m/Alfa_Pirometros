package com.hrodriguesdev.gui.controller.view.saida.equipemento;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class OpenSaidaEquipamentoViewController extends SaidaEquipamentoViewController{

	private Equipamento equipamento;
	private Orcamento orcamento;

	public OpenSaidaEquipamentoViewController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}

	@Override
	protected void gerarPDF(ActionEvent event) {
		GeneratorPDF pdf = new GeneratorPDF();	
		Coletor coletor = getColetor();
		Empresa empressa = empresaController.find( equipamento.getEmpresa() );			
		
		pdf.newDocument(coletor, equipamento, empressa, orcamento);
		Stage stage = (Stage) salvar.getScene().getWindow();
		stage.close();
				
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		super.imageInit();
		
		nomeEmpressa.setText(equipamento.getEmpresaName());
	    data.setText(Format.formatData.format( orcamento.getData_chegada() ) );
	    if(equipamento.getUltimaCalibDate() != null)
	    	ultimaCal.setText(Format.formatData.format( equipamento.getUltimaCalibDate() ) );
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		dataColeta.setText(Format.formatData.format( orcamento.getData_saida() ) );	    	    
		
		Coletor coletor = coletorController.findById( orcamento.getColetor_id() );
		nomeColetor.setText( coletor.getNomeColetor() );
		coleta.setValue(  coletor.getEmpresaName() );
		
		nomeColetor.setEditable(false);
		coleta.setEditable(false);
		salvar.setVisible(false);
				
	}	

}
