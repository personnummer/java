import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import dev.personnummer.IdentificationNumber;
import dev.personnummer.IdentificationNumberFactory;
import dev.personnummer.PersonnummerException;

class IdentificationNumberFactoryTest {
	@BeforeAll
	public static void setup() throws IOException {
		DataProvider.initialize();
	}

	@ParameterizedTest
	@MethodSource({"DataProvider#getOrganisationsnummer","DataProvider#getValidPersonnummer"})
	void testOrgNummer(PersonnummerData ssn) throws PersonnummerException {
		IdentificationNumber identificationNumber = IdentificationNumberFactory.valueOf(ssn.shortFormat);
		assertNotEquals(null,identificationNumber);
	}
}