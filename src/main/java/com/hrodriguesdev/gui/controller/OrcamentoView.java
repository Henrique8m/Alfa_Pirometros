package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.controller.EstoqueRepController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.SaidaEquipamentoTransacao;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.enums.OSStatus;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.insert.CertificadoInsert;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.relatorio.RelatorioGeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 * chamado na main view em OrcamentoMainView linha 38
 * 
*/

public class OrcamentoView extends EnsaioViewController implements Initializable {
	
	@FXML
	private Button cancelar, orcamentoEnviado, aprovado, aprovadoSemOrca, liberado, naoAprovado, liberadoSemOrcamento, manutencaoArea;
	
	@FXML
	private ImageView cancelarImg, salvarImg, relatorioImg, ensaioImg;
		
	@FXML
	public TextField nomeEmpressa, data, modelo, ns, pat, ultimaCal, relatorioN, fabricanteTxt, equipamentoTxt;
	@FXML
	private TextArea obs;
	
	@FXML
	protected TableView<Product> productSelectedTable = new TableView<>();
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> productsSelected, descriptionSelected, unitMeasurementSelected;
	@FXML
	protected TableColumn<Product, Double> amountSelected;
	
	
	private RelatorioGeneratorPDF pdf = new RelatorioGeneratorPDF();
	private Equipamento equipamento;
	private Orcamento orcamento;

	protected SaidaEquipamentoTransacao transaction = new SaidaEquipamentoTransacao();
	private EnsaiosController ensaioController = new EnsaiosController();
	private EstoqueRepController estoqueController = new EstoqueRepController();
	
	public OrcamentoView(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageInit();
		textFildInserts();
		switchStatus(orcamento.getStatus());
//		obs.setText( allItens(orcamento.getId(), orcamento) );
		findOS();
		startTable();
		
		ensaioGet();
		editable(false);
		obs.setFocusTraversable(false);
		obs.setEditable(false);
		obs.setText(orcamento.getItem());
	}	
	
	@FXML
	public void cancelar(ActionEvent event) {
		NewView.fecharView();
	}

