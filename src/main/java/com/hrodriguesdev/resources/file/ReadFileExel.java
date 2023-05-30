package com.hrodriguesdev.resources.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.hrodriguesdev.resources.calibracao.LineTableEntradaEquipamento;

public class ReadFileExel {
//	private String fileName = "C:/users/henri/desktop/TermoparJ.xls";

	public List<?> getLines(File file) {			   
		List<LineTableEntradaEquipamento> list = new ArrayList<>();
            try {
                   FileInputStream arquivo = new FileInputStream(file);

                   HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
                   HSSFSheet sheetSinal = workbook.getSheetAt(0);
                   Iterator<Row> rowIterator = sheetSinal.iterator();

                   while (rowIterator.hasNext()) {
                          Row row = rowIterator.next();
                          Iterator<Cell> cellIterator = row.cellIterator();
                          LineTableEntradaEquipamento obj = new LineTableEntradaEquipamento();
                          list.add(obj);
                          while (cellIterator.hasNext()) {
                                 Cell cell = cellIterator.next();
                                 switch (cell.getColumnIndex()) {
                                 case 0:
                                	 obj.setIndicado(cell.getNumericCellValue());
                                       break;
                                 case 1:
                                	 obj.setAplicado(cell.getNumericCellValue());
                                     break;
                                 }
                          }
                   }
                   arquivo.close();

            } catch (IOException e) {
                   e.printStackTrace();
                   System.out.println("Arquivo Excel não encontrado!");
            }

            if (list.size() == 0) {
                   System.out.println("Nada encontrado!");
            } 
      return list;
      }
	
}