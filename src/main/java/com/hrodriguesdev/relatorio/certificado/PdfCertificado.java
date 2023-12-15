package com.hrodriguesdev.relatorio.certificado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.CalibracaoEnsaio;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.DescricaoInstrumento;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Padrao;
import com.hrodriguesdev.utilitary.Fontes;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.pdf.PDFTableAuxiliar;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

public class PdfCertificado {

	
//	Falta range + resoluçao
	public boolean generatedCertificado(Equipamento equipamento, Certificado certificado, Empresa empresa, DescricaoInstrumento descricao, Padrao padrao, CalibracaoEnsaio calEnsaio) {
		
			
//		Font negritoPequena = new Font(Font. ,10, Font.BOLD);
		
		
//		Informacoes sobre o cliente
		String cliente = empresa.getName();
		String endereco = empresa.getEndereco();
		String cidade = empresa.getCidade() + "/" + empresa.getEstado();
		String cep = empresa.getCep();
				
//		Descricao do instrumento
		String instrumento =descricao.getInstrumento();
		String modelo = descricao.getModelo();
		String fabricante = descricao.getFabricante();			
				
		String pat = "*********";
		if(!equipamento.getPat().isBlank())
			pat = equipamento.getPat();
		
		String nSerie = "**********";
		
		if(!equipamento.getNs().isBlank())
			nSerie = equipamento.getNs();
		
		String resolucao = descricao.getResolucao();
		
//		Resultados Calibracao
		String medida = descricao.getMedida();
		String unidadeGrandesa = descricao.getUnidade();
		String fem = descricao.getFem();		
		
		String valor1 = calEnsaio.getValor1();
		String valor2 = calEnsaio.getValor2();
		
		String fem1 = calEnsaio.getFem1();
		String fem2 = calEnsaio.getFem2();
		
		String em1 = calEnsaio.getEm1();
		String em2 = calEnsaio.getEm2();
		
		String desvio1 = calEnsaio.getDesvio1();
		String desvio2 = calEnsaio.getDesvio2();
		
		String emIndicada1 = calEnsaio.getEmIndicada1();
		String emIndicada2 = calEnsaio.getEmIndicada2();
		
		String ism1 = calEnsaio.getIsm1();
		String ism2 = calEnsaio.getIsm2();
		
		String fatorK = calEnsaio.getFatorK();
		
//		RASTREABILIDADE DO PADRAO UTILIZADO
		String nsPadrao = padrao.getIdentificacao();
		String modeloPadrao = padrao.getInstrumento();
		String certificadoPadra = padrao.getCertificado();
		String validadePadra = padrao.getValidade();
		String rastreabilidadePadrao = padrao.getRastreabilidade();
		
		
//		Data de calibracao
		SimpleDateFormat dayString = new SimpleDateFormat("dd");
		SimpleDateFormat yearString = new SimpleDateFormat("yyyy");
		
		String day = dayString.format(certificado.getDate_cal());
		String year = yearString.format(certificado.getDate_cal());
		
		@SuppressWarnings("deprecation")
		String month = Format.getMonthString(certificado.getDate_cal().getMonth());	
		
//		operador ternário
//		Condiçao antes do ? se verdadeiro retorna aqui : se false retorna aqui
//		(corpo) -> { metodo }
				
//		Numero do certificado total de 4 casas decimais, se faltar o for coloca
		String certifNumero = String.valueOf( certificado.getNumero() );
		int size = certifNumero.length();
		for(; size <= 3; size++) 
			certifNumero = certifNumero.length()==4 ? certifNumero : "0" + certifNumero;	
		
		String certificadoN = certifNumero + "/" + year;
		String titleCertificadoN = "CERTIFICADO DE CALIBRAÇÃO N°:";
		
		
		try {	
			// Criação do objeto que será um documento PDF
			Document documento = new Document();	
			
			String caminho = AlfaPirometrosApplication.CERTIFICADO_CAMINHO;
			String caminhoOne = AlfaPirometrosApplication.CERTIFICADO_CAMINHO_ONEDRIVER;
			String fileName = 
					"/"
							+ certifNumero
							+ " - " 
							+ empresa.getName().split(" ")[0]
							+ " - " 
							+ equipamento.getNs().replaceAll("/", "-") 
							+ " "
							+ equipamento.getPat().replaceAll("/", "-") 
							+ ".pdf";
			
			String arquivo = caminho + 
					fileName;
			String arquivo2 = caminhoOne + 
					fileName;
			
//	        documento.setMargins(1, 1, 1, 1);
	        
			// Faz o apontamento para o arquivo de destino
			try {
				File diretorio = new File(caminho);
				diretorio.mkdir();
				File diretorio1 = new File(caminhoOne);
				diretorio1.mkdir();
			}catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				PdfWriter.getInstance(documento, new FileOutputStream(arquivo) );
			}catch(FileNotFoundException fil) {
				try {
					PdfWriter.getInstance(documento, new FileOutputStream(arquivo2) );
				}catch(FileNotFoundException fil2) {
					fil2.printStackTrace();
					return false;
				}
			}
			
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


			PdfPCell cell = new PdfPCell();			
			
		   
		/*Cabeçalho
		 * 	
		 */

//			Add o cabeçalho no documento
			documento.add(PDFTableAuxiliar.headerDefaultAlfa(titleCertificadoN, certificadoN));
						
			
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
			paragraph.add(new Phrase("CLIENTE:", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(cliente, Fontes.TIMES_10));	
			cell.setBorderColor(BaseColor.WHITE);
			cell.getColumn().go();
			cell.addElement(paragraph);	
			tableName.addCell(cell);
	
//			Cidade/estado		
			cell = new PdfPCell();			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("CIDADE / ESTADO:", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(cidade, Fontes.TIMES_10));
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
			paragraph.add(new Phrase("ENDEREÇO:", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(endereco, Fontes.TIMES_10));	
			cell.setBorderColor(BaseColor.WHITE);
			cell.addElement(paragraph);	
			tableEndereco.addCell(cell);
			
//			Cidade/estado		
			cell = new PdfPCell();			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("CEP:", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(cep, Fontes.TIMES_10));			
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
			paragraph.add(new Phrase("1. DESCRIÇÃO DO INSTRUMENTO:", Fontes.times_Negrito_12));
			documento.add(paragraph);
			
				
			PdfPTable tableDescriInstrumento = new PdfPTable(1);
			tableDescriInstrumento.setWidthPercentage(100);
			tableDescriInstrumento.setSpacingBefore(10);
			
			
//			Primeira linha
//			Criar a tabela
			float[] widthsInstrumento = new float[] { 100f, 420f };	
			PdfPTable tableInstrumento = getTable(2,100,totalWidths,widthsInstrumento);		
			
//			Instrumento
			tableInstrumento.addCell(addCellWriteTopPading(new Paragraph("INSTRUMENTO:", Fontes.TIMES_NEGRITO_10)));		
//			Descriçao do  Instrumento		
			tableInstrumento.addCell(addCellWriteTopPading(new Paragraph(instrumento, Fontes.TIMES_10)));	
			
			tableDescriInstrumento.addCell(tableInstrumento);
			
//			Segunda Linha
			PdfPTable tableModelo = getTable(2,100,totalWidths,widthsInstrumento);			
//			Modelo
			tableModelo.addCell(addCellWriteTopPading(new Paragraph("MODELO:", Fontes.TIMES_NEGRITO_10)));		
//			Modelo do  Instrumento		
			tableModelo.addCell(addCellWriteTopPading(new Paragraph(modelo, Fontes.TIMES_10)));	
			
			tableDescriInstrumento.addCell(tableModelo);			
			documento.add(tableDescriInstrumento);								
			
			
			
			PdfPTable tableDupla = new PdfPTable(2);
			tableDupla.setWidthPercentage(100);
						
					
//			Terceira Linha
			float[] widthsDupla = new float[] { 100f, 160f };		
			PdfPTable tableMeia = getTable(2,50,260,widthsDupla);		
			
//			Tabela Fabricante
			
//			Fabricante
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("FABRICANTE:", Fontes.TIMES_NEGRITO_10)));	
//			Nome do fabricante
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(fabricante, Fontes.TIMES_10)));			
			tableDupla.addCell(tableMeia);	
						
