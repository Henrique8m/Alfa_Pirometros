package com.hrodriguesdev.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.resources.files.ReadFiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EnsaioViewController implements Initializable{
	
	public static Logger logger = Logger.getLogger(EnsaioViewController.class);
	
	private Equipamento equipamento;
	private Orcamento orcamento; 
	private EnsaiosController controller = new EnsaiosController();

	
	@FXML
	private TextField refeVal1, refeVal2, refeVal3,
						aplicado1, aplicado2, aplicado3,
						sinalCalibr11, sinalCalibr12, sinalCalibr13,
						sinalCalibr21, sinalCalibr22, sinalCalibr23;
	
	@FXML
	private ImageView salvarImg, cancelarImg;
	
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
		if(controller.isExistByOrcamentoId(orcamento.getId()) ) {
			Ensaios ensaio = controller.getEnsaio(orcamento.getId());
			try {
				writeValues(ensaio);
			}catch(NullPointerException e) {
				readRefPadrao();
			}
			
			logger.info("Orcamento Existe");
		}
		else {			
			readRefPadrao();
		}
	}


	@FXML
	private void salvar( ActionEvent e) {
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
		
		logger.info("id orcamento" + orcamento.getId() );
		
		if(controller.isExistByOrcamentoId(orcamento.getId()) ) {
			ensaio.setId( controller.getEnsaio(orcamento.getId()).getId());
			boolean atualizacaoEnsaio = controller.updatedeEnsaio(ensaio);
			
			logger.error("Atualização de ensaio bem sucedida? " + atualizacaoEnsaio);
			
			if(atualizacaoEnsaio)
				closeStage();
		}else {
			long idEnsaio = controller.saveEnsaio(ensaio);
			
			logger.info("Add novo ensaio, id = " + idEnsaio);
			
			if(idEnsaio != 0) 
				closeStage();

		}

	}
	
	@FXML
	private void cancelar(ActionEvent e) {
		closeStage();
	}
	
	@FXML
	private void salvarReferencia(ActionEvent e) {
		String[] ensaiosRef = new String[] { refeVal1.getText(), refeVal2.getText(), refeVal3.getText() };
		ReadFiles.saveOrUpdatedeFile(equipamento.getModelo(), ensaiosRef);
	}

	
	private void writeValues(Ensaios ensaio) throws NullPointerException{
		String referencia = ensaio.getReferencia();
		String primeiro = ensaio.getPrimeiro();
		String segundo = ensaio.getSegundo();
		String terceiro = ensaio.getTerceiro();
		
		String[] ref = referencia.split("\n");
		String[] pri = primeiro.split("\n");
		String[] seg = segundo.split("\n");
		String[] ter = terceiro.split("\n");
		
		if(ref.length == 3)
			writeRef(ref);
		if(pri.length == 3)
			writeVal1(pri);
		if(seg.length == 3)
			writeVal2(seg);
		if(ter.length == 3)
			writeVal3(ter);
		
	}
	
	private void writeRef(String[] ensaiosRef) {
		refeVal1.setText(ensaiosRef[0]);
		refeVal2.setText(ensaiosRef[1]);
		refeVal3.setText(ensaiosRef[2]);
		
	}
	
	private void writeVal1(String[] value) {
		aplicado1.setText(value[0]);
		aplicado2.setText(value[1]);
		aplicado3.setText(value[2]);
		
	}
	private void writeVal2(String[] value) {
		sinalCalibr11.setText(value[0]);
		sinalCalibr12.setText(value[1]);
		sinalCalibr13.setText(value[2]);
		
	}
	private void writeVal3(String[] value) {
		sinalCalibr21.setText(value[0]);
		sinalCalibr22.setText(value[1]);
		sinalCalibr23.setText(value[2]);
		
	}
	
	private void closeStage() {
		Stage stage = (Stage) refeVal1.getScene().getWindow();
		stage.close();
	}
	
	private void readRefPadrao() {
		String[] ensaiosRef = ReadFiles.readFile(equipamento.getModelo());
		if(ensaiosRef != null ) 
			if(ensaiosRef.length >= 3 ) {
				writeRef(ensaiosRef);
				refeVal1.setFocusTraversable(false);
				refeVal2.setFocusTraversable(false);
				refeVal3.setFocusTraversable(false);
			}	
	}
	
	protected void editable(boolean editable) {
		refeVal1.setEditable(editable);
		refeVal2.setEditable(editable);
		refeVal3.setEditable(editable);
		aplicado1.setEditable(editable);
		aplicado2.setEditable(editable);
		aplicado3.setEditable(editable);
		sinalCalibr11.setEditable(editable);
		sinalCalibr12.setEditable(editable);
		sinalCalibr13.setEditable(editable);
		sinalCalibr21.setEditable(editable);
		sinalCalibr22.setEditable(editable);
		sinalCalibr23.setEditable(editable);
		
		refeVal1.setFocusTraversable(editable);
		refeVal2.setFocusTraversable(editable);
		refeVal3.setFocusTraversable(editable);
		aplicado1.setFocusTraversable(editable);
		aplicado2.setFocusTraversable(editable);
		aplicado3.setFocusTraversable(editable);
		sinalCalibr11.setFocusTraversable(editable);
		sinalCalibr12.setFocusTraversable(editable);
		sinalCalibr13.setFocusTraversable(editable);
		sinalCalibr21.setFocusTraversable(editable);
		sinalCalibr22.setFocusTraversable(editable);
		sinalCalibr23.setFocusTraversable(editable);
	}
	
}
