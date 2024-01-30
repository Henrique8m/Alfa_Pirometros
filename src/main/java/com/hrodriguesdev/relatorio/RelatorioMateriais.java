package com.hrodriguesdev.relatorio;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class RelatorioMateriais {


	public Boolean relatorio(List<Orcamento> orcamento, double total, String receptaculo) {
		receptaculo = receptaculo.replaceAll("[^A-Z]+", "");
		String data = Format.formatData2.format(new Date( System.currentTimeMillis() ) );
		Paragraph paragraph;
		Document documento = new Document();
		String caminho = AlfaPirometrosApplication.URL_RELATORIOS;
		String caminhoOne = AlfaPirometrosApplication.URL_RELATORIOS_ONEDRIVER;
			
		try {
			File diretorio = new File(caminho);
			diretorio.mkdir();
			File diretorio1 = new File(caminhoOne);
			diretorio1.mkdir();
		}catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		String nameArquivo = "\\" + 
						data + " Receptaculos " + 
						receptaculo + 
						".pdf";
		
		String arquivo = caminho + 
				nameArquivo;
		String arquivo2 = caminhoOne + 
				nameArquivo;
		
		
		try {
			File file = new File(arquivo);		
			if(file.exists()) {
				file.delete();
			}
			File file1 = new File(arquivo2);		
			if(file1.exists()) {
				file1.delete();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		
		try { 
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
		 
	    	documento.open();
	    	documento.addTitle("Saida de peças");		
	    	documento.addSubject("Estoque produtos");
	    	documento.addKeywords("");
	    	documento.addAuthor("Henrique Rodrigues");
	    	documento.addCreator("Henrique Rodrigues");
	    	
        	paragraph = new Paragraph();
        	paragraph.add("Levantamento Estoque Equipamento");
    		paragraph.setAlignment(Element.ALIGN_CENTER); 	
    		documento.add(paragraph);
    		
    		paragraph = new Paragraph(" ");    		
    		documento.add(paragraph);
  
    		
    		for(Orcamento orc: orcamento) {
    			try {
    				float[] f = new float[] {100,50,50,38};
    				PdfPTable table = new PdfPTable(f);
    				
    				table.setWidthPercentage(100);
    				
    				PdfPCell cell = new PdfPCell(   						
    						new Paragraph(orc.getEmpressa() ) );
    				
    				table.addCell(cell);
    				
    				table.addCell(new Paragraph(orc.getRelatorio() ));
//	    				table.addCell(new Paragraph(product.getDescricao()));
    				table.addCell(new Paragraph(orc.getProducts() ));
    	        	paragraph = new Paragraph();
    	        	paragraph.setAlignment(Element.ALIGN_RIGHT); 
    	        	Date date = orc.getData_saida();
    	        	if(date != null)
    	        		paragraph.add(date.toString() );
    	    		table.addCell(paragraph);
    				documento.add(table);
    				
    			}catch(NullPointerException e) {
    				System.out.println("NullPointerException RelatorioMaterias");
    			}   	
    		}
    		
    		documento.add(new Paragraph());

			PdfPTable table = new PdfPTable(2);
			
			table.setWidthPercentage(100);

			
			table.addCell(new Paragraph("Total" ));

			table.addCell(new Paragraph(total + ""));
        	
			documento.add(table);
    		
    		
    		
    		documento.addCreationDate();  
	         
	    }catch(DocumentException de) {
	    	de.printStackTrace();
	    	System.err.println(de.getMessage());
	    	return false;
	     }
		
		finally {			
			documento.close();
		}
		
		try {

			PrintService servico = PrintServiceLookup.lookupDefaultPrintService();
			
			//		        PrintService[] impressoras = PrintServiceLookup.lookupPrintServices(null, null);
			//
			//		        for (PrintService ps : impressoras) {
			//		            System.out.println(ps.getName());
			//		        }
			
			PDDocument documentoPdd = null;
			
			try {
				documentoPdd = PDDocument.load(new File(arquivo));
				Runtime.getRuntime().exec("explorer.exe " + arquivo);
			}catch(FileNotFoundException fil) {
				try {
					documentoPdd = PDDocument.load(new File(arquivo2));
					Runtime.getRuntime().exec("explorer.exe " + arquivo2);
				}catch(FileNotFoundException fil2) {
					fil2.printStackTrace();
					return false;
				}
			}
			
			
			java.awt.print.PrinterJob job = PrinterJob.getPrinterJob();
			
			if(Alerts.showAlertConfirmation("Relatorio printer", "Deseja imprimir o relatorio na impressora: \n" + servico.getName(), "")) {
				job.setPageable( new PDFPageable(documentoPdd));
				job.setPrintService(servico);
				job.print();
				documentoPdd.close();
			}
		} catch (IOException | PrinterException e) {
			e.printStackTrace();
		}

		return true;		
	}
		

}
