import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonnummerTest {

    @Test
    public void testWithControlDigit() {
        assertTrue(Personnummer.valid("6403273813"));
        assertTrue(Personnummer.valid("510818-9167"));
        assertTrue(Personnummer.valid("19900101-0017"));
        assertTrue(Personnummer.valid("19130401+2931"));
        assertTrue(Personnummer.valid("196408233234"));
        assertTrue(Personnummer.valid("0001010107"));
        assertTrue(Personnummer.valid("000101-0107"));
        assertTrue(Personnummer.valid("1010101010"));
    }

    @Test
    public void testWithoutControlDigit() {
        assertFalse(Personnummer.valid("640327-381"));
        assertFalse(Personnummer.valid("510818-916"));
        assertFalse(Personnummer.valid("19900101-001"));
        assertFalse(Personnummer.valid("100101+001"));
    }

    @Test
    public void testWithInvalidValues() {
        assertFalse(Personnummer.valid("A string"));
        assertFalse(Personnummer.valid("Two"));
        assertFalse(Personnummer.valid("222"));
        assertFalse(Personnummer.valid(null));
        assertFalse(Personnummer.valid("9701063-2391"));
    }

    @Test
    public void testCoordinationNumbers() {
        assertTrue(Personnummer.valid("701063-2391"));
        assertTrue(Personnummer.valid("640883-3231"));
    }

    @Test
    public void testWithBadCoordinationNumbers() {
        assertFalse(Personnummer.valid("900161-0017"));
        assertFalse(Personnummer.valid("640893-3231"));
    }

}
