package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpressaController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

public class EstoqueSaidaController  extends OrcamentoInsert implements Initializable{
	
	public EstoqueSaidaController(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}

	private Orcamento orcamento;
	private OrcamentoController orcamentoController;
	private EmpressaController empressaController = new EmpressaController();
	private ColetorController coletorController = new ColetorController();
	
	private Long orcamento_id;
	
	private ObservableList<String> obsString = FXCollections.observableArrayList();	
	
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quantidadeItem.setValue("1");
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
		conboBoxInit();
		tabelaInit();
		imageInit();
		addListener();
		
	}
	
	@Override
	protected void salvar(ActionEvent event) throws IOException {
		if( !nfeText.getText().isBlank() && nfeText.getText() != "0") 
		if( !empressaComboBox.getValue().isBlank() && empressaComboBox.getValue() != "")
		if( !responsavel.getText().isBlank() && responsavel.getText() != "") {
			
				orcamento = createOrcamento();
				orcamentoController = new OrcamentoController();
				
				orcamento_id = orcamentoController.add(orcamento);
				orcamento.setId(orcamento_id);
				
				if(orcamento_id == null) {
					erro.setText("Erro ao salvar orcamento");
					return;
				}
				
				int nfe = Integer.parseInt( nfeText.getText() );
				boolean entrada = false;
				boolean saida = true;
				
				Itens itens = new Itens(orcamento_id, saida , nfe , entrada);				
	
				Coletor coletor = getColetor();
				
				orcamentoController.updatede(orcamento);
			
				orcamento.setColetor_id(coletor.getId());
				
				if(obsMateriais.size()>0) {
					obsMateriais.forEach((orcamento)-> {	
		
					if( !itens.addItem(orcamento.getItemRealString(), orcamento.getQuantidade() ) )	{
							String itemStr = orcamento.getItem();
							this.nova = this.list + itemStr  + "\n";
							this.list = nova;
						}				
					});
					if(!this.list.isEmpty())
						orcamento.setItem(list);		
					orcamento.setStatus(20);
					if(itens.saveAll( orcamento) ) {
						try {
							cancelar( new ActionEvent() );
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}else {
						erro.setText("Erro");
					}
				}
				
			NewView.addChildren((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
		}else 
			erro.setText("Cambos obrigatorios em branco");
		
	}
	
	@FXML
	protected void cancelar(ActionEvent event) throws IOException {
		NewView.addChildren((Node) NewView.loadFXML("estoque" , new EstoqueController() ));	
	}

	protected Coletor getColetor() {
		Coletor coletor = new Coletor();
		if(empressaComboBox.getValue()== "" ||  responsavel.getText()== "" ) {
			erro.setText("O campo nome da Empressa e nome do coletor, não pode estar vazio");
			return null;
		}
		try {	
			if ( empressaController.isExist(empressaComboBox.getValue()) == null ) {
				erro.setText("Empressa nao existe");
				return null;
			}

			coletor.setOrcamento_id( orcamento_id);	
			coletor.setEmpressaName(empressaComboBox.getValue());
			coletor.setNomeColetor(responsavel.getText());
			coletor.setDate(new java.sql.Date(System.currentTimeMillis()));
			coletor.setHoraColeta( Integer.parseInt( Format.formataTimeInt.format(new Date(System.currentTimeMillis() )  ) ) );
			orcamento.setColetor_id( coletorController.add(coletor) );
			
		}catch(DbException e2) {
			erro.setText("Empresa Não Encontrada");
			return null;
		}
		return coletor;
	}	
	
	public void addListener() {
		obsString =  MainViewController.empressaController.findAll();
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( empressaComboBox, filteredList );
		empressaComboBox.getEditor().textProperty().addListener(inputFilter);	
	}
	
	@Override
	protected void removeListner(KeyEvent event) {
		if(event.getCode().toString() == "ESCAPE") {
			empressaComboBox.getEditor().textProperty().removeListener(inputFilter);
			empressaComboBox.setValue("");
		}	
		
	}
	
	protected Orcamento createOrcamento() {
		Orcamento obj = new Orcamento();
		obj.setEquipamento_id(0l);
		obj.setData_chegada(new Date(System.currentTimeMillis() ) );
		obj.setLaboratorio(false);
		return obj;
	}
}

