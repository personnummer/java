package dev.personnummer;

public class PersonnummerException extends Exception {
    PersonnummerException(String message) {
        super(message);
    }

    PersonnummerException(String message, Throwable cause) {
        super(message, cause);
    }
}
