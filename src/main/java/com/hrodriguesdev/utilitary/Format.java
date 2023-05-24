package com.hrodriguesdev.utilitary;

import java.text.SimpleDateFormat;

public class Format {
	
	public static String replacePlaca(String input) {
		String replace = input.toUpperCase();
		String clearChar;
		
		StringBuilder stringBuilder = new StringBuilder(input.toUpperCase());
		
		if(input.length()<3) {			
			replace = replace.replaceAll("[^A-Z]+", "");
			
		}else if(input.length()==3) {
			replace = stringBuilder.insert(input.length(), '-').toString();
		}else if(input.length()>3 && replace.charAt(3) != '-' ) {
			replace = stringBuilder.insert(3 , '-').toString();
		}else if(input.length()==5 || input.length()<6){
			clearChar = String.valueOf(replace.charAt(input.length()-1)).replaceAll("[^0-9]+", "");
			replace = stringBuilder.replace(input.length()-1, input.length(), "").toString() + clearChar;
		}
		if(input.length() > 8) {
			replace = stringBuilder.replace(input.length()-1, input.length(), "").toString();
		}
		return replace;
	}
	
	public static String replaceData(String input) {
		input = input.replaceAll("[^0-9]+", "");
		String replace = input;
		StringBuilder stringBuilder = new StringBuilder(input);

		if(input.length() > 8) {
			replace = stringBuilder.replace(input.length()-1, input.length(), "").toString();
			stringBuilder = new StringBuilder(replace);
		
		} if(input.length()>4) {
			replace = stringBuilder.insert(replace.length()-4, '/').toString();
			if(input.length()>6)
				replace = stringBuilder.insert(replace.length()-7, '/').toString();
		}
		
		return replace;
	}
	
	public static String replaceCep(String input) {
		input = input.replaceAll("[^0-9]+", "");
		String replace = input;
		StringBuilder stringBuilder = new StringBuilder(input);
		
		if(input.length() > 8) {
			replace = stringBuilder.replace(input.length()-1, input.length(), "").toString();
			stringBuilder = new StringBuilder(replace);
		}
		if(input.length()==8) {
			replace = stringBuilder.insert(replace.length()-3, '-').toString();
		}

		
		return replace;
	}
	public static final SimpleDateFormat formatData2 = new SimpleDateFormat("dd-MM-yy");
	public static final SimpleDateFormat formataTimeString = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat formataTimeInt = new SimpleDateFormat("HHmm");
	public static final SimpleDateFormat formataDateTimeString = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static final SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
	public static String getMonthString(int monthInt) {
		switch (monthInt) {
		case 0:
			return "Janeiro";
		case 1:
			return "Fevereiro";		
		case 2:
			return  "Março";	
		case 3:
			return  "Abril";	
		case 4:
			return  "Maio";	
		case 5:
			return  "Junho";
		case 6:
			return "Julho";		
		case 7:
			return "Agosto";	
		case 8:
			return  "Setembro";		
		case 9:
			return  "Outubro";	
		case 10:
			return "Novembro";		
		case 11:
			return "Desembro";
		}
		return "";
	}
	
}
