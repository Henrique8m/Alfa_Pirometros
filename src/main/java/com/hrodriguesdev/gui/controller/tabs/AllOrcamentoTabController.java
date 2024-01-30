package com.hrodriguesdev.gui.controller.tabs;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.utilitary.Format;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AllOrcamentoTabController implements Initializable{
	protected OrcamentoController orcamentoController = InjecaoDependency.ORCAMENTO_CONTROLLER;
	List<OrcamentoDTOEquipamento> obsList = FXCollections.observableArrayList();
	
	@FXML
	private Tab tabOs;
	
	@FXML
	private ImageView os;
	
	@FXML
	private TextField osSize;
	
	@FXML
	private DatePicker datePickerLevantamento;
	
	@FXML
	private CheckBox sortSaida, sortChegada, sortOs;
	
	@FXML
	public TableView<OrcamentoDTOEquipamento> tableOs;
	public static ObservableList<OrcamentoDTOEquipamento> obsListTableAllOs = FXCollections.observableArrayList();

	@FXML
	protected TableColumn<OrcamentoDTOEquipamento, String> empresa, status, modelo, ns, pat, relatorio, orcamentoN;
	
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, Date> ultimaCal, dateChegada, dataSaida;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-orcamento.png").toString());
		os.setImage(image);
		strartTable();
		sortTable();
		tabOs.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				refreshTable();			
								
			}
		});
		
	
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	private void levantamento() {
		if(datePickerLevantamento == null)
			return;
		Date dateFirst = Date.valueOf(datePickerLevantamento.getValue() );
		Date dateLast = Date.valueOf(datePickerLevantamento.getValue() );
		
		
		dateFirst.setDate(1);
		dateFirst.setDate(dateFirst.getDate()-1);
		dateLast.setDate(1);
		dateLast.setMonth(dateLast.getMonth()+1);
		
		
		System.out.println(dateFirst);		
		System.out.println(dateLast);
		
		OrcamentoController orcamentoController = new OrcamentoController();
		List<OrcamentoDTOEquipamento> obsMaintenance = orcamentoController.findAllDTOEquipamento();
		
		obsMaintenance = obsMaintenance.stream().filter(x -> {
			if (x.getData_chegada().after(dateFirst)
					|| x.getData_chegada().before(dateLast))
				return true;
			return false;
		}).collect(Collectors.toList());
		
		obsList = orcamentoController.findAllDTOEquipamento();
		
		obsList = obsList.stream().filter(x -> {
			if (x.getData_chegada().after(dateFirst)
					&& x.getData_chegada().before(dateLast)) 
				return true;		
			return false;
		}).collect(Collectors.toList());
		
//		for(OrcamentoDTOEquipamento os: obsList) {
//			System.out.println( os.getEmpresa() + "  " + os.getNs() );
//		}
		
		obsListTableAllOs = FXCollections.observableArrayList();
		obsListTableAllOs.addAll( obsList);		
		tableOs.setItems(obsListTableAllOs);
	}

	private void refreshTable() {
		obsList = orcamentoController.findAllDTOEquipamento();
		
		listShort(obsList); 		
		
		osSize.setText("0" +  obsList.size());
		
		obsListTableAllOs = FXCollections.observableArrayList();
		obsListTableAllOs.addAll( obsList);
		
		tableOs.setItems(obsListTableAllOs);
	}
	
	public void sortTable() {
		sortOs.selectedProperty().addListener((value)->{
			if(sortOs.isSelected()){
				sortChegada.setSelected(false);
				sortSaida.setSelected(false);
				refreshTable();	
			};
		});
		sortChegada.selectedProperty().addListener((value)->{
			if(sortChegada.isSelected()){
				sortOs.setSelected(false);
				sortSaida.setSelected(false);
				refreshTable();	
			};
		});
		sortSaida.selectedProperty().addListener((value)->{
			if(sortSaida.isSelected()){
				sortChegada.setSelected(false);
				sortOs.setSelected(false);
				refreshTable();	
			};
		});

		
    }
	
	public List<OrcamentoDTOEquipamento> listShort(List<OrcamentoDTOEquipamento> list){
		if(sortSaida.isSelected()) {
			try {
							
				list.sort( (a, b) ->{
					if(b.getData_saida()!= null && a.getData_saida()!= null) {						
							return b.getData_saida().compareTo(a.getData_saida());
						}
					return -10;
				});
				
				
			}catch(NullPointerException e) {
				
			}			
		}
		if(sortChegada.isSelected()) {
			try {
				list.sort( (a, b) -> b.getData_chegada().compareTo(a.getData_chegada()) );
			}catch(NullPointerException e) {
				
			}			
		}
		if(sortOs.isSelected()) {
			try {
				list.sort( (a, b) -> {
					if(!b.getOrcamentoN().isBlank() && !a.getOrcamentoN().isBlank()) {
						if(Integer.parseInt(b.getOrcamentoN()) > Integer.parseInt(a.getOrcamentoN()))
							return 1;
						if(Integer.parseInt(b.getOrcamentoN()) < Integer.parseInt(a.getOrcamentoN()))
							return -1;
						if(Integer.parseInt(b.getOrcamentoN()) == Integer.parseInt(a.getOrcamentoN()))
							return 0;
						}
					return 0;
				} );
			}catch(NullPointerException e) {
				
			}			
		}
		
		return list;
	}
	private void strartTable() {	
		
		tableOs.setEditable(false);		    
		orcamentoN.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("orcamentoN"));
	    empresa.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("empresa"));
	    status.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("situation"));	    	    
		dateChegada.setCellValueFactory( new PropertyValueFactory<>("data_chegada"));		
		dateChegada.setCellFactory( cell -> {
            return new TableCell<OrcamentoDTOEquipamento, Date>() {
				@Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );		
		
		modelo.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("modelo"));
		ns.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("ns"));
		pat.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("pat"));
		relatorio.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("relatorio"));
		dataSaida.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
		dataSaida.setCellFactory( cell -> {
            return new TableCell<OrcamentoDTOEquipamento, Date>() {
                @Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                      
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );
		ultimaCal.setCellValueFactory(new PropertyValueFactory<>("ultimaCalibDate"));	
		ultimaCal.setCellFactory( cell -> {
            return new TableCell<OrcamentoDTOEquipamento, Date>() {
                @Override
                protected void updateItem( Date item, boolean empty) {
                   super.updateItem(item, empty);
                   if( !empty ) {
                	   try {
                		   setText( Format.formatData.format(item) );
                	   }catch(NullPointerException e){
                           setText("");
                           setGraphic(null);
                	   }
                      
                   }else {
                      setText("");
                      setGraphic(null);
                   }
                }
            };        
         } );		

		tableOs.setItems(obsListTableAllOs);		
		
	}
}
