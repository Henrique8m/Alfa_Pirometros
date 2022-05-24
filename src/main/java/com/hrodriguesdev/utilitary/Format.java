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
		StringBuilder stringBuilder = new StringBuilder(input);;
		
		if(input.length()>4) {
			replace = stringBuilder.insert(input.length()-4, '/').toString();
			if(input.length()>6)
				replace = stringBuilder.insert(input.length()-6, '/').toString();
		}
		if(input.length() > 10) {
			replace = stringBuilder.replace(input.length()-1, input.length(), "").toString();
		}
		return replace;
	}

	public static final SimpleDateFormat formataTimeString = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
	
}
