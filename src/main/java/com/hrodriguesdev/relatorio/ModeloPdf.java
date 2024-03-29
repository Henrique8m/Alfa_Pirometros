package com.hrodriguesdev.relatorio;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ModeloPdf {
	private static Font fonteCabecalho = new Font(Font.FontFamily.COURIER, 18,
			Font.BOLD);
	private static Font fontePadrao = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font fonteVermelha = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.NORMAL, BaseColor.RED);
	private static Font negritoPequena = new Font(Font.FontFamily.HELVETICA,
			10, Font.BOLD);

		
	public void CriaNovoPDF() {

		try {
			System.out.println("Início");

			// Criação do objeto que será um documento PDF
			Document documento = new Document();
			// Faz o apontamento para o arquivo de destino
			PdfWriter.getInstance(documento, new FileOutputStream(
					"C:/ArquivoCompleto.pdf"));
			// Realiza a abertura do arquivo para escrita
			documento.open();
			// Escreve uma mensagem no arquivo
			documento.add(new Paragraph("Alo mundo!!"));

			// Adicionando meta dados ao nosso arquivo
			documento.addTitle("Aqui está o título");
			documento.addSubject("Agora temos um assunto no arquivo!");
			documento.addKeywords("Java, PDF, iText");
			documento.addAuthor("André Felix");
			documento.addCreator("André Félix");

			Paragraph conteudo = new Paragraph();
			// Assim criaremos uma linha em branco
			conteudo.add(new Paragraph(" "));

			conteudo.add(new Paragraph("Cabeçalho da página", fonteCabecalho));
			// Pular nova linha
			conteudo.add(new Paragraph(" "));

			// Will create: Report generated by: _name, _date
			conteudo.add(new Paragraph("Arquivo gerado por: Felix "
					+ new Date(), negritoPequena));
			conteudo.add(new Paragraph(" "));
			conteudo.add(new Paragraph("Isso pode ser uma nota de rodape",
					negritoPequena));

			// Adicionar nova página
			documento.newPage();

			conteudo.add(new Paragraph(
					"Frase de atenção! Está em vermelha por destaque!",
					fonteVermelha));

			// Adicionar nova página
			documento.newPage();

			// Criação de um apontamento para um capítulo
			Anchor ancora = new Anchor("Capítulo 1", fontePadrao);
			ancora.setName("Capitulo 1");

			// Capítulo do arquivo
			Chapter capitulo = new Chapter(new Paragraph(ancora), 1);

			Paragraph novoParagrafo = new Paragraph("Tabela de precos",
					negritoPequena);

			// Seção é uma área que adicionaremos conteúdo
			Section secao = capitulo.addSection(novoParagrafo);

			// Tabela com 3 colunas
			PdfPTable table = new PdfPTable(3);

			PdfPCell c1 = new PdfPCell(new Phrase("Cabecalho1"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Cabecalho 2"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Cabeçalho 3"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			table.setHeaderRows(1);

			table.addCell("Banana");
			table.addCell("Laranja");
			table.addCell("Abacate");
			table.addCell("2.1");
			table.addCell("2.2");

			// Serão criadas mais 10 células na tabela
			// a ordem será da esquerda para a direita de criação
			// ao final de cada linha uma nova célula será criada na próxima
			// linha
			for (int i = 0; i < 10; i++) {
				table.addCell("Céclula" + i);
			}

			secao.add(table);
			documento.add(conteudo);
			documento.add(secao);


			
			// Fecha o arquivo após a escrita da mensagem
			documento.close();
			System.out.println("Fim");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
