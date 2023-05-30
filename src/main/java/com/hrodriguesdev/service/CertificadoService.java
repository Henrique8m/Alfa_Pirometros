package com.hrodriguesdev.service;

import java.io.File;
import java.sql.Date;
import java.util.List;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.CalibracaoEnsaio;
import com.hrodriguesdev.entities.DescricaoInstrumento;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Padrao;
import com.hrodriguesdev.resources.calibracao.CalculoIncerteza;
import com.hrodriguesdev.resources.calibracao.LineTableEntradaEquipamento;
import com.hrodriguesdev.resources.file.ReadFileExel;
import com.hrodriguesdev.resources.file.ReadFiles;

public class CertificadoService {

	private ReadFileExel readFile = new ReadFileExel();
	
	private CalculoIncerteza incertezaLow;
	private CalculoIncerteza incertezaHigh;
	
	
//	Busca a descricao do equipamento armazenada no %appData% com o nome do modelo
	public DescricaoInstrumento getDescricao(String modeloEquipamento) {
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
		File file = new File(AlfaPirometrosApplication.strDiretorioYggDrasil +"\\" + termopar + ".xls");
		return (List<LineTableEntradaEquipamento>) readFile.getLines(file);
	}
	
	public CalibracaoEnsaio getCalibracaoEnsaio(Ensaios ensaio, List<LineTableEntradaEquipamento> listEntrada) {
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
		CalHigh[0] = Double.parseDouble( ensaio.getSegundo().split("\n")[0] );
		CalHigh[1] = Double.parseDouble( ensaio.getTerceiro().split("\n")[0] );
		
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
	
}
