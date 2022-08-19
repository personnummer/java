package dev.personnummer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to validate Swedish personal identity numbers.
 *
 * @author Johannes Tegnér
 */
public final class OrganizationNummer implements Comparable<OrganizationNummer>, IdentificationNumber {
    private static final Pattern regexPattern;

    static {
        regexPattern = Pattern.compile("^(16)?(\\d)(\\d[2-9]\\d\\d{2})([-+]?)?((?!000)\\d{3})(\\d?)$");
    }

    private final String type;
    private final String date;

    /**
     * Create a new Personnummber object from a string.
     * In case options is not passed, they will default to accept any personal and coordination numbers.
     *
     * @param personnummer Personal identity number as a string to create the object from.
     * @return Personnummer
     * @throws PersonnummerException On parse error.
     */
    public static OrganizationNummer parse(String personnummer) throws PersonnummerException {
        return new OrganizationNummer(personnummer);
    }

    private final String numbers;
    private final String controlNumber;

    public String getNumbers() {
        return numbers;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    /**
     * Create a new Personnummber object from a string.
     * In case options is not passed, they will default to accept any personal and coordination numbers.
     *
     * @param orgnr Personal identity number as a string to create the object from.
     * @throws PersonnummerException On parse error.
     */
    public OrganizationNummer(String orgnr) throws PersonnummerException {
        if (orgnr == null) {
            throw new PersonnummerException("Failed to parse personal identity number. Invalid input.");
        }

        Matcher matches = regexPattern.matcher(orgnr);
        if (!matches.find()) {
            throw new PersonnummerException("Failed to parse organization number. Invalid input.");
        }

        this.type = matches.group(2);
        this.date = type + matches.group(3);
        this.controlNumber = matches.group(6);
        String numbersWithoutControl = matches.group(5);
        this.numbers = numbersWithoutControl + controlNumber;
        // The format passed to Luhn method is supposed to be YYmmDDNNN
        // Hence all numbers that are less than 10 (or in last case 100) will have leading 0's added.
        if (Luhn.luhn(String.format("%s%s", date, numbersWithoutControl)) != Integer.parseInt(this.controlNumber)) {
            throw new PersonnummerException("Invalid organization number.");
        }
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
        return (longFormat ? "16" + date : date) + (longFormat ? "" : "-") + numbers;
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
        OrganizationNummer other = (OrganizationNummer) obj;
        return Objects.equals(format(true), other.format(true));
    }

    @Override
    public int compareTo(OrganizationNummer other) {
        return format(true).compareTo(other.format(true));
    }

}
