package dev.personnummer;

public class Options {
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
