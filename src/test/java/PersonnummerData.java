public class PersonnummerData {

    public PersonnummerData(long integer, String longFormat, String shortFormat, String separatedFormat, String separatedLong, boolean valid, String type, boolean isMale, boolean isFemale) {
        this.integer = integer;
        this.longFormat = longFormat;
        this.shortFormat = shortFormat;
        this.separatedFormat = separatedFormat;
        this.separatedLong = separatedLong;
        this.valid = valid;
        this.type = type;
        this.isMale = isMale;
        this.isFemale = isFemale;
    }

    public PersonnummerData(long integer, String shortFormat, String separatedFormat, boolean valid, String type) {
        this.integer = integer;
        this.shortFormat = shortFormat;
        this.separatedFormat = separatedFormat;
        this.valid = valid;
        this.type = type;
    }

    public long integer;
    public String longFormat;
    public String shortFormat;
    public String separatedFormat;
    public String separatedLong;
    public boolean valid;
    public String type;
    public boolean isMale;
    public boolean isFemale;

}
