package awe.ideeninitiative.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DatumUtil {

    private static final String ZEITSTEMPEL_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static LocalDateTime formeStringZuDatumUm(String erstelldatum) {
        if(erstelldatum ==  null || StringUtils.isEmpty(erstelldatum)){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ZEITSTEMPEL_FORMAT);
        return LocalDateTime.parse(erstelldatum, formatter);
    }

    public static String formeDatumZuStringUm(LocalDateTime datum){
        if(datum == null){
            return null;
        }
        return String.format("%02d.%02d.%04d %02d:%02d:%02d", datum.getDayOfMonth(), datum.getMonthValue(), datum.getYear(), datum.getHour(), datum.getMinute(), datum.getSecond());
    }
}
