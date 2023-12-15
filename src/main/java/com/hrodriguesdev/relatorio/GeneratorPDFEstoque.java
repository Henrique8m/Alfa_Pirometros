package com.hrodriguesdev.relatorio;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.utilitary.Format;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratorPDFEstoque {
	
	public Boolean newDocument(List<Product> products) {
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
						data +
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
	    	documento.addTitle("Estoque");		
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
    		
    		
    		for(Product product: products) {
    			try {
    				PdfPTable table = new PdfPTable(2);
    				table.setWidthPercentage(100);
    				table.addCell(new Paragraph(product.getName()));
//    				table.addCell(new Paragraph(product.getDescricao()));
    	        	paragraph = new Paragraph();
    	        	paragraph.setAlignment(Element.ALIGN_RIGHT); 	 
    	        	paragraph.add(product.getQtde().toString());
    	    		table.addCell(paragraph);
    				documento.add(table);
    				
    			}catch(NullPointerException e) {
//    				System.out.println(e.getMessage());
    			}
//    			Paragraph paragrap = new Paragraph(product.get(i));
//    			paragrap.setAlignment(Element.ALIGN_LEFT);
//    			paragrap.setFont(font);
//    			documento.add(paragrap);    	
    		}

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
			
			
			PrinterJob job = PrinterJob.getPrinterJob();
			
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
