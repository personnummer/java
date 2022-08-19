package dev.personnummer;

public final class IdentificationNumberFactory {
	private IdentificationNumberFactory() {
	}

	public static IdentificationNumber valueOf(String id) throws PersonnummerException {
		return valueOf(id, new Options());
	}

	public static IdentificationNumber valueOf(String id, Options options) throws PersonnummerException {
		if (Personnummer.valid(id)) {
			return Personnummer.parse(id, options);
		}
		if (OrganizationNummer.valid(id)) {
			return OrganizationNummer.parse(id);
		}
		throw new PersonnummerException("Not a valid identification number");
	}
}
