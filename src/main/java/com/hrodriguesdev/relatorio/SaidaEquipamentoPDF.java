package com.hrodriguesdev.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Coletor;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.Log;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class SaidaEquipamentoPDF {
	
	
	@SuppressWarnings("deprecation")
	public Boolean newDocument(Coletor coletor, Equipamento equipamento, Empresa empressa, Orcamento orcamento) {
		String data = Format.formatData2.format(coletor.getDate() );
		Paragraph paragraph;
		Document document = new Document();
		
		String caminho = AlfaPirometrosApplication.URL_RELATORIOS;		
		
		try {
			File diretorio1 = new File(caminho);
			diretorio1.mkdir();
			caminho = caminho + "\\";
		}catch (Exception e) {
			e.printStackTrace();
			Log.logString("SaidaEquipamentoPdf", e.getMessage());
			return false;
			
		}		
		
		String arquivo =  caminho +
						empressa.getName() +
						" " + 
						data +
						".pdf";
				
		File file = new File(arquivo);		
		if(file.exists()) {
			file.delete();
		}

		try { 
			Font font = new Font();
			font.setSize(15);
			Font fontNegrito = new Font();
			font.setSize(15);
			font.setStyle(Font.BOLD);
			Font fontUnderline30 = new Font();
			fontUnderline30.setSize(25);
			fontUnderline30.setStyle(Font.UNDERLINE);
			
		   	PdfWriter.getInstance(document, new FileOutputStream(arquivo) );
	    	document.open();
	    	document.addTitle("Controle Saída de Equipamento");		
	    	
	    	
        	paragraph = new Paragraph();
			paragraph.setFont(fontUnderline30);
        	paragraph.add("Controle Saída de Equipamento");
    		paragraph.setAlignment(Element.ALIGN_CENTER); 	
    		document.add(paragraph);
    		
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( 
					"Recebi de Alfa Controler de Processos Industriais Eireli o(s) equipamento(s) da empresa:"
					);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(fontNegrito);
			document.add(paragraph);
			
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( empressaStr(empressa) );
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(font);
			document.add(paragraph);
			
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( 
					"Dados do equipamento:"
					);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(fontNegrito);
			document.add(paragraph);
			
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( equipamentoStr( equipamento, orcamento) );
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(font);
			document.add(paragraph);
			
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);	
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
        	paragraph = new Paragraph();
			paragraph.setFont(fontUnderline30);
        	paragraph.add("TERMO DE COMPROMISSO");
    		paragraph.setAlignment(Element.ALIGN_CENTER); 	
    		document.add(paragraph);
      
    		
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);
    		
			paragraph = new Paragraph( coletroStr( coletor ) );
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(font);
			document.add(paragraph);
			
	  		paragraph = new Paragraph(" ");    		
    		document.add(paragraph);

    		
			paragraph = new Paragraph( 
					"Por ser verdade firmo o presente:"
					);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(fontNegrito);
			document.add(paragraph);
			
    		paragraph = new Paragraph(" ");    		
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
			
			paragraph = new Paragraph( coletor.getEmpresaName()  );
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(font);
			document.add(paragraph);  
			
			paragraph = new Paragraph(
					"Divinópolis, " 
					+ coletor.getDate().getDay() 
					+ " de "
					+ Format.getMonthString(coletor.getDate().getMonth())
					+ " "
					+ Format.formatYear.format(coletor.getDate())
					);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(font);
			document.add(paragraph);   
    		
    		
    		paragraph = new Paragraph(" ");   
    		document.add(paragraph);
	        
	        document.addCreationDate();   
	        document.close();

	        Runtime.getRuntime().exec("explorer.exe " + caminho);
	        Runtime.getRuntime().exec("explorer.exe " + arquivo);
	    }catch(DocumentException | IOException e1) {
	    	System.err.println(e1.getMessage());
	    	Log.logString("SaidaEquipamentoPdf", e1.getMessage());
	     }	    

		return null;		
	}

	private String coletroStr(Coletor coletor) {
		return "Declaro, para os devidos fins, que eu, " + coletor.getNomeColetor() +
				", trabalhando na empressa " + coletor.getEmpresaName() +
				", levarei sobe minha responsabilidade o(s) equipamento(s) anteriormente descrito.";

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
				+ "DATA DA CHEGADDA: " + Format.formatData.format(orcamento.getData_chegada())
				+ "  DATA DA SAÍDA: " + Format.formatData.format(orcamento.getData_saida())
				;
			
	}

	private String empressaStr(Empresa empressa) {
		return "CLIENTE: " + empressa.getName() +  "   CEP: " + empressa.getCep() + "\n"
				+ "ENDEREÇO: " + empressa.getEndereco()
				+ "  CIDADE: " + empressa.getCidade() 
				+ "  ESTADO: " + empressa.getEstado();
	}
	
}