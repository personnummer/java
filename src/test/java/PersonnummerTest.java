import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PersonnummerTest {

    @Test
    public void testVerifySuccess() {
        assertTrue(Personnummer.valid("510818-9167"));
        assertTrue(Personnummer.valid("19900101-0017"));
        assertTrue(Personnummer.valid("19130401+2931"));
        assertTrue(Personnummer.valid("196408233234"));
        assertTrue(Personnummer.valid("0001010107"));
        assertTrue(Personnummer.valid("000101-0107"));
        assertTrue(Personnummer.valid("701063-2391"));
        assertTrue(Personnummer.valid("640883-3231"));

        assertTrue(Personnummer.valid(6403273813L));
        assertTrue(Personnummer.valid(5108189167L));
        assertTrue(Personnummer.valid(199001010017L));
        assertTrue(Personnummer.valid(7010632391L));
        assertTrue(Personnummer.valid(6408833231L));
    }

    @Test
    public void testVerifyFail() {
        assertFalse(Personnummer.valid("640327-381"));
        assertFalse(Personnummer.valid("510818-916"));
        assertFalse(Personnummer.valid("19900101-001"));
        assertFalse(Personnummer.valid("100101+001"));
        assertFalse(Personnummer.valid("900161-0017"));
        assertFalse(Personnummer.valid("640893-3231"));

        assertFalse(Personnummer.valid(640327381L));
        assertFalse(Personnummer.valid(510818916L));
        assertFalse(Personnummer.valid(19900101001L));
        assertFalse(Personnummer.valid(100101001L));
        assertFalse(Personnummer.valid(9001610017L));
        assertFalse(Personnummer.valid(6408933231L));
    }
}
