package com.hrodriguesdev.utilitary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.AlfaPirometrosApplication;

public class Log {
	private static String logName = "log.txt";
	
	public static void logOfList(List<String> log) {
		String path = System.getProperty("user.home").toString() + AlfaPirometrosApplication.URL_DIRETORIO_YGGDRASIL +"\\" + logName;
		File arquivo = new File(path);
		
		if( !arquivo.exists()){
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		log.add(Format.formataDateTimeString.format(new Date(System.currentTimeMillis())) + "\n");
		
		try {
//			Files.write(Paths.get(arquivo.getPath()), lista, StandardOpenOption.APPEND);			
			Files.write(Path.of(path), log, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	public static void logString(String clas, String texto) {
		List<String> list = new ArrayList<>();
		list.add(clas);
		list.add(texto);
		Log.logOfList(list);
	}

}
