package com.hrodriguesdev.gui.controller.view.main;

import com.hrodriguesdev.dao.db.DbException;

public class TableMainView extends CertificadoPaginaController{
	

    public void refreshTable() {
    	try{
			obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
    		oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
    		dbConection = true;  
    		tableFilaEquipamentos.refresh();
    		
		} catch (DbException e1) {
			dbConection = false;
			e1.printStackTrace();
		}
    }

}
