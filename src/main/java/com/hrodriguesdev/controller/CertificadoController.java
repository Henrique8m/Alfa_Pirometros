package com.hrodriguesdev.controller;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.certificado.pdf.PdfCertificado;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.CalibracaoEnsaio;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.DescricaoInstrumento;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Padrao;
import com.hrodriguesdev.service.CertificadoService;

public class CertificadoController {
	private CertificadoService service = new CertificadoService();
	private EquipamentoController equipamentoController = new EquipamentoController();
	private EmpresaController empresaController = new EmpresaController();
	private EnsaiosController ensaioController = new EnsaiosController();
	private PdfCertificado pdfCertificado = new PdfCertificado();
	
	public boolean gerarCertificadoPDF(Certificado certificado) throws ExceptionAlfa , DbException, NullPointerException{
		Equipamento equipamento = equipamentoController.findById(certificado.getEquipamento_id());
		Empresa empresa = empresaController.find(equipamento.getEmpressa());
		DescricaoInstrumento descricao = service.getDescricao(equipamento.getModelo());
		Padrao padrao = service.getPadrao(certificado.getDate_cal());
		Ensaios ensaio = ensaioController.findById(certificado.getEnsaio_id());
		if(ensaio == null)  throw new ExceptionAlfa("Nao tem ensaio para este certificado");
		
		if(descricao.getInstrumento().contentEquals("Analisador carboquimico")) {
			
		}		
		CalibracaoEnsaio calibraco = service.getCalibracaoEnsaio(
				ensaio,
				service.getFem(descricao.getSensor()));
		
		return pdfCertificado.generatedCertificado(
				equipamento, 
				certificado, 
				empresa, 
				descricao, 
				padrao, 
				calibraco);
	}

	public void updateEnsaio(Long ensaio_id, Long id) {
		service.updateEnsaio(ensaio_id,id);		
	}

	public Certificado findById(Long id) {		
		return service.findById(id);
	}
		
}
