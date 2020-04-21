import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to validate Swedish personal identity numbers.
 *
 * @author Johannes Tegnér
 */
public final class Personnummer {
    private static final Pattern regexPattern;

    static {
        regexPattern = Pattern.compile("^(\\d{2})?(\\d{2})(\\d{2})(\\d{2})([-|+]?)?(\\d{3})(\\d?)$");
    }

    private Personnummer() {
    	throw new AssertionError("Class cannot be instantiated");
    }

    /**
     * Validate a Swedish personal identity number.
     *
     * @param value personal identity number to validate, as string.
     * @return True if valid.
     */
    public static boolean valid(String value) {
        if (value == null) {
            return false;
        }

        Matcher matches = regexPattern.matcher(value);
        if (!matches.find()) {
            return false;
        }

        int year, month, day, control, number;
        try {
            String y = matches.group(2);
            year     = Integer.parseInt((y.length() == 4 ? y.substring(2) : y));
            month    = Integer.parseInt(matches.group(3));
            day      = Integer.parseInt(matches.group(4));
            control  = Integer.parseInt(matches.group(7));
            number   = Integer.parseInt(matches.group(6));
        } catch (NumberFormatException e) {
            return false;
        }

        // The format passed to Luhn method is supposed to be YYmmDDNNN
        // Hence all numbers that are less than 10 (or in last case 100) will have leading 0's added.
        int luhn = luhn(String.format("%02d%02d%02d%03d0", year, month, day, number));
        return (luhn == control) && (testDate(year, month, day) || testDate(year, month, day - 60));
    }

    /**
     * Validate a Swedish personal identity number.
     *
     * @param value personal identity number to validate, as long.
     * @return True if valid.
     */
    public static boolean valid(long value) {
        return valid(Long.toString(value));
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

        return (int)(Math.ceil((double)sum / 10.0) * 10.0 - (double)sum);
    }

    private static boolean testDate(int year, int month, int day) {
        try {
            DateFormat df = new SimpleDateFormat("yy-MM-dd");
            df.setLenient(false);
            df.parse(String.format("%02d-%02d-%02d", year, month, day));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
