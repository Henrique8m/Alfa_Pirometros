package com.hrodriguesdev.relatorio;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CriptografiaPDF {

	   @SuppressWarnings("deprecation")
	public static void main(String[] args) {
	       // criação do objeto documento
	      Document document = new Document();
	      try {

	          PdfWriter writer = PdfWriter.getInstance(document, new
	          FileOutputStream("C:\\Encrypted.pdf"));
	          writer.setEncryption(PdfWriter.STRENGTH128BITS,
	          "PDFDevmediaJava", "devmedia", PdfWriter.AllowCopy
	          | PdfWriter.AllowPrinting);
	          document.open();
	          document.add(new Paragraph("Exemplo Criptografia"));
	      }
	      catch(DocumentException de) {
	          System.err.println(de.getMessage());
	      }
	      catch(IOException ioe) {
	          System.err.println(ioe.getMessage());
	      }
	      document.close();
	  }
}

/*

 precisamos baixar o jar do shared-bouncycastle-reduced-0.9.9.jar

PdfWriter writer = PdfWriter.getInstance(document,
new FileOutputStream("C:\\Encrypted.pdf"));
writer.setEncryption(PdfWriter.STRENGTH128BITS, "PDFDevmediaJava",
"devmedia", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);

*/