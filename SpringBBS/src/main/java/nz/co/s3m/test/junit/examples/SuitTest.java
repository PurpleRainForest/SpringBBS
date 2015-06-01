/* JUnit Examples from javacodegeeks */
package nz.co.s3m.test.junit.examples;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PrepareMyBagTest.class, AddPencilsTest.class })
public class SuitTest {
}