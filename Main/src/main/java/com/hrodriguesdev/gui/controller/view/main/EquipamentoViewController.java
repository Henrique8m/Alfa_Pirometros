package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.sql.SQLException;

import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.OpenSaidaEquipamentoViewController;
import com.hrodriguesdev.gui.controller.SaidaEquipamentoViewController;
import com.hrodriguesdev.gui.controller.StatusViewController;
import com.hrodriguesdev.gui.controller.view.insert.EquipamentoInsert;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.gui.controller.view.updatede.EquipamentoUpdatede;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class EquipamentoViewController extends OrcamentoMainViewController {
	public static EquipamentoController equipamentoController = new EquipamentoController();
	
    
    @FXML
    private void addEquipamento(ActionEvent e) throws IOException {
    	NewView.getNewView("Entrada Equipamento", "entradaEquipamento", new EquipamentoInsert() );    	
    }
	
	@FXML
    protected void updatedEquipamento(KeyEvent keyEvent) throws IOException {
		super.updatedEquipamento(keyEvent);
		if(keyEvent.getCode().toString() == "F5" )
			refreshTable();
			
    	if(keyEvent.getCode().toString() == "F2" ) {
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			equipamentoEdit = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    			NewView.getNewView("Edit Equipamento", "entradaEquipamento", new EquipamentoUpdatede() );

    		}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			equipamentoEdit = tableFindEquipamentos.getSelectionModel().getSelectedItem();
    			NewView.getNewView("Edit Equipamento", "entradaEquipamento", new EquipamentoUpdatede() );

    		}
    		 		
    		
    		
    	}else if(keyEvent.getCode().toString() == "F12" ) {
    		
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
				equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
				equipamento.setLaboratorio(true);
				equipamentoController.updatede(equipamento.getId(), equipamento.getStatus(), equipamento);			

    		}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
				equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
				equipamento.setLaboratorio(true);
				equipamentoController.updatede(equipamento.getId(), equipamento.getStatus(), equipamento);	

    		}
    		refreshTable();		
    		
    	}
    	else if(keyEvent.getCode().toString() == "DELETE" ) {    		
    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
    		{
    			Equipamento equipament = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    			if(equipament.getOrcamento_id() == null || equipament.getOrcamento_id() == 0l) {
    				if (equipamentoController.delete( equipament.getId() ) ) {
    					refreshTable();
    				}else {
    					System.out.println("Nao deletado");
    				}
    					
    			}else 
    				System.out.println("orcamento id " + equipament.getOrcamento_id() );
    		}
    		
    	}    
    	
    }
	
	@FXML
    private void updateStatus(ActionEvent e) throws IOException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			NewView.getNewView("Alterar Status","status", new StatusViewController() );
			try {
				obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
				dbConection = true;
			} catch (DbException | SQLException e1) {
				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
				dbConection = false;
			}
			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
			oldObs = obsListTableFilaEquipamentos;
			tableFilaEquipamentos.refresh();
		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
	   	
    }
	
	@FXML
    private void addOrcamento(ActionEvent e) throws IOException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {			
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id()  == null || tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() == 0) 
			{
				NewView.getNewView("Entrada Equipamento", "orcamento", new OrcamentoInsert() );

			}
			else
			{
				showAlerts("Orcamento ", "", "Ja consta orcamento para o equipamento selecionado ", AlertType.INFORMATION );
			}

		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
    	
    }   
	
	@FXML
    private void addColeta(ActionEvent e) throws IOException {
    	 if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
     		equipamentoEdit = tableFilaEquipamentos.getSelectionModel().getSelectedItem();     		
     		
     		if(equipamentoEdit.getStatus() != 1 ) {
     			if( equipamentoEdit.getColetor_id() == null || equipamentoEdit.getColetor_id() == 0) {
     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new SaidaEquipamentoViewController() );
	     			try {
						obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
			    		oldObs = obsListTableFilaEquipamentos;
			    		dbConection = true;
			    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
					} catch (DbException | SQLException e1) {
					
						e1.printStackTrace();
					}
     			} else{
     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new OpenSaidaEquipamentoViewController());
	     			try {
						obsListTableFilaEquipamentos = equipamentoController.findAllByLaboratorio(true);
			    		oldObs = obsListTableFilaEquipamentos;
			    		dbConection = true;
			    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
					} catch (DbException | SQLException e1) {
					
						e1.printStackTrace();
					}
     			}
     		}else
     			Alerts.showAlert("Saida de Equipamento" , "Equipamento nao pode ser liberado por:", equipamentoEdit.getStatusStr() , AlertType.INFORMATION);
     		
     	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
    }
}
