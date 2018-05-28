package minos.view.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MinosDateFormatter {
	
	public static String format(LocalDateTime localDateTime){
		String dateFormatee = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return dateFormatee;
	}

	public static String format(LocalDate localDate){
		String dateFormatee = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return dateFormatee;
	}
}
