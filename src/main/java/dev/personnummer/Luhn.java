package dev.personnummer;

public class Luhn {
	private Luhn() {
	}

	public static int luhn(String value) {
		// Luhn/mod10 algorithm. Used to calculate a checksum from the
		// passed value. The checksum is returned and tested against the control number
		// in the personal identity number to make sure that it is a valid number.

		int temp;
		int sum = 0;

		for (int i = 0; i < value.length(); i++) {
			temp = Character.getNumericValue(value.charAt(i));
			temp *= 2 - (i % 2);
			if (temp > 9) {
				temp -= 9;
			}

			sum += temp;
		}

		return (10 - (sum % 10)) % 10;
	}
}
