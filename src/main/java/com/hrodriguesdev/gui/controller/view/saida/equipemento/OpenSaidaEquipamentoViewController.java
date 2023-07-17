package com.hrodriguesdev.gui.controller.view.saida.equipemento;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.updatede.ColetorUpdateViewController;

import javafx.event.ActionEvent;

public class OpenSaidaEquipamentoViewController extends ColetorUpdateViewController{

	public OpenSaidaEquipamentoViewController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	@Override
	protected void gerarPDF(ActionEvent event) {
		saidaPdf.newDocument(coletor, equipamento, empresa, orcamento);
		super.fecharView();				
	}

	@Override
	protected void salvar(ActionEvent event) {
		super.fecharView();		
	}

}
