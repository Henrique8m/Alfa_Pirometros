package com.hrodriguesdev.gui.controller.view.main;

import java.sql.SQLException;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


@SuppressWarnings("deprecation")
public class TableMainView extends CertificadoPaginaController implements Runnable{
	private Thread thread = new Thread(this);
	
	public void refreshTable() {    	
		if(thread.getState().toString() == "NEW")
			thread.start();
		else 
			thread.resume();
					
		
    }


	@Override
	public void run() {
		while(true) {
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
    	thread.suspend();
		}
	}
    

}
