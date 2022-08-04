package com.hrodriguesdev.gui.controller.view.main;

import java.sql.SQLException;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.gui.controller.view.MainViewController;

public class TableMainView extends MainViewController{
	

    public void refreshTable() {
    	try{
			obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
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
