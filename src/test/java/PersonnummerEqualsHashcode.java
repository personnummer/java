import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import dev.personnummer.*;

class PersonnummerEqualsHashcode {

    private static final String YOUNG_LONG_FORMAT = "201701022384";

    @Test
    void testEquals() throws PersonnummerException {

        assertEquals(new Personnummer(YOUNG_LONG_FORMAT), new Personnummer(YOUNG_LONG_FORMAT));
        assertEquals(new Personnummer(YOUNG_LONG_FORMAT), new Personnummer("20170102-2384"));
        assertEquals(new Personnummer(YOUNG_LONG_FORMAT), new Personnummer("20170102+2384"));
        assertEquals(new Personnummer(YOUNG_LONG_FORMAT), new Personnummer("170102-2384"));

        assertNotEquals(new Personnummer("170102+2384"), new Personnummer("170102-2384"));
        assertNotEquals(new Personnummer(YOUNG_LONG_FORMAT), new Personnummer("191701022384"));
        assertNotEquals(new Personnummer("20170102-2384"), new Personnummer("19170102-2384"));
        assertNotEquals(new Personnummer("20170102+2384"), new Personnummer("19170102+2384"));

    }

    @Test
    void testHashCode() throws PersonnummerException {

        Personnummer young = new Personnummer(YOUNG_LONG_FORMAT);
        Personnummer young2 = new Personnummer("1701022384");
        Personnummer other = new Personnummer("194308239088");

        assertEquals(young.hashCode(), young2.hashCode());
        assertNotEquals(young, other);
        assertNotEquals(young2, other);
    }

    @Test
    void testToString() throws PersonnummerException {

        Personnummer young = new Personnummer(YOUNG_LONG_FORMAT);
        assertEquals("170102-2384", young.toString());
        assertEquals(young.format(), young.toString());
    }

    @Test
    void naturalOrderingWorks() throws PersonnummerException {

        List<Personnummer> numbers = new ArrayList<>();

        final Personnummer retiree = new Personnummer("194308239088");
        final Personnummer young = new Personnummer(YOUNG_LONG_FORMAT);
        final Personnummer centenarian = new Personnummer("170102+2384");


        numbers.add(retiree);
        numbers.add(young);
        numbers.add(centenarian);
        numbers.add(retiree);
        assertEquals(4, numbers.size());

        numbers.sort(null);

        assertEquals(centenarian, numbers.get(0));
        assertEquals(retiree, numbers.get(1));
        assertEquals(retiree, numbers.get(2));
        assertEquals(young, numbers.get(3));
    }

    @Test
    void testLongFormat() throws PersonnummerException {

        final Personnummer centenarian = new Personnummer("170102+2384");
        assertEquals("191701022384", centenarian.format(true));
    }


    @Test()
    void disallowNullValuesForOrdering() throws PersonnummerException {

        final Personnummer young = new Personnummer(YOUNG_LONG_FORMAT);

        assertNotEquals(null, young);
        assertThrows(NullPointerException.class, () -> {
            young.compareTo(null);
        });


    }

}
