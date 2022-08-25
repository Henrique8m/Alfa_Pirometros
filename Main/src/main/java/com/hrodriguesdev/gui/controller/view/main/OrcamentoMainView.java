package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.sql.SQLException;

import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.OrcamentoViewController;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.gui.controller.view.updatede.OrcamentoUpdatede;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class OrcamentoMainView extends TableMainView{
	
	public static OrcamentoController controller = new OrcamentoController();
	
    @FXML
    protected void openOrcamento(ActionEvent e) throws IOException {
    	if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
    		equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
    		try {
				orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
				dbConection = true;
			} catch (SQLException e1) {
				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
				dbConection = false;
				return;
			}
    		if(orcamento != null) {
//    			NewView.getNewView("Entrada Equipamento", "orcamentoView", new OrcamentoViewController() );
    			NewView.addChildren((Node) NewView.loadFXML("orcamentoViewDois" , new OrcamentoViewController() ));
    		}else
    			Alerts.showAlert("Orcamento" , "Status Orcamento", "Não consta orçamento para este equipamento" , AlertType.INFORMATION);
    		
    	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
	       
    	refreshTable();

    }
    
    @FXML
	protected void addOrcamento(ActionEvent e) throws IOException, SQLException {
		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {			
			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
			orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
			if(orcamento.getStatus() == 1) 
			{
				
//				NewView.getNewView("Entrada Equipamento", "orcamentoDois", new OrcamentoInsert() );
				NewView.addChildren((Node) NewView.loadFXML("orcamentoDois" , new OrcamentoInsert() ));

			}else openOrcamento(new ActionEvent());

		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
    	
    }   
    
	protected void updatedEquipamento(KeyEvent keyEvent) throws IOException {
	    if(keyEvent.getCode().toString() == "F3" ) {			
			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
			{
				try {
					equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
					orcamentoEdit = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
					NewView.getNewView("Edit Orcamento", "orcamento", new OrcamentoUpdatede() );
					refreshTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
	
			}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
			{
				try {
					equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
					orcamentoEdit = controller.findById( tableFindEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
					NewView.getNewView("Edit Orcamento", "orcamento", new OrcamentoUpdatede() );
					refreshTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	
			}
			 		
	    }
    }
    

}
