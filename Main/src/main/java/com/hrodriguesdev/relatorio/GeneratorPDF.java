package com.hrodriguesdev.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empressa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.utilitary.Format;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratorPDF {
	
	
	public Boolean newDocument(Coletor coletor, Equipamento equipamento, Empressa empressa, Orcamento orcamento) {
		String data = Format.formatData2.format(new Date( System.currentTimeMillis() ) );
		Paragraph paragraph;
		Document document = new Document();
		
		String local = System.getProperty("user.home")
						.toString() + 
						AlfaPirometrosApplication.caminhoPDF;
		
		try {
			File diretorio1 = new File(local);
			diretorio1.mkdir();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String nameArquivo = "\\" + 
						empressa.getName() +
						"  " + 
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
	    	document.addTitle("Controle Saída de Equipamento");		
	    	
	    	
        	paragraph = new Paragraph();
			paragraph.setFont(font2);
        	paragraph.add("Controle Saída de Equipamento");
    		paragraph.setAlignment(Element.ALIGN_CENTER); 	
    		document.add(paragraph);
    		
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( empressaStr(empressa) );
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(font);
			document.add(paragraph);
			
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( equipamentoStr( equipamento, orcamento) );
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(font);
			document.add(paragraph);
			
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);			
    		
        	paragraph = new Paragraph();
			paragraph.setFont(font2);
        	paragraph.add("TERMO DE COMPROMISSO");
    		paragraph.setAlignment(Element.ALIGN_CENTER); 	
    		document.add(paragraph);
      
    		
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( coletroStr( coletor ) );
			paragraph.setAlignment(Element.ALIGN_LEFT);
			paragraph.setFont(font);
			document.add(paragraph);
			
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
	  		paragraph = new Paragraph("______________________________________");    	
			paragraph.setAlignment(Element.ALIGN_CENTER);
    		document.add(paragraph);
    		
			paragraph = new Paragraph( coletor.getNomeColetor()  );
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(font);
			document.add(paragraph);    		
    		
    		
    		paragraph = new Paragraph(" ");   
    		document.add(paragraph);
	        
	        document.addCreationDate();  
	         
	    }catch(DocumentException de) {
	    	System.err.println(de.getMessage());
	     }
	     catch(IOException ioe) {
	         System.err.println(ioe.getMessage());
	     }
	     document.close();
		return null;		
	}

	private String coletroStr(Coletor coletor) {
		return "Declaro, para os devidos fins, que eu, " + coletor.getNomeColetor() +
				", trabalhando na empressa " + coletor.getEmpressaName() +
				", levarei sobe minha responsabilidade o(s) equipamento(s) descrito, na data de hoje "
				+ coletor.getDataHoraColeta();
	}

	private String equipamentoStr(Equipamento equipamento, Orcamento orcamento) {
		String patrimonio = "";
		String ns = "";
		if(equipamento.getPat() != null) {
			patrimonio = "  NUMERO DE PATRIMONIO: " + equipamento.getPat();
		}
		if(equipamento.getNs() != null) {
			ns = "  NUMERO DE SERIE: " + equipamento.getNs();
		}
		
		
		return "MODELO: " + equipamento.getModelo() + ns
				+ patrimonio + "\n"
				+ "DATA DA CHEGADDA: " + orcamento.getData_chegada()
				+ "  DATA DA SAÍDA: " + orcamento.getData_saida()
				;
			
	}

	private String empressaStr(Empressa empressa) {
		return "CLIENTE: " + empressa.getName() +  "   CEP: " + empressa.getCep() + "\n"
				+ "ENDEREÇO: " + empressa.getEndereço()
				+ "  CIDADE: " + empressa.getCidade() 
				+ "  ESTADO: " + empressa.getEstado();
	}
	
}