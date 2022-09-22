package com.hrodriguesdev.gui.controller.view.main;

import java.sql.SQLException;

import com.hrodriguesdev.dao.db.DbException;

public class TableMainView extends CertificadoPaginaController{
	

    public void refreshTable() {
    	try{
    		if(!filtro.getText().isEmpty() && !filtro.getText().isBlank() )   			
				obsListTableFilaEquipamentos = equipamentoController.findByName( filtro.getText() );
    		else
    			obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
    		oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
    		dbConection = true;  
    		tableFilaEquipamentos.refresh();
    		
		} catch (DbException | SQLException e1) {
			dbConection = false;
			e1.printStackTrace();
		}
    }

}
