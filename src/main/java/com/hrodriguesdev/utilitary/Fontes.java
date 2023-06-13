package com.hrodriguesdev.utilitary;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

public final class Fontes {
	public static final Font comic_Sans_12 = FontFactory.getFont("C://windows//fonts//comic.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11.0f, Font.NORMAL, BaseColor.BLACK);
	
	public static final	Font tahoma_Negrito_18 = FontFactory.getFont("C://windows//fonts//tahomabd.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 18.0f, Font.UNDERLINE, BaseColor.BLACK);
	
	public static final Font TIMES_NEGRITO_10 = FontFactory.getFont("C://windows//fonts//timesbd.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10.0f, Font.NORMAL, BaseColor.BLACK);
	
	public static final Font times_Negrito_ITALICO_10 = FontFactory.getFont("C://windows//fonts//timesbi.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10.0f, Font.NORMAL, BaseColor.BLACK);
	
	public static final Font TIMES_10 = FontFactory.getFont("C://windows//fonts//times.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10.0f, Font.NORMAL, BaseColor.BLACK);
	
	public static final Font times_Negrito_12 = FontFactory.getFont("C://windows//fonts//timesbd.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12.0f, Font.NORMAL, BaseColor.BLACK);
	
	public static final	Font VERDANA_8 = FontFactory.getFont("C://windows//fonts//verdana.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8.0f, Font.NORMAL, BaseColor.BLACK);
}
