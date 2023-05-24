package com.hrodriguesdev.certificado.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.DescricaoInstrumento;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Padrao;
import com.hrodriguesdev.utilitary.Format;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

public class PdfCertificado {
	public final static Font comic_Sans_12 = FontFactory.getFont("C://windows//fonts//comic.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11.0f, Font.NORMAL, BaseColor.BLACK);
	
	public final static	Font tahoma_Negrito_18 = FontFactory.getFont("C://windows//fonts//tahomabd.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 18.0f, Font.UNDERLINE, BaseColor.BLACK);
	
	public final static	Font TIMES_NEGRITO_10 = FontFactory.getFont("C://windows//fonts//timesbd.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10.0f, Font.NORMAL, BaseColor.BLACK);
	
	public final static	Font times_Negrito_ITALICO_10 = FontFactory.getFont("C://windows//fonts//timesbi.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10.0f, Font.NORMAL, BaseColor.BLACK);
	
	public final static	Font TIMES_10 = FontFactory.getFont("C://windows//fonts//times.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10.0f, Font.NORMAL, BaseColor.BLACK);
	
	public final static	Font times_Negrito_12 = FontFactory.getFont("C://windows//fonts//timesbd.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12.0f, Font.NORMAL, BaseColor.BLACK);
	
	public final static	Font VERDANA_8 = FontFactory.getFont("C://windows//fonts//verdana.ttf",
			BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8.0f, Font.NORMAL, BaseColor.BLACK);
	
	
	
//	Falta range + resoluçao
	public void generatedCertificado(Equipamento equipamento, Certificado certificado, Empresa empresa, DescricaoInstrumento descricao, Padrao padrao) {
		
			
//		Font negritoPequena = new Font(Font. ,10, Font.BOLD);
		
		
		
		String cliente = empresa.getName();
		String endereco = empresa.getEndereco();
		String cidade = empresa.getCidade() + "/" + empresa.getEstado();
		String cep = empresa.getCep();
		
		String instrumento = "Indicador Digital Portátil de Temperatura em Metal Líquido";
		String modelo = equipamento.getModelo() + " " + descricao.getRange();
		String fabricante = equipamento.getFabricante();
		String pat;
		if(equipamento.getPat().isBlank())
			pat = "*********";
		else 
			pat = equipamento.getPat();
		String nSerie;
		if(equipamento.getNs().isBlank())
			nSerie = "**********";
		else 
			nSerie = equipamento.getNs();
		
		String resolucao = descricao.getResolucao();
		String unidade = descricao.getUnidade();
		String unidadeGrandesa = descricao.getUnidadeGrandesa();
		String fem = descricao.getFem();
		
		
		String valor1 = "1200";
		String valor2 = "1600";
		
		String fem1 = "11,951";
		String fem2 = "16,777";
		
		String em1 = "0";
		String em2 = "0";
		
		String desvio1 = "0";
		String desvio2 = "0";
		
		String em1graus = "0";
		String em2graus = "0";
		
		String ism1 = "0,100";
		String ism2 = "0,100";
		
		String fatorK = "1,65";
		
		String nsPadrao = padrao.getNs();
		String modeloPadrao = padrao.getModelo();
		String certificadoPadra = padrao.getCertificado();
		String validadePadra = padrao.getValidade();
		String rastreabilidadePadrao = padrao.getRastreabilidade();
		
		SimpleDateFormat dayString = new SimpleDateFormat("dd");
		SimpleDateFormat yearString = new SimpleDateFormat("yyyy");
		
		String day = dayString.format(certificado.getDate_cal());
		String year = yearString.format(certificado.getDate_cal());
		
		@SuppressWarnings("deprecation")
		String month = Format.getMonthString(certificado.getDate_cal().getMonth());		
		
		String certifNumero = String.valueOf( certificado.getNumero() );
		
//		operador ternário
//		Condiçao antes do ? se verdadeiro retorna aqui : se false retorna aqui
//		(corpo) -> { metodo }
		
		certifNumero = certifNumero.length() == 4 ? certifNumero :  "0" + certifNumero;
		
		String certificadoN = "";
		
		
		try {	
			// Criação do objeto que será um documento PDF
			Document documento = new Document();			
//	        documento.setMargins(1, 1, 1, 1);
	        
			// Faz o apontamento para o arquivo de destino
			File diretorio = new File(AlfaPirometrosApplication.areaDeTrabalho + "\\Certificados_YggDrasil");
			diretorio.mkdir();
			PdfWriter.getInstance(documento, new FileOutputStream(
					"C:/Users/henri/Desktop/Relatorios/ " 
			+ certificado.getNumero() 
			+ " - " 
			+ empresa.getName()
			+ " - " 
			+ equipamento.getNs() 
			+ " "
			+ equipamento.getPat() 
			+ ".pdf"));
			
			// Realiza a abertura do arquivo para escrita
			documento.open();
	
			// Adicionando meta dados ao nosso arquivo
			documento.addTitle("Certificado " + certificado.getNumero() + "/" + year);
			documento.addSubject("Certificado de pirometros");
			documento.addKeywords("Java, PDF, yggdrasil");
			documento.addAuthor("Henrique Rodrigues");
			documento.addCreator("Henrique Rodrigues");
	
//			Criado o paragrafo
			Paragraph paragraph = new Paragraph();

//			criado a tablela
			PdfPTable tableHeader = new PdfPTable(2);
			tableHeader.setWidthPercentage(100);
			PdfPCell cell = new PdfPCell();			
			
		   
		/*Cabeçalho
		 * 	
		 */
				

//			Logo Alfa
			URL imageUrl = new URL(AlfaPirometrosApplication.class.getResource("gui/resources/icons/logo-alfa.png").toString() );			
			Image img = PngImage.getImage(imageUrl);
			img.scaleAbsolute(143, 50);
			img.setAlignment(Element.ALIGN_LEFT);	
			cell.addElement(img);
			cell.setBorderColor(BaseColor.WHITE);	
			tableHeader.addCell(cell);				
			cell = new PdfPCell();			
			
//			Texto certificado de Calibração
//			criou uma outra tabela para que ficasem na mesma linha e alinhamento do logo, com duas linhas
			PdfPTable tableCertificado = new PdfPTable(1);
			paragraph = new Paragraph("CERTIFICADO DE CALIBRAÇÃO N°:", comic_Sans_12);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(BaseColor.WHITE);
			cell.addElement(paragraph);			
			tableCertificado.addCell(cell);
			
//			Numero do Certificado			
			tableCertificado = new PdfPTable(1);
			paragraph = new Paragraph(certificadoN, tahoma_Negrito_18);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			cell.setBorderColor(BaseColor.WHITE);
			cell.addElement(paragraph);			
			tableCertificado.addCell(cell);
			
//			Colocando a string do certificado e na proxima linha o numero, dentro da tabela cabeçalho
			cell = new PdfPCell();
			cell.addElement(tableCertificado);
			cell.setBorderColor(BaseColor.WHITE);		
			tableHeader.addCell(cell);

//			Add o cabeçalho no documento
			documento.add(tableHeader);
						
			
			/*
			 * Dados do Cliente
			 */
			
			
			float[] widthsClient = new float[] { 330f, 190f };		
			float totalWidths = 520f;
			PdfPTable tableInfoClient = new PdfPTable(1);
			tableInfoClient.setWidthPercentage(100);
			tableInfoClient.setSpacingBefore(10);
			
			
//			Primeira linha
//			Criar a tabela
			PdfPTable tableName = new PdfPTable(2);
			tableName.setWidthPercentage(100);
			tableName.setHorizontalAlignment(0);
			tableName.setTotalWidth(totalWidths);
			tableName.setLockedWidth(true);

			tableName.setWidths(widthsClient);			
			
			
//			Cliente
			cell = new PdfPCell();				
			paragraph = new Paragraph();
			paragraph.add(new Phrase("CLIENTE:", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(cliente, TIMES_10));	
			cell.setBorderColor(BaseColor.WHITE);
			cell.getColumn().go();
			cell.addElement(paragraph);	
			tableName.addCell(cell);
	
//			Cidade/estado		
			cell = new PdfPCell();			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("CIDADE / ESTADO:", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(cidade, TIMES_10));
			cell.addElement(paragraph);
			cell.setBorderColor(BaseColor.WHITE);
			
			tableName.addCell(cell);	
			

			
			
//			Segunda linha
//			Criar a tabela
			PdfPTable tableEndereco = new PdfPTable(2);
			tableEndereco.setWidthPercentage(100);
			tableEndereco.setHorizontalAlignment(0);
			tableEndereco.setTotalWidth(totalWidths);
			tableEndereco.setLockedWidth(true);
			tableEndereco.setWidths(widthsClient);
			
//			Endereco
			cell = new PdfPCell();				
			paragraph = new Paragraph();
			paragraph.add(new Phrase("ENDEREÇO:", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(endereco, TIMES_10));	
			cell.setBorderColor(BaseColor.WHITE);
			cell.addElement(paragraph);	
			tableEndereco.addCell(cell);
			
//			Cidade/estado		
			cell = new PdfPCell();			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("CEP:", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(cep, TIMES_10));			
			cell.setBorderColor(BaseColor.WHITE);
			cell.addElement(paragraph);	
			tableEndereco.addCell(cell);

//			Colocando ambas linha em celulas de borda branca
			PdfPCell cellTable = new PdfPCell();			
			cellTable.addElement(tableName);
			cellTable.addElement(tableEndereco);
			cellTable.setPaddingBottom(10);
			
//			Inserindo na grande tabela
			tableInfoClient.addCell(cellTable);			
			documento.add(tableInfoClient);
			
			
			/*
			 * Descrição do Instrumento
			 */
			
			
//			Instrumento
			paragraph = new Paragraph();		
			paragraph.add(new Phrase("1. DESCRIÇÃO DO INSTRUMENTO:", times_Negrito_12));
			documento.add(paragraph);
			
				
			PdfPTable tableDescriInstrumento = new PdfPTable(1);
			tableDescriInstrumento.setWidthPercentage(100);
			tableDescriInstrumento.setSpacingBefore(10);
			
			
//			Primeira linha
//			Criar a tabela
			float[] widthsInstrumento = new float[] { 100f, 420f };	
			PdfPTable tableInstrumento = getTable(2,100,totalWidths,widthsInstrumento);		
			
//			Instrumento
			tableInstrumento.addCell(addCellWriteTopPading(new Paragraph("INSTRUMENTO:", TIMES_NEGRITO_10)));		
//			Descriçao do  Instrumento		
			tableInstrumento.addCell(addCellWriteTopPading(new Paragraph(instrumento, TIMES_10)));	
			
			tableDescriInstrumento.addCell(tableInstrumento);
			
//			Segunda Linha
			PdfPTable tableModelo = getTable(2,100,totalWidths,widthsInstrumento);			
//			Modelo
			tableModelo.addCell(addCellWriteTopPading(new Paragraph("MODELO:", TIMES_NEGRITO_10)));		
//			Modelo do  Instrumento		
			tableModelo.addCell(addCellWriteTopPading(new Paragraph(modelo, TIMES_10)));	
			
			tableDescriInstrumento.addCell(tableModelo);			
			documento.add(tableDescriInstrumento);								
			
			
			
			PdfPTable tableDupla = new PdfPTable(2);
			tableDupla.setWidthPercentage(100);
						
					
//			Terceira Linha
			float[] widthsDupla = new float[] { 100f, 160f };		
			PdfPTable tableMeia = getTable(2,50,260,widthsDupla);		
			
//			Tabela Fabricante
			
//			Fabricante
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("FABRICANTE:", TIMES_NEGRITO_10)));	
//			Nome do fabricante
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(fabricante, TIMES_10)));			
			tableDupla.addCell(tableMeia);	
						
//			Tabela Patrimonio
						
			tableMeia = getTable(2,50,260,widthsDupla);			
//			Patrimonio
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("PATRIMONIO:", TIMES_NEGRITO_10)));	
//			Pat do  Instrumento		
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(pat, TIMES_10)));			
			tableDupla.addCell(tableMeia);	
			
			
			tableMeia = getTable(2,50,260,widthsDupla);			
//			Nserie
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("N.º SÉRIE:", TIMES_NEGRITO_10)));	
//			Nserie do  Instrumento		
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(nSerie, TIMES_10)));			
			tableDupla.addCell(tableMeia);	
			
			tableMeia = getTable(2,50,260,widthsDupla);	
