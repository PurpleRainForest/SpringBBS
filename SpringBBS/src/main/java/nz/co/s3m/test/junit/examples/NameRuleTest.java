/* JUnit Examples from javacodegeeks */
package nz.co.s3m.test.junit.examples;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class NameRuleTest {
	@Rule
	public TestName name = new TestName();

	@Test
	public void testA() {
		System.out.println(name.getMethodName());
		assertEquals("testA", name.getMethodName());
	}

	@Test
	public void testB() {
		System.out.println(name.getMethodName());
		assertEquals("testB", name.getMethodName());
	}
}