package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.itens.GridPaneEnsaios;
import com.hrodriguesdev.resources.file.ReadFiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Onde sao inseridos os ensaios e tambem informacoes do equipamento
 * salvando direto no diretorio
 * chamado por CertificadoOrcamentoEnsaio
 * chamado por ensaio orcamentoViewController
 * ensaioInserts.fxml
*/
public class EnsaioViewController extends GridPaneEnsaios implements Initializable {
	
//	public static Logger logger = Logger.getLogger(EnsaioViewController.class);
	
	protected Equipamento equipamento;
	private Orcamento orcamento; 

	@FXML
	protected ImageView salvarImg, cancelarImg;
	
	@FXML
	protected TextField Sensor, Unidade, FEM,Resolucao,Fabricante,Modelo,Instrumento,Medida,ModeloFile;
	
	@FXML
	protected VBox VBoxInfo;
	
	@FXML
	protected MenuItem salvarReferencia;
	
	public EnsaioViewController(Equipamento equipamento, Orcamento orcamento) {
		this.orcamento = orcamento;
		this.equipamento = equipamento;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BasicConfigurator.configure();
		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-salvar-arquivo.png").toString());
		salvarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString());
		cancelarImg.setImage(image);
		
		ensaioGet();
	}


	protected void ensaioGet() {
		if(ensaiosController.isExistByOrcamentoId(orcamento.getId()) ) {
			Ensaios ensaio = getEnsaio();
			try {
				writeValues(ensaio);
			}catch(NullPointerException e) {
				readRefPadrao();
			}
			
//			logger.info("Orcamento Existe");
		}
		else {			
			readRefPadrao();
		}
	}
	
	protected Ensaios getEnsaio() {
		return ensaiosController.findByOrcamentoId(orcamento.getId());
	}


	@FXML
	protected void salvar( ActionEvent e) {
		Ensaios ensaio = new Ensaios(equipamento.getId(), orcamento.getId());
		ensaio.setReferencia(
				refeVal1.getText() + "\n"
				+ refeVal2.getText() + "\n"
				+ refeVal3.getText());
		ensaio.setPrimeiro(aplicado1.getText() + "\n"
				+ aplicado2.getText()  + "\n"
				+ aplicado3.getText());
		
		ensaio.setSegundo(sinalCalibr11.getText() + "\n"
				+ sinalCalibr12.getText()  + "\n"
				+ sinalCalibr13.getText());
		
		ensaio.setTerceiro(sinalCalibr21.getText()  + "\n"
				+ sinalCalibr22.getText() + "\n"
				+ sinalCalibr23.getText());
		
		ensaio.setOrcamento_id(orcamento.getId());
		
//		logger.info("id orcamento" + orcamento.getId() );
		
		if(ensaiosController.isExistByOrcamentoId(orcamento.getId()) ) {
			ensaio.setId( ensaiosController.findByOrcamentoId(orcamento.getId()).getId());
			boolean atualizacaoEnsaio = ensaiosController.updatedeEnsaio(ensaio);
			
//			logger.error("Atualização de ensaio bem sucedida? " + atualizacaoEnsaio);
			
			if(atualizacaoEnsaio)
				closeStage();
		}else {
			long idEnsaio = ensaiosController.saveEnsaio(ensaio);
			
//			logger.info("Add novo ensaio, id = " + idEnsaio);
			
			if(idEnsaio != 0) 
				closeStage();

		}

	}
	
	@FXML
	protected void cancelar(ActionEvent e) {
		closeStage();
	}
	
	@FXML
	protected void salvarReferencia(ActionEvent e) {
		String modelo = "Modelo=" + Modelo.getText();
		String instrumento = "Instrumento=" + Instrumento.getText();
		String fabricante = "Fabricante=" + Fabricante.getText();
		String resolucao = "Resolucao=" + Resolucao.getText();
		String fem = "FEM="+ FEM.getText();
		String unidade = "Unidade=" + Unidade.getText();
		String sensor = "Sensor=" + Sensor.getText();
		String medida = "Medida=" + Medida.getText();
		
		String[] ensaiosRef = new String[] { 
				refeVal1.getText(),
				refeVal2.getText(), 
				refeVal3.getText(),
				modelo,
				instrumento,
				fabricante,
				resolucao,
				fem,
				unidade,
				sensor,	
				medida
		};
		ReadFiles.saveOrUpdatedeFile(equipamento.getModelo(), ensaiosRef);
	}
	
	@FXML
	protected void infoEquip(ActionEvent e) {
		VBoxInfo.setVisible(!VBoxInfo.isVisible());
		salvarReferencia.setVisible(!salvarReferencia.isVisible());
		try {
			String[] ensaiosRef = ReadFiles.readFile(equipamento.getModelo());
			if(ensaiosRef != null ) 
				for(String info: ensaiosRef) {
					String[] split = info.split("=");
					if(split.length>1) {
						String descricao = split[0];
						String informacao = split[1];
						switch (descricao.toString()) {
							case "Modelo":
								Modelo.setText(informacao);
								break;
							case "Instrumento":
								Instrumento.setText(informacao);
								break;
							case "Fabricante":
								Fabricante.setText(informacao);
								break;
							case "Resolucao":
								Resolucao.setText(informacao);
								break;
							case "FEM":
								FEM.setText(informacao);
								break;
							case "Unidade":
								Unidade.setText(informacao);
								break;
							case "Medida":
								Medida.setText(informacao);
								break;
							case "Sensor":
								Sensor.setText(informacao);
							default:
								break;
						}
					}
				}
				
		}catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	protected void closeStage() {
		Stage stage = (Stage) refeVal1.getScene().getWindow();
		stage.close();
	}
	
	protected void readRefPadrao() {
		try {
			String[] ensaiosRef = ReadFiles.readFile(equipamento.getModelo());
			if(ensaiosRef != null ) 
				if(ensaiosRef.length >= 3 ) {
					writeRef(ensaiosRef);
					refeVal1.setFocusTraversable(false);
					refeVal2.setFocusTraversable(false);
					refeVal3.setFocusTraversable(false);
				}	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	@FXML
	protected void autoCompletar(ActionEvent e) {		
		String[] ref = new String [] { 
				refeVal1.getText(),
				refeVal2.getText(),
				refeVal3.getText()};

		if(ref.length == 3)
			writeRef(ref);
			writeVal1(ref);
			writeVal2(ref);
			writeVal3(ref);	
	}
	
	@FXML
	protected void completInfoFornero(ActionEvent e) {
		String equip = equipamento.getModelo();
		equipamento.setModelo("Fornero II");
		infoEquip(e); 
		equipamento.setModelo(equip);
	}
	
	@FXML
	protected void completInfoCarbomax(ActionEvent e) {
		String equip = equipamento.getModelo();
		equipamento.setModelo("Carbomax II");
		infoEquip(e); 
		equipamento.setModelo(equip);
	}
	
}