//			Resolucao
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("RESOLUÇÃO:", TIMES_NEGRITO_10)));	
//			Resoluçao do  Instrumento		
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(resolucao, TIMES_10)));			
			tableDupla.addCell(tableMeia);				
			
			documento.add(tableDupla);
			
			
			/*
			 * Resultados da calibração
			 */
			
			
//			Resultados da Calibração
			paragraph = new Paragraph();		
			paragraph.add(new Phrase("2. RESULTADOS DA CALIBRAÇÃO:", times_Negrito_12));
			documento.add(paragraph);
			
						
			float[] calibracaoWidths = new float[] { 100f, 70f, 70f, 70f, 70f, 70f, 70f,};	
			PdfPTable calibracao = getTable(7,100,520,calibracaoWidths);
			calibracao.setSpacingBefore(10);
			
//			Primeira linha
			calibracao.addCell(addCellCentralizada(new Paragraph(unidade,  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("F.E.M.",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("E.M",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("σ",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("E.M",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("I.S.M",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("FATOR",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			
//			Segunda linha
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(fem,  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(fem,  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa, times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("K",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);

//			Terceira linha
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(valor1,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fem1,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(em1,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(desvio1,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(em1graus, TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(ism1,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fatorK,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			
//			Quarta linha
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(valor2,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fem2,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(em2,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(desvio2,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(em2graus, TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(ism2,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fatorK,  TIMES_10))).setBorderColor(BaseColor.BLACK);
//						 		
			
			documento.add(calibracao);
			
			
//			Legendas da Calibração
			paragraph = new Paragraph("Legendas:", TIMES_10);		
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("F.E.M", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Força eletro motriz, gerada pelo termopar", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("I.S.M", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Incerteza do sistema de medição", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("E.M", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Erro da medição", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("σ", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Desvio padrão", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);			
			paragraph = new Paragraph();	
			documento.add(paragraph);	

			
			paragraph = new Paragraph("A incerteza da calibração foi obtida a partir do"
					+ " Procedimento Italterm IO 016-25 , "
					+ "para um nível de confiança de"
					+ "aproximadamente 95%.", TIMES_10);	
			documento.add(paragraph);			
			paragraph = new Paragraph();	
			documento.add(paragraph);

			paragraph = new Paragraph("3. RASTREABILIDADE DOS PADRÕES UTILIZADOS:", times_Negrito_12);		
			documento.add(paragraph);
			
			float[] padraoWidths = new float[] { 90f, 90f, 80f, 100f, 80f, 80f };	
			PdfPTable padraoTable = getTable(6,100,520,padraoWidths);
			padraoTable.setSpacingBefore(10);
			
//			Primeira linha
			padraoTable.addCell(addCellCentralizada(new Paragraph("IDENTIFICAÇÃO\r\n(Patrimônio) \r\n",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("INSTRUMENTO\r\nDE MEDIÇÃO ",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("TIPO\r\n(MODELO) ",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("CERTIFICADO DE\r\nCALIBRAÇÃO",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("VALIDADE", times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("RASTREABI-\nLIDADE",  times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);	
			
//			Segunda linha
			padraoTable.addCell(addCellCentralizada(new Paragraph(nsPadrao,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("Fonte Padrão",  TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(modeloPadrao,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(certificadoPadra,  TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(validadePadra, TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(rastreabilidadePadrao,  TIMES_10))).setBorderColor(BaseColor.BLACK);	
						
			documento.add(padraoTable);
			
			
			paragraph = new Paragraph("4. NOTAS", times_Negrito_12);		
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("4.1", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" – Calibração realizada através da injeção de um sinal equivalente na entrada de termopar do equipamento.", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("4.2", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" – Normas de Referência: ITS-90, ANSI MC/96.1.", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("4.3", TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" – Temperatura ambiente não interfere no resultado da calibração pois os equipamentos possuem compensação junta fria.", TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			
			paragraph = new Paragraph(" ", TIMES_10);
			documento.add(paragraph);
			
			
			paragraph = new Paragraph("DIVINÓPOLIS, " + day + " "+ month + " de " + year + ".", TIMES_10);		
			paragraph.setAlignment(Element.ALIGN_CENTER);
			documento.add(paragraph);
			
			imageUrl = new URL(AlfaPirometrosApplication.class.getResource("gui/resources/icons/asinatura-Henrique.png").toString() );			
			img = PngImage.getImage(imageUrl);
			img.scaleAbsolute(135, 50);
			img.setAlignment(Element.ALIGN_CENTER);	
			documento.add(img);
			
			
			paragraph = new Paragraph("__________________________________");
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingBefore(-25);
			documento.add(paragraph);			
			paragraph = new Paragraph("Responsável Técnico:", TIMES_10);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			documento.add(paragraph);	
			paragraph = new Paragraph("Henrique Rodrigues.\n",TIMES_10);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			documento.add(paragraph);	

			paragraph = new Paragraph(" ",TIMES_10);
			documento.add(paragraph);	
			
			paragraph = new Paragraph ("Os resultados apresentados neste certificado referem-se exclusivamente ao equipamento submetido à calibração nas\r\n"
					+ "condições especificadas, não sendo extensivo a quaisquer lotes, mesmo que similares.\r\n"
					+ ""
					+ "A reprodução deste certificado para outros fins, só poderá ser feita integralmente, sem nenhuma alteração"
					, TIMES_NEGRITO_10);
			paragraph.getFont().setColor(BaseColor.RED);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			
			documento.add(paragraph);
			
			
			paragraph = new Paragraph ("____________________________________________________________________________________________________\r\n"
					+ "\n"
					+ "Alfa Controle de Processos Industriais Eireli.\r\n"
					+ "R. Itapecerica, 46 – Centro – Divinópolis – MG\r\n"
					+ "Tel: (37) 3213-5791 – (37) 9 8402-6020 \r\n"
					+ "henrique@alfacontroles.com\r\n"
					+ "", VERDANA_8);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			
			documento.add(paragraph);
			
			
			
			// Fecha o arquivo após a escrita da mensagem
			documento.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static PdfPTable getTable(int columns, long percentage, float totalWidth, float[] widths) {
		PdfPTable table = new PdfPTable(columns);
		table.setWidthPercentage(percentage);
		table.setHorizontalAlignment(0);
		table.setTotalWidth(totalWidth);
		table.setLockedWidth(true);
		try {
			table.setWidths(widths);
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		return table;
	}
	
	private static PdfPCell addCellWriteTopPading(Paragraph paragraph) {
		PdfPCell cell = new PdfPCell();				
		cell.setBorderColor(BaseColor.WHITE);
		cell.setPaddingTop(-4);
		cell.addElement(paragraph);	
		return cell;

	}
	
	private static PdfPCell addCellCentralizada(Paragraph paragraph) {
		PdfPCell cell = new PdfPCell();	
		paragraph.setAlignment(Element.ALIGN_CENTER);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setPaddingTop(-3);
		cell.setPaddingBottom(5);
		cell.addElement(paragraph);	
		return cell;

	}
	
	private static PdfPCell addCellCentralizadaFechada(Paragraph paragraph) {
		PdfPCell cell = new PdfPCell();	
		paragraph.setAlignment(Element.ALIGN_CENTER);
		cell.setBorderColor(BaseColor.WHITE);
		cell.setPaddingTop(-5);
		cell.setPaddingBottom(3);
		cell.addElement(paragraph);	
		return cell;

	}
}
