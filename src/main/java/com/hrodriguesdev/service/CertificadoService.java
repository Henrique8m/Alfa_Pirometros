package com.hrodriguesdev.service;

import java.io.File;
import java.sql.Date;
import java.util.List;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.repository.CertificadoRepository;
import com.hrodriguesdev.entities.CalibracaoEnsaio;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.DescricaoInstrumento;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Padrao;
import com.hrodriguesdev.resources.calibracao.CalculoIncerteza;
import com.hrodriguesdev.resources.calibracao.LineTableEntradaEquipamento;
import com.hrodriguesdev.resources.file.ReadFileExel;
import com.hrodriguesdev.resources.file.ReadFiles;

public class CertificadoService {

	private ReadFileExel readFile = new ReadFileExel();
	private CertificadoRepository repository = new CertificadoRepository();
	
	private CalculoIncerteza incertezaLow;
	private CalculoIncerteza incertezaHigh;
//	private boolean carbomax = false;
	
	
//	Busca a descricao do equipamento armazenada no %appData% com o nome do modelo
	public DescricaoInstrumento getDescricao(String modeloEquipamento) throws com.hrodriguesdev.ExceptionAlfa{
		String[] fileModelo = ReadFiles.readFile(modeloEquipamento);
		return new DescricaoInstrumento(fileModelo);
	}
	
	/*
	 * Busca osdados do padrao
	 *  armazenada no %appData% com o nome padrao
	 */
	public Padrao getPadrao(Date date_cal) {
		//nome padrao depois sera modificado
		String[] file = ReadFiles.readFile("Padrao");
		return new Padrao(file);
	}
	
	/**
	 *For�a eletro motriz, grandesa aplicada no equipamento 
	 *Buscamos a tabela
	 * do tipo de termopar que criamos
	 **/	
	@SuppressWarnings("unchecked")
	public List<LineTableEntradaEquipamento> getFem(String termopar){
		File file = new File(System.getProperty("user.home").toString() + AlfaPirometrosApplication.URL_DIRETORIO_YGGDRASIL +"\\" + termopar + ".xls");
		return (List<LineTableEntradaEquipamento>) readFile.getLines(file);
	}
//	public CalibracaoEnsaio getCalibracaoEnsaioCarbomax(Ensaios ensaio, List<LineTableEntradaEquipamento> listEntrada) throws NullPointerException{
//		carbomax = true;
//		CalibracaoEnsaio calEnsaio = getCalibracaoEnsaio(ensaio, listEntrada);
//		carbomax = false;
//		return calEnsaio;
//	}
	public CalibracaoEnsaio getCalibracaoEnsaio(Ensaios ensaio, List<LineTableEntradaEquipamento> listEntrada) throws NullPointerException{
//		Todas informa�oes que vao no certificado, a calibra�aoEnsaio comtempla
//		Vamos buscalas atraves de tabelas de termopar e informa�oes no arquivo do equipamento
		
		CalibracaoEnsaio calEnsaio = new CalibracaoEnsaio();
//		s�o os valores referencia que s�o Indicados 
//		Vamos buscar dos arquivos do ensaio que foi feito!
		Double ReferenciaLow = Double.parseDouble( ensaio.getReferencia().split("\n")[0] );
		calEnsaio.setValor1(String.format("%.0f", ReferenciaLow) );
		Double ReferenciaHigh = Double.parseDouble( ensaio.getReferencia().split("\n")[2] );	
		calEnsaio.setValor2(String.format("%.0f", ReferenciaHigh) );
		
//		s�o os valores referencia que s�o aplicados 
//		Vamos buscar na tabela do termopar, ou termoresistencia etc...
		Double femLow = listEntrada.stream().filter((x) -> x.getIndicado() >= ReferenciaLow ).findFirst().get().getAplicado();
		calEnsaio.setFem1(String.format("%.3f", femLow));
		Double femHigh = listEntrada.stream().filter((x) -> x.getIndicado() >= ReferenciaHigh ).findFirst().get().getAplicado();
		calEnsaio.setFem2(String.format("%.3f", femHigh));
//		Instanciando a clase incerteza que vai conter varios valores 
//		que iremos utilizar
		
		
//		Erro da medi�ao, vamos buscar atravez de contas,
//		pegando o valor padrao, e subtraindo os valores indicados		
		
		double[] CalLow = new double[2];
		CalLow[0] = Double.parseDouble( ensaio.getSegundo().split("\n")[0] );
		CalLow[1] = Double.parseDouble( ensaio.getTerceiro().split("\n")[0] );
		incertezaLow = new CalculoIncerteza(CalLow, ReferenciaLow);
		Double emIndicadaLow = incertezaLow.getErroSis();
		emIndicadaLow = Math.abs(emIndicadaLow);
		calEnsaio.setEmIndicada1(String.format("%.1f", emIndicadaLow));
				
			
		double[] CalHigh = new double[2];
		CalHigh[0] = Double.parseDouble( ensaio.getSegundo().split("\n")[2] );
		CalHigh[1] = Double.parseDouble( ensaio.getTerceiro().split("\n")[2] );
		
		incertezaHigh = new CalculoIncerteza(CalHigh, ReferenciaHigh);		
		Double emIndicadaHigh = incertezaHigh.getErroSis();
		emIndicadaHigh = Math.abs(emIndicadaHigh);
		calEnsaio.setEmIndicada2(String.format("%.1f", emIndicadaHigh));
		
		
//		Com os erros na mao, vamos calcular o erro da for�a eletro motriz fem
//		DecimalFormat fmt = new DecimalFormat("##.###");		
		Double emLow = emIndicadaLow * (femLow / ReferenciaLow);
//		emLow = emLow*10;
		calEnsaio.setEm1(String.format("%.4f", emLow));
		
		Double emHigh = emIndicadaHigh * (femHigh/ ReferenciaHigh);
//		emHigh = emHigh*10;
		calEnsaio.setEm2(String.format("%.4f", emHigh));
		
//		Calculo do desvia padrao;
		calEnsaio.setDesvio1(  String.format("%.3f", incertezaLow.getDesvioPadrao()));
		calEnsaio.setDesvio2(  String.format("%.3f", incertezaHigh.getDesvioPadrao()));
		
		calEnsaio.setIsm1(String.format("%.3f", incertezaLow.getIncertezaFinal()));
		calEnsaio.setIsm2(String.format("%.3f", incertezaHigh.getIncertezaFinal()));
		
		System.out.println("Temperatura " + calEnsaio.getValor1() + "\n"
				+ "FEM " + calEnsaio.getFem1() + "\n"
						+ "EM "  + calEnsaio.getEm1() + "\n"
								+ "Desvio padrao " + calEnsaio.getDesvio1() + "\n"
										+ "EM indicado " + calEnsaio.getEmIndicada1() + "\n"
												+ "ISM "  + calEnsaio.getIsm1() + "\n" );
		
		return calEnsaio;
			
	}

	public void updateEnsaio(Long ensaio_id, Long id) {
		repository.updateEnsaio(ensaio_id, id);		
	}

	public Certificado findById(Long id) {
		return repository.findById(id);
	}

	public List<Certificado> findAllByEquipamento(Long id) {
		return repository.findAllByEquipamento(id);
	}

	public Long add(Certificado certificado) {
		return repository.add(certificado);
	}

	public void updateEquipamento(Long certificado_id, Long id, Date valueOf) {
		repository.updateEquipamento(certificado_id, id, valueOf);
		
	}

	public boolean delete(Certificado certificado) {
		return repository.delete(certificado);
	}

	public List<Certificado> findAll() {
		return repository.findAll();
	}
	
}
