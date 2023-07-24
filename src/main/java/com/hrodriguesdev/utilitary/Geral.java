package com.hrodriguesdev.utilitary;

import java.sql.Date;

public class Geral {
	
	@SuppressWarnings("deprecation")
	public static Date dateParceString(String dateStr){		
		StringBuilder stringBuilder = new StringBuilder(dateStr);
		if(dateStr.length() >= 10 ) {
			while(dateStr.length() > 10) {
				dateStr = stringBuilder.replace(dateStr.length()-1, dateStr.length(), "").toString();
			}
			String[] split = dateStr.split("/");
			int day = Integer.parseInt(split[0]);
			int month = Integer.parseInt(split[1]);
			month -= 1;
			int year = Integer.parseInt(split[2]);
			year -= 1900;
			return new Date(year, month, day);
		}
		return null;
		
	}
	
	@SuppressWarnings("deprecation")
	public static Date dateParceTraco(String dateStr){		
		StringBuilder stringBuilder = new StringBuilder(dateStr);
		if(dateStr.length() >= 10 ) {
			while(dateStr.length() > 10) {
				dateStr = stringBuilder.replace(dateStr.length()-1, dateStr.length(), "").toString();
			}
			String[] split = dateStr.split("-");
			int day = Integer.parseInt(split[2]);
			int month = Integer.parseInt(split[1]);
			month -= 1;
			int year = Integer.parseInt(split[0 ]);
			year -= 1900;
			return new Date(year, month, day);
		}
		return null;
		
	}
	
	@SuppressWarnings("deprecation")
	public static Date dateParceNotSeparator(String dateStr){		
		StringBuilder stringBuilder = new StringBuilder(dateStr);

			while(dateStr.length() > 10) {
				dateStr = stringBuilder.replace(dateStr.length()-1, dateStr.length(), "").toString();
			}
			int day = 0;
			if(stringBuilder.length()==8) {
				day = Integer.parseInt(stringBuilder.substring(stringBuilder.length() -8, stringBuilder.length()-7));
				
			}else
				day = Integer.parseInt(String.valueOf(stringBuilder.charAt(0)));
			int month = Integer.parseInt(stringBuilder.substring(stringBuilder.length() -6, stringBuilder.length()-5));
//			month -= 1;
			int year = Integer.parseInt(stringBuilder.substring(stringBuilder.length() -4, stringBuilder.length()));
//			System.out.println("Sysou " +  day + "-" + month + "-" + year);
			year -= 1900;
			return new Date(year, month, day);

		
	}
	
	public static int getDecimal(String hex){
	    String digits = "0123456789ABCDEF";
	             hex = hex.toUpperCase();
	             int val = 0;
	             for (int i = 0; i < hex.length(); i++)
	             {
	                 char c = hex.charAt(i);
	                 int d = digits.indexOf(c);
	                 val = 16*val + d;
	             }
	             return val;
	}

}