	/*
	 * 
	 * 
	 * Botão de salvar, faz toda uma atualização nos dados do orcamento
	 * 
	 * 
	 * 
	 * 
	 */
	@FXML
	protected void salvar(ActionEvent event) {
		if(orcamento.getStatus() == 100)
			return;
		if(data.getText().length()==10) {
			orcamento.setData_chegada( Geral.dateParceString( data.getText() ) );
		}else{
//			Alerts.showAlert("Data", "Data de entrada errada", null, AlertType.ERROR);
			return;
		}
			
		if(relatorioN.getText() != null && relatorioN.getText() != "" ) {
			orcamento.setRelatorio(relatorioN.getText() );
		}
		try {
			
//			Passa laboratorio para falso, e o retira da fila
//			No cado de manutenção na area, com o orçamento aprovado
			
			if(orcamento.getStatus() == 20) {
				if(!MainViewController.equipamentoController.updateSaida(equipamento) ) {
//					Alerts.showAlert("Equipamento ", "Falha em dar saida no equipamento", "" , AlertType.ERROR);
					return;
				}
			}
			
			
			MainViewController.orcamentoController.updatedeStatusRelatorio( orcamento.getId(), orcamento.getStatus(), orcamento );
//			Alerts.showAlert("Status ", "Status Alterado com sucesso", "Equipamento da Empressa " + equipamento.getEmpressaName() , AlertType.INFORMATION);
		} catch (DbException e1) {
//			Alerts.showAlert("DB exception ", "Erro na comunicação com banco de dados", e1.getMessage(), AlertType.ERROR);
		}
		AlfaPirometrosApplication.viewController.refreshTable();
		try{
			Thread.sleep(200);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		NewView.fecharView();
	}
	
	
	@FXML
	private void ensaioOpen(ActionEvent e) {
		NewView.getNewView("Ensaios","ensaioInserts" , new EnsaioViewController(equipamento, orcamento));
	}
	
	@FXML
	private void relatorio(ActionEvent event) {
		Ensaios ensaios = ensaioController.findByOrcamentoId(orcamento.getId());
		pdf.printRelatorioPdf(equipamento, ensaios, orcamento, obs.getText());
		
	}
	

	
	@FXML
	public void esc(KeyEvent e) {
		
	}
	
	

	/*
	 * 
	 * 
	 * 
	 * Botões para troca de status do orçamento
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	@FXML
	protected void liberado(ActionEvent e) {
		if(!gerarCertificado()) {
			NewView.fecharView();
			Alerts.showAlert("Erro", "Falha ao gerar certificado", "", AlertType.ERROR);
			return;
		}
//		Manutenção executada, baixa no estoque	
		
		if(outProducts(obsMateriais) ) {
			NewView.fecharView();
			Alerts.showAlert("Erro", "Falha ao dar saida no banco de dados", "Ocorreu uma falha ao atualizar o orcamento", AlertType.ERROR);
		}
		
		update(5);
		liberado.setVisible(false);
		aprovadoSemOrca.setVisible(false);
	}
	
//	Botao Orcamento enviado
	@FXML
	private void aguardandoAprovacao(ActionEvent e) {
//		Status 3 = Aguardando Aprovação
		switch (orcamento.getStatus()) {
		case 2:
			update(3);
			orcamentoEnviado.setVisible(false);
			aprovadoSemOrca.setVisible(false);
			manutencaoArea.setVisible(false);
			break;
		case 8:
			update(4);
			liberadoSemOrcamento.setVisible(false);
			break;
		case 9:
			update(5);
			orcamentoEnviado.setVisible(false);
			break;
		case 12:
			update(13);
			orcamentoEnviado.setVisible(false);
			break;
		case 13:
			update(7);
			if(! transaction.updateSaidaEquipamento(equipamento, orcamento)) {
				Alerts.showAlert( "SQL Exeption " ,"Error ao Salvar, id não teve retorno", "",AlertType.ERROR);		
				return;				
			}
			aprovado.setVisible(false);
		case 15:
			update(16);
			orcamentoEnviado.setVisible(false);
		}
		

	}
	
//	Botao aprovado sem orcamento	
	@FXML
	private void aguardandoReparoSemOrca(ActionEvent e) {
//		Status 8 = Aprovado sem orcamento, aquardando reparo!
		update(8);
		orcamentoEnviado.setVisible(false);
		aprovadoSemOrca.setVisible(false);
		manutencaoArea.setVisible(false);
	}
	
	@FXML
	private void manutencaoArea(ActionEvent e) {
		if(!gerarCertificado()) {
			Alerts.showAlert("Erro", "Falha ao gerar certificado", "", AlertType.ERROR);
			return;
		}
		
		if(!estoqueController.saidaDoEstoque(orcamento.getId()) ) {
			NewView.fecharView();
			Alerts.showAlert("Erro", "Falha ao dar saida no banco de dados", "Ocorreu uma falha ao atualizar o orcamento", AlertType.ERROR);
		}
		data.setEditable(true);
		update(15);			
		aprovado.setVisible(false);
		orcamentoEnviado.setVisible(false);
		aprovadoSemOrca.setVisible(false);
		manutencaoArea.setVisible(false);
		
	}

	//	Botao aprovado
	@FXML
	private void aguardandoReparo(ActionEvent e) {
		switch(orcamento.getStatus()) {
		case 13:
			update(7);
			if(! transaction.updateSaidaEquipamento(equipamento, orcamento)) {
				Alerts.showAlert( "SQL Exeption " ,"Error ao Salvar, id não teve retorno", "",AlertType.ERROR);		
				return;				
			}
			break;
		case 16:
			update(20);
			equipamento.setLaboratorio(false);
			break;
		default:
			update(4);	
			break;
		}
		aprovado.setVisible(false);
		naoAprovado.setVisible(false);
	}
	
	
	@FXML
	private void naoAprovado(ActionEvent e) {
		update(6);
		naoAprovado.setVisible(false);
		aprovado.setVisible(false);
		}
	
	@FXML
	private void liberadoSemOrcamento(ActionEvent e) {
		if(!gerarCertificado()) {
			NewView.fecharView();
//			Alerts.showAlert("Erro", "Falha ao gerar certificado", "", AlertType.ERROR);
			return;
		}
		if(outProducts(obsMateriais)  ) {
			NewView.fecharView();
			Alerts.showAlert("Erro", "Falha ao dar saida no banco de dados", "Ocorreu uma falha ao atualizar o orcamento", AlertType.ERROR);
		}
		
		update(9);
		liberadoSemOrcamento.setVisible(false);
		orcamentoEnviado.setVisible(false);
	}
			


	@FXML
	private void format(KeyEvent event) {
    	if(!data.getText().isBlank()) {
    		data.setText( Format.replaceData(data.getText()) );
    		data.end();
    	}
		
	}

	private boolean outProducts(ObservableList<Product> list) {
		List<ProductsOs> listProductsOs = new ArrayList<>();
		list.forEach((product) -> {
			listProductsOs.add(new ProductsOs(orcamento.getId(), product.getId(), product.getQtde()));				
		});		
		OSController osController = new OSController();
		return osController.createNewOSOut(listProductsOs);
	}
	
	private boolean gerarCertificado() {
		Ensaios ensaio = getEnsaio();
		if(ensaio == null) {
			Alerts.showAlert("Erro", "Falta inserir o ensaio", "", AlertType.ERROR);
			return false;
		}
		try {
			NewView.getNewViewModall("Novo certificado",
					(Pane) NewView.loadFXML(FXMLPath.CERTIFICADO, new CertificadoInsert(equipamento, ensaio )) ,
					(Stage) cancelar.getParent().getScene().getWindow());
			
		} catch (IOException e) {
			Log.logString("OrcamentoView", e.getMessage());
			e.printStackTrace();
		} 
		return true;
		
	}	
	
//	Atraves do status em que se encontra o relatorio,
//	Libera o botão pertinente
	

	private void switchStatus(Integer status) {
		if(status.equals(OSStatus.STATUS_3_AG_OS.getStatusInt()) ){
			aprovado.setVisible(true);
			naoAprovado.setVisible(true);			
		}else if(status.equals(OSStatus.STATUS_4_AG_OS.getStatusInt()) ){
			liberado.setVisible(true);
		}else if(status.equals(OSStatus.STATUS_8_AG_OS.getStatusInt()) ){
			liberadoSemOrcamento.setVisible(true);
			orcamentoEnviado.setVisible(true);
		}else if(status.equals(OSStatus.STATUS_9_AG_OS.getStatusInt()) ){
			orcamentoEnviado.setVisible(true);
		}else if(status.equals(OSStatus.STATUS_12_AG_OS.getStatusInt()) ){
			orcamentoEnviado.setVisible(true);
		}else if(status.equals(OSStatus.STATUS_13_AG_OS.getStatusInt()) ){
			aprovado.setVisible(true);
		}else if(status.equals(OSStatus.STATUS_15_AG_OS.getStatusInt()) ){
			orcamentoEnviado.setVisible(true);
		}else if(status.equals(OSStatus.STATUS_16_AG_OS.getStatusInt()) ){
			aprovado.setVisible(true);
		}else if(status.equals(100) ){
			cancelar.setVisible(true);
			orcamentoEnviado.setVisible(true);
			aprovado.setVisible(true);
			aprovadoSemOrca.setVisible(true);
			liberado.setVisible(true); 
			naoAprovado.setVisible(true); 
			liberadoSemOrcamento.setVisible(true); 
			manutencaoArea.setVisible(true);
		}else {
			orcamentoEnviado.setVisible(true);
			aprovadoSemOrca.setVisible(true);
			manutencaoArea.setVisible(true);
		}

	}

	protected void update(int status) {		
		orcamento.setStatus(status);
	}
	
	
//	Inicia as tabelas conforme os dados que vao ser inseridos na mesma
	public void startTable() {	
//		Tabela de produtos selecionados
		productsSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));		
		descriptionSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurementSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountSelected.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));		
		productSelectedTable.setItems(obsMateriais);
	}
	
//	Inicia todas as imagens contidas na view
	protected void imageInit() {
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		relatorioImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-ensaio.png").toString() );
		ensaioImg.setImage(image);
		
	}
	
//	insere as informações dos equipamento assim que a view abre
	protected void textFildInserts() {
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(Format.formatData.format(orcamento.getData_chegada()));
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		
		if( equipamento.getRelatorio() != null) 
			relatorioN.setText( equipamento.getRelatorio() );
		
		if(equipamento.getUltimaCalibDate() != null) 
			ultimaCal.setText( Format.formatData.format(equipamento.getUltimaCalibDate()) );
		
		try {
			fabricanteTxt.setText(equipamento.getFabricante());
			equipamentoTxt.setText(equipamento.getInstrumento());
			
		}catch(NullPointerException e) {
			System.err.println(e.getMessage());
		}
		
	}

	private void findOS() {
		ProductsController controller = new ProductsController();
		obsMateriais = controller.findAllOsByOrcamentoId(orcamento.getId());
		
	}
	
}
