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
        Matcher matches = regexPattern.matcher(value);
        if (!matches.find()) {
            return false;
        }

        int year, month, day, control, number;
        try {
            year    = Integer.parseInt(matches.group(2).substring(2));
            month   = Integer.parseInt(matches.group(3));
            day     = Integer.parseInt(matches.group(4));
            number  = Integer.parseInt(matches.group(6));
            control = Integer.parseInt(matches.group(7));
        } catch (NumberFormatException e) {
            return false;
        }

        boolean valid = luhn(String.format("%d%d%d%d", year, month, day, number)) == control;
        return valid && (testDate(year, month, day) || testDate(year, month, day - 60));
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
        return 0;
    }

    private static boolean testDate(int year, int month, int day) {
        return false;
    }

}
