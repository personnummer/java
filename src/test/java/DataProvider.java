import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProvider {
    private static final List<PersonnummerData> all = new ArrayList<>();
    private static final List<PersonnummerData> orgNr = new ArrayList<>();
    private static final List<PersonnummerData> interimNr = new ArrayList<>();

    public static void initialize() throws IOException {
        InputStream in = new URL("https://raw.githubusercontent.com/personnummer/meta/master/testdata/list.json").openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String json = "";
        String line;
        while ((line = reader.readLine()) != null) {
            json = json.concat(line);
        }
        in.close();
        JSONArray rootObject = new JSONArray(json);
        for (int i = 0; i < rootObject.length(); i++) {
            JSONObject current = rootObject.getJSONObject(i);
            all.add(new PersonnummerData(
                    current.getLong("integer"),
                    current.getString("long_format"),
                    current.getString("short_format"),
                    current.getString("separated_format"),
                    current.getString("separated_long"),
                    current.getBoolean("valid"),
                    current.getString("type"),
                    current.getBoolean("isMale"),
                    current.getBoolean("isFemale")
            ));
        }

        in = new URL("https://raw.githubusercontent.com/personnummer/meta/master/testdata/orgnumber.json").openStream();
        reader = new BufferedReader(new InputStreamReader(in));
        json = "";
        while ((line = reader.readLine()) != null) {
            json = json.concat(line);
        }

        in.close();
        rootObject = new JSONArray(json);
        for (int i = 0; i < rootObject.length(); i++) {
            JSONObject current = rootObject.getJSONObject(i);
            orgNr.add(new PersonnummerData(
                    current.getLong("integer"),
                    current.getString("short_format"),
                    current.getString("separated_format"),
                    current.getBoolean("valid"),
                    current.getString("type")
            ));
        }


        in = new URL("https://raw.githubusercontent.com/personnummer/meta/master/testdata/interim.json").openStream();
        reader = new BufferedReader(new InputStreamReader(in));
        json = "";
        while ((line = reader.readLine()) != null) {
            json = json.concat(line);
        }

        in.close();
        rootObject = new JSONArray(json);
        for (int i = 0; i < rootObject.length(); i++) {
            JSONObject current = rootObject.getJSONObject(i);
            interimNr.add(new PersonnummerData(
                    current.getLong("integer"),
                    current.getString("long_format"),
                    current.getString("short_format"),
                    current.getString("separated_format"),
                    current.getString("separated_long"),
                    current.getBoolean("valid"),
                    current.getString("type"),
                    false, // ignore
                    false // ignore
                )
            );
        }
    }

    public static List<PersonnummerData> getInterimNumbers() {
        return interimNr;
    }
    public static List<PersonnummerData> getValidInterimNumbers() {
        return interimNr.stream().filter(o -> o.valid).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getInvalidInterimNumbers() {
        return interimNr.stream().filter(o -> !o.valid).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getCoordinationNumbers() {
        return all.stream().filter(o -> !o.type.equals("ssn")).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getPersonnummer() {
        return all.stream().filter(o -> o.type.equals("ssn")).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getInvalidCoordinationNumbers() {
        return getCoordinationNumbers().stream().filter(o -> !o.valid).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getInvalidPersonnummer() {
        return getPersonnummer().stream().filter(o -> !o.valid).collect(Collectors.toList());
    }
    
    public static List<PersonnummerData> getDate() {
        return all.stream().filter(o -> o.valid).collect(Collectors.toList());
    }
    
    public static List<PersonnummerData> getValidCoordinationNumbers() {
        return getCoordinationNumbers().stream().filter(o -> o.valid).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getValidPersonnummer() {
        return getPersonnummer().stream().filter(o -> o.valid).collect(Collectors.toList());
    }
    public static List<PersonnummerData> getOrganisationsnummer() {
        return orgNr;
    }

}
