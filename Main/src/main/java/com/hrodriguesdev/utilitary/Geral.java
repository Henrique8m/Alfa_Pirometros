package com.hrodriguesdev.utilitary;

import java.sql.Date;

public class Geral {
	
	@SuppressWarnings("deprecation")
	public static Date dateParceString(String dateStr){		
		String[] split = dateStr.split("/");
		int day = Integer.parseInt(split[0]);
		int month = Integer.parseInt(split[1]);
		month -= 1;
		int year = Integer.parseInt(split[2]);
		year -= 1900;
		
		return new Date(year, month, day);
	}

}
