package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.sql.SQLException;

import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
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

    }
			
	@FXML
    private void addColeta(ActionEvent e) throws IOException, SQLException {
		Orcamento orcamento;
    	 if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {    		
			int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
			orcamento = controller.findById( tableFilaEquipamentos.getSelectionModel().getSelectedItem().getOrcamento_id() );
			if(status == 5 || status == 6 || status == 9) {					
     			if( orcamento.getColetor_id() == null || orcamento.getColetor_id() == 0) {
     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new SaidaEquipamentoViewController(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento ) );

     			} else{
     				NewView.getNewView("Saida de equipamento", "saidaEquipamento", new OpenSaidaEquipamentoViewController(tableFilaEquipamentos.getSelectionModel().getSelectedItem(), orcamento));

     			}
     		}else
     			Alerts.showAlert("Saida de Equipamento" , "Equipamento nao pode ser liberado por:", "" , AlertType.INFORMATION);
     		
     	}else showAlerts("Seleção ", "", "Nada Selecionado ", AlertType.INFORMATION );
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
