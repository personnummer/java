package dev.personnummer;

public final class Options {

    public static final Options DEFAULT = new Options();

    public Options(boolean allowCoordinationNumber) {
        this.allowCoordinationNumber = allowCoordinationNumber;
        this.allowInterimNumbers = false;
    }

    public Options(boolean allowCoordinationNumber, boolean allowInterimNumbers) {
        this.allowCoordinationNumber = allowCoordinationNumber;
        this.allowInterimNumbers = allowInterimNumbers;
    }

    public Options() {
        this.allowInterimNumbers = false;
        this.allowCoordinationNumber = true;
    }

    final boolean allowInterimNumbers;

    final boolean allowCoordinationNumber;
}
