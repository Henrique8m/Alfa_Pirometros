package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.sql.SQLException;

import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.OrcamentoView;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;

public class OrcamentoMainView extends TableMainView{
	
	public static OrcamentoController controller = new OrcamentoController();
	
    @FXML
    protected void openOrcamento(ActionEvent e) throws IOException {
    	if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
    		Orcamento orcamento;
    		try {
				orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
				dbConection = true;
			} catch (SQLException e1) {
				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
				dbConection = false;
				return;
			}
    		if(orcamento != null) {
	    		if(orcamento.getStatus()>1){
	//    			NewView.getNewView("Entrada Equipamento", "orcamentoView", new OrcamentoViewController() );
	    			NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.OS_VIEW , new OrcamentoView(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento ) ));
	    		}
    		}else
    			Alerts.showAlert("Orcamento" , "Status Orcamento", "Não consta orçamento para este equipamento" , AlertType.INFORMATION);
    		
    	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
	       
    	refreshTable();

    }
    
    @FXML
	protected void addOrcamento(ActionEvent e) throws IOException, SQLException {
		if(!tableFilaEquipamentos.getSelectionModel().isEmpty()) {
			int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
			if(status == 1){
				NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.ADD_PRODUCT_OS , new OrcamentoInsert(
						tableFilaEquipamentos.getSelectionModel().getSelectedItem(),
						controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() ) ) ));

			}else openOrcamento(new ActionEvent());

		}
		else {
			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
		}
    	
    }   
    
//	protected void updatedEquipamento(KeyEvent keyEvent) throws IOException {
//		Orcamento orcamento;
//	    if(keyEvent.getCode().toString() == "F3" ) {			
//			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
//			{
//				try {
//					orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
//					NewView.addChildrenToMain((Node) NewView.loadFXML( FXMLPath.ADD_PRODUCT_OS, new OrcamentoUpdatedeDois(
//							tableFilaEquipamentos.getSelectionModel().getSelectedItem(),
//							orcamento
//							) ));
//					refreshTable();
//					
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//				
//	
//			}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) 
//			{
//				try {
//					orcamento = controller.findById( tableFindEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
//					NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.ADD_PRODUCT_OS, new OrcamentoUpdatedeDois(
//							tableFilaEquipamentos.getSelectionModel().getSelectedItem(),
//							orcamento
//							)));
//					refreshTable();
//					
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//	
//			}
//			 		
//	    }
//	    else if(keyEvent.getCode().toString() == "F2" ) {
//	    	
//	    	if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
//	    		try {
//					orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
//					dbConection = true;
//				} catch (SQLException e1) {
//					showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
//					dbConection = false;
//					return;
//				}
//	    		if(orcamento != null) {
//		    		if(orcamento.getStatus()>1){
//		    			orcamento.setStatus(100);
//		    			NewView.addChildrenToMain((Node) NewView.loadFXML(FXMLPath.OS_VIEW, new OrcamentoView(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento ) ));
//		    		}
//	    		}	    		
//	    	}		       
//	    	refreshTable();
//	    }
//    }
//    

}
