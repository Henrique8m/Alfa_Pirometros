package com.hrodriguesdev.gui.controller.view.insert;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.hrodriguesdev.dao.repository.CertificadoRepository;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * Chamado ao fechar uma manutencao na classe OrcamentoView
 * Sua view é certificadoInsert.fxml
*/
public class CertificadoInsert implements Initializable {
	private CertificadoRepository repositoryCertificado = new CertificadoRepository();

	private Equipamento equipamento;
	private Ensaios ensaios;
	
	@FXML
	private TextField numeroCertificadoNovo;
	
	@FXML
	private DatePicker dataCalibracaoNovo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dataCalibracaoNovo.setValue(LocalDate.now());

	}
	
	public CertificadoInsert(Equipamento equipamento, Ensaios ensaios ) {
		this.equipamento = equipamento;
		this.ensaios = ensaios;
	}

	@FXML
	public void salvar(ActionEvent event) {		
		if (equipamento != null)
			if (!numeroCertificadoNovo.getText().isBlank()) {
				Long certificado_id = repositoryCertificado
						.add(new Certificado(equipamento.getId(), Date.valueOf(dataCalibracaoNovo.getValue()),
								Integer.parseInt(numeroCertificadoNovo.getText()), ensaios.getId()));

				if (equipamento.getUltimaCalibDate() == null
						|| equipamento.getUltimaCalibDate().before(Date.valueOf(dataCalibracaoNovo.getValue())))
					if (certificado_id != null)
						repositoryCertificado.updateEquipamento(certificado_id, equipamento.getId(),
								Date.valueOf(dataCalibracaoNovo.getValue()));
			}
		Stage stage = (Stage) numeroCertificadoNovo.getParent().getScene().getWindow();
		stage.close();
	}
	
}
