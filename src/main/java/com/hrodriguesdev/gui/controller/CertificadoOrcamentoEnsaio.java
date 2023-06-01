package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.CertificadoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.itens.GridPaneEnsaios;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*
 * Chamada no botao de inserir certificado na pagina main na aba certificado
 * certificadoOrcamentoEnsaio.fxml
*/
public class CertificadoOrcamentoEnsaio extends GridPaneEnsaios implements Initializable {
	@FXML
	private TextField numeroCertificadoNovo, dataCalibracaoNovo;
	
	@FXML
	private TableView<Orcamento> tableOrcamentos;
	private static ObservableList<Orcamento> obsOrcamento = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Orcamento, String>  relatorioTable;
	
	@FXML
	private TableColumn<Orcamento, Date>  chegadaTable;
	
	@FXML
	private TableColumn<Orcamento, Date>  saidaTable;
	
	@FXML
	private TextArea itensOrcamentoClick;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startTableOrcamentos();
		numeroCertificadoNovo.setText(String.valueOf(certificado.getNumero()));
		dataCalibracaoNovo.setText(certificado.getDate_cal().toString());
	}
	
	Equipamento equipamento;
	Certificado certificado;
	Ensaios ensaio;
	Orcamento orcamento;
	OrcamentoController orcamentoController = new OrcamentoController();
	CertificadoController certificadoController = new CertificadoController();

	public CertificadoOrcamentoEnsaio(Equipamento equipamento,Certificado certificado) {
		this.certificado = certificado;
		this.equipamento = equipamento;
	}
	
	private void startTableOrcamentos() {
		tableOrcamentos.setEditable(false);	 		
		relatorioTable.setCellValueFactory(new PropertyValueFactory<Orcamento, String>("relatorio"));		
		chegadaTable.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		chegadaTable.setCellFactory( cell -> {
            return new TableCell<Orcamento, Date>() {
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
		
		saidaTable.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
		saidaTable.setCellFactory( cell -> {
            return new TableCell<Orcamento, Date>() {
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
		
		obsOrcamento = FXCollections.observableArrayList();
		obsOrcamento = orcamentoController.findAllNoCertificado(equipamento.getId());
		tableOrcamentos.setItems(obsOrcamento);			
		tableOrcamentos.refresh();						
		itensOrcamentoClick.setText("");
	}
	
	@FXML
	public void clickOrcamento(MouseEvent event) throws SQLException {
		if(equipamento != null)
			if(tableOrcamentos.getSelectionModel().getSelectedItem() != null) {
				orcamento = orcamentoController.getOrcamento( tableOrcamentos.getSelectionModel().getSelectedItem().getId() ); 
				if(orcamento == null)
					return;	
				itensOrcamentoClick.setText(allItens(orcamento.getId(), orcamento));
				ensaio = ensaiosController.findByOrcamentoId(orcamento.getId());
				if(ensaio!= null) {
					writeValues(ensaio);
				}else 
					clearValues();
			}
			
	}
	
	private String allItens(Long orcamento_id, Orcamento orcamento) {
		ItensRepositoryFind find = new ItensRepositoryFind();
		String output = "";
		try{
			output = output + find.consumoByOrcamentoId(orcamento_id).toString();
			output = output + find.eletricosByOrcamentoId(orcamento_id).toString();
			output = output + find.eletronicosByOrcamentoId(orcamento_id).toString();
			output = output + find.esteticoByOrcamentoId(orcamento_id).toString();
			output = output + find.sinalByOrcamentoId(orcamento_id).toString();
		
			output = output + find.cabosByOrcamentoId(orcamento_id).toString();
		}catch (NullPointerException e) {
		}
		
		output = output + orcamento.toString();
		return output;
	}
	
	@FXML
	private void inserirEnsaio() {
		if(orcamento != null) {
			NewView.getNewView("Ensaios","ensaioInserts" , new EnsaioViewController(equipamento, orcamento));
		}
	}
	
	@FXML
	private void salvarCertificado(ActionEvent event) {
		if(ensaio!=null) {
			certificadoController.updateEnsaio(ensaio.getId(), certificado.getId());
			Stage stage = (Stage) numeroCertificadoNovo.getScene().getWindow();
			stage.close();
		}
	}
	
	@FXML
	private void salvarCericado(ActionEvent event) {
		if(ensaio!=null) {
			certificadoController.updateEnsaio(ensaio.getId(), certificado.getId());
			Stage stage = (Stage) numeroCertificadoNovo.getScene().getWindow();
			stage.close();
		}
	}

}


