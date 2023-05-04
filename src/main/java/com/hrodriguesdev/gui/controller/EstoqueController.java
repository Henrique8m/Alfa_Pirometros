package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.EstoqueCabos;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.relatorio.GeneratorPDFEstoque;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class EstoqueController implements Initializable{
	GeneratorPDFEstoque pdf = new GeneratorPDFEstoque();
	private EstoqueConsumo consumo;
	private EstoqueCabos cabos;
	private EstoqueEletricos eletricos;
	private EstoqueEletronicos eletronicos;
	private EstoqueEstetico estetico;
	private EstoqueSinal sinal;
	
	@FXML
	protected Text b9, b9S,
	bfii, bfiiS,
	bi , biS,
	bl, blS,
	bliti, blitiS,
	bm, bmS,
	cb, cbS,
	cbk, cbkS,
	cbs, cbsS,
	ce, ceS,
	cffk, cffkS,
	cffs, cffsS,
	cfsk, cfskS,
	cfss, cfssS,
	ci, ciS,
	cifii, cufiiS,
	cii, ciiS,
	dfkal,dfkalS,
	elfii, elfiiS,
	elfiii, elfiiiS,
	etfii, etfiiS,
	fcdS, fcd,
	fci, fciS,
	fiii, fiiiS,
	fkd, fkdS,
	indi, indiS,
	kfskS, kfsk,
	mc, mcSm,
	mfII, mfIIS,
	mfIII,mfIIIS,
	mfkal, mfkalS, 
	mi, miS,
	p, ps,
	p4f, p4fs,
	pfafiii, pfafiiiS,
	pfiii, pfiiiS,
	pfkal, pfkalS,
	pfs, pfsS,
	plms, plmsS,
	pmk, pmkS,
	pfk, pfkS,
	recil,recilS,
	rk, rkS,
	rs, rsS,
	rsu, rsuS,
	s, sS,
	ts, tsS;
	
	@FXML
	private ImageView saidaMaterialImg, logoYgg, entradaMaterialImg, cancelarImg, pdfImg, relatorioImg;
	
	@FXML
	protected Button cancelar;

	protected Itens itens;
	
	@FXML
	protected void cancelar(ActionEvent event) {
		NewView.fecharView();
	}
	
	@FXML
	protected void salvarSaida(ActionEvent event) {
		NewView.fecharView();
	} 
	
	@FXML
	protected void saidaMaterial(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("orcamentoDois" , new EstoqueSaidaController(new Equipamento(), new Orcamento() ) ));
	}
	
	@FXML
	protected void entradaMaterial(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("orcamentoDois" , new EstoqueEntradaController(new Equipamento(), new Orcamento() ) ));
	}
	
	@FXML
	protected void relatorios(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("relatorios" , new RelatoriosController( ) ));
	}
	
	@FXML
	protected void relatoriosPdf(ActionEvent event) throws IOException {
		if(pdf.newDocument(cabos, consumo, eletricos, eletronicos, estetico, sinal))
			Alerts.showAlert("Relatorio", "Relatorio gerado com sucesso", "", AlertType.INFORMATION);
	}
			

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itens = new Itens();
		consumo = itens.getEstoqueConsumo();
		bl.setText(String.valueOf( consumo.getBotaoLiga() ));		
		bm.setText(String.valueOf( consumo.getBoMeFIIFIIIIndicmax() ));
		cb.setText(String.valueOf( consumo.getCaixaBat() ));
		
		cabos = itens.getEstoqueCabos();
		cbs.setText(String.valueOf( cabos.getS_borracha() ));
		cffs.setText( String.valueOf( cabos.getS_miolo() ));
		cfss.setText(String.valueOf( cabos.getS_extensao() ));
		
		cbk.setText(String.valueOf( cabos.getK_borracha() ));
		cffk.setText(String.valueOf( cabos.getK_miolo() ));
		cfsk.setText(String.valueOf( cabos.getK_extensao() ));
		
		eletricos = itens.getEstoqueEletrico();
		
		fci.setText(String.valueOf( eletricos.getFontCarbIndic() ));		
		fcd.setText(String.valueOf( eletricos.getFontCarbDelta() ));
		
		p4f.setText(String.valueOf( eletricos.getPinFemeAliFII() ));
		pfafiii.setText(String.valueOf( eletricos.getPinFemeAliFIII() ));
		bfii.setText(String.valueOf( eletricos.getBatFIIFIII() ));
		bi.setText(String.valueOf(eletricos.getBatInditemp()));
		
		b9.setText(String.valueOf( eletricos.getBatDescartavel() ));
		bliti.setText( String.valueOf( eletricos.getBatLitio() ) );
		
		ce.setText(String.valueOf( eletricos.getCarrEcil() ));
		ci.setText(String.valueOf( eletricos.getCarrItalterm() ));
		
		eletronicos = itens.getEstoqueEletronicos();
		
		s.setText(String.valueOf( eletronicos.getSirene() ));
		pfiii.setText(String.valueOf( eletronicos.getPCIFIII() ));
		pfkal.setText(String.valueOf( eletronicos.getPCIFKal() ));
		fiii.setText(String.valueOf( eletronicos.getFIII() ));
		indi.setText(String.valueOf(eletronicos.getIndicmax() ));
		cifii.setText( String.valueOf( eletronicos.getCIFII() ) );
		cii.setText( String.valueOf( eletronicos.getCIIndicmax() ));
		dfkal.setText(String.valueOf(eletronicos.getDispFKal()));
		
		estetico = itens.getEstoqueEstetico();
		
		mfII.setText( String.valueOf( estetico.getMascaraFII() ) );
		mfkal.setText( String.valueOf( estetico.getMascaraFKal()));
		mfIII.setText( String.valueOf(estetico.getMascaraFIII()));
		mc.setText( String.valueOf( estetico.getMascaraCarbo()));
		mi.setText( String.valueOf( estetico.getMascaraIndic()));
		elfii.setText( String.valueOf( estetico.getEtiqLatFII()));
		elfiii.setText( String.valueOf( estetico.getEtiqLatFIII()));
		etfii.setText( String.valueOf(estetico.getEtiqTrasFII()));
		p.setText( String.valueOf( estetico.getPunho()));
		
		sinal = itens.getEstoqueSinal();
		
		rs.setText( String.valueOf(sinal.getReceptaculoS()));
		rsu.setText( String.valueOf( sinal.getReceptaculoSU()));
		recil.setText(String.valueOf(sinal.getReceptaculoEcil()));
		rk.setText(String.valueOf(sinal.getReceptaculoK()));
		pfs.setText(String.valueOf(sinal.getPlugFS()));
		pfk.setText(String.valueOf(sinal.getPlugFK()));
		plms.setText(String.valueOf(sinal.getPlugMS()));
		pmk.setText(String.valueOf(sinal.getPlugMK()));
		ts.setText(String.valueOf(sinal.getTomadaS()));
		
//		
		
		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/Yggdrasilicon.jpg").toString() );
		logoYgg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-adicionar.png").toString() );
		entradaMaterialImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-remover.png").toString() );
		saidaMaterialImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-relatorio.png").toString() );
		relatorioImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString() );
		cancelarImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString() );
		pdfImg.setImage(image);
		
	}
	
}
