package com.hrodriguesdev.utilitary.appdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.hrodriguesdev.AlfaPirometrosApplication;

public class UserData {
	
	private File arquivoProperties;
	private String caminho = System.getProperty("user.home").toString() + AlfaPirometrosApplication.strDiretorioYggDrasil;
	private String nameProperties = "UserConfig.properties";
	
	public void writeFile(String data) {
		try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(arquivoProperties))){
			bw1.write(data);
			
		}
		catch (IOException e ) {
			e.printStackTrace();
		}
		
	}

	public void createdDiretorioAndFile() {		
		try {
			File diretorio1 = new File(caminho);
			diretorio1.mkdir();
			arquivoProperties = new File(diretorio1, "userConfig.properties" );
			arquivoProperties.createNewFile(); 
										
		}catch (IOException e1) {
			e1.printStackTrace(); 
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	
	}
	
	public boolean checkFile() {
		try {
			File diretorio1 = new File(caminho);
			diretorio1.mkdir();
			arquivoProperties = new File(diretorio1, nameProperties);
			if(!arquivoProperties.exists()) {
				return false; 
			}else return true;
							
		}catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
	}
	
    public String read(){   
    	String pathToProperties = caminho +"\\" + nameProperties;  
    	if(pathToProperties!=null) {
    		try(BufferedReader br = new BufferedReader(new FileReader(pathToProperties) ) ){
    			return br.readLine();    			
				
	    	}catch (IOException e) {
				System.out.println(e.getMessage());

			}catch(NullPointerException e) {
				System.out.println("Falha no caminho do arquivo de propriedades!");
			}
    	}
    	return "";
	}				
	
}
