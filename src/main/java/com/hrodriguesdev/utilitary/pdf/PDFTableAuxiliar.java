package com.hrodriguesdev.utilitary.pdf;

import java.io.IOException;
import java.net.URL;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.utilitary.Fontes;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.codec.PngImage;

public class PDFTableAuxiliar {
	
	public static PdfPTable headerDefaultAlfa(String titleNumberDoc, String numberDoc) throws IOException {
//		Criado o paragrafo
		Paragraph paragraph = new Paragraph();
		
//		criado a tablela
		PdfPTable tableHeader = new PdfPTable(2);
		// Tamanho ocupando 100% da pagina
		tableHeader.setWidthPercentage(100);
		PdfPCell cell = new PdfPCell();		
	   
	/*Cabeçalho
	 * 	
	 */	

//		Logo Alfa
		URL imageUrl = new URL(AlfaPirometrosApplication.class.getResource("gui/resources/icons/logo-alfa.png").toString() );			
		Image img = PngImage.getImage(imageUrl);
		img.scaleAbsolute(143, 50);
		img.setAlignment(Element.ALIGN_LEFT);	
		cell.addElement(img);
		cell.setBorderColor(BaseColor.WHITE);	
		tableHeader.addCell(cell);				
		cell = new PdfPCell();			
		
//		Nome do texto do documento
		PdfPTable tableCertificado = new PdfPTable(1);
		paragraph = new Paragraph(titleNumberDoc, Fontes.comic_Sans_12);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		cell.setBorderColor(BaseColor.WHITE);
		cell.addElement(paragraph);			
		tableCertificado.addCell(cell);
		
//		Numero do Documento			
		tableCertificado = new PdfPTable(1);
		paragraph = new Paragraph(numberDoc, Fontes.tahoma_Negrito_18);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		cell.setBorderColor(BaseColor.WHITE);
		cell.addElement(paragraph);			
		tableCertificado.addCell(cell);
		
//		Colocando a string do certificado e na proxima linha o numero, dentro da tabela cabeçalho
		cell = new PdfPCell();
		cell.addElement(tableCertificado);
		cell.setBorderColor(BaseColor.WHITE);		
		tableHeader.addCell(cell);
		
		return tableHeader;

	}
	
	public static Paragraph footerDefaultAlfa() {
		Paragraph paragraph = new Paragraph ("____________________________________________________________________________________________________\r\n"
				+ "\n"
				+ "Alfa Controle de Processos Industriais Eireli.\r\n"
				+ "R. Itapecerica, 46 – Centro – Divinópolis – MG\r\n"
				+ "Tel: (37) 3213-5791 – (37) 9 8402-6020 \r\n"
				+ "henrique@alfacontroles.com\r\n"
				+ "", Fontes.VERDANA_8);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		return paragraph;		
	}

}
