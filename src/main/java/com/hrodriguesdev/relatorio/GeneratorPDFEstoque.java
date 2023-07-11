package com.hrodriguesdev.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.EstoqueCabos;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;
import com.hrodriguesdev.utilitary.Format;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratorPDFEstoque {
	
	public Boolean newDocument(
			EstoqueCabos cabos, 
			EstoqueConsumo consumo, 
			EstoqueEletricos eletricos, 
			EstoqueEletronicos eletronicos, 
			EstoqueEstetico estetico, 
			EstoqueSinal sinal) {
		
		String data = Format.formatData2.format(new Date( System.currentTimeMillis() ) );
		Paragraph paragraph;
		Document document = new Document();
		
		String local = System.getProperty("user.home")
						.toString() + 
						AlfaPirometrosApplication.URL_RELATORIOS;
		
		try {
			File diretorio1 = new File(local);
			diretorio1.mkdir();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String nameArquivo = "\\" + 
						data +
						".pdf";
		
		String caminho = local + 
				nameArquivo;
		
		File file = new File(caminho);		
		if(file.exists()) {
			file.delete();
		}

		try { 
			Font font = new Font();
			font.setSize(15);
			Font font2 = new Font();
			font2.setSize(16);
			font2.setStyle(4);
			
		   	PdfWriter.getInstance(document, new FileOutputStream(caminho) );
	    	document.open();
	    	document.addTitle("Estoque");		
	    	
	    	
        	paragraph = new Paragraph();
			paragraph.setFont(font2);
        	paragraph.add("Levantamento Estoque Equipamento");
    		paragraph.setAlignment(Element.ALIGN_CENTER); 	
    		document.add(paragraph);
    		
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
    		
    		
    		
    		
    		
			paragraph = new Paragraph( cabos.string() + consumo.string() + eletricos.string() + eletronicos.string() + estetico.string() + sinal.string() );
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(font);
			document.add(paragraph);
    	
	        document.addCreationDate();  
	         
	    }catch(DocumentException de) {
	    	System.err.println(de.getMessage());
	    	 return false;
	     }
	     catch(IOException ioe) {	    	 
	         System.err.println(ioe.getMessage());
	         return false;
	     }
	     document.close();
		return true;		
	}

}
