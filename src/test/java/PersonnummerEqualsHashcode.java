import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dev.personnummer.*;

class PersonnummerEqualsHashcode {

	@Test
	void testEquals() throws PersonnummerException {
		
		assertEquals(new Personnummer("201701022384"), new Personnummer("201701022384"));
		assertEquals(new Personnummer("201701022384"), new Personnummer("20170102-2384"));
		assertEquals(new Personnummer("201701022384"), new Personnummer("20170102+2384"));
		assertEquals(new Personnummer("201701022384"), new Personnummer("170102-2384"));
		
		assertNotEquals(new Personnummer("170102+2384"), new Personnummer("170102-2384"));
		assertNotEquals(new Personnummer("201701022384"), new Personnummer("191701022384"));
		assertNotEquals(new Personnummer("20170102-2384"), new Personnummer("19170102-2384"));
		assertNotEquals(new Personnummer("20170102+2384"), new Personnummer("19170102+2384"));
		
	}
	
	@Test
	void testHashCode() throws PersonnummerException {
		
		Personnummer young = new Personnummer("201701022384");
		Personnummer young2 = new Personnummer("1701022384");
		Personnummer other = new Personnummer("194308239088");
		
		assertEquals(young.hashCode(), young2.hashCode());
		assertNotEquals(young, other);
		assertNotEquals(young2, other);
	}
	
}