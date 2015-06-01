/* JUnit Examples from javacodegeeks */
package nz.co.s3m.test.junit.examples;

import org.junit.Test;
import static org.junit.Assert.*;

public class AddPencilsTest {
	FirstDayAtSchool school = new FirstDayAtSchool();
	String[] bag = { "Books", "Notebooks", "Pens", "Pencils" };

	@Test
	public void testAddPencils() {
		System.out.println("Inside testAddPencils()");
		assertArrayEquals(bag, school.addPencils());
	}
}