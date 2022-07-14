package com.hrodriguesdev.gui.controller.view.table;

import java.sql.SQLException;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.gui.controller.view.MainViewController;

import javafx.fxml.FXML;

public class TableViewController extends MainViewController{
	
	@FXML
    public void refreshTable() {
    	try{
			obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
//    		oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
    		dbConection = true;  
    		tableFilaEquipamentos.refresh();
    		
		} catch (DbException | SQLException e1) {
			dbConection = false;
			e1.printStackTrace();
		}
    }

}
