/* JUnit Examples from javacodegeeks */
package nz.co.s3m.test.junit.examples;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class AnnotationsTest {
	private ArrayList testList;

	@BeforeClass
	public static void onceExecutedBeforeAll() {
		System.out.println("@BeforeClass: onceExecutedBeforeAll");
	}

	@Before
	public void executedBeforeEach() {
		testList = new ArrayList();
		System.out.println("@Before: executedBeforeEach");
	}

	@AfterClass
	public static void onceExecutedAfterAll() {
		System.out.println("@AfterClass: onceExecutedAfterAll");
	}

	@After
	public void executedAfterEach() {
		testList.clear();
		System.out.println("@After: executedAfterEach");
	}

	@Test
	public void EmptyCollection() {
		assertTrue(testList.isEmpty());
		System.out.println("@Test: EmptyArrayList");
	}

	@Test
	public void OneItemCollection() {
		testList.add("oneItem");
		assertEquals(1, testList.size());
		System.out.println("@Test: OneItemArrayList");
	}

	@Ignore
	public void executionIgnored() {
		System.out.println("@Ignore: This execution is ignored");
	}
}