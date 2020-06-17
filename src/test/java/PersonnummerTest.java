import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//@RunWith(Parameterized.class)
public class PersonnummerTest {
    private static Boolean fileLoaded = false;

    private static List<Long> validSsnInt = new ArrayList<>();
    private static List<String> validSsnString = new ArrayList<>();
    private static List<Long> invalidSsnInt = new ArrayList<>();
    private static List<String> invalidSsnString = new ArrayList<>();
    private static List<Long> validConInt = new ArrayList<>();
    private static List<String> validConString = new ArrayList<>();
    private static List<Long> invalidConInt = new ArrayList<>();
    private static List<String> invalidConString = new ArrayList<>();


    @BeforeClass
    public static void loadTestData() throws IOException {
        if (fileLoaded) {
            return;
        }

        if (!Files.exists(Paths.get("temp.json"))) {
            InputStream in = new URL("https://raw.githubusercontent.com/personnummer/meta/master/testdata/structured.json").openStream();
            Files.copy(in, Paths.get("temp.json"), StandardCopyOption.REPLACE_EXISTING);
            fileLoaded = true;
        }

        String jsonString = new String(Files.readAllBytes(Paths.get("temp.json")));
        JSONObject json = new JSONObject(jsonString);

        JSONObject ssn = json.getJSONObject("ssn");
        JSONObject con = json.getJSONObject("con");

        validSsnInt = getIntList(ssn, "integer", "valid");
        invalidSsnInt = getIntList(ssn, "integer", "invalid");
        validSsnString = getStringList(ssn, "string", "valid");
        invalidSsnString = getStringList(ssn, "string", "invalid");

        validConInt = getIntList(con, "integer", "valid");
        invalidConInt = getIntList(con, "integer", "invalid");
        validConString = getStringList(con, "string", "valid");
        invalidConString = getStringList(con, "string", "invalid");
    }

    private static ArrayList<String> getStringList(JSONObject root, String dataType, String valid) {
        JSONArray arr = root.getJSONObject(dataType).getJSONArray(valid);
        ArrayList<String> result = new ArrayList<>();
        for (int i=0; i<arr.length(); i++) {
            result.add(arr.getString(i));
        }
        return result;
    }

    private static ArrayList<Long> getIntList(JSONObject root, String dataType, String valid) {
        JSONArray arr = root.getJSONObject(dataType).getJSONArray(valid);
        ArrayList<Long> result = new ArrayList<>();
        for (int i=0; i<arr.length(); i++) {
            result.add(arr.getLong(i));
        }
        return result;
    }

    @Test
    public void testPersonnNummerWithInvalidIntegerValues() {
        for (Long ssn: invalidSsnInt) {
            assertFalse(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testCoordinationNummerWithInvalidIntegerValues() {
        for (Long ssn: invalidConInt) {
            assertFalse(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testPersonnNummerWithInvalidStringValues() {
        for (String ssn: invalidSsnString) {
            assertFalse(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testCoordinationNummerWithInvalidStringValues() {
        for (String ssn: invalidConString) {
            assertFalse(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testPersonnNummerWithValidIntegerValues() {
        for (Long ssn: validSsnInt) {
            assertTrue(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testCoordinationNummerVnvalidIntegerValues() {
        for (Long ssn: validConInt) {
            assertTrue(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testPersonnNummerWithValidStringValues() {
        for (String ssn: validSsnString) {
            assertTrue(Personnummer.valid(ssn));
        }
    }

    @Test
    public void testCoordinationNummerWithValidStringValues() {
        for (String ssn: validConString) {
            assertTrue(Personnummer.valid(ssn));
        }
    }

}
