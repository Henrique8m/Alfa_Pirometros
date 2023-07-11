package com.hrodriguesdev.gui.controller.view.updatede;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dao.repository.OrcamentoRepository;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.controller.view.saida.equipemento.SaidaEquipamentoViewController;
import com.hrodriguesdev.relatorio.GeneratorPDF;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Geral;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ColetorUpdatede extends SaidaEquipamentoViewController{

	public ColetorUpdatede(Equipamento equipamento, Orcamento orcamento) {
		super(equipamento, orcamento);
	}
	
	private OrcamentoRepository orRepo = new OrcamentoRepository();


	@Override
	protected void gerarPDF(ActionEvent event) {
		GeneratorPDF pdf = new GeneratorPDF();	
		Coletor coletor = getColetor();
		Empresa empressa = empresaController.find( equipamento.getEmpresa() );			
		
		pdf.newDocument(coletor, equipamento, empressa, orcamento);
		Stage stage = (Stage) salvar.getScene().getWindow();
		stage.close();
				
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		super.imageInit();
		
		nomeEmpressa.setText(equipamento.getEmpresaName());
	    data.setText(Format.formatData.format( orcamento.getData_chegada() ) );
	    if(equipamento.getUltimaCalibDate() != null)
	    	ultimaCal.setText(Format.formatData.format( equipamento.getUltimaCalibDate() ) );
		modelo.setText(equipamento.getModelo());
		ns.setText(equipamento.getNs());
		pat.setText(equipamento.getPat());
		dataColeta.setText(Format.formatData.format( orcamento.getData_saida() ) );	    	    
		
		Coletor coletor = coletorController.findById( orcamento.getColetor_id() );
		nomeColetor.setText( coletor.getNomeColetor() );
		coleta.setValue(  coletor.getEmpresaName() );
		
		cancelar.setVisible(false);
		coleta.setEditable(false);
		
		dataColeta.setEditable(true);
		nomeColetor.setEditable(true);
	}	
	
	@Override
	protected void salvar(ActionEvent event) {
		if(!updateColetor()) {
			erro.setText("ERRO");
			return;
		}
		
		orcamento.setData_saida(Geral.dateParceString(dataColeta.getText()));
		if(!orRepo.updateDataSaida( orcamento ) ) {
			erro.setText("ERRO");
			return;
		}
		erro.setText("");
	}
	
	protected Boolean updateColetor() {
		Coletor coletor = new Coletor();

		try {	
			coletor.setNomeColetor(nomeColetor.getText());
			coletor.setDate( Geral.dateParceString(dataColeta.getText()) );
			coletor.setId(orcamento.getColetor_id());
			return coletorController.update(coletor);
			
		}catch(DbException e) {
			e.printStackTrace();
			return false;
		}

	}	
	
	
}
