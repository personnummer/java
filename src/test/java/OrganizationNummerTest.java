import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import dev.personnummer.OrganizationNummer;
import dev.personnummer.PersonnummerException;

public class OrganizationNummerTest {

    @BeforeAll
    public static void setup() throws IOException {
        DataProvider.initialize();
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getOrganisationsnummer")
    void testOrgNummer(PersonnummerData ssn) throws PersonnummerException {
        assertEquals(ssn.separatedFormat, OrganizationNummer.parse(ssn.shortFormat).format());
        assertEquals(ssn.separatedFormat, OrganizationNummer.parse(ssn.shortFormat).format());
        assertEquals(ssn.separatedFormat, OrganizationNummer.parse(ssn.separatedFormat).format());
        assertEquals(ssn.separatedFormat, OrganizationNummer.parse(ssn.separatedFormat).format());
        assertEquals("16" + ssn.shortFormat, OrganizationNummer.parse(ssn.separatedFormat).format(true));
        assertEquals("16" + ssn.shortFormat, OrganizationNummer.parse(ssn.separatedFormat).format(true));
    }

    @ParameterizedTest
    @MethodSource({ "DataProvider#getInvalidPersonnummer", "DataProvider#getValidCoordinationNumbers" })
    void testParseInvalid(PersonnummerData ssn) {
        assertThrows(PersonnummerException.class, () -> OrganizationNummer.parse(ssn.longFormat));
        assertThrows(PersonnummerException.class, () -> OrganizationNummer.parse(ssn.shortFormat));
        assertThrows(PersonnummerException.class, () -> OrganizationNummer.parse(ssn.separatedFormat));
        assertThrows(PersonnummerException.class, () -> OrganizationNummer.parse(ssn.separatedFormat));
    }

}
