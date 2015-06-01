/* JUnit Examples from javacodegeeks */
package nz.co.s3m.test.junit.examples;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalculateTest {
	Calculate calculation = new Calculate();
	int sum = calculation.sum(2, 5);
	int testSum = 7;

	@Test
	public void testSum() {
		System.out.println("@Test sum(): " + sum + " = " + testSum);
		assertEquals(sum, testSum);
	}
}