//			Tabela Patrimonio
						
			tableMeia = getTable(2,50,260,widthsDupla);			
//			Patrimonio
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("PATRIMONIO:", Fontes.TIMES_NEGRITO_10)));	
//			Pat do  Instrumento		
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(pat, Fontes.TIMES_10)));			
			tableDupla.addCell(tableMeia);	
			
			
			tableMeia = getTable(2,50,260,widthsDupla);			
//			Nserie
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("N.º SÉRIE:", Fontes.TIMES_NEGRITO_10)));	
//			Nserie do  Instrumento		
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(nSerie, Fontes.TIMES_10)));			
			tableDupla.addCell(tableMeia);	
			
			tableMeia = getTable(2,50,260,widthsDupla);	
//			Resolucao
			tableMeia.addCell(addCellWriteTopPading(new Paragraph("RESOLUÇÃO:", Fontes.TIMES_NEGRITO_10)));	
//			Resoluçao do  Instrumento		
			tableMeia.addCell(addCellWriteTopPading(new Paragraph(resolucao, Fontes.TIMES_10)));			
			tableDupla.addCell(tableMeia);				
			
			documento.add(tableDupla);
			
			
			/*
			 * Resultados da calibração
			 */
			
			
//			Resultados da Calibração
			paragraph = new Paragraph();		
			paragraph.add(new Phrase("2. RESULTADOS DA CALIBRAÇÃO:", Fontes.times_Negrito_12));
			documento.add(paragraph);
			
						
			float[] calibracaoWidths = new float[] { 100f, 70f, 70f, 70f, 70f, 70f, 70f,};	
			PdfPTable calibracao = getTable(7,100,520,calibracaoWidths);
			calibracao.setSpacingBefore(10);
			
