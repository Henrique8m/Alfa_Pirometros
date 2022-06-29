package com.hrodriguesdev.gui.controller.view.entities;

import java.sql.SQLException;

import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.MainViewController;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PaginaBuscaController extends ExtendsMainView{

	@FXML
	private TextField nomeEmpressaClick, nsClick, patClick,
			modeloClick, dataChegadaClick, relatorioClick, 
			ultimaCalClick, dataSaidaClick, empressaColetaClick,
			dataColetaClick, nomeColetorClick;
	@FXML
	private TextArea itensOrcamentoClick;
	
	@FXML
	public void click(MouseEvent event) {
		if(tableFindEquipamentos.getSelectionModel().getSelectedItem() != null) {
			Equipamento equipamento = tableFindEquipamentos.getSelectionModel().getSelectedItem();
			Coletor coletor = new Coletor();
			Orcamento orcamento = new Orcamento();
			if( equipamento.getColetor_id() != null && equipamento.getColetor_id() != 0 ) {
				coletor = MainViewController.coletorController.findById( equipamento.getColetor_id() );
			}
			if ( equipamento.getOrcamento_id() != null && equipamento.getOrcamento_id() != 0 ) {
				try {
					orcamento = MainViewController.controller.findById( equipamento.getOrcamento_id() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			nomeEmpressaClick.setText(equipamento.getEmpressaName());
			if( equipamento.getNs()!= null ) nsClick.setText(equipamento.getNs() );
			if( equipamento.getPat()!= null ) patClick.setText(equipamento.getPat() );
			if( equipamento.getModelo()!= null ) modeloClick.setText( equipamento.getModelo() );
			if( equipamento.getDataChegada()!= null ) dataChegadaClick.setText( equipamento.getDataChegada() ); 
			if( equipamento.getRelatorio() != null ) relatorioClick.setText(equipamento.getRelatorio() );
			if( equipamento.getUltimaCalib() != null ) ultimaCalClick.setText( equipamento.getUltimaCalib() );
			if( equipamento.getDataSaida() != null ) dataSaidaClick.setText( equipamento.getDataSaida() );
			if( coletor!= null) {
				empressaColetaClick.setText(coletor.getEmpressaName() );
				dataColetaClick.setText( coletor.getDataHoraColeta() );
				nomeColetorClick.setText( coletor.getNomeColetor() );
			}
			if( orcamento.getItem() != null ) itensOrcamentoClick.setText(orcamento.getItem());
			
		}
	}	
	
}
