import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersonnummerTest {
    //region Static setup stuff!
    private static Boolean fileLoaded = false;

    public static List<Long> getValidSsnInt() {
        return validSsnInt;
    }

    public static List<String> getValidSsnString() {
        return validSsnString;
    }

    public static List<Long> getInvalidSsnInt() {
        return invalidSsnInt;
    }

    public static List<String> getInvalidSsnString() {
        return invalidSsnString;
    }

    public static List<Long> getValidConInt() {
        return validConInt;
    }

    public static List<String> getValidConString() {
        return validConString;
    }

    public static List<Long> getInvalidConInt() {
        return invalidConInt;
    }

    public static List<String> getInvalidConString() {
        return invalidConString;
    }

    private static List<Long> validSsnInt = new ArrayList<>();
    private static List<String> validSsnString = new ArrayList<>();
    private static List<Long> invalidSsnInt = new ArrayList<>();
    private static List<String> invalidSsnString = new ArrayList<>();
    private static List<Long> validConInt = new ArrayList<>();
    private static List<String> validConString = new ArrayList<>();
    private static List<Long> invalidConInt = new ArrayList<>();
    private static List<String> invalidConString = new ArrayList<>();

    @AfterAll
    public static void deleteTestData() throws IOException {
        Files.delete(Paths.get("temp.json"));
    }

    @BeforeAll
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

        validSsnInt = getIntList(ssn, "valid");
        invalidSsnInt = getIntList(ssn, "invalid");
        validSsnString = getStringList(ssn, "valid");
        invalidSsnString = getStringList(ssn, "invalid");

        validConInt = getIntList(con, "valid");
        invalidConInt = getIntList(con, "invalid");
        validConString = getStringList(con, "valid");
        invalidConString = getStringList(con, "invalid");
    }

    //endregion

    private static ArrayList<String> getStringList(JSONObject root, String valid) {
        JSONArray arr = root.getJSONObject("string").getJSONArray(valid);
        ArrayList<String> result = new ArrayList<>();
        for (int i=0; i<arr.length(); i++) {
            result.add(arr.getString(i));
        }
        return result;
    }

    private static ArrayList<Long> getIntList(JSONObject root, String valid) {
        JSONArray arr = root.getJSONObject("integer").getJSONArray(valid);
        ArrayList<Long> result = new ArrayList<>();
        for (int i=0; i<arr.length(); i++) {
            result.add(arr.getLong(i));
        }
        return result;
    }

    @ParameterizedTest
    @MethodSource({"getInvalidSsnInt", "getInvalidConInt"})
    public void testInvalidIntegerValues(long ssn) {
        assertFalse(Personnummer.valid(ssn));
    }

    @ParameterizedTest
    @MethodSource({"getInvalidConString", "getInvalidSsnString"})
    public void testInvalidStringValues(String ssn) {
        assertFalse(Personnummer.valid(ssn));
    }

    @ParameterizedTest
    @MethodSource({"getValidConInt", "getValidSsnInt"})
    public void testValidIntegerValues(Long ssn) {
        assertTrue(Personnummer.valid(ssn));
    }


    @ParameterizedTest
    @MethodSource({"getValidConString", "getValidSsnString"})
    public void testValidStringValues(String ssn) {
        assertTrue(Personnummer.valid(ssn));
    }

}
