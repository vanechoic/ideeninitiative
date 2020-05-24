package awe.ideeninitiative.util;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DatumUtilTest {

    private Given given = new Given();
    private When when =  new When();
    private Then then = new Then();
    private String zeitstempelString;
    private LocalDateTime localDateTime;

    @Before
    public void setup(){
        zeitstempelString = null;
        localDateTime = null;
    }

    @Test
    public void formeStringErfolgreichZuLocalDateTimeUm(){
        given.einZeitstempelAlsStringImKorrektenFormat();
        when.formeStringZuDatumUmAufgerufenWird();
        then.dasErstellteDatumEntsprichtDemVorgegebenenWert();
    }

    @Test(expected = DateTimeParseException.class)
    public void formeStringZuLocalDateTimeUmFalschesFormat(){
        given.einZeitstempelAlsStringImFalschenFormat();
        when.formeStringZuDatumUmAufgerufenWird();
    }

    @Test
    public void formeDatumErfolgreichZuStringUm(){
        given.einZeitstempelAlsLocalDate();
        when.formeDatumZuStringUmAufgerufenWird();
        then.derErstellteStringEntsprichtDenDatumswerten();
    }

    private class Given{

        public void einZeitstempelAlsStringImKorrektenFormat() {
            zeitstempelString = "01.02.2003 13:58:23";
        }

        public void einZeitstempelAlsStringImFalschenFormat() {
            zeitstempelString = "2003-02-01 13:58:23";
        }

        public void einZeitstempelAlsLocalDate() {
            localDateTime = LocalDateTime.of(2003, 02, 01, 13, 58, 23);
        }
    }

    private class When{

        public void formeStringZuDatumUmAufgerufenWird() {
            localDateTime = DatumUtil.formeStringZuDatumUm(zeitstempelString);
        }

        public void formeDatumZuStringUmAufgerufenWird() {
            zeitstempelString = DatumUtil.formeDatumZuStringUm(localDateTime);
        }
    }

    private class Then{

        public void dasErstellteDatumEntsprichtDemVorgegebenenWert() {
            assertNotNull(localDateTime);
            assertEquals(1, localDateTime.getDayOfMonth());
            assertEquals(2, localDateTime.getMonthValue());
            assertEquals(2003, localDateTime.getYear());
            assertEquals(13, localDateTime.getHour());
            assertEquals(58, localDateTime.getMinute());
            assertEquals(23, localDateTime.getSecond());
        }

        public void derErstellteStringEntsprichtDenDatumswerten() {
            assertEquals("01.02.2003 13:58:23", zeitstempelString);
        }
    }
}
