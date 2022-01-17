package jazmin.driver.jdbc.smartjdbc.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 
 * @author skydu
 *
 */
public class DateUtils {
 
    public static Date asDate(LocalDate localDate) {
    	if(localDate==null){
    		return null;
    	}
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
 
    public static Date asDate(LocalDateTime localDateTime) {
    	if(localDateTime==null){
    		return null;
    	}
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
 
    public static LocalDate asLocalDate(Date date) {
    	if(date==null){
    		return null;
    	}
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
 
    public static LocalDateTime asLocalDateTime(Date date) {
    	if(date==null){
    		return null;
    	}
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    public static LocalTime asLocalTime(java.sql.Time time) {
    	if(time==null){
    		return null;
    	}
        return time.toLocalTime();
      }
}