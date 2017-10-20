import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PersonnummerTest {

    @Test
    public void testVerifySuccess() {
        assertTrue(Personnummer.verify("510818-9167"));
        assertTrue(Personnummer.verify("19900101-0017"));
        assertTrue(Personnummer.verify("19130401+2931"));
        assertTrue(Personnummer.verify("196408233234"));
        assertTrue(Personnummer.verify("0001010107"));
        assertTrue(Personnummer.verify("000101-0107"));
        assertTrue(Personnummer.verify("701063-2391"));
        assertTrue(Personnummer.verify("640883-3231"));

        assertTrue(Personnummer.verify(6403273813L));
        assertTrue(Personnummer.verify(5108189167L));
        assertTrue(Personnummer.verify(199001010017L));
        assertTrue(Personnummer.verify(7010632391L));
        assertTrue(Personnummer.verify(6408833231L));
    }

    @Test
    public void testVerifyFail() {
        assertFalse(Personnummer.verify("640327-381"));
        assertFalse(Personnummer.verify("510818-916"));
        assertFalse(Personnummer.verify("19900101-001"));
        assertFalse(Personnummer.verify("100101+001"));
        assertFalse(Personnummer.verify("900161-0017"));
        assertFalse(Personnummer.verify("640893-3231"));

        assertFalse(Personnummer.verify(640327381L));
        assertFalse(Personnummer.verify(510818916L));
        assertFalse(Personnummer.verify(19900101001L));
        assertFalse(Personnummer.verify(100101001L));
        assertFalse(Personnummer.verify(9001610017L));
        assertFalse(Personnummer.verify(6408933231L));
    }
}
