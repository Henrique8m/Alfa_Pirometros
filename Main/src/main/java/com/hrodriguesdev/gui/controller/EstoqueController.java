package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.utilitary.Itens;
import com.hrodriguesdev.utilitary.NewView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class EstoqueController implements Initializable{
	
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
	reicl,recilS,
	rk, rkS,
	rs, rsS,
	rsu, rsuS,
	s, sS,
	ts, tsS;
	
	@FXML
	private ImageView saidaMaterialImg, logoYgg, entradaMaterialImg, cancelarImg;
	
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
		NewView.addChildren((Node) NewView.loadFXML("orcamentoDois" , new EstoqueSaidaController(new Equipamento(), new Orcamento() ) ));
	}
	
	@FXML
	protected void entradaMaterial(ActionEvent event) throws IOException {
		NewView.addChildren((Node) NewView.loadFXML("orcamentoDois" , new EstoqueEntradaController(new Equipamento(), new Orcamento() ) ));
	}
	
	@FXML
	protected void relatorios(ActionEvent event) throws IOException {
		NewView.addChildren((Node) NewView.loadFXML("orcamentos" , new RelatoriosController( ) ));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itens = new Itens();
		EstoqueConsumo consumo = itens.getEstoqueConsumo();
		bl.setText(String.valueOf( consumo.getBotaoLiga() ));		
		bm.setText(String.valueOf( consumo.getBoMeFIIFIIIIndicmax() ));
		cb.setText(String.valueOf( consumo.getCaixaBat() ));
	}
	
}
