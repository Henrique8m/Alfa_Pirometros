package com.hrodriguesdev.relatorio;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Ensaios;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableEnsaios {
	private Ensaios ensaios;

	public TableEnsaios(Ensaios ensaios) {
		this.ensaios = ensaios;
	}

	public List<PdfPTable> getTableEnsaios() throws NullPointerException {
		List<PdfPTable> tableList = new ArrayList<>();

		String referencia = ensaios.getReferencia();
		String[] referenciaArray = referencia.split("\n");

		String primeiro = ensaios.getPrimeiro();
		String[] primeiroArray = primeiro.split("\n");

		String segundo = ensaios.getSegundo();
		String[] segundoArray = segundo.split("\n");

		String terceiro = ensaios.getTerceiro();
		String[] terceiroArray = terceiro.split("\n");

		for (int i = 0; i < referenciaArray.length; i++) {
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.addCell(addCell(referenciaArray[i]));
			table.addCell(addCell(primeiroArray[i]));
			table.addCell(addCell(segundoArray[i]));
			table.addCell(addCell(terceiroArray[i]));
			tableList.add(table);
		}

		return tableList;
	}

	private PdfPCell addCell(String phrase) {

		PdfPCell cell = new PdfPCell(new Phrase(phrase));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(1);
		cell.setPaddingBottom(5);

		return cell;
	}

}
