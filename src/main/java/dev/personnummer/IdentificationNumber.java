package dev.personnummer;

public interface IdentificationNumber {
	String format();

	String format(boolean longFormat);
}
