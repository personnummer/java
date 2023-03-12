import java.io.IOException;

import dev.personnummer.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class InterimnummerTest {
    private static Options opts = new Options(true, true);

    @BeforeAll
    public static void setup() throws IOException {
        DataProvider.initialize();
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidInterimNumbers")
    public void testValidateInterim(PersonnummerData ssn) {
        assertTrue(Personnummer.valid(ssn.longFormat, opts));
        assertTrue(Personnummer.valid(ssn.shortFormat, opts));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getInvalidInterimNumbers")
    public void testValidateInvalidInterim(PersonnummerData ssn) {
        assertFalse(Personnummer.valid(ssn.longFormat, opts));
        assertFalse(Personnummer.valid(ssn.shortFormat, opts));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidInterimNumbers")
    public void testFormatLongInterim(PersonnummerData ssn) throws PersonnummerException {
        Personnummer pnr = Personnummer.parse(ssn.longFormat, opts);

        assertEquals(pnr.format(false), ssn.separatedFormat);
        assertEquals(pnr.format(true), ssn.longFormat);
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidInterimNumbers")
    public void testFormatShortInterim(PersonnummerData ssn) throws PersonnummerException {
        Personnummer pnr = Personnummer.parse(ssn.shortFormat, opts);

        assertEquals(pnr.format(false), ssn.separatedFormat);
        assertEquals(pnr.format(true), ssn.longFormat);
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getInvalidInterimNumbers")
    public void testInvalidInterimThrows(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.shortFormat, opts));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.longFormat, opts));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedLong, opts));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat, opts));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValidInterimNumbers")
    public void testInterimThrowsIfNotActive(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.shortFormat));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.longFormat));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedLong));
        assertThrows(PersonnummerException.class, () -> Personnummer.parse(ssn.separatedFormat));
    }
}
