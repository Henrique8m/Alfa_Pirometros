package com.hrodriguesdev.gui.controller.view.main;

import java.sql.SQLException;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableMainView extends CertificadoPaginaController{
	

    public void refreshTable() {
		ObservableList<Equipamento> obsList = FXCollections.observableArrayList();
    	try{
    		if(!filtro.getText().isEmpty() && !filtro.getText().isBlank() )   			
    			obsList = equipamentoController.findByName( filtro.getText() );
    		else
    			obsList = orcamentoController.findAllLaboratorio(true);
    		
    		listShort(obsList); 		
    		
    		obsListTableFilaEquipamentos = obsList;
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
