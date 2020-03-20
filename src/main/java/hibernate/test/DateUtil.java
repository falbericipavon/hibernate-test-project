package hibernate.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String FECHA_F1 = "yyyy-MM-dd";
	public static String FECHA_F2= "yyyy-MM-dd HH:mm:ss";
	public static String format(String pattern, Date date) {
		SimpleDateFormat sdfDMY = new SimpleDateFormat(pattern);
		return sdfDMY.format(date);
		}

}
