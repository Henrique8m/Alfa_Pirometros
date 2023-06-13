package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.SaidaEquipamentoTransacao;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.insert.CertificadoInsert;
import com.hrodriguesdev.gui.controller.view.main.MainViewController;
import com.hrodriguesdev.relatorio.RelatorioGeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/*
 * chamado na main view em OrcamentoMainView linha 120 e 39
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
	
	private RelatorioGeneratorPDF pdf = new RelatorioGeneratorPDF();
	private Equipamento equipamento;
	private Orcamento orcamento;
	private Itens itens;
	protected SaidaEquipamentoTransacao transaction = new SaidaEquipamentoTransacao();
	private EnsaiosController ensaioController = new EnsaiosController();
	
	public OrcamentoView(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
		this.equipamento = equipamento;
		this.orcamento = orcamento;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itens = new Itens();
		if( equipamento.getRelatorio() != null) 
			relatorioN.setText( equipamento.getRelatorio() );
		
		if(equipamento.getUltimaCalibDate() != null) 
			ultimaCal.setText( Format.formatData.format(equipamento.getUltimaCalibDate()) );
		switchStatus(orcamento.getStatus());		
		nomeEmpressa.setText(equipamento.getEmpressaName());
		data.setText(Format.formatData.format(orcamento.getData_chegada()));
		try {
			fabricanteTxt.setText(equipamento.getFabricante());
			equipamentoTxt.setText(equipamento.getInstrumento());
			
		}catch(NullPointerException e) {
			System.err.println(e.getMessage());
		}
		
		
		
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		
		obs.setText( allItens(orcamento.getId(), orcamento) );
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString() );
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		relatorioImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-ensaio.png").toString() );
		ensaioImg.setImage(image);
		ensaioGet();
		editable(false);
		obs.setFocusTraversable(false);
		obs.setEditable(false);
	}	

	/*
	 * Varre o orcamento e formata o mesmo para ser passado em formato de texto oque
	 * vai ser feito no equipamento
	 */
	
	private String allItens(Long orcamento_id, Orcamento orcamento) {
		ItensRepositoryFind find = new ItensRepositoryFind();
		String output = "";
		
		output = output + find.consumoByOrcamentoId(orcamento_id).toString();
		output = output + find.eletricosByOrcamentoId(orcamento_id).toString();
		output = output + find.eletronicosByOrcamentoId(orcamento_id).toString();
		output = output + find.esteticoByOrcamentoId(orcamento_id).toString();
		output = output + find.sinalByOrcamentoId(orcamento_id).toString();
		try{
			output = output + find.cabosByOrcamentoId(orcamento_id).toString();
		}catch (NullPointerException e) {
		}
		
		output = output + orcamento.toString();
		return output;
	}

//	Atraves do status em que se encontra o relatorio,
//	Libera o botão pertinente
	
	private void switchStatus(int status) {
		switch (status) {
		case 3:
			aprovado.setVisible(true);
			naoAprovado.setVisible(true);
			break;
		case 4:
			liberado.setVisible(true);
			break;
		case 5:			
			break;
		case 6:			
			break;		
		case 8:
			liberadoSemOrcamento.setVisible(true);
			orcamentoEnviado.setVisible(true);
			break;
		case 9:			
			orcamentoEnviado.setVisible(true);
			break;
		case 12:	
			orcamentoEnviado.setVisible(true);
			break;
		case 13:
			aprovado.setVisible(true);
			break;
		case 15:
			orcamentoEnviado.setVisible(true);
			break;
		case 16:
			aprovado.setVisible(true);
			break;
		case 100:
			cancelar.setVisible(true);
			orcamentoEnviado.setVisible(true);
			aprovado.setVisible(true);
			aprovadoSemOrca.setVisible(true);
			liberado.setVisible(true); 
			naoAprovado.setVisible(true); 
			liberadoSemOrcamento.setVisible(true); 
			manutencaoArea.setVisible(true);
		default:
			orcamentoEnviado.setVisible(true);
			aprovadoSemOrca.setVisible(true);
			manutencaoArea.setVisible(true);
			
			break;
		}
		
	}

	protected void update(int status) {		
		orcamento.setStatus(status);
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
		if(!itens.setSaida(orcamento.getId()) ) {
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
			NewView.fecharView();
			Alerts.showAlert("Erro", "Falha ao gerar certificado", "", AlertType.ERROR);
			return;
		}
		
		if(!itens.setSaida(orcamento.getId()) ) {
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
			Alerts.showAlert("Erro", "Falha ao gerar certificado", "", AlertType.ERROR);
			return;
		}
		if(!itens.setSaida(orcamento.getId()) ) {
			NewView.fecharView();
			Alerts.showAlert("Erro", "Falha ao dar saida no banco de dados", "Ocorreu uma falha ao atualizar o orcamento", AlertType.ERROR);
		}
		
		update(9);
		liberadoSemOrcamento.setVisible(false);
		orcamentoEnviado.setVisible(false);
	}
			
	@FXML
	public void cancelar(ActionEvent event) {
		NewView.fecharView();
	}

	@FXML
	private void format(KeyEvent event) {
    	if(!data.getText().isBlank()) {
    		data.setText( Format.replaceData(data.getText()) );
    		data.end();
    	}
		
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
	
	private boolean gerarCertificado() {
		NewView.getNewView("Novo certificado", "certificadoInsert", new CertificadoInsert(equipamento, getEnsaio() ));
		return true;
	}	
}
