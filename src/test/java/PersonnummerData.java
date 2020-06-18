public class PersonnummerData {

    public PersonnummerData(Long integer, String longFormat, String shortFormat, String separatedFormat, String separatedLong, Boolean valid, String type, Boolean isMale, Boolean isFemale) {
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

    public Long integer;
    public String longFormat;
    public String shortFormat;
    public String separatedFormat;
    public String separatedLong;
    public Boolean valid;
    public String type;
    public Boolean isMale;
    public boolean isFemale;

}