//			Primeira linha
			calibracao.addCell(addCellCentralizada(new Paragraph(medida,  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("F.E.M.",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("E.M", Fontes. times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("σ",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("E.M",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("I.S.M",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("FATOR",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			
//			Segunda linha
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(fem,  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(fem,  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph(unidadeGrandesa,  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizada(new Paragraph("K",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);

//			Terceira linha
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(valor1,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fem1,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(em1,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(desvio1,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(emIndicada1, Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(ism1,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fatorK,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			
//			Quarta linha
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(valor2,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fem2,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(em2,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(desvio2,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(emIndicada2, Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(ism2,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			calibracao.addCell(addCellCentralizadaFechada(new Paragraph(fatorK,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
//						 		
			
			documento.add(calibracao);
			
			
//			Legendas da Calibração
			paragraph = new Paragraph("Legendas:", Fontes.TIMES_10);		
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("F.E.M", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Força eletro motriz, gerada pelo termopar", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("I.S.M", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Incerteza do sistema de medição", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("E.M", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Erro da medição", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("σ", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" = Desvio padrão", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);			
			paragraph = new Paragraph();	
			documento.add(paragraph);	

			
			paragraph = new Paragraph("A incerteza da calibração foi obtida a partir do"
					+ " Procedimento Italterm IO 016-25 , "
					+ "para um nível de confiança de"
					+ "aproximadamente 95%.", Fontes.TIMES_10);	
			documento.add(paragraph);			
			paragraph = new Paragraph();	
			documento.add(paragraph);

			paragraph = new Paragraph("3. RASTREABILIDADE DOS PADRÕES UTILIZADOS:", Fontes.times_Negrito_12);		
			documento.add(paragraph);
			
			float[] padraoWidths = new float[] { 90f, 90f, 80f, 100f, 80f, 80f };	
			PdfPTable padraoTable = getTable(6,100,520,padraoWidths);
			padraoTable.setSpacingBefore(10);
			
//			Primeira linha
			padraoTable.addCell(addCellCentralizada(new Paragraph("IDENTIFICAÇÃO\r\n(Patrimônio) \r\n",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("INSTRUMENTO\r\nDE MEDIÇÃO ",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("TIPO\r\n(MODELO) ",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("CERTIFICADO DE\r\nCALIBRAÇÃO",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("VALIDADE", Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("RASTREABI-\nLIDADE",  Fontes.times_Negrito_ITALICO_10))).setBorderColor(BaseColor.BLACK);	
			
//			Segunda linha
			padraoTable.addCell(addCellCentralizada(new Paragraph(nsPadrao,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph("Fonte Padrão",  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(modeloPadrao,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(certificadoPadra,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(validadePadra, Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);
			padraoTable.addCell(addCellCentralizada(new Paragraph(rastreabilidadePadrao,  Fontes.TIMES_10))).setBorderColor(BaseColor.BLACK);	
						
			documento.add(padraoTable);
			
			
			paragraph = new Paragraph("4. NOTAS", Fontes.times_Negrito_12);		
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("4.1", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" – Calibração realizada através da injeção de um sinal equivalente na entrada de termopar do equipamento.", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("4.2", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" – Normas de Referência: ITS-90, ANSI MC/96.1.", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			paragraph = new Paragraph();	
			paragraph.add(new Phrase("4.3", Fontes.TIMES_NEGRITO_10));
			paragraph.add(new Phrase(" – Temperatura ambiente não interfere no resultado da calibração pois os equipamentos possuem compensação junta fria.", Fontes.TIMES_10));
			paragraph.setSpacingBefore(-5);
			documento.add(paragraph);
			
			
			paragraph = new Paragraph(" ", Fontes.TIMES_10);
			documento.add(paragraph);
			
			
			paragraph = new Paragraph("DIVINÓPOLIS, " + day + " "+ month + " de " + year + ".", Fontes.TIMES_10);		
			paragraph.setAlignment(Element.ALIGN_CENTER);
			documento.add(paragraph);
			
			URL imageUrl = new URL(AlfaPirometrosApplication.class.getResource("gui/resources/icons/asinatura-Henrique.png").toString() );			
			Image img = PngImage.getImage(imageUrl);
			img.scaleAbsolute(135, 50);
			img.setAlignment(Element.ALIGN_CENTER);	
			documento.add(img);
			
			
			paragraph = new Paragraph("__________________________________");
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingBefore(-25);
			documento.add(paragraph);			
			paragraph = new Paragraph("Responsável Técnico:", Fontes.TIMES_10);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			documento.add(paragraph);	
			paragraph = new Paragraph("Henrique Rodrigues.\n",Fontes.TIMES_10);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			documento.add(paragraph);	

			paragraph = new Paragraph(" ",Fontes.TIMES_10);
			documento.add(paragraph);	
			
			paragraph = new Paragraph ("Os resultados apresentados neste certificado referem-se exclusivamente ao equipamento submetido à calibração nas\r\n"
					+ "condições especificadas, não sendo extensivo a quaisquer lotes, mesmo que similares.\r\n"
					+ ""
					+ "A reprodução deste certificado para outros fins, só poderá ser feita integralmente, sem nenhuma alteração"
					, Fontes.TIMES_NEGRITO_10_RED);
//			paragraph.getFont().setColor(BaseColor.RED);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			
			documento.add(paragraph);
			
			documento.add(PDFTableAuxiliar.footerDefaultAlfa());
			
			
			
			// Fecha o arquivo após a escrita da mensagem
			documento.close();
			
//			Runtime.getRuntime().exec("explorer.exe " + caminho);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
