import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dev.personnummer.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class PersonnummerTest {

    @BeforeAll
    public static void setup() throws IOException {
        DataProvider.initialize();
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidPersonnummer")
    public void testConstructor(PersonnummerData ssn) {
        assertDoesNotThrow(() -> new Personnummer(ssn.longFormat, new Options(false)));
        assertDoesNotThrow(() -> new Personnummer(ssn.shortFormat, new Options(false)));
        assertDoesNotThrow(() -> new Personnummer(ssn.separatedFormat, new Options(false)));
        assertDoesNotThrow(() -> new Personnummer(ssn.separatedFormat, new Options(false)));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidCoordinationNumbers")
    public void testConstructorCoord(PersonnummerData ssn) {
        assertDoesNotThrow(() -> new Personnummer(ssn.longFormat, new Options(true)));
        assertDoesNotThrow(() -> new Personnummer(ssn.shortFormat, new Options(true)));
        assertDoesNotThrow(() -> new Personnummer(ssn.separatedFormat, new Options(true)));
        assertDoesNotThrow(() -> new Personnummer(ssn.separatedFormat, new Options(true)));
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getInvalidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testConstructorInvalid(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.longFormat, new Options(false)), ssn.longFormat);
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.shortFormat, new Options(false)), ssn.shortFormat);
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.separatedFormat, new Options(false)), ssn.separatedFormat);
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.separatedLong, new Options(false)), ssn.separatedLong);
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getInvalidCoordinationNumbers"})
    public void testConstructorCoordInvalid(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.longFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.shortFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.separatedFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> new Personnummer(ssn.separatedFormat, new Options(true)));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidPersonnummer")
    public void testParse(PersonnummerData ssn) {
        assertDoesNotThrow(() -> Personnummer.parse(ssn.longFormat, new Options(false)));
        assertDoesNotThrow(() -> Personnummer.parse(ssn.shortFormat, new Options(false)));
        assertDoesNotThrow(() -> Personnummer.parse(ssn.separatedFormat, new Options(false)));
        assertDoesNotThrow(() -> Personnummer.parse(ssn.separatedFormat, new Options(false)));
    }
    
    @ParameterizedTest
    @MethodSource("DataProvider#getDate")
    public void testDate(PersonnummerData ssn) {
    	List<Personnummer> data = new ArrayList<>();
		try {
			data.add(new Personnummer(ssn.longFormat, new Options()));
			data.add(new Personnummer(ssn.shortFormat, new Options()));
			data.add(new Personnummer(ssn.separatedFormat, new Options()));
			
			data.forEach(entry -> {
				
				assertDoesNotThrow(() -> entry.getDate());
				LocalDateTime dateTime = entry.getDate();
				String expectedYear = entry.getFullYear();
				String expectedMonth = entry.getMonth();
				if(expectedMonth.charAt(0)=='0')
					expectedMonth = expectedMonth.substring(1);
				String expectedDate = String.valueOf(entry.getRealDay());
				if(expectedDate.charAt(0)=='0')
					expectedDate = expectedDate.substring(1);
//				Integer expected = Integer.valueOf(ssn.longFormat.substring(0,4));
				String actualYear = String.valueOf(dateTime.getYear());
				String actualMonth = String.valueOf(dateTime.getMonth().getValue());
				String actualDate = String.valueOf(dateTime.getDayOfMonth());
				
				assertEquals(expectedYear, actualYear); //, "expected year = " + expectedYear + "\n" + "Actual year = " + actualYear);
				assertEquals(expectedMonth, actualMonth);//, "expected month = " + expectedMonth + "\n" + "Actual month = " + actualMonth);
				assertEquals(expectedDate, actualDate); //, "expected date = " + expectedDate + "\n" + "Actual date = " + actualDate);
				
			});
			
			
			
		} catch (PersonnummerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidCoordinationNumbers", "DataProvider#getValidPersonnummer"})
    public void testParseCoord(PersonnummerData ssn) {
        assertDoesNotThrow(() -> Personnummer.parse(ssn.longFormat, new Options(true)));
        assertDoesNotThrow(() -> Personnummer.parse(ssn.shortFormat, new Options(true)));
        assertDoesNotThrow(() -> Personnummer.parse(ssn.separatedFormat, new Options(true)));
        assertDoesNotThrow(() -> Personnummer.parse(ssn.separatedFormat, new Options(true)));
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getInvalidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testParseInvalid(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.longFormat, new Options(false)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.shortFormat, new Options(false)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, new Options(false)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, new Options(false)));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getInvalidCoordinationNumbers")
    public void testParseInvalidCoord(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.longFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.shortFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, new Options(true)));
    }


    @ParameterizedTest
    @MethodSource({"DataProvider#getValidPersonnummer"})
    public void testAge(PersonnummerData ssn) throws PersonnummerException {
        LocalDate date = LocalDate.parse(ssn.longFormat.substring(0, ssn.longFormat.length() - 4), DateTimeFormatter.ofPattern("yyyyMMdd"));
        int years = (date.until(LocalDate.now())).getYears();

        assertEquals(years, Personnummer.parse(ssn.separatedLong, new Options(false)).getAge());
        assertEquals(years, Personnummer.parse(ssn.separatedFormat, new Options(false)).getAge());
        assertEquals(years, Personnummer.parse(ssn.longFormat, new Options(false)).getAge());
        assertEquals(years > 99 ? years - 100 : years, Personnummer.parse(ssn.shortFormat, new Options(false)).getAge());
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidCoordinationNumbers"})
    public void testAgeCn(PersonnummerData ssn) throws PersonnummerException {
        String strDay = ssn.longFormat.substring(ssn.longFormat.length() - 6, ssn.longFormat.length() - 4);
        int day = Integer.parseInt(strDay) - 60;
        strDay = day < 10 ? "0" + Integer.toString(day) : Integer.toString(day);

        LocalDate date = LocalDate.parse(ssn.longFormat.substring(0, ssn.longFormat.length() - 6) + strDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
        int years = (date.until(LocalDate.now())).getYears();

        assertEquals(years, Personnummer.parse(ssn.separatedLong, new Options(true)).getAge());
        assertEquals(years, Personnummer.parse(ssn.separatedFormat, new Options(true)).getAge());
        assertEquals(years, Personnummer.parse(ssn.longFormat, new Options(true)).getAge());
        assertEquals(years > 99 ? years - 100 : years, Personnummer.parse(ssn.shortFormat, new Options(true)).getAge());
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testFormat(PersonnummerData ssn) throws PersonnummerException {
        assertEquals(ssn.separatedFormat, Personnummer.parse(ssn.separatedLong, new Options(true)).format());
        assertEquals(ssn.separatedFormat, Personnummer.parse(ssn.separatedFormat, new Options(true)).format());
        assertEquals(ssn.separatedFormat, Personnummer.parse(ssn.longFormat, new Options(true)).format());
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testFormatLong(PersonnummerData ssn) throws PersonnummerException {
        assertEquals(ssn.longFormat, Personnummer.parse(ssn.separatedLong, new Options(true)).format(true));
        assertEquals(ssn.longFormat, Personnummer.parse(ssn.separatedFormat, new Options(true)).format(true));
        assertEquals(ssn.longFormat, Personnummer.parse(ssn.longFormat, new Options(true)).format(true));
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testValid(PersonnummerData ssn) {
        assertTrue(Personnummer.valid(ssn.longFormat));
        assertTrue(Personnummer.valid(ssn.separatedLong));
        assertTrue(Personnummer.valid(ssn.separatedFormat));
        assertTrue(Personnummer.valid(ssn.shortFormat));
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getInvalidPersonnummer", "DataProvider#getInvalidCoordinationNumbers"})
    public void testValidInvalid(PersonnummerData ssn) {
        assertFalse(Personnummer.valid(ssn.longFormat));
        assertFalse(Personnummer.valid(ssn.separatedLong));
        assertFalse(Personnummer.valid(ssn.separatedFormat));
        assertFalse(Personnummer.valid(ssn.shortFormat));
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testMaleFemale(PersonnummerData ssn) throws PersonnummerException {
        assertEquals(ssn.isMale, Personnummer.parse(ssn.longFormat, new Options(true)).isMale());
        assertEquals(ssn.isMale, Personnummer.parse(ssn.separatedLong, new Options(true)).isMale());
        assertEquals(ssn.isMale, Personnummer.parse(ssn.separatedFormat, new Options(true)).isMale());
        assertEquals(ssn.isMale, Personnummer.parse(ssn.shortFormat, new Options(true)).isMale());

        assertEquals(ssn.isFemale, Personnummer.parse(ssn.longFormat, new Options(true)).isFemale());
        assertEquals(ssn.isFemale, Personnummer.parse(ssn.separatedLong, new Options(true)).isFemale());
        assertEquals(ssn.isFemale, Personnummer.parse(ssn.separatedFormat, new Options(true)).isFemale());
        assertEquals(ssn.isFemale, Personnummer.parse(ssn.shortFormat, new Options(true)).isFemale());
    }

    @ParameterizedTest
    @MethodSource({"DataProvider#getValidPersonnummer", "DataProvider#getValidCoordinationNumbers"})
    public void testSeparator(PersonnummerData ssn) throws PersonnummerException {
        String sep = ssn.separatedFormat.contains("+") ? "+" : "-";
        assertEquals(sep, Personnummer.parse(ssn.longFormat, new Options(true)).separator());
        assertEquals(sep, Personnummer.parse(ssn.separatedLong, new Options(true)).separator());
        assertEquals(sep, Personnummer.parse(ssn.separatedFormat, new Options(true)).separator());
        // Getting the separator from a short formatted none-separated person number is not actually possible if it is intended to be a +.
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getOrganisationsnummer")
    public void testOrgNummer(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.shortFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.shortFormat, new Options(false)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, new Options(true)));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, new Options(false)));
    }

}
