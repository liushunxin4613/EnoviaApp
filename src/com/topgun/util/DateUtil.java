package com.topgun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	public static final String TAG = "DateUtil";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
			Locale.ENGLISH);

	public static final SimpleDateFormat sdfa = new SimpleDateFormat("yyyy年MM月dd日",
			Locale.ENGLISH);
	
	public static final SimpleDateFormat sdfb = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒",
			Locale.ENGLISH);
	
	public static String dateToString(Calendar c,SimpleDateFormat sdf) {
		String time = sdf.format(c.getTime());
		return time;
	}

	public static String dateToString(Date date,SimpleDateFormat sdf) {
		String time = sdf.format(date);
		return time;
	}
	
	public static String ToString(String dateStr,SimpleDateFormat sdfStr,SimpleDateFormat sdf) {
		String dateSt = null;
		if (dateStr != null) {
			String ss = dateStr.trim();
			if (ss.length() > 0) {
				Date date = null;
				try {
					date = sdfStr.parse(dateStr);
					dateSt= sdf.format(date);
				} catch (ParseException e) {
					e.printStackTrace();
					dateSt = dateStr;
				}
			}
		}
		return dateSt;
	}
	
	@SuppressWarnings("unused")
	public static Date StringToDate(String dataStr){
		Date date = null;
		try {
			date = sdfa.parse(dataStr);
			String dataSt = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
