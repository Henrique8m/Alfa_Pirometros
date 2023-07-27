package com.hrodriguesdev.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.utilitary.pdf.PDFTableAuxiliar;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

public class RelatorioGastosProductPDF {

	public static void generated(
			List<List<Product>> listOfListMaintenance,
			List<OrcamentoDTOEquipamento> listMaintenance, 
			List<List<Product>> listOfListOrcamento,
			List<OrcamentoDTOEquipamento> listOrcamento) {

		
		// Criação do objeto que será um documento PDF
		Document documento = new Document();
		String caminho = AlfaPirometrosApplication.URL_RELATORIOS;
		String numeroRelatorioStr = "2023";
		String titleNumberDoc = "RELATORIO DE BUSCA N°:";
		
		try {

			// Faz o apontamento para o arquivo de destino
			File diretorio = new File(caminho);
			diretorio.mkdir();
			caminho = caminho + "\\";
			String arquivo = 
					caminho 
					+ LocalDate.now().toString() 
					+ " Referencia Cruzada" 
					+  ".pdf";

			PdfWriter.getInstance(documento, new FileOutputStream(arquivo));

			// Realiza a abertura do arquivo para escrita
			documento.open();

			// Adicionando meta dados ao nosso arquivo
			documento.addTitle("Referencia Cruzada");
			documento.addSubject("Estoque, materias e orcamento");
			documento.addAuthor("Henrique Rodrigues");
			documento.addCreator("Henrique Rodrigues");

//			Add o cabeçalho no documento
			documento.add(PDFTableAuxiliar.headerDefaultAlfa(titleNumberDoc, numeroRelatorioStr));
						


			
			
			documento.add(PDFTableAuxiliar.footerDefaultAlfa());

			documento.setMargins(0, 0, 10, 10);

			// Fecha o arquivo após a escrita da mensagem
			documento.close();
			Runtime.getRuntime().exec("explorer.exe " + caminho);
			Runtime.getRuntime().exec("explorer.exe " + arquivo);

		} catch (Exception e) {
			documento.close();
			e.printStackTrace();
		}
	}

	

}