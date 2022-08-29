package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.sql.SQLException;

import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.EquipamentoEntradaViewController;
import com.hrodriguesdev.gui.controller.view.saida.equipemento.OpenSaidaEquipamentoViewController;
import com.hrodriguesdev.gui.controller.view.saida.equipemento.SaidaEquipamentoViewController;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class EquipamentoMainView extends LogoutMainView {
	public static EquipamentoController equipamentoController = new EquipamentoController();
	
	@FXML
	private TextField filtro;
	

	@FXML
	private void clear(ActionEvent e) {
		filtro.setText("");
		FILTER = true;
		refreshTable();
	}
    
    @FXML
    private void addEquipamento(ActionEvent e) throws IOException {
    	NewView.addChildren((Node) NewView.loadFXML("entradaEquipamentoDois" , new EquipamentoEntradaViewController() ));
    }
    
	
	@FXML
    protected void updatedEquipamento(KeyEvent keyEvent) throws IOException {
		super.updatedEquipamento(keyEvent);
		
		if(keyEvent.getTarget() == filtro) {
			if(keyEvent.getCode().toString() == "ENTER") {
				clear(new ActionEvent());
				
		    }else if( filtro.getText() != "" ){
		    	FILTER = false;
			
		    	try{
					obsListTableFilaEquipamentos = equipamentoController.findByName( filtro.getText() );
		    		oldObs = obsListTableFilaEquipamentos;
					tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
		    		dbConection = true;  
		    		tableFilaEquipamentos.refresh();
		    		
				} catch (DbException | SQLException e1) {
					dbConection = false;
					e1.printStackTrace();
				}
			
		    }else if(filtro.getText() == "") {
		    	FILTER = true;
		    }

		}
		
		if(keyEvent.getCode().toString() == "F5" )
			refreshTable();

		else if(keyEvent.getCode().toString() == "F12" ) {
    		
//    		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) 
//    		{
//				equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
//				equipamento.setLaboratorio(true);
//				equipamentoController.updatede(equipamento.getId(), equipamento.getStatus(), equipamento);			
//
//    		}else if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
//				equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
//				equipamento.setLaboratorio(true);
//				equipamentoController.updatede(equipamento.getId(), equipamento.getStatus(), equipamento);	
//
//    		}
//    		refreshTable();		
    		
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
//		if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
//			equipamento = tableFilaEquipamentos.getSelectionModel().getSelectedItem();
//			NewView.getNewView("Alterar Status","status", new StatusViewController() );
//			try {
//				obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
//				dbConection = true;
//			} catch (DbException e1) {
//				showAlerts("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR );
//				dbConection = false;
//			}
//			tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
//			oldObs = obsListTableFilaEquipamentos;
//			tableFilaEquipamentos.refresh();
//		}
//		else {
//			showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
//		}
//	   	
    }
	
		
	@FXML
    private void addColeta(ActionEvent e) throws IOException, SQLException {
//    	 if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
//     		equipamentoEdit = tableFilaEquipamentos.getSelectionModel().getSelectedItem(); 
//     		orcamentoColeta = orcamentoController.getOrcamento( equipamentoEdit.getOrcamento_id() );     		
//     		
//     		if(equipamentoEdit.getStatus() != 1 ) {
//     			if( orcamentoColeta.getColetor_id() == null || orcamentoColeta.getColetor_id() == 0) {
//     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new SaidaEquipamentoViewController() );
//	     			try {
//	    				obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
//			    		oldObs = obsListTableFilaEquipamentos;
//			    		dbConection = true;
//			    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
//					} catch (DbException e1) {
//					
//						e1.printStackTrace();
//					}
//     			} else{
//     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new OpenSaidaEquipamentoViewController());
//	     			try {
//	    				obsListTableFilaEquipamentos = orcamentoController.findAllLaboratorio(true);
//			    		oldObs = obsListTableFilaEquipamentos;
//			    		dbConection = true;
//			    		tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);
//					} catch (DbException e1) {
//					
//						e1.printStackTrace();
//					}
//     			}
//     		}else
//     			Alerts.showAlert("Saida de Equipamento" , "Equipamento nao pode ser liberado por:", equipamentoEdit.getStatusStr() , AlertType.INFORMATION);
//     		
//     	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
    }
	
	
	@FXML
	public void tableFilaEquipamentoClick(MouseEvent event) throws IOException, SQLException {
		if(event.getClickCount() >= 2) {
			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
				int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
				if(status == 1) 
					addOrcamento(new ActionEvent());

				else if(status == 2 || status == 3 || status == 4 || status == 8 || status == 12 || status == 13) 
					super.openOrcamento(new ActionEvent());
				else if( status == 5 || status == 6 || status == 9)
					addColeta(new ActionEvent());
			}
				
		}
	
}
}
