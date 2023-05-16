package com.hrodriguesdev.relatorio;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.utilitary.Format;
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
	
	private TableEnsaios tableEnsaios;
	
	public void printRelatorioPdf(Equipamento equipamento, Ensaios ensaios, Orcamento orcamento, String obsList) {
		tableEnsaios = new TableEnsaios(ensaios);
		
		// Criação do objeto que será um documento PDF
		Document documento = new Document();
		String id = orcamento.getId().toString();
		String idOrcamento = id + "-" + Format.formatYear.format(orcamento.getData_chegada());
		try {	
			
			// Faz o apontamento para o arquivo de destino
			PdfWriter.getInstance(documento, new FileOutputStream(
					"C:/Users/henri/Desktop/Relatorios/" + idOrcamento + ".pdf"));
			
			// Realiza a abertura do arquivo para escrita
			documento.open();
	
			// Adicionando meta dados ao nosso arquivo
			documento.addTitle("Relatorios");
			documento.addSubject("Relatorios de pirometros");
			documento.addKeywords(idOrcamento);
			documento.addAuthor("Henrique Rodrigues");
			documento.addCreator("Henrique Rodrigues");
	
			// Tabela com 3 colunas
			PdfPTable table = new PdfPTable(2);
			
			//Tamanho ocupando 100% da pagina
			table.setWidthPercentage(100);
			
//			Linha 1
			List<String> list = new ArrayList<String>();
			String client = equipamento.getEmpressaName();
			client = client.length() <= 34 ? client : client.substring(0,34);
			list.add("Cliente: " + client );
			table.addCell(addCell(list,10,10,Element.ALIGN_LEFT));
	
			list = new ArrayList<String>();
			list.add("Cidade: " );
			table.addCell(addCell(list,10,10,Element.ALIGN_LEFT));	
			
//			Linha 2
			
			list = new ArrayList<String>();
			list.add("Instrumento: " + equipamento.getInstrumento());
			table.addCell(addCell(list,10,10,Element.ALIGN_LEFT));	
			
			list = new ArrayList<String>();
			list.add("Modelo: " + equipamento.getModelo());
			table.addCell(addCell(list,10,10,Element.ALIGN_LEFT));	
			
//			Linha 3
			
			list = new ArrayList<String>();
			list.add("Fabricante: " + equipamento.getFabricante());
			table.addCell(addCell(list,10,10,Element.ALIGN_LEFT));	
			
			list = new ArrayList<String>();
			list.add("N.Série/ Pat: "  + equipamento.getNs()+ "   " + equipamento.getPat());
			table.addCell(addCell(list,10,10,Element.ALIGN_LEFT));				
			
			table.setHeaderRows(1);
			documento.add(table);
			
//			Linha 4
			
			table = new PdfPTable(5);
			table.setWidthPercentage(100);

			list = new ArrayList<String>();
			list.add("Ultima Calibração:");
			String dataCal = "";
			try {
				dataCal = Format.formatData.format( equipamento.getUltimaCalibDate());
			}catch(NullPointerException e) {
				e.printStackTrace();
			}		
			list.add(dataCal);		
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));	
			
			list = new ArrayList<String>();
			list.add("Data:");
			list.add(Format.formatData.format( new Date() ));	
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));	
			
			list = new ArrayList<String>();
			list.add("Certificado N:");
			list.add(equipamento.getCertificado());	
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));
	
			list = new ArrayList<String>();
			list.add("Os:");
			list.add(idOrcamento);
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));
			
			list = new ArrayList<String>();
			list.add("Relatorio N:");
			list.add(equipamento.getRelatorio());
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));
			
			documento.add(table);
			
//			linha 5
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);

			list = new ArrayList<String>();
			list.add("Valores obtidos");		
			table.addCell(addCell(list,1,5,Element.ALIGN_CENTER));	
			
			documento.add(table);
			
//			Linha 6
			
			table = new PdfPTable(4);
			table.setWidthPercentage(100);

			list = new ArrayList<String>();
			list.add("Referencia:");		
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));
			
			list = new ArrayList<String>();
			list.add("1º Valor obtido:");		
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));	
			
			list = new ArrayList<String>();
			list.add("2º Valor obtido:");		
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));	
			
			list = new ArrayList<String>();
			list.add("3º Valor obtido:");		
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));
			
			documento.add(table);
		
//			Tabela com resultados dos ensaios
			
			List<PdfPTable> tableList = tableEnsaios.getTableEnsaios();		
			for(PdfPTable tableFor: tableList) {
				documento.add(tableFor);
			}		
			
//			Final, Lista dos materias gastos, e as observações
			
			table = new PdfPTable(1);
			table.setWidthPercentage(100);

			list = new ArrayList<String>();
			list.add(obsList.replaceAll("\n", ", "));		
			table.addCell(addCell(list,1,5,Element.ALIGN_LEFT));
			
			documento.add(table);
			
			
			
			documento.setMargins(0, 0, 10, 10);
	
			// Fecha o arquivo após a escrita da mensagem
			documento.close();

		} catch (Exception e) {
			documento.close();
			e.printStackTrace();
		}
	}
	
	private PdfPCell addCell(List<String> phraseList, int paddingTop, int paddingBottom, int align) {
		
		PdfPCell cell = new PdfPCell();
		for(String phrase: phraseList) {
			cell.addElement(new Phrase(phrase));
		}		
		cell.setHorizontalAlignment(align);
		cell.setPaddingTop(paddingTop);
		cell.setPaddingBottom(paddingBottom);	
		
		return cell;
	}
	
	
	
}