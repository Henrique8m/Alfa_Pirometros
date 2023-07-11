package com.hrodriguesdev.resources.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.AlfaPirometrosApplication;

public class ReadFiles {

//	Os arquivos de calibrações dos pirometros, sao armazanados como o nome do modelo do mesmo
	
    private static String pathToFile;    
    private static String[] line; 
       
    
    public static String[] readFile(String nameFile) throws com.hrodriguesdev.ExceptionAlfa {    
    	ReadFiles.pathToFile = System.getProperty("user.home").toString() + AlfaPirometrosApplication.URL_DIRETORIO_YGGDRASIL +"\\" + nameFile;
    	
    	if(pathToFile!=null)
    		try(BufferedReader br = new BufferedReader(new FileReader(pathToFile, StandardCharsets.UTF_8) ) ){
    			String itemsToFile = br.readLine();
    			List<String> listLine = new ArrayList<>();

    			while(itemsToFile != null) {				
    				listLine.add(itemsToFile);								
					itemsToFile = br.readLine();					
				}	
    			
    			if(listLine.size()>0) {
    				line = new String[listLine.size()];
    				for(int i=0; i< listLine.size(); i++) {
    					line[i] = listLine.get(i);
    				}
    				return line;
    			}
				
	    	}catch (IOException e) {
				throw new com.hrodriguesdev.ExceptionAlfa("Falta arquivo com os dados sobre o modelo do equipamento");
			}catch(NullPointerException e) {
				System.out.println("Falha no caminho do arquivo\n" + e.getMessage());
				throw new com.hrodriguesdev.ExceptionAlfa("Falha no caminho do arquivo\n" + e.getMessage());
			}
    	return line;
	}


	public static boolean saveOrUpdatedeFile(String fileName, String[] lines) {
		try {
			File diretorio = new File(System.getProperty("user.home").toString() + AlfaPirometrosApplication.URL_DIRETORIO_YGGDRASIL);
			diretorio.mkdir();
			File arquivo = new File(diretorio, fileName );
			
			if(arquivo.exists()) {
				arquivo.delete();
			}
			arquivo.createNewFile(); 
			writeFile(arquivo, lines);

			
		}catch (IOException e1) {
			e1.printStackTrace();
			return false; 
		}catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}	
		
		return true;
	}
	
	private static void writeFile(File arquivo, String[] lines) {
		try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))){
			for(String x : lines) {
				bw1.write(x);
				bw1.newLine();
			}
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		
	}
		
}
