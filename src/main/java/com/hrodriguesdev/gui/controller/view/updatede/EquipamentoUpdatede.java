package com.hrodriguesdev.gui.controller.view.updatede;

import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.entities.Equipamento;

public class EquipamentoUpdatede {
	
	private Equipamento equipamento;
	private EquipamentoController equipamentoController= new EquipamentoController();
	
	
//	recebe no construtor o equipamento que vamos atualizar
	public EquipamentoUpdatede( Equipamento equipamento ) {
		this.equipamento = equipamento;
	}

	
//	Atualizar o equipamento com o que foi passado de novo, 
	public boolean atualizar() {
		if((equipamento.getNs().isBlank() && equipamento.getPat().isBlank()) ||
				equipamento.getModelo().isBlank() ||
				equipamento.getFabricante().isBlank() ||
				equipamento.getInstrumento().isBlank()) {
			return false;
		}
		try {			
			if( equipamentoController.updatedeNsPatModelo(equipamento)  ) 
				return true;
			else 	
				return false;		
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			
		}
		return false;
	}	
		
}

