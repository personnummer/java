package dev.personnummer;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.*;

/**
 * Class used to validate Swedish personal identity numbers.
 *
 * @author Johannes Tegnér
 */
public final class Personnummer implements Comparable<Personnummer> {
    private static final Pattern regexPattern;

    static {
        regexPattern = Pattern.compile("^(\\d{2})?(\\d{2})(\\d{2})(\\d{2})([-+]?)?((?!000)\\d{3})(\\d?)$");
    }

    /**
     * Create a new Personnummber object from a string.
     * In case options is not passed, they will default to accept any personal and coordination numbers.
     *
     * @param personnummer Personal identity number as a string to create the object from.
     * @param options Options to use when creating the object.
     * @return Personnummer
     * @throws PersonnummerException On parse error.
     */
    public static Personnummer parse(String personnummer, Options options) throws PersonnummerException {
        return new Personnummer(personnummer, options);
    }

    /**
     * Create a new Personnummber object from a string.
     * In case options is not passed, they will default to accept any personal and coordination numbers.
     *
     * @param personnummer Personal identity number as a string to create the object from.
     * @return Personnummer
     * @throws PersonnummerException On parse error.
     */
    public static Personnummer parse(String personnummer) throws PersonnummerException {
        return parse(personnummer, new Options());
    }


    private final int realDay;
    private final Year fullYear;
    private final String century;
    private final String year;
    private final Month month;
    private final String day;
    private final String numbers;
    private final String controlNumber;
    private final boolean isMale;

    public boolean isMale() {
        return isMale;
    }

    public boolean isFemale() {
        return !isMale();
    }

    public String separator() {
        return this.getAge() >= 100 ? "+" : "-";
    }

    public Year getFullYear() {
        return fullYear;
    }

    public String getCentury() {
        return century;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        final int monthNumber = month.getValue();
        return monthNumber < 10 ? "0" + monthNumber : Integer.toString(monthNumber);
    }

    public String getDay() {
        return day;
    }

    public String getNumbers() {
        return numbers;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public int getAge() {
        return (LocalDate.of(fullYear.getValue(), month, realDay).until(LocalDate.now())).getYears();
    }

    /**
     * Create a new Personnummber object from a string.
     * In case options is not passed, they will default to accept any personal and coordination numbers.
     *
     * @param personnummer Personal identity number as a string to create the object from.
     * @param options Options to use when creating the object.
     * @throws PersonnummerException On parse error.
     */
    public Personnummer(String personnummer, Options options) throws PersonnummerException {
        if (personnummer == null) {
            throw new PersonnummerException("Failed to parse personal identity number. Invalid input.");
        }

        Matcher matches = regexPattern.matcher(personnummer);
        if (!matches.find()) {
            throw new PersonnummerException("Failed to parse personal identity number. Invalid input.");
        }

        String century;
        String decade = matches.group(2);
        if (matches.group(1) != null &&  !matches.group(1).isEmpty()) {
            century = matches.group(1);
        } else {
            int born = LocalDate.now().getYear() - Integer.parseInt(decade);
            if (!matches.group(5).isEmpty() && matches.group(5).equals("+")) {
                born -= 100;
            }

            century = Integer.toString(born).substring(0, 2);
        }

        int day = Integer.parseInt(matches.group(4));
        if (options.allowCoordinationNumber) {
            day = day > 60 ? day - 60 : day;
        } else if(day > 60) {
            throw new PersonnummerException("Invalid personal identity number.");
        }

        this.realDay = day;
        this.century = century;
        this.year = decade;
        this.fullYear = Year.of(Integer.parseInt(century + decade));
        String tempMonth = matches.group(3);
        this.day = matches.group(4);
        this.numbers = matches.group(6) + matches.group(7);
        this.controlNumber = matches.group(7);

        try {
            DateTimeFormatter.ofPattern("yyyy MM dd").parse(String.format("%s %s %02d", this.fullYear, tempMonth, this.realDay));
        } catch (DateTimeParseException e) {
            throw new PersonnummerException("Invalid personal identity number.");
        }
        this.month = Month.of(Integer.parseInt(tempMonth));

        this.isMale =  Integer.parseInt(Character.toString(this.numbers.charAt(2))) % 2 == 1;

        // The format passed to Luhn method is supposed to be YYmmDDNNN
        // Hence all numbers that are less than 10 (or in last case 100) will have leading 0's added.
        if (luhn(String.format("%s%s%s%s", this.year, this.getMonth(), this.day, matches.group(6))) != Integer.parseInt(this.controlNumber)) {
            throw new PersonnummerException("Invalid personal identity number.");
        }
    }

    /**
     * Create a new Personnummber object from a string.
     * In case options is not passed, they will default to accept any personal and coordination numbers.
     *
     * @param personnummer Personal identity number as a string to create the object from.
     * @throws PersonnummerException On parse error.
     */
    public Personnummer(String personnummer) throws PersonnummerException {
        this(personnummer, new Options());
    }

	@Override
	public String toString() {
		return format();
	}

    /**
     * Format the personal identity number into a valid string (YYMMDD-/+XXXX)
     * If longFormat is true, it will include the century (YYYYMMDD-/+XXXX)
     *
     * @return Formatted personal identity number.
     */
    public String format() {
        return format(false);
    }

    /**
     * Format the personal identity number into a valid string (YYMMDD-/+XXXX)
     * If longFormat is true, it will include the century (YYYYMMDD-/+XXXX)
     *
     * @param longFormat If century should be included.
     * @return Formatted personal identity number.
     */
    public String format(boolean longFormat) {
        return (longFormat ? fullYear : year) + getMonth() + day + (longFormat ? "" : separator()) + numbers;
    }

    /**
     * Validate a Swedish personal identity number.
     *
     * @param personnummer personal identity number to validate, as string.
     * @return True if valid.
     */
    public static boolean valid(String personnummer) {
        try {
            parse(personnummer);
            return true;
        } catch (PersonnummerException ex) {
            return false;
        }
    }

    private static int luhn(String value) {
        // Luhn/mod10 algorithm. Used to calculate a checksum from the
        // passed value. The checksum is returned and tested against the control number
        // in the personal identity number to make sure that it is a valid number.

        int temp;
        int sum = 0;

        for (int i = 0; i < value.length(); i++) {
            temp = Character.getNumericValue(value.charAt(i));
            temp *= 2 - (i % 2);
            if (temp > 9)
                temp -= 9;

            sum += temp;
        }

        return (10 - (sum % 10)) % 10;
    }

	@Override
	public int hashCode() {
		return format(true).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Personnummer other = (Personnummer) obj;
		return Objects.equals(format(true), other.format(true));
	}

	@Override
	public int compareTo(Personnummer other) {
		return format(true).compareTo(other.format(true));
	}

}
