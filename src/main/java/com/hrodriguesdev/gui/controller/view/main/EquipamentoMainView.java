package com.hrodriguesdev.gui.controller.view.main;

import java.io.IOException;
import java.sql.SQLException;

import com.hrodriguesdev.controller.EquipamentoController;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class EquipamentoMainView extends LogoutMainView {
	public static EquipamentoController equipamentoController = new EquipamentoController();


	@FXML
	private void clear(ActionEvent e) {
		filtro.setText("");
		FILTER = true;
		refreshTable();
	}
    
    @FXML
    private void addEquipamento(ActionEvent e) throws IOException {
    	NewView.addChildrenToMain((Node) NewView.loadFXML("entradaEquipamento" , new EquipamentoEntradaViewController() ));
    }
    
	
	@FXML
    protected void updatedEquipamento(KeyEvent keyEvent) throws IOException {
//		super.updatedEquipamento(keyEvent);
		
		if(keyEvent.getTarget() == filtro) {
			if(keyEvent.getCode().toString() == "ENTER") {
				clear(new ActionEvent());
				
		    }else if( filtro.getText() != "" ){
		    	FILTER = false;
		    	refreshTable();
		    	/*try{
					obsListTableFilaEquipamentos = equipamentoController.findByName( filtro.getText() );
					
		    		oldObs = obsListTableFilaEquipamentos;
					tableFilaEquipamentos.setItems(obsListTableFilaEquipamentos);			
		    		dbConection = true;  
		    		tableFilaEquipamentos.refresh();
		    		
				} catch (DbException | SQLException e1) {
					dbConection = false;
					e1.printStackTrace();
				}*/
			
		    }else if(filtro.getText() == "") {
		    	FILTER = true;
		    }

		}
		
		if(keyEvent.getCode().toString() == "F5" )
			refreshTable();
		    	
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

	/*
	* evento do click na tabela principal	
	*/
	@FXML
	public void tableFilaEquipamentoClick(MouseEvent event) throws IOException, SQLException {
		if(event.getClickCount() >= 2) {
			if(tableFilaEquipamentos.getSelectionModel().getSelectedItem() != null) {
				int status = tableFilaEquipamentos.getSelectionModel().getSelectedItem().getStatus();
				
//				Status 1, equipamento nao tem orcamento, ir para a pagina para adcionar
				if(status == 1) 
					addOrcamento(new ActionEvent());

				else if(status == 2 || status == 3 || status == 4 || status == 8 || status == 12 || status == 13 || status == 15 || status == 16) 
					super.openOrcamento(new ActionEvent());
				
				else if( status == 5 || status == 6 || status == 9)
					addColeta(new ActionEvent());
			}
				
			
		}
//		status.setSortType(SortType.DESCENDING);
//		status.isSortable();
//
//		tableFilaEquipamentos.sort();
//
//		tableFilaEquipamentos.sortPolicyProperty().set( new Callback<TableView<Equipamento>, Boolean>() {
//		    @Override
//		    public Boolean call(TableView<Equipamento> param) {
//		            Comparator<Equipamento> comparator = new Comparator<Equipamento>() {
//		                @Override
//		                public int compare(Equipamento r1, Equipamento r2) {
//		                
//							return "";
//		            }
//		        };
//		        FXCollections.sort(tableFilaEquipamentos.getItems(), comparator);
//		        return true;
//		    }
//		});
//		
//		
//		System.out.println(tableFilaEquipamentos.getColumns().get(0).getColumns().get(1).sortTypeProperty().getName()  );
//		tableFilaEquipamentos.refresh();
//		
//		
//		
//		tableFilaEquipamentos.viewOrderProperty().addListener( (observable, oldValue, newValue) -> {
//			System.out.println("Observable = " + observable.toString()
//			+ "\nOld Value = " + oldValue.toString() +
//			"\nNew Value = " + newValue);
//		});
//		
//		status.cellFactoryProperty().addListener( (observable, oldValue, newValue) -> {
//			System.out.println("Observable = " + observable.toString()
//			+ "\nOld Value = " + oldValue.toString() +
//			"\nNew Value = " + newValue);
//		});
//		tableFilaEquipamentos.onSortProperty().addListener( (observable, oldValue, newValue) -> {
//			System.out.println("Observable = " + observable.toString()
//			+ "\nOld Value = " + oldValue.toString() +
//			"\nNew Value = " + newValue);
//		});
//		
//		tableFilaEquipamentos.sortPolicyProperty().addListener( (observable, oldValue, newValue) -> {
//			System.out.println("Observable = " + observable.toString()
//			+ "\nOld Value = " + oldValue.toString() +
//			"\nNew Value = " + newValue);
//		});
//		
//		status.addEventHandler(MouseEvent.MOUSE_PRESSED , (key) -> {
//				    System.out.println("ENTER pressed");
//				    System.out.println(key.toString());
//				    System.out.println(key.getTarget().toString());
//				  }); 
//		
//		
//		tableFilaEquipamentos.selectionModelProperty().addListener( (observable, oldValue, newValue) -> {
//			System.out.println("Observable = " + observable.toString()
//			+ "\nOld Value = " + oldValue.toString() +
//			"\nNew Value = " + newValue);
//		});
	
}
}
