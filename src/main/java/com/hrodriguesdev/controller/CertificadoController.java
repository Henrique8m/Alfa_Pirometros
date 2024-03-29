package com.hrodriguesdev.controller;

import java.sql.Date;
import java.util.List;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.CalibracaoEnsaio;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.DescricaoInstrumento;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Padrao;
import com.hrodriguesdev.entities.DTO.CertificadoDTO;
import com.hrodriguesdev.relatorio.certificado.PdfCertificado;
import com.hrodriguesdev.service.CertificadoService;

public class CertificadoController {
	
	private EquipamentoController equipamentoController;
	private CertificadoService certificadoService = new CertificadoService();
	private EmpresaController empresaController;
	private EnsaiosController ensaioController;
	private PdfCertificado pdfCertificado = new PdfCertificado();
	
	public boolean gerarCertificadoPDF(Certificado certificado) throws ExceptionAlfa , DbException, NullPointerException{
		equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
		empresaController = InjecaoDependency.EMPRESA_CONTROLLER;
		ensaioController = InjecaoDependency.ENSAIO_CONTROLLER;

		
		Equipamento equipamento = equipamentoController.findById(certificado.getEquipamento_id());
		Empresa empresa = empresaController.find(equipamento.getEmpresa());
		DescricaoInstrumento descricao = certificadoService.getDescricao(equipamento.getModelo());
		Padrao padrao = certificadoService.getPadrao(certificado.getDate_cal());
		Ensaios ensaio = ensaioController.findById(certificado.getEnsaio_id());
		if(ensaio == null)  throw new ExceptionAlfa("Nao tem ensaio para este certificado");
		
		if(descricao.getInstrumento().contentEquals("Analisador carboquimico")) {
			
		}		
		CalibracaoEnsaio calibraco = certificadoService.getCalibracaoEnsaio(
				ensaio,
				certificadoService.getFem(descricao.getSensor()));
		
		return pdfCertificado.generatedCertificado(
				equipamento, 
				certificado, 
				empresa, 
				descricao, 
				padrao, 
				calibraco);
	}

	public void updateEnsaio(Long ensaio_id, Long id) {
		certificadoService.updateEnsaio(ensaio_id,id);		
	}
	
	public void updateEquipamento(Long certificado_id, Long id, Date valueOf) {
		certificadoService.updateEquipamento(certificado_id, id, valueOf);
	}

	public Certificado findById(Long id) {		
		return certificadoService.findById(id);
	}

	public List<Certificado> findAllByEquipamento(Long id) {
		return certificadoService.findAllByEquipamento(id);
	}

	public Long add(Certificado certificado) {
		return certificadoService.add(certificado);
	}

	public boolean delete(Certificado certificado) {
		return certificadoService.delete(certificado);
	}

	public  List<Certificado> findAll() {
		return certificadoService.findAll();
	}

	public List<CertificadoDTO> findAllDTO() {
		return certificadoService.findAllDTO();
	}

	public List<CertificadoDTO> findExpiredDTO(int i) {
		return certificadoService.findExpiredDTO(i);
	}
		
}
