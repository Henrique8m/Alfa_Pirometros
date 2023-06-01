package com.hrodriguesdev.gui.controller.view.insert;

import java.net.URL;
import java.util.ResourceBundle;

import com.hrodriguesdev.dao.repository.CertificadoRepository;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.utilitary.Geral;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/*
 * Chamado ao fechar uma manutencao na classe OrcamentoView
 * Sua view é certificadoInsert.fxml
*/
public class CertificadoInsert implements Initializable {
	private CertificadoRepository repositoryCertificado = new CertificadoRepository();

	private Equipamento equipamento;
	private Ensaios ensaios;
	
	@FXML
	private TextField numeroCertificadoNovo, dataCalibracaoNovo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public CertificadoInsert(Equipamento equipamento, Ensaios ensaios ) {
		this.equipamento = equipamento;
		this.ensaios = ensaios;
	}

	@FXML
	public void salvar(ActionEvent event) {
		if (equipamento != null)
			if (!dataCalibracaoNovo.getText().isBlank() && !numeroCertificadoNovo.getText().isBlank()) {
				if (dataCalibracaoNovo.getText().length() < 10)
					return;
				Long certificado_id = repositoryCertificado
						.add(new Certificado(equipamento.getId(), Geral.dateParceString(dataCalibracaoNovo.getText()),
								Integer.parseInt(numeroCertificadoNovo.getText()), ensaios.getId()));

				if (equipamento.getUltimaCalibDate() == null
						|| equipamento.getUltimaCalibDate().before(Geral.dateParceString(dataCalibracaoNovo.getText())))
					if (certificado_id != null)
						repositoryCertificado.updateEquipamento(certificado_id, equipamento.getId(),
								Geral.dateParceString(dataCalibracaoNovo.getText()));
			}
	}

}
