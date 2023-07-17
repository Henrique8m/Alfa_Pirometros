package com.hrodriguesdev.gui.controller.view.updatede;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.saida.equipemento.SaidaEquipamentoViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;

import javafx.event.ActionEvent;

public class ColetorUpdateViewController extends SaidaEquipamentoViewController{

	public ColetorUpdateViewController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		super.imageInit();
		super.textFildInserts();
		super.coletor = coletorController.findById(orcamento.getColetor_id());
		super.empresa = empresaController.find( equipamento.getEmpresa() );	
		this.textFildInserts();
		
	}	
	
	@Override
	protected void gerarPDF(ActionEvent event) {			
		if(atualizarSaidaDb()) {		
			saidaPdf.newDocument(coletor, equipamento, empresa, orcamento);
			super.fecharView();
		}
				
	}

	@Override
	protected void salvar(ActionEvent event) {
		if(atualizarSaidaDb())
			super.fecharView();
		
	}
	
	protected boolean atualizarSaidaDb() {
		if(!updateColetor()) {
			erro.setText("ERRO");
			return false;
		}		
		orcamento.setData_saida(Geral.dateParceString(dataColeta.getText()));
		if(!orcamentoController.updateDataSaida( orcamento ) ) {
			erro.setText("ERRO");
			return false;
		}
		return true;
	}
	
	protected boolean updateColetor() {
		try {	
			coletor.setNomeColetor(nomeColetor.getText());
			coletor.setDate( Geral.dateParceString(dataColeta.getText()) );
			coletor.setId(orcamento.getColetor_id());
			return coletorController.update(coletor);			
		}catch(DbException e) {
			e.printStackTrace();
			return false;
		}

	}	
	
	protected void textFildInserts() {
		empresaColeta.setValue(coletor.getEmpresaName());
		empresaColeta.setEditable(false);
		dataColeta.setText( Format.formatData.format(coletor.getDate()) );
		nomeColetor.setText(coletor.getNomeColetor());
		nomeColetor.setEditable(false);
	}
	
	
}
