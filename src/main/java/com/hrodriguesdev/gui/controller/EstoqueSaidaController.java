package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class EstoqueSaidaController  extends OrcamentoInsert implements Initializable{
	
	public EstoqueSaidaController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	private Orcamento orcamento;
	private OrcamentoController orcamentoController;
	private EmpresaController empressaController = new EmpresaController();
	private ColetorController coletorController = new ColetorController();
	
	private Long orcamento_id;
	
	private ObservableList<String> obsString = FXCollections.observableArrayList();	
	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startTable();
		quantidadeItem.setText("1");
		chegada.setVisible(false);
		modeloVbox.setVisible(false);
		nsVbox.setVisible(false);
		patVbox.setVisible(false);
		calVbox.setVisible(false);
		observacaoVbox.setVisible(false);
		empressaVBox.setVisible(false);
		nfeVbox.setVisible(true);
		nfeText.setEditable(true);
		responsavelVbox.setVisible(true);
		empressaVBox1.setVisible(true);
		infoText.setVisible(true);		
		imageInit();
		addListener();
		
	}
	
	@Override
	protected void salvar(ActionEvent event) throws IOException {
		if( !nfeText.getText().isBlank() && nfeText.getText() != "0") 
			if( !empressaComboBox.getValue().isBlank() && empressaComboBox.getValue() != "")
				if( !responsavel.getText().isBlank() && responsavel.getText() != "") 
					if ( empressaController.isExist(empressaComboBox.getValue()) != null ) {
						if(obsMateriais.size()>0){
						
							orcamento = createOrcamento();
							orcamentoController = new OrcamentoController();
							orcamento_id = orcamentoController.add(orcamento);
							orcamento.setId(orcamento_id);						
							
							if(orcamento_id == null || orcamento_id == 0l) {
								erro.setText("Erro ao salvar orcamento");
								return;
							}
							
							int nfe = 0;
							try {
								nfe = Integer.parseInt( nfeText.getText() );					
							}catch(NumberFormatException e){
								Log.logString("EstoqueSaidaController", e.getMessage());
								e.getMessage();
								return;
							}
							
							Coletor coletor = getColetor();		
							if(coletor==null) {
								return;
							}
							
							orcamento.setColetor_id(coletor.getId());
							orcamento.setNfe(nfe);
							if(!orcamentoController.updatede(orcamento)) {
								erro.setText("Erro ao atualizar orcamento");
							};		
							
							if(addOsOut())
								NewView.addChildrenn((Node) NewView.loadFXML(FXMLPath.ESTOQUE , new EstoqueController() ));	
							else
								erro.setText("Erro ao salvar os");
						}else 
							erro.setText("Cambos obrigatorios em branco");
					}
	}
	
	private boolean addOsOut() {
		List<ProductsOs> listProductsOs = new ArrayList<>();
		obsMateriais.forEach((product) -> {
			listProductsOs.add(new ProductsOs(orcamento_id, product.getId(), product.getQtde()));				
		});
		OSController osController = new OSController();		
		return osController.createNewOSOut(listProductsOs);
	}
	
	@FXML
	protected void cancelar(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}

	protected Coletor getColetor() {
		Coletor coletor = new Coletor();
		try {	
			coletor.setOrcamento_id( orcamento_id);	
			coletor.setEmpressaName(empressaComboBox.getValue());
			coletor.setNomeColetor(responsavel.getText());
			coletor.setDate(new java.sql.Date(System.currentTimeMillis()));
			coletor.setHoraColeta( Integer.parseInt( Format.formataTimeInt.format(new Date(System.currentTimeMillis() )  ) ) );
			coletor.setId( coletorController.add(coletor) );
			
		}catch(Exception e) {
			erro.setText(e.getMessage());
			Log.logString("EstoqueSaidaController", e.getMessage());
			return null;
		}
		return coletor;
	}	
		
	protected Orcamento createOrcamento() {
		Orcamento obj = new Orcamento();
		obj.setEquipamento_id(0l);
		obj.setData_chegada(new Date(System.currentTimeMillis() ) );
		obj.setLaboratorio(false);
		return obj;
	}
	
	public void addListener() {
		obsString = empressaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( empressaComboBox, filteredList );	
		empressaComboBox.getEditor().textProperty().addListener(inputFilter);	
		empressaComboBox.setTooltip(new Tooltip("Campo para inserir o nome da empresa"));
		

	}	
}

