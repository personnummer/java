import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to validate Swedish social security numbers.
 *
 * @author Johannes Tegnér
 */
public final class Personnummer {
    private static final Pattern regexPattern;

    static {
        regexPattern = Pattern.compile("(\\d{2}){0,1}(\\d{2})(\\d{2})(\\d{2})([-|+]{0,1})?(\\d{3})(\\d{0,1})");
    }

    /**
     * Validate a Swedish social security number.
     *
     * @param value Social security number to validate, as string.
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
     * Validate a Swedish social security number.
     *
     * @param value Social security number to validate, as long.
     * @return True if valid.
     */
    public static boolean valid(long value) {
        return valid(Long.toString(value));
    }

    private static int luhn(String value) {
        // Luhn/mod10 algorithm. Used to calculate a checksum from the
        // passed value. The checksum is returned and tested against the control number
        // in the social security number to make sure that it is a valid number.

        int sum = 0;
        int temp;

        for (int i=value.length();i-->0;) {
            temp = value.charAt(i) - 48;
            sum += (i % 2 == 0) ? ((temp *= 2) > 9 ? temp - 9 : temp) : temp;
        }

        if (sum != 0) {
            sum = 10 - (sum % 10);
        }

        return sum;
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
