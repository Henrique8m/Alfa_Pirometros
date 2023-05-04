package com.hrodriguesdev.relatorio;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class RelatorioGeneratorPDF {	
//	private  Font fonteCabecalho = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
//	private Font fontePadrao = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//	private Font fonteVermelha = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
//	private Font negritoPequena = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	
	public void printPdf() {
		try {	
			// Criação do objeto que será um documento PDF
			Document documento = new Document();
			
			// Faz o apontamento para o arquivo de destino
			PdfWriter.getInstance(documento, new FileOutputStream(
					"C:/Users/henri/Desktop/Relatorios/ArquivoCompleto.pdf"));
			
			// Realiza a abertura do arquivo para escrita
			documento.open();
	
			// Adicionando meta dados ao nosso arquivo
			documento.addTitle("Relatorios");
			documento.addSubject("Relatorios de pirometros");
			documento.addKeywords("Java, PDF, yggdrasil");
			documento.addAuthor("Henrique Rodrigues");
			documento.addCreator("Henrique Rodrigues");
	
			// Tabela com 3 colunas
			PdfPTable table = new PdfPTable(2);
	
			PdfPCell c1 = new PdfPCell(new Phrase("Cliente:"));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPaddingTop(15);
			c1.setPaddingBottom(15);
			table.addCell(c1);
	
			c1 = new PdfPCell(new Phrase("Cidade:"));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
	
			table.setHeaderRows(1);
	
			table.addCell("Instrumento:");
			table.addCell("Modelo:");
			table.addCell("Fabricante:");
			table.addCell("N.Série/ Pat:");
	
			// Serão criadas mais 10 células na tabela
			// a ordem será da esquerda para a direita de criação
			// ao final de cada linha uma nova célula será criada na próxima
			// linha
			for (int i = 0; i < 3; i++) {
				table.addCell("Céclula" + i);
			}
	
			documento.add(table);
	
			// Fecha o arquivo após a escrita da mensagem
			documento.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